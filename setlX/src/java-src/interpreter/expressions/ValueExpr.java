package interpreter.expressions;

import interpreter.exceptions.SetlException;
import interpreter.types.Term;
import interpreter.types.Value;

// this class wraps values into an expression

public class ValueExpr extends Expr {
    private Value mValue;

    public ValueExpr(Value value) {
        mValue = value;
    }

    public Value eval() {
        return mValue;
    }

    public Value evaluate() {
        return eval();
    }

    /* string operations */

    public String toString(int tabs) {
        return mValue.toString(tabs);
    }

    /* term operations */

    public Value toTerm() throws SetlException {
        return mValue.toTerm();
    }
}
