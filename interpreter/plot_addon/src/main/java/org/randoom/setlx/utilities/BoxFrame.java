package org.randoom.setlx.utilities;

import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arne on 03.06.15.
 */
public class BoxFrame extends AbstractFrame {

    private CategoryPlot plot;

    @Override
    protected Plot getPlot() {
        return plot;
    }

    private DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
    private final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();

    private List<Chart> functions = new ArrayList<Chart>();


    @Override
    protected List getFunctions() {
        return this.functions;
    }

    @Override
    protected void setFunctions(List fun) {
        this.functions = fun;
    }

    @Override
    public Value addTextLabel(List<Double> coordinates, String text) {
        Chart chart = new Chart(coordinates, null, text, true);
        CategoryTextAnnotation label = new CategoryTextAnnotation(text,dataset.getColumnKey(coordinates.get(0).intValue()-1) ,coordinates.get(1));
        label.setTextAnchor(TextAnchor.BOTTOM_LEFT);
        functions.add(chart);
        plot.addAnnotation(label);
        chartCount++;
        return chart;
    }

    public BoxFrame(String title, double width, double height) {
        super(title, width, height);
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        ((NumberAxis)yAxis).setAutoRangeIncludesZero(false);
        renderer.setFillBox(false);
    }

    @Override
    protected void remakeFunctions() throws SetlException {
        dataset.clear();

        for(Chart item: functions){
            this.addBoxChart(item.getValues(), item.getCategories(), item.getName());
        }

    }

    @Override
    public void setLabel(String xLabel, String yLabel) {
        plot.getDomainAxis().setLabel(xLabel);
        plot.getRangeAxis().setLabel(yLabel);
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }

    public Chart addBoxChart(List<List<Double>> values, List<String> categories, String name) {

        Chart chart = new Chart(values, categories, name, false);
        int i = 0;
        for(List<Double> item : values) {
            dataset.add(item, name, categories.get(i));
            i++;
        }
        renderer.setSeriesPaint(chartCount, getNewColor());
        renderer.setMeanVisible(false);
        plot = new CategoryPlot(dataset, (CategoryAxis) xAxis, (NumberAxis) yAxis, renderer);
        functions.add(chart);
        this.redraw();
        chartCount++;
        return chart;
    }
}
