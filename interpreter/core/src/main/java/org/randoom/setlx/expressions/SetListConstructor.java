package org.randoom.setlx.expressions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.expressionUtilities.CollectionBuilder;
import org.randoom.setlx.types.CollectionValue;
import org.randoom.setlx.types.SetlList;
import org.randoom.setlx.types.SetlSet;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.State;
import java.util.List;

/**
 * Implementation of expressions creating sets or lists.
 *
 * grammar rules:
 * list
 *     : '[' collectionBuilder? ']'
 *     ;
 *
 * set
 *     : '{' collectionBuilder? '}'
 *     ;
 *
 * implemented here as:
 * ====      ============
 * type         builder
 */
public class SetListConstructor extends Expr {
    /**
     * Type of collection to construct.
     */
    public enum CollectionType {
        /**
         * Construct a SetList.
         */
        LIST,
        /**
         * Construct a SetlSet.
         */
        SET
    }
    // precedence level in SetlX-grammar
    private final static int        PRECEDENCE  = 9999;

    private final CollectionType    type;
    private final CollectionBuilder builder;

    /**
     * Create a new SetListConstructor expression.
     *
     * @param type        Type of collection to construct.
     * @param constructor Collection contents generation object.
     */
    public SetListConstructor(final CollectionType type, final CollectionBuilder constructor) {
        this.type    = type;
        this.builder = unify(constructor);
    }

    @Override
    protected Value evaluate(final State state) throws SetlException {
        if (type == CollectionType.SET) {
            final SetlSet set = new SetlSet();
            if (builder != null) {
                builder.fillCollection(state, set);
            }
            return set;
        } else /* if (mType == LIST) */ {
            final SetlList list = new SetlList();
            if (builder != null) {
                builder.fillCollection(state, list);
            }
            list.compress();
            return list;
        }
    }

    @Override
    protected void collectVariables (
        final State        state,
        final List<String> boundVariables,
        final List<String> unboundVariables,
        final List<String> usedVariables
    ) {
        if (builder != null) {
            builder.collectVariablesAndOptimize(state, boundVariables, unboundVariables, usedVariables);
        }
    }

    /* string operations */

    @Override
    public void appendString(final State state, final StringBuilder sb, final int tabs) {
        if (type == CollectionType.SET) {
            sb.append("{");
        } else /* if (mType == LIST) */ {
            sb.append("[");
        }
        if (builder != null) {
            builder.appendString(state, sb);
        }
        if (type == CollectionType.SET) {
            sb.append("}");
        } else /* if (mType == LIST) */ {
            sb.append("]");
        }
    }

    /* term operations */

    @Override
    public Value toTerm(final State state) throws SetlException {
        final CollectionValue result;
        if (type == CollectionType.SET) {
            result = new SetlSet();
        } else /* if (type == CollectionType.LIST) */ {
            result = new SetlList();
        }
        if (builder != null) {
            builder.addToTerm(state, result);
        }
        return result;
    }

    /**
     * Convert a term representing a StringConstructor into such an expression.
     *
     * @param state                    Current state of the running setlX program.
     * @param value                    Term to convert.
     * @return                         Resulting StringConstructor Expression.
     * @throws TermConversionException Thrown in case of an malformed term.
     */
    public static SetListConstructor valueToExpr(final State state, final Value value) throws TermConversionException {
        if ( ! (value instanceof SetlList || value instanceof SetlSet)) {
            throw new TermConversionException("not a collectionValue");
        } else {
            final CollectionValue cv = (CollectionValue) value;
            if (cv.size() == 0) { // empty
                if (cv instanceof SetlList) {
                    return new SetListConstructor(CollectionType.LIST, null);
                } else /* if (cv instanceof SetlSet) */ {
                    return new SetListConstructor(CollectionType.SET,  null);
                }
            } else { // not empty
                final CollectionBuilder c = CollectionBuilder.collectionValueToBuilder(state, cv);
                if (cv instanceof SetlList) {
                    return new SetListConstructor(CollectionType.LIST, c);
                } else /* if (cv instanceof SetlSet) */ {
                    return new SetListConstructor(CollectionType.SET,  c);
                }
            }
        }
    }

    /* comparisons */

    @Override
    public int compareTo(final CodeFragment other) {
        if (this == other) {
            return 0;
        } else if (other.getClass() == SetListConstructor.class) {
            SetListConstructor otr = (SetListConstructor) other;
            int cmp = type.compareTo(otr.type);
            if (cmp != 0) {
                return cmp;
            }

            if (builder != null) {
                if (otr.builder != null) {
                    return builder.compareTo(otr.builder);
                } else {
                    return 1;
                }
            } else if (otr.builder != null) {
                return -1;
            }
            return 0;
        } else {
            return (this.compareToOrdering() < other.compareToOrdering())? -1 : 1;
        }
    }

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(SetListConstructor.class);

    @Override
    public long compareToOrdering() {
        return COMPARE_TO_ORDER_CONSTANT;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj.getClass() == SetListConstructor.class) {
            SetListConstructor other = (SetListConstructor) obj;
            if (type == other.type) {
                if (builder != null && other.builder != null) {
                    return builder.equals(other.builder);
                } else if (builder == null && other.builder == null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int computeHashCode() {
        int hash = ((int) COMPARE_TO_ORDER_CONSTANT) + type.hashCode();
        if (builder != null) {
            hash = hash * 31 + builder.hashCode();
        }
        return hash;
    }

    @Override
    public int precedence() {
        return PRECEDENCE;
    }
}

