package org.randoom.setlx.utilities;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.title.LegendTitle;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.Value;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arne on 03.06.15.
 */
public abstract class AbstractFrame extends JFrame {
    Plot plot;
    ChartPanel chartPanel;
    LegendTitle legend;
    protected double x_Min;
    protected double x_Max;
    protected List<Value> functions = new ArrayList<Value>();
    protected Axis xAxis;
    protected Axis yAxis;
    protected JPanel jPanel;
    protected int chartCount;
    protected String title = "title";

    public int getChartCount(){
        return this.chartCount;
    }
    public abstract Graph addTextLabel(List<Double> coordinates, String text);

    public Axis getyAxis() {
        return yAxis;
    }

    public Axis getxAxis() {
        return xAxis;
    }

    public AbstractFrame(String title){
        super(title);
        this.setVisible(true);
        this.chartCount = 0;
        jPanel = new JPanel();
        jPanel.setName(title);
        add(jPanel, BorderLayout.CENTER);
        setSize(640, 480);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void setTitle(String title) {
        this.title = title;
        if (chartPanel != null) {
            chartPanel.setName(title);
        }
    }

    protected abstract void remakeFunctions() throws SetlException;

    protected abstract void redraw();

    public void removeGraph(Graph graph) throws SetlException {
        boolean ispresent = functions.remove(graph);
        if (!ispresent) {
            System.out.println("nicht gelöscht");
        }
        remakeFunctions();
    }

}
