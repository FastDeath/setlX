package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.ParameterDef;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.StdDraw;

import java.util.HashMap;

public class PD_gfx_textRight extends GfxFunction {
    private final static ParameterDef        X          = createParameter("x");
    private final static ParameterDef        Y          = createParameter("y");
    private final static ParameterDef        STRING     = createParameter("string");

    public  final static PreDefinedProcedure DEFINITION = new PD_gfx_textRight();

    protected PD_gfx_textRight() {
        super();
        addParameter(X);
        addParameter(Y);
        addParameter(STRING);
    }

    @Override
    protected Value execute(final State state, final HashMap<ParameterDef, Value> args) throws SetlException {
        final double x = doubleFromValue( state, args.get(X) );
        final double y = doubleFromValue( state, args.get(Y) );
        final String s = stringFromValue( state, args.get(STRING) );
        StdDraw.textRight(x, y, s);
        return SetlBoolean.TRUE;
    }

}
