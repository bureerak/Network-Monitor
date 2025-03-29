package main.ui.visualization;


import main.database.graph.DeviceFetch;
import org.jfree.chart.*;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;

import javax.swing.*;
import java.awt.*;

public class DeviceGraph implements ChartMouseListener{
    public static final int Fetch = 0;
    public static final int NotFetch = 1;
    private DefaultCategoryDataset dataset;
    private ChartPanel chartPanel;

    public DeviceGraph(int profile_id, int fetch) {
        DeviceFetch lf = DeviceFetch.getInstance();
        if (fetch == 0) {
            lf.fetch(profile_id);
        }
        dataset = new DefaultCategoryDataset();
        for (int i = 1; i < lf.getDevices().size();i++){
            dataset.addValue(lf.getDevices().get(i),"Number of Devices",lf.getDateTime().get(i).toString());
        }
        JFreeChart chart = ChartFactory.createLineChart(
                "Devices Monitor",
                "",
                "Number of Devices",
                dataset
        );

        chart.getCategoryPlot().getDomainAxis().setVisible(false);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 20));
        chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Segoe UI", Font.PLAIN, 14));
        chart.getPlot().setBackgroundPaint(new Color(255, 255, 255));
        chart.setBackgroundPaint(new Color(246,246,246));
        chart.getCategoryPlot().setRangeGridlinePaint(Color.BLACK);
        chart.getCategoryPlot().setRangeGridlineStroke(new BasicStroke(0.1f));

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        chart.getCategoryPlot().setRenderer(renderer);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setSeriesPaint(0, new Color(236, 140, 67));
        renderer.setSeriesStroke(0, new BasicStroke(1.6f));
        renderer.setDefaultItemLabelsVisible(true);

        chartPanel = new ChartPanel(chart);
        chartPanel.addChartMouseListener(this);

    }

    @Override
    public void chartMouseClicked(ChartMouseEvent e) {
        ChartEntity entity = e.getEntity();
        try {
            if (entity.getClass().equals(CategoryItemEntity.class)) {
                CategoryItemEntity categoryEntity = (CategoryItemEntity)entity;
                Comparable datetimes = categoryEntity.getColumnKey();
                String devices = dataset.getValue(categoryEntity.getRowKey(), datetimes).toString();

                ImageIcon image = new ImageIcon("resources/titleIcon.png");
                ImageIcon icon = new ImageIcon(image.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                String times = datetimes.toString().replace("T","\nTimes: ").replace("-","/").strip();

                JOptionPane.showMessageDialog(
                        null,
                        "Date: " + times+ "\nLatency: " + devices,
                        "Number of Devices",
                        JOptionPane.PLAIN_MESSAGE,
                        icon
                );
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent e) {

    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

}