package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.UndefinedOperationException;
import org.randoom.setlx.types.*;
import org.randoom.setlx.utilities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PD_plot_addGraph extends PreDefinedProcedure {


    private final static ParameterDef CANVAS = createParameter(" canvas ");
    private final static ParameterDef FUNCTIONDEFINITION = createParameter(" functiondefinition ");
    private final static ParameterDef GRAPHNAME = createParameter(" graphname ");
    private final static ParameterDef GRAPHCOLOR = createOptionalParameter("graphcolor", Rational.ONE);
    private final static ParameterDef PLOTAREA = createOptionalParameter(" plotarea ", SetlBoolean.FALSE);

    public final static PreDefinedProcedure
            DEFINITION = new PD_plot_addGraph();

    private PD_plot_addGraph() {
        super();
        addParameter(CANVAS);
        addParameter(FUNCTIONDEFINITION);
        addParameter(GRAPHNAME);
        addParameter(GRAPHCOLOR);
        addParameter(PLOTAREA);

    }

    @Override
    protected Value execute(State state, HashMap<ParameterDef, Value> args) throws SetlException {

        // initialise parameter canvas, function, functionName and plotArea
        Canvas canvas;
        SetlString functionDefinition;
        SetlString functionName;
        Value colorListValue;
        SetlBoolean plotArea;

        if (!PlotCheckType.isCanvas(args.get(CANVAS))) {
            throw new UndefinedOperationException("First parameter canvas has to be a Canvas object (eg. created with plot_createCanvas() )");
        }

        if (!PlotCheckType.isSetlString(args.get(FUNCTIONDEFINITION))) {
            throw new UndefinedOperationException("Second parameter functiondefinition has to be a Function of type String (eq. \"sin(x)*x\" )");
        }

        if (!PlotCheckType.isSetlString(args.get(GRAPHNAME))) {
            throw new UndefinedOperationException("Third parameter graphname has to be of type String (eq. \"Name of the Graph\" )");
        }

        if (!PlotCheckType.isSetlBoolean(args.get(PLOTAREA))) {
            throw new UndefinedOperationException("Fifth paramter plotarea (optional) has to be of type boolean (eq. ");
        }

        canvas = (Canvas) args.get(CANVAS);
        functionDefinition = (SetlString) args.get(FUNCTIONDEFINITION);
        functionName = (SetlString) args.get(GRAPHNAME);
        colorListValue = args.get(GRAPHCOLOR);
        plotArea = (SetlBoolean) args.get(PLOTAREA);

        boolean area = false;
        if (plotArea.equalTo(SetlBoolean.TRUE)) {
            area = true;
        }

        // function comes as ""sin(x)"" so i have to replace the quotation marks
        String function = functionDefinition.toString().replace("\"", "");
        String graphNameString = functionName.toString().replace("\"", "");

        //if graphcolor is set
        if (!colorListValue.equalTo(Rational.ONE)) {

            if(!PlotCheckType.isSetlList(colorListValue)){
                throw new UndefinedOperationException("Forth parameter graphcolor (optional) has to be of type SetlList with three entries (eq. [0,0,0] )");
            }
            SetlList colorListSetl = (SetlList) colorListValue;

            if(colorListSetl.size() != 3){
                throw new UndefinedOperationException("Forth parameter graphcolor (optional) must have three entries (eq. [0,0,0] ");
            }

            if(!PlotCheckType.isSetlListWithInteger(colorListSetl)){
                throw new UndefinedOperationException("Forth parameter graphcolor (optional) must contain only Integers (eq. [0,0,0] ");
            }

            List<Integer> colorList = ConvertSetlTypes.convertSetlListAsInteger(colorListSetl);
            return ConnectJFreeChart.getInstance().addGraph(canvas, function, graphNameString, state, colorList, area);
        }

        //if no optional parameter is set
        List colorList = new ArrayList();
        colorList.add(0);
        colorList.add(0);
        colorList.add(0);
        return ConnectJFreeChart.getInstance().addGraph(canvas, function, graphNameString, state, colorList, area);
    }
}
