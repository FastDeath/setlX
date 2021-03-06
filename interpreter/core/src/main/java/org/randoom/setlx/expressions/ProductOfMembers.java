package org.randoom.setlx.expressions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.TermConverter;

import java.util.List;

/**
 * This class implements the expression representing the ProductOfMembers operator.
 */
///  grammar rule:
///  prefixOperation
///      : '*/' factor
///      | [...]
///      ;
///
///  implemented here as:
///             ======
///             mExpr
///
public class ProductOfMembers extends UnaryExpression {
    // functional character used in terms
    private final static String FUNCTIONAL_CHARACTER = generateFunctionalCharacter(ProductOfMembers.class);
    // precedence level in SetlX-grammar
    private final static int    PRECEDENCE           = 1900;

    /**
     * Constructor.
     *
     * @param expr Expression to evaluate to a CollectionValue.
     */
    public ProductOfMembers(final Expr expr) {
        super(expr);
    }

    @Override
    protected Value evaluate(final State state) throws SetlException {
        return expr.eval(state).productOfMembers(state, Om.OM);
    }

    /* string operations */

    @Override
    public void appendString(final State state, final StringBuilder sb, final int tabs) {
        sb.append("*/");
        expr.appendBracketedExpr(state, sb, tabs, PRECEDENCE, false);
    }

    /* term operations */

    @Override
    public String getFunctionalCharacter() {
        return FUNCTIONAL_CHARACTER;
    }

    /**
     * Convert a term representing a ProductOfMembers expression into such an expression.
     *
     * @param state                    Current state of the running setlX program.
     * @param term                     Term to convert.
     * @return                         Resulting expression of this conversion.
     * @throws TermConversionException If term is malformed.
     */
    public static ProductOfMembers termToExpr(final State state, final Term term) throws TermConversionException {
        if (term.size() != 1) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            final Expr expr = TermConverter.valueToExpr(state, term.firstMember());
            return new ProductOfMembers(expr);
        }
    }

    /* comparisons */

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(ProductOfMembers.class);

    @Override
    public long compareToOrdering() {
        return COMPARE_TO_ORDER_CONSTANT;
    }

    @Override
    public int precedence() {
        return PRECEDENCE;
    }
}

