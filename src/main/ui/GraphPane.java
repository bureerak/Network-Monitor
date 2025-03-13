package main.ui;

import main.database.graph.LatencyFetch;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class GraphPane {
    public static void main(String[] args) {
        LatencyFetch lf = LatencyFetch.getInstance();
        lf.fetch();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < lf.getAvg().size(); i++) {
            String dateTime = String.valueOf(lf.getDateTimes().get(i));
            Double avg = lf.getAvg().get(i);
            dataset.addValue(avg, "Avg Latency", dateTime);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Latency Monitor",
                "DateTime",
                "Average Latency",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame("Latency Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
