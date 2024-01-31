package it.unicam.cs.pa.app;

import it.unicam.cs.pa.SimulationController;
import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.shapes.Circle;
import it.unicam.cs.pa.environment.shapes.Rectangle;
import it.unicam.cs.pa.environment.shapes.Shape;
import it.unicam.cs.pa.robot.Robot;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

import java.util.List;

public class ChartController {
    private final XYChart<Number, Number> chart;

    private final NumberAxis xAxis;

    private final NumberAxis yAxis;

    private final Environment environment;

    private double scaleFactorX;

    private double scaleFactorY;

    static final int INITIAL_GRAPH_BOUND = 10;

    private final Color ROBOT_COLOR = new Color(0.0, 0.0, 1.0, 1.0);

    private final Color CIRCLE_COLOR = new Color(0.0, 1.0, 0.0, 0.5);

    private final Color RECTANGLE_COLOR = new Color(1.0, 0.0, 0.0, 0.5);

    public ChartController(XYChart<Number, Number> chart, NumberAxis xAxis, NumberAxis yAxis, SimulationController simulationController) {
        this.chart = chart;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.environment = simulationController.getEnvironment();

        initializeChart();
    }

    private void initializeChart() {
        chart.setLegendVisible(false);

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(-INITIAL_GRAPH_BOUND);
        xAxis.setUpperBound(INITIAL_GRAPH_BOUND);
        xAxis.setTickUnit(1);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(-INITIAL_GRAPH_BOUND);
        yAxis.setUpperBound(INITIAL_GRAPH_BOUND);
        yAxis.setTickUnit(1);
    }

    private void calculateScaleFactors() {
        double xAxisSpan = xAxis.getUpperBound() - xAxis.getLowerBound();
        double yAxisSpan = yAxis.getUpperBound() - yAxis.getLowerBound();
        scaleFactorX = xAxis.getWidth() / xAxisSpan;
        scaleFactorY = yAxis.getHeight() / yAxisSpan;
    }

    public void updateChart() {
        this.chart.getData().clear();

        calculateScaleFactors();
        drawShapes(environment.getShapes());
        drawRobots(environment.getRobots());
    }

    public void drawShapes(List<Shape> shapes) {
        for (Shape shape : shapes) {
            switch (shape.getType()) {
                case CIRCLE -> addCircleShapeToChart((Circle) shape);
                case RECTANGLE -> addRectangleShapeToChart((Rectangle) shape);
                default -> {
                }
            }
        }
    }

    public void drawRobots(List<Robot> robots) {
        for (Robot robot : robots) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(robot.getPosition().x(), robot.getPosition().y()));

            javafx.scene.shape.Circle robotNode = new javafx.scene.shape.Circle(0.5 * scaleFactorX, ROBOT_COLOR);
            series.getData().get(0).setNode(robotNode);

            this.chart.getData().add(series);
        }
    }

    private void addCircleShapeToChart(Circle shape) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(shape.center().x(), shape.center().y()));

        javafx.scene.shape.Circle circleNode = new javafx.scene.shape.Circle(shape.radius() * scaleFactorX, CIRCLE_COLOR);

        // Aggiungi un bordo alla forma Circle
        circleNode.setStroke(Color.BLACK);  // Puoi impostare il colore del bordo a tuo piacimento
        circleNode.setStrokeWidth(0.5);    // Puoi impostare la larghezza del bordo a tuo piacimento

        series.getData().get(0).setNode(circleNode);

        this.chart.getData().add(series);
    }

    private void addRectangleShapeToChart(Rectangle shape) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(shape.center().x(), shape.center().y()));

        javafx.scene.shape.Rectangle rectangleNode = new javafx.scene.shape.Rectangle(
                shape.width() * scaleFactorX,
                shape.height() * scaleFactorY,
                RECTANGLE_COLOR);

        rectangleNode.setStroke(Color.BLACK);
        rectangleNode.setStrokeWidth(0.5);

        series.getData().get(0).setNode(rectangleNode);

        this.chart.getData().add(series);
    }

    public void zoomIn() {
        xAxis.setUpperBound(xAxis.getUpperBound() - 1);
        xAxis.setLowerBound(xAxis.getLowerBound() + 1);
        yAxis.setUpperBound(yAxis.getUpperBound() - 1);
        yAxis.setLowerBound(yAxis.getLowerBound() + 1);
        updateChart();
    }

    public void zoomOut() {
        xAxis.setUpperBound(xAxis.getUpperBound() + 1);
        xAxis.setLowerBound(xAxis.getLowerBound() - 1);
        yAxis.setUpperBound(yAxis.getUpperBound() + 1);
        yAxis.setLowerBound(yAxis.getLowerBound() - 1);
        updateChart();
    }

    public void panLeft() {
        xAxis.setUpperBound(xAxis.getUpperBound() - 1);
        xAxis.setLowerBound(xAxis.getLowerBound() - 1);
        updateChart();
    }

    public void panRight() {
        xAxis.setUpperBound(xAxis.getUpperBound() + 1);
        xAxis.setLowerBound(xAxis.getLowerBound() + 1);
        updateChart();
    }

    public void panUp() {
        yAxis.setUpperBound(yAxis.getUpperBound() + 1);
        yAxis.setLowerBound(yAxis.getLowerBound() + 1);
        updateChart();
    }

    public void panDown() {
        yAxis.setUpperBound(yAxis.getUpperBound() - 1);
        yAxis.setLowerBound(yAxis.getLowerBound() - 1);
        updateChart();
    }

    public void panCenter() {
        xAxis.setUpperBound(INITIAL_GRAPH_BOUND);
        xAxis.setLowerBound(-INITIAL_GRAPH_BOUND);
        yAxis.setUpperBound(INITIAL_GRAPH_BOUND);
        yAxis.setLowerBound(-INITIAL_GRAPH_BOUND);
        updateChart();
    }
}