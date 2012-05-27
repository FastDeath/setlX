package org.randoom.setlx.boolExpressions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.expressions.Expr;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.utilities.TermConverter;

/*
grammar rule:
comparison
    : expr '==' expr
    ;

implemented here as:
      ====      ====
      mLhs      mRhs
*/

public class Equal extends Expr {
    // functional character used in terms
    private final static String FUNCTIONAL_CHARACTER = "^equal";
    // precedence level in SetlX-grammar
    private final static int    PRECEDENCE           = 1500;

    private Expr mLhs;
    private Expr mRhs;

    public Equal(Expr lhs, Expr rhs) {
        mLhs = lhs;
        mRhs = rhs;
    }

    protected SetlBoolean evaluate() throws SetlException {
        return mLhs.eval().isEqual(mRhs.eval());
    }

    /* string operations */

    public String toString(int tabs) {
        return mLhs.toString(tabs) + " == " + mRhs.toString(tabs);
    }

    /* term operations */

    public Term toTerm() {
        Term result = new Term(FUNCTIONAL_CHARACTER);
        result.addMember(mLhs.toTerm());
        result.addMember(mRhs.toTerm());
        return result;
    }

    public static Equal termToExpr(Term term) throws TermConversionException {
        if (term.size() != 2) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            Expr lhs = TermConverter.valueToExpr(PRECEDENCE, false, term.firstMember());
            Expr rhs = TermConverter.valueToExpr(PRECEDENCE, true , term.lastMember());
            return new Equal(lhs, rhs);
        }
    }

    // precedence level in SetlX-grammar
    public int precedence() {
        return PRECEDENCE;
    }
}
