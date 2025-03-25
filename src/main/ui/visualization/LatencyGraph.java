package main.ui.visualization;

import main.database.graph.LatencyFetch;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;

import javax.swing.*;
import java.awt.*;

public class LatencyGraph extends JScrollPane {
    public static final int allRange = 0;
    public static final int inRange = 1;
    public LatencyGraph(int profile_id, int range) {
        super();
        LatencyFetch lf = LatencyFetch.getInstance();
        if (range == 0) {lf.fetch(profile_id);}
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
        CategoryAxis xAxis = chart.getCategoryPlot().getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(510,260));
        chartPanel.setFont(new Font(null,Font.PLAIN,18));
        setViewportView(chartPanel);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setPreferredSize(new Dimension(510,280));
    }
}
