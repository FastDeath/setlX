package org.randoom.setlx.types;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.State;

/**
 * The setlX data type for encapsulating errors caught in try-catch.
 */
public class SetlError extends ImmutableValue {

    private final String message;

    /**
     * Create a new SetlError containing the specified message.
     *
     * @param message Error message.
     */
    public SetlError(final String message) {
        this.message = message;
    }

    /**
     * Create a new SetlError containing the message of the specified exception.
     *
     * @param exception Exception to copy the message from.
     */
    public SetlError(final SetlException exception) {
        this(exception.getMessage());
    }

    @Override
    public SetlError clone() {
        // this value is more or less atomic and can not be changed once set
        return this;
    }

    /* type checks (sort of boolean operation) */

    @Override
    public SetlBoolean isError() {
        return SetlBoolean.TRUE;
    }

    /* string and char operations */

    @Override
    public void appendString(final State state, final StringBuilder sb, final int tabs) {
        sb.append(message);
    }

    /* comparisons */

    @Override
    public int compareTo(final CodeFragment other) {
        if (this == other) {
            return 0;
        } else if (other.getClass() == SetlError.class) {
            return message.compareTo(((SetlError) other).message);
        } else {
            return (this.compareToOrdering() < other.compareToOrdering())? -1 : 1;
        }
    }

    @Override
    public long compareToOrdering() {
        return Long.MIN_VALUE;
    }

    @Override
    public boolean equalTo(final Object other) {
        if (this == other) {
            return true;
        } else if (other.getClass() == SetlError.class) {
            return message.equals(((SetlError) other).message);
        } else {
            return false;
        }
    }

    private final static int initHashCode = SetlError.class.hashCode();

    @Override
    public int computeHashCode() {
        return initHashCode * 31 + message.hashCode();
    }
}

