import solver.Point;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ChartIt extends ApplicationFrame {
    private XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
    private String chartTitle = "";
    public boolean debugging = false;
    int width = 640;
    int height = 480;

    public ChartIt(XYSeriesCollection xySeriesCollection) {
        super("XY Points Graph");
        this.xySeriesCollection = xySeriesCollection;
    }

    public ChartIt(Point[] pointsArray, String key) {
        super("XY Points Graph");
        XYSeries points = new XYSeries(key, false, true);
        for (Point point : pointsArray) {
            points.add(point.getX(), point.getY());
        }
        xySeriesCollection.addSeries(points);
        new ChartIt(xySeriesCollection);
    }

    public void setWindowTitle(String windowTitle) {
        super.setTitle(windowTitle);
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    private java.awt.Color selectSeriesColor(int series) {
        switch (series % 3){
            case 0 :
                return Color.RED;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
        }
        return Color.BLACK;
    }

    public void chartIt(ChartIt chart) {
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "X",
                "Y",
                xySeriesCollection,
                PlotOrientation.VERTICAL,
                true, true, false);

        File xyChart = new File("TestPoint.png");
        try {
            ChartUtils.saveChartAsPNG(xyChart, xyLineChart, width, height);
        } catch (IOException e) {
            System.out.println("PNG file not saved.");
            //e.printStackTrace();
        }

        SVGGraphics2D svg2D = new SVGGraphics2D(width, height);
        Rectangle rectangle = new Rectangle(0, 0, width, height);
        xyLineChart.draw(svg2D, rectangle);
        File xyChartSVG = new File("TestPoint.svg");
        try {
            SVGUtils.writeToSVG(xyChartSVG, svg2D.getSVGElement());
        } catch (IOException e) {
            System.out.println("SVG file not saved.");
            //e.printStackTrace();
        }

        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(width, height));
        final XYPlot plot = xyLineChart.getXYPlot();

        StandardXYItemRenderer renderer = new StandardXYItemRenderer();
        for (int i = 0; i < xySeriesCollection.getSeriesCount(); i++) {
            renderer.setSeriesPaint(i, selectSeriesColor(i));
            renderer.setSeriesStroke(i, new BasicStroke(3.0f));
        }

        plot.setRenderer(renderer);
        setContentPane(chartPanel);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
