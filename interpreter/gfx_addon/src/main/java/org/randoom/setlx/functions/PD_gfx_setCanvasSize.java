package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.ParameterDef;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.StdDraw;

import java.util.HashMap;

public class PD_gfx_setCanvasSize extends GfxFunction {
    private final static ParameterDef        W          = createOptionalParameter("w", Om.OM);
    private final static ParameterDef        H          = createOptionalParameter("h", Om.OM);

    public  final static PreDefinedProcedure DEFINITION = new PD_gfx_setCanvasSize();

    private PD_gfx_setCanvasSize(){
        super();
        addParameter(W);
        addParameter(H);
    }

    @Override
    protected Value execute(final State state, final HashMap<ParameterDef, Value> args) throws SetlException{
        if (args.get(W) != Om.OM && args.get(H) != Om.OM){
            StdDraw.setCanvasSize(integerFromValue(state, args.get(W)),integerFromValue(state, args.get(H)));
        }else{
            StdDraw.setCanvasSize();
        }
        return SetlBoolean.TRUE;
    }
}
