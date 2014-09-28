package org.randoom.setlx.functions;

import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.ParameterDef;
import org.randoom.setlx.utilities.State;

import java.util.HashMap;

/**
 * isMap(value) : Test if value-type is map.
 */
public class PD_isMap extends PreDefinedProcedure {

    private final static ParameterDef        VALUE      = createParameter("value");

    /** Definition of the PreDefinedProcedure `isMap'. */
    public  final static PreDefinedProcedure DEFINITION = new PD_isMap();

    private PD_isMap() {
        super();
        addParameter(VALUE);
    }

    @Override
    public Value execute(final State state, final HashMap<ParameterDef, Value> args) {
        return args.get(VALUE).isMap();
    }
}

