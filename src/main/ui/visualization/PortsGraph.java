package main.ui.visualization;

import main.database.graph.PortFetch;
import org.jfree.chart.*;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PortsGraph implements ChartMouseListener {
    public static final int Fetch = 0;
    public static final int NotFetch = 1;
    private DefaultCategoryDataset dataset;
    private ChartPanel chartPanel;

    public PortsGraph(int profile_id, int fetch) {
        PortFetch lf = PortFetch.getInstance();
        if (fetch == 0) {lf.fetch(profile_id);}
        dataset = new DefaultCategoryDataset();
        for (int i = 1; i < lf.getCount().size();i++){
            dataset.addValue(lf.getCount().get(i),"Number of Ports",lf.getDateTime().get(i).toString());
        }
        JFreeChart chart = ChartFactory.createLineChart(
                "Ports Monitor",
                "",
                "Number of Ports",
                dataset
        );

        chart.getCategoryPlot().getDomainAxis().setVisible(false);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 20));
        chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
        chart.getPlot().setBackgroundPaint(new Color(255, 255, 255));
        chart.setBackgroundPaint(new Color(246,246,246));
        chart.getCategoryPlot().setRangeGridlinePaint(Color.BLACK);
        chart.getCategoryPlot().setRangeGridlineStroke(new BasicStroke(0.1f));

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        chart.getCategoryPlot().setRenderer(renderer);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setSeriesPaint(0, new Color(243, 213, 61));
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
                String num = dataset.getValue(categoryEntity.getRowKey(), datetimes).toString();

                ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/titleIcon.png")));
                ImageIcon icon = new ImageIcon(image.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                String times = datetimes.toString().replace("T","\nTimes: ").replace("-","/");

                JOptionPane.showMessageDialog(
                        null,
                        "Date: " + times+ "\nPort: " + num,
                        "Number of Ports",
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