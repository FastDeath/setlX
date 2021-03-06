package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.ParameterDef;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.StdDraw;

import java.util.HashMap;

public class PD_gfx_addPlayPauseButton extends PreDefinedProcedure {

    private final static ParameterDef        ADD        = createOptionalParameter("add", Om.OM);

    public  final static PreDefinedProcedure DEFINITION = new PD_gfx_addPlayPauseButton();

    protected PD_gfx_addPlayPauseButton() {
        super();
        addParameter(ADD);
    }

    @Override
    protected Value execute(final State state, final HashMap<ParameterDef, Value> args) throws SetlException {
        if ( args.get(ADD) == Om.OM ){
            StdDraw.addPlayPauseButton(true);
        }else{
            if ( args.get(ADD) instanceof SetlBoolean ){
                final SetlBoolean bool = (SetlBoolean) args.get(ADD);
                if ( bool.equalTo(SetlBoolean.TRUE) ){
                    StdDraw.addPlayPauseButton(true);
                }else{
                    StdDraw.addPlayPauseButton(false);
                }
            }
        }
        return SetlBoolean.TRUE;
    }

}