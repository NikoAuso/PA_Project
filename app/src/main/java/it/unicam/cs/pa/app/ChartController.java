package it.unicam.cs.pa.app;

import it.unicam.cs.pa.SimulationController;
import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.shapes.Circle;
import it.unicam.cs.pa.environment.shapes.Rectangle;
import it.unicam.cs.pa.environment.shapes.Shape;
import it.unicam.cs.pa.robot.Robot;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class ChartController {
    private final XYChart<Number, Number> chart;

    private final NumberAxis xAxis;

    private final NumberAxis yAxis;

    private final Environment environment;

    private final XYChart.Series<Number, Number> robotSeries;

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

        robotSeries = new XYChart.Series<>();

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
                    //do nothing
                }
            }
        }
    }

    public void drawRobots(List<Robot> robots) {
        robotSeries.getData().clear();

        for (Robot robot : robots) {
            XYChart.Data<Number, Number> data = new XYChart.Data<>(robot.position().x(), robot.position().y());
            robotSeries.getData().add(data);

            javafx.scene.shape.Circle robotNode = new javafx.scene.shape.Circle(0.5 * scaleFactorX);
            robotNode.setFill(ROBOT_COLOR);
            robotNode.setStroke(Color.BLACK);
            robotNode.setStrokeWidth(0.5);

            Label label = createLabel(robot.currentLabel());
            label.setTranslateY(-20);

            StackPane container = new StackPane(robotNode, label);
            container.setBackground(Background.fill(Color.TRANSPARENT));

            data.setNode(container);
        }

        this.chart.getData().add(robotSeries);
    }

    private void addCircleShapeToChart(Circle shape) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(shape.center().x(), shape.center().y()));

        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(shape.radius() * scaleFactorX, CIRCLE_COLOR);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(0.5);

        StackPane container = new StackPane(circle, createLabel(shape.label()));
        container.setBackground(Background.fill(Color.TRANSPARENT));

        series.getData().get(0).setNode(container);

        this.chart.getData().add(series);
    }

    private void addRectangleShapeToChart(Rectangle shape) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(shape.center().x(), shape.center().y()));

        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(
                shape.width() * scaleFactorX,
                shape.height() * scaleFactorY,
                RECTANGLE_COLOR);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(0.5);

        StackPane container = new StackPane(rectangle, createLabel(shape.label()));
        container.setBackground(Background.fill(Color.TRANSPARENT));

        series.getData().get(0).setNode(container);

        this.chart.getData().add(series);
    }

    private Label createLabel(String label){
        Label labelToCreate = new Label(label);
        labelToCreate.setFont(new Font(0.5 * scaleFactorX));
        labelToCreate.setTextFill(Color.BLACK);
        return labelToCreate;
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