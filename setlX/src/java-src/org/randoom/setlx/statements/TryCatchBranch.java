package org.randoom.setlx.statements;

import org.randoom.setlx.exceptions.CatchableInSetlXException;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.exceptions.ThrownInSetlXException;
import org.randoom.setlx.expressions.Variable;
import org.randoom.setlx.types.SetlError;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.utilities.TermConverter;

/*

This catch block catches any exception, which is catchable in SetlX.

grammar rule:
statement
    : [...]
    | 'try' '{' block '}' ('catch' '(' variable ')' '{' block '}' | 'catchLng' '(' variable ')' '{' block '}' | 'catchUsr' '(' variable ')' '{' block '}')+
    ;

implemented here as:
                                       ========         =====
                                       mErrorVar   mBlockToRecover
*/

public class TryCatchBranch extends TryCatchAbstractBranch {
    // functional character used in terms
    /*package*/ final static String FUNCTIONAL_CHARACTER = "^tryCatchBranch";

    private Variable                    mErrorVar;
    private Block                       mBlockToRecover;
    private CatchableInSetlXException   mException;      // last catched exception

    public TryCatchBranch(Variable errorVar, Block blockToRecover){
        mErrorVar       = errorVar;
        mBlockToRecover = blockToRecover;
    }

    public boolean catches(CatchableInSetlXException cise) {
        // store exception
        mException = cise;
        return true;
    }

    public void execute() throws SetlException {
        if (mException instanceof ThrownInSetlXException) {
            // assign directly
            mErrorVar.assign( ((ThrownInSetlXException) mException).getValue() );
        } else {
            // wrap into error
            mErrorVar.assign(new SetlError(mException));
        }
        // remove stored exception
        mException = null;
        // execute
        mBlockToRecover.execute();
    }

    protected void exec() throws SetlException {
        execute();
    }

    /* string operations */

    public String toString(int tabs) {
        String result = " catch (" + mErrorVar.toString(tabs) + ") ";
        result += mBlockToRecover.toString(tabs, true);
        return result;
    }

    /* term operations */

    public Term toTerm() {
        Term    result  = new Term(FUNCTIONAL_CHARACTER);
        result.addMember(mErrorVar.toTerm());
        result.addMember(mBlockToRecover.toTerm());
        return result;
    }

    public static TryCatchBranch termToBranch(Term term) throws TermConversionException {
        if (term.size() != 2 || ! (term.firstMember() instanceof Term)) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            Variable    var     = Variable.termToExpr((Term) term.firstMember());
            Block       block   = TermConverter.valueToBlock(term.lastMember());
            return new TryCatchBranch(var, block);
        }
    }
}
