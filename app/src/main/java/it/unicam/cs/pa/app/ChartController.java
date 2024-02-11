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

/**
 * The ChartController class manages the display of a chart in the application, allowing functionalities such as
 * zooming, panning, and updating the chart based on the environment and robot data.
 * It interacts with JavaFX components to render the chart and its elements.
 */
public class ChartController {
    private final XYChart<Number, Number> chart;                // The chart to be controlled
    private final NumberAxis xAxis;                             // The x-axis of the chart
    private final NumberAxis yAxis;                             // The y-axis of the chart
    private final Environment environment;                      // The environment containing shapes and robots

    private final XYChart.Series<Number, Number> robotSeries;   // Series for robot data

    private double scaleFactorX;                                // Scale factor for x-axis
    private double scaleFactorY;                                // Scale factor for y-axis

    // Initial graph bound for x and y axes
    static final int INITIAL_GRAPH_BOUND = 10;

    // Color constants for robot, circle, and rectangle
    private final Color ROBOT_COLOR = new Color(0.0, 0.0, 1.0, 1.0);
    private final Color CIRCLE_COLOR = new Color(0.0, 1.0, 0.0, 0.5);
    private final Color RECTANGLE_COLOR = new Color(1.0, 0.0, 0.0, 0.5);

    /**
     * Constructs a new ChartController with the specified chart, x-axis, y-axis, and simulation controller.
     *
     * @param chart                The chart to control
     * @param xAxis                The x-axis of the chart
     * @param yAxis                The y-axis of the chart
     * @param simulationController The simulation controller providing environment data
     */
    public ChartController(XYChart<Number, Number> chart, NumberAxis xAxis, NumberAxis yAxis, SimulationController simulationController) {
        this.chart = chart;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.environment = simulationController.getEnvironment();

        robotSeries = new XYChart.Series<>();

        initializeChart();
    }

    /**
     * Initializes the chart with default settings.
     */
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

    /**
     * Calculates the scale factors for x and y axes based on their spans and chart dimensions.
     */
    private void calculateScaleFactors() {
        double xAxisSpan = xAxis.getUpperBound() - xAxis.getLowerBound();
        double yAxisSpan = yAxis.getUpperBound() - yAxis.getLowerBound();
        scaleFactorX = xAxis.getWidth() / xAxisSpan;
        scaleFactorY = yAxis.getHeight() / yAxisSpan;
    }

    /**
     * Updates the chart by clearing existing data and redrawing shapes and robots based on the current environment data.
     */
    public void updateChart() {
        this.chart.getData().clear();

        calculateScaleFactors();
        drawShapes(environment.getShapes());
        drawRobots(environment.getRobots());
    }

    /**
     * Draws the robots on the chart based on the provided list of robots.
     *
     * @param robots The list of robots to draw
     */
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
            label.setTranslateY(-1 * scaleFactorY);

            StackPane container = new StackPane(robotNode, label);
            container.setBackground(Background.fill(Color.TRANSPARENT));

            data.setNode(container);
        }

        this.chart.getData().add(robotSeries);
    }

    /**
     * Draws the shapes on the chart based on the provided list of shapes.
     *
     * @param shapes The list of shapes to draw
     */
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

    /**
     * Adds a circle shape to the chart.
     *
     * @param shape The circle shape to add
     */
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

    /**
     * Adds a rectangle shape to the chart.
     *
     * @param shape The rectangle shape to add
     */
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

    /**
     * Creates a label with the specified text and font size.
     *
     * @param label The text for the label
     * @return The created label
     */
    private Label createLabel(String label) {
        Label labelToCreate = new Label(label);
        labelToCreate.setFont(new Font(0.5 * scaleFactorX));
        labelToCreate.setTextFill(Color.BLACK);
        return labelToCreate;
    }

    /**
     * Zooms in the chart by adjusting the upper and lower bounds of both x and y axes.
     */
    public void zoomIn() {
        xAxis.setUpperBound(xAxis.getUpperBound() - 1);
        xAxis.setLowerBound(xAxis.getLowerBound() + 1);
        yAxis.setUpperBound(yAxis.getUpperBound() - 1);
        yAxis.setLowerBound(yAxis.getLowerBound() + 1);
        updateChart();
    }

    /**
     * Zooms out the chart by adjusting the upper and lower bounds of both x and y axes.
     */
    public void zoomOut() {
        xAxis.setUpperBound(xAxis.getUpperBound() + 1);
        xAxis.setLowerBound(xAxis.getLowerBound() - 1);
        yAxis.setUpperBound(yAxis.getUpperBound() + 1);
        yAxis.setLowerBound(yAxis.getLowerBound() - 1);
        updateChart();
    }

    /**
     * Pans the chart to the left by adjusting the upper and lower bounds of the x-axis.
     */
    public void panLeft() {
        xAxis.setUpperBound(xAxis.getUpperBound() - 1);
        xAxis.setLowerBound(xAxis.getLowerBound() - 1);
        updateChart();
    }

    /**
     * Pans the chart to the right by adjusting the upper and lower bounds of the x-axis.
     */
    public void panRight() {
        xAxis.setUpperBound(xAxis.getUpperBound() + 1);
        xAxis.setLowerBound(xAxis.getLowerBound() + 1);
        updateChart();
    }

    /**
     * Pans the chart upward by adjusting the upper and lower bounds of the y-axis.
     */
    public void panUp() {
        yAxis.setUpperBound(yAxis.getUpperBound() + 1);
        yAxis.setLowerBound(yAxis.getLowerBound() + 1);
        updateChart();
    }

    /**
     * Pans the chart downward by adjusting the upper and lower bounds of the y-axis.
     */
    public void panDown() {
        yAxis.setUpperBound(yAxis.getUpperBound() - 1);
        yAxis.setLowerBound(yAxis.getLowerBound() - 1);
        updateChart();
    }

    /**
     * Centers the chart by resetting the upper and lower bounds of both x and y axes to their initial values.
     */
    public void panCenter() {
        xAxis.setUpperBound(INITIAL_GRAPH_BOUND);
        xAxis.setLowerBound(-INITIAL_GRAPH_BOUND);
        yAxis.setUpperBound(INITIAL_GRAPH_BOUND);
        yAxis.setLowerBound(-INITIAL_GRAPH_BOUND);
        updateChart();
    }
}