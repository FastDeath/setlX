package org.randoom.setlx.expressions;

import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Term;

public class LiteralConstructor extends Expr {
    // functional character used in terms (MUST be class name starting with lower case letter!)
    private final static String FUNCTIONAL_CHARACTER = "^literalConstructor";
    // precedence level in SetlX-grammar
    private final static int    PRECEDENCE           = 9999;

    private String     mOriginalLiteral;
    private SetlString mRuntimeString;

    public LiteralConstructor(final String originalLiteral) {
        this(originalLiteral, SetlString.newLiteral(originalLiteral));
    }

    private LiteralConstructor(final String originalLiteral, final SetlString runtimeString) {
        mOriginalLiteral = originalLiteral;
        mRuntimeString   = runtimeString;
    }

    public SetlString eval() {
        return mRuntimeString;
    }

    protected SetlString evaluate() {
        return mRuntimeString;
    }

    /* string operations */

    public void appendString(final StringBuilder sb, final int tabs) {
        sb.append(mOriginalLiteral);
    }

    /* term operations */

    public Term toTerm() {
        final Term result  = new Term(FUNCTIONAL_CHARACTER, 1);

        result.addMember(mRuntimeString);

        return result;
    }

    public static LiteralConstructor termToExpr(final Term term) throws TermConversionException {
        if (term.size() != 1 || ! (term.firstMember() instanceof SetlString)) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            final SetlString runtimeString   = (SetlString) term.firstMember();
            final String     originalLiteral = "'" + runtimeString.getUnquotedString() + "'";
            return new LiteralConstructor(originalLiteral, runtimeString);
        }
    }

    // precedence level in SetlX-grammar
    public int precedence() {
        return PRECEDENCE;
    }
}
