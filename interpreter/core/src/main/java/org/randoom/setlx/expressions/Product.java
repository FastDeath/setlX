package org.randoom.setlx.expressions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.TermConverter;

import java.util.List;

/**
 * Implementation of the * operator.
 *
 * grammar rule:
 * product
 *     : reduce ('*' reduce | [...])*
 *     ;
 *
 * implemented here as:
 *       ======      ======
 *        lhs         rhs
 */
public class Product extends LeftAssociativeBinaryExpression {
    // functional character used in terms
    private final static String FUNCTIONAL_CHARACTER = generateFunctionalCharacter(Product.class);
    // precedence level in SetlX-grammar
    private final static int    PRECEDENCE           = 1700;

    /**
     * Constructor.
     *
     * @param lhs Left hand side of the operator.
     * @param rhs Right hand side of the operator.
     */
    public Product(final Expr lhs, final Expr rhs) {
        super(lhs, rhs);
    }

    @Override
    protected Value evaluate(final State state) throws SetlException {
        return lhs.eval(state).product(state, rhs.eval(state));
    }

    /* string operations */

    @Override
    public void appendOperator(final StringBuilder sb) {
        sb.append(" * ");
    }

    /* term operations */

    @Override
    public String getFunctionalCharacter() {
        return FUNCTIONAL_CHARACTER;
    }

    /**
     * Convert a term representing a Product expression into such an expression.
     *
     * @param state                    Current state of the running setlX program.
     * @param term                     Term to convert.
     * @return                         Resulting expression of this conversion.
     * @throws TermConversionException If term is malformed.
     */
    public static Product termToExpr(final State state, final Term term) throws TermConversionException {
        if (term.size() != 2) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            final Expr lhs = TermConverter.valueToExpr(state, term.firstMember());
            final Expr rhs = TermConverter.valueToExpr(state, term.lastMember());
            return new Product(lhs, rhs);
        }
    }

    /* comparisons */

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(Product.class);

    @Override
    public long compareToOrdering() {
        return COMPARE_TO_ORDER_CONSTANT;
    }

    @Override
    public int precedence() {
        return PRECEDENCE;
    }

    /**
     * Get the functional character used in terms.
     *
     * @return functional character used in terms.
     */
    public static String functionalCharacter() {
        return FUNCTIONAL_CHARACTER;
    }
}

