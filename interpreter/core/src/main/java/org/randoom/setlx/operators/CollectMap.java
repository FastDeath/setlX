package org.randoom.setlx.operators;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.UnknownFunctionException;
import org.randoom.setlx.operatorUtilities.OperatorExpression;
import org.randoom.setlx.operatorUtilities.OperatorExpression.OptimizerData;
import org.randoom.setlx.operatorUtilities.Stack;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.State;

import java.util.List;

/**
 * Operator that collects specific elements of a collection value and puts the result on the stack.
 */
public class CollectMap extends AUnaryPostfixOperator {
    private final OperatorExpression argument;

    /**
     * Create a new call operator.
     *
     * @param argument    Parameter to the call.
     */
    public CollectMap(OperatorExpression argument) {
        this.argument = unify(argument);
    }

    @Override
    public OptimizerData collectVariablesAndOptimize(State state, List<String> boundVariables, List<String> unboundVariables, List<String> usedVariables, OptimizerData lhs) {
        argument.collectVariablesAndOptimize(state, boundVariables, unboundVariables, usedVariables);
        return new OptimizerData(
                false
        );
    }

    @Override
    public Value evaluate(State state, Stack<Value> values) throws SetlException {
        final Value lhs = values.poll();
        if (lhs == Om.OM) {
            throw new UnknownFunctionException(
                    "Left hand side is undefined (om)."
            );
        }
        return lhs.collectMap(state, argument.evaluate(state).clone());
    }

    @Override
    public void appendOperatorSign(State state, StringBuilder sb) {
        sb.append("{");

        argument.appendString(state, sb, 0);

        sb.append("}");
    }

    @Override
    public boolean isLeftAssociative() {
        return false;
    }

    @Override
    public boolean isRightAssociative() {
        return false;
    }

    @Override
    public int precedence() {
        return 2100;
    }

    @Override
    public Value modifyTerm(State state, Term term) throws SetlException {
        term.addMember(state, argument.toTerm(state));
        return term;
    }

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(CollectMap.class);

    @Override
    public int compareTo(CodeFragment other) {
        if (this == other) {
            return 0;
        } else if (other.getClass() == CollectMap.class) {
            final CollectMap otr = (CollectMap) other;
            if (argument == otr.argument) {
                return 0; // clone
            }
            return argument.compareTo(otr.argument);
        } else {
            return (this.compareToOrdering() < other.compareToOrdering())? -1 : 1;
        }
    }

    @Override
    public long compareToOrdering() {
        return COMPARE_TO_ORDER_CONSTANT;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj.getClass() == CollectMap.class) {
            return argument.equals(((CollectMap) obj).argument);
        }
        return false;
    }

    @Override
    public int computeHashCode() {
        return ((int) COMPARE_TO_ORDER_CONSTANT) + argument.hashCode();
    }
}
