package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.IncompatibleTypeException;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.SetlMatrix;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.ParameterDef;
import org.randoom.setlx.utilities.State;

import java.util.HashMap;

/**
 * @author Patrick Robinson
 *         <p/>
 *         Calculate eigen values
 */
public class PD_la_eigenValues extends PreDefinedProcedure {

    private final static ParameterDef        MATRIX     = createParameter("matrix");

    public  final static PreDefinedProcedure DEFINITION = new PD_la_eigenValues();

    private PD_la_eigenValues() {
        super();
        addParameter(MATRIX);
    }

    @Override
    public Value execute(State state, HashMap<ParameterDef, Value> args) throws SetlException {
        if ((args.get(MATRIX) instanceof SetlMatrix)) {
            return ((SetlMatrix) args.get(MATRIX)).eigenValues(state);
        } else {
            throw new IncompatibleTypeException("The parameter needs to be a matrix.");
        }
    }
}
