package it.unicam.cs.pa.app;

import it.unicam.cs.pa.environment.shapes.Circle;
import it.unicam.cs.pa.environment.shapes.Rectangle;
import it.unicam.cs.pa.environment.shapes.Shape;
import it.unicam.cs.pa.robot.Robot;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

import java.util.List;

public class ChartController {
    private ScatterChart<Number, Number> chart;

    private double scaleFactorX;

    private double scaleFactorY;

    public ChartController(ScatterChart<Number, Number> chart, NumberAxis xAxis, NumberAxis yAxis) {
        this.chart = chart;

        chart.setLegendVisible(false);

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(-7);
        xAxis.setUpperBound(7);
        xAxis.setTickUnit(1);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(-10);
        yAxis.setUpperBound(10);
        yAxis.setTickUnit(1);

        scaleFactorX = xAxis.getTickUnit() * 25;
        scaleFactorY = yAxis.getTickUnit() * 25;
    }

    public void drawFigures(List<Shape> shapes) {
        for (Shape shape : shapes) {
            switch (shape.getType()) {
                case CIRCLE -> addCircleToChart((Circle) shape);
                case RECTANGLE -> addRectangleToChart((Rectangle) shape);
                default -> {
                }
            }
        }
    }

    private void addCircleToChart(Circle shape) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(shape.center().x(), shape.center().y()));

        Color color = new Color(0.0, 1.0, 0.0, 0.5);
        Node node = new javafx.scene.shape.Circle(shape.radius() * scaleFactorX, color);
        series.getData().get(0).setNode(node);

        this.chart.getData().add(series);
    }

    private void addRectangleToChart(Rectangle shape) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(shape.center().x(), shape.center().y()));

        Color color = new Color(1.0, 0.0, 0.0, 0.5);
        Node node = new javafx.scene.shape.Rectangle(shape.width() * scaleFactorX, shape.height() * scaleFactorY, color);
        series.getData().get(0).setNode(node);

        this.chart.getData().add(series);
    }

    public void drawRobots(List<Robot> robots) {
        for (Robot robot : robots) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(robot.getPosition().x(), robot.getPosition().y()));

            Color color = new Color(0.0, 0.0, 1.0, 1.0);
            Node node = new javafx.scene.shape.Circle(10, color);
            series.getData().get(0).setNode(node);

            this.chart.getData().add(series);
        }
    }
}
