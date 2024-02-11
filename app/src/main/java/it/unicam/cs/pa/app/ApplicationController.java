package it.unicam.cs.pa.app;

import it.unicam.cs.pa.SimulationController;
import it.unicam.cs.pa.SimulationListener;
import it.unicam.cs.pa.Simulator;
import it.unicam.cs.pa.environment.EnvironmentException;
import it.unicam.cs.pa.environment.EnvironmentParser;
import it.unicam.cs.pa.environment.shapes.ShapeChecker;
import it.unicam.cs.pa.robot.commands.CommandException;
import it.unicam.cs.pa.robot.commands.CommandParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

/**
 * The ApplicationController class controls the user interface and manages user interactions with the simulation
 * application. It handles loading environment shapes, robot programs, generating robots, starting, pausing, and
 * resetting simulations, and controlling the chart display.
 */
public class ApplicationController implements SimulationListener, Simulator {
    private SimulationController simulationController;  // Manages the simulation logic
    private ChartController chartController;            // Manages the chart display

    @FXML
    public VBox window;

    @FXML
    private Font mainLabel;

    @FXML
    private Button loadShapeButton;

    @FXML
    private Button loadProgramButton;

    @FXML
    private Button generateRobotsButton;

    @FXML
    private Button startSimulation;

    @FXML
    private Button startSimulationStepped;

    @FXML
    private Button resetButton;

    @FXML
    public Button panLeftButton;

    @FXML
    public Button panRightButton;

    @FXML
    public Button panUpButton;

    @FXML
    public Button panDownButton;

    @FXML
    public Button panCenterButton;

    @FXML
    public Button zoomInButton;

    @FXML
    public Button zoomOutButton;

    @FXML
    private TextArea programLoadedDetails;

    @FXML
    private TextArea shapesLoadedDetails;

    @FXML
    private FileChooser fileChooser;

    @FXML
    private XYChart<Number, Number> chart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    /**
     * Initializes the controller, creating instances of SimulationController and ChartController.
     */
    @FXML
    private void initialize() {
        simulationController = new SimulationController();
        simulationController.addSimulationListener(this);
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.txt", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        chartController = new ChartController(chart, xAxis, yAxis, simulationController);
        checkIfSimulationCanStart();
    }

    /**
     * Handles the action event when the "Load Shape" button is clicked.
     * Loads environment shapes from a file, updates the chart and updates the shape details area.
     * Also checks if the simulation can start and updates buttons accordingly.
     *
     * @param event The action event triggered by clicking the "Load Shape" button.
     */
    @FXML
    private void handleLoadShapeButton(ActionEvent event) {
        File selectedFile = showFileChooser("Load environment file");
        if (selectedFile != null && selectedFile.exists()) {
            if (selectedFile.length() > 0) {
                if (loadShapeFromFile(selectedFile)) {
                    updateShapeInfo(selectedFile);
                    updateChart();
                }
            } else
                showAlert(Alert.AlertType.INFORMATION, "Info", "The selected file seems to be empty.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "No file selected.");
        }
        checkIfSimulationCanStart();
        event.consume();
    }

    /**
     * Handles the action event when the "Load Program" button is clicked.
     * Loads robot programs from a file and updates the program details area.
     * Also checks if the simulation can start and updates buttons accordingly.
     *
     * @param event The action event triggered by clicking the "Load Program" button.
     */
    @FXML
    private void handleLoadProgramButton(ActionEvent event) {
        File selectedFile = showFileChooser("Load program file");
        if (selectedFile != null && selectedFile.exists()) {
            if (selectedFile.length() > 0) {
                if (loadProgramFormFile(selectedFile)) {
                    updateProgramInfo(selectedFile);
                }
            } else
                showAlert(Alert.AlertType.INFORMATION, "Info", "The selected file seems to be empty.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "No file selected.");
        }
        checkIfSimulationCanStart();
        event.consume();
    }

    /**
     * Handles the action event when the "Generate Robots" button is clicked.
     * Generates random robots based on user input and updates the chart.
     * Also checks if the simulation can start and updates buttons accordingly.
     *
     * @param event The action event triggered by clicking the "Generate Robots" button.
     */
    @FXML
    private void handleGenerateRobotsButton(ActionEvent event) {
        try {
            int n = showEnteringNumberDialogInt("Enter the number of robots to spawn.");
            if (n <= 0)
                showAlert(Alert.AlertType.WARNING, "Warning", "No robots generated.");
            else {
                this.simulationController.getEnvironment().getRobots().clear();
                this.simulationController.getEnvironment().generateRandomRobots(n, ChartController.INITIAL_GRAPH_BOUND);
                updateChart();
            }
            checkIfSimulationCanStart();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No number of robots to generate provided.");
        }
        event.consume();
    }

    /**
     * Handles the action event when the "Start Simulation" button is clicked.
     * Show a dialog to get the duration of the simulation time and the duration of each instruction time.
     * Starts the simulation with the specified duration and time step.
     * Shows alerts for invalid inputs or errors during simulation.
     *
     * @param event The action event triggered by clicking the "Start Simulation" button.
     */
    @FXML
    private void handleStartSimulation(ActionEvent event) {
        double time;
        double dt;
        try {
            time = showEnteringNumberDialogDouble("Enter the duration of the simulation.");
            dt = showEnteringNumberDialogDouble("Enter the duration of each instruction.");
            if (dt > 0 && dt <= time)
                this.simulate(dt, time);
            else
                showAlert(Alert.AlertType.WARNING, "Simulation input", "Invalid simulation time or duration.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Simulation input", "No number provided.");
        }
        event.consume();
    }

    /**
     * Handles the action event when the "Move one step" button is clicked.
     * Steps the simulation forward by one instruction.
     *
     * @param event The action event triggered by clicking the "Start Simulation Stepped" button.
     */
    @FXML
    private void handleStartSimulationStepped(ActionEvent event) {
        this.simulationController.step();
        event.consume();
    }

    /**
     * Handles the action event when the "Reset" button is clicked.
     * Resets the simulation environment, clears loaded shapes and programs, and updates the chart.
     * Also checks if the simulation can start and updates buttons accordingly.
     *
     * @param event The action event triggered by clicking the "Reset" button.
     */
    @FXML
    private void handleResetButton(ActionEvent event) {
        this.simulationController.getEnvironment().getRobots().clear();
        this.simulationController.getEnvironment().getShapes().clear();
        this.programLoadedDetails.clear();
        this.shapesLoadedDetails.clear();
        updateChart();
        checkIfSimulationCanStart();
        event.consume();
    }

    /**
     * Handles the action event when the "Zoom in" button is clicked.
     * Zooms in the chart display.
     *
     * @param event The action event triggered by clicking the "Zoom in" button.
     */
    @FXML
    private void handleZoomInButton(ActionEvent event) {
        chartController.zoomIn();
        event.consume();
    }

    /**
     * Handles the action event when the "Zoom out" button is clicked.
     * Zooms out the chart display.
     *
     * @param event The action event triggered by clicking the "Zoom out" button.
     */
    @FXML
    private void handleZoomOutButton(ActionEvent event) {
        chartController.zoomOut();
        event.consume();
    }

    /**
     * Handles the action event when the "Pan left" button is clicked.
     * Moves the chart display to the left.
     *
     * @param event The action event triggered by clicking the "Pan left" button.
     */
    @FXML
    private void handlePanLeftButton(ActionEvent event) {
        chartController.panLeft();
        event.consume();
    }

    /**
     * Handles the action event when the "Pan right" button is clicked.
     * Moves the chart display to the right.
     *
     * @param event The action event triggered by clicking the "Pan right" button.
     */
    @FXML
    private void handlePanRightButton(ActionEvent event) {
        chartController.panRight();
        event.consume();
    }

    /**
     * Handles the action event when the "Pan up" button is clicked.
     * Moves the chart display to the up.
     *
     * @param event The action event triggered by clicking the "Pan up" button.
     */
    @FXML
    private void handlePanUpButton(ActionEvent event) {
        chartController.panUp();
        event.consume();
    }

    /**
     * Handles the action event when the "Pan down" button is clicked.
     * Moves the chart display to the down.
     *
     * @param event The action event triggered by clicking the "Pan down" button.
     */
    @FXML
    private void handlePanDownButton(ActionEvent event) {
        chartController.panDown();
        event.consume();
    }

    /**
     * Handles the action event when the "Pan center" button is clicked.
     * Moves the chart display to the center of the chart. The chart view is restored at the initial bound.
     *
     * @param event The action event triggered by clicking the "Pan center" button.
     */
    @FXML
    private void handlePanCenterButton(ActionEvent event) {
        chartController.panCenter();
        event.consume();
    }

    /**
     * Checks if the simulation can start based on the loaded environment shapes, robot programs, and generated robots.
     * Enables or disables simulation-related buttons accordingly.
     */
    private void checkIfSimulationCanStart() {
        if (!this.simulationController.getEnvironment().getShapes().isEmpty() &&
                !this.simulationController.getEnvironment().getRobots().isEmpty() &&
                !this.simulationController.getProgram().isEmpty()) {
            handleEnablingSimulationButton(false);
            handleEnablingConfigurationButton(true);
        } else {
            handleEnablingSimulationButton(true);
            handleEnablingConfigurationButton(false);
        }
    }

    /**
     * Enables or disables the configuration buttons based on the provided boolean value.
     * Configuration buttons include loading shape, loading program, and generating robots.
     *
     * @param s True to disable the buttons, false to enable them.
     */
    private void handleEnablingConfigurationButton(boolean s) {
        loadShapeButton.setDisable(s);
        loadProgramButton.setDisable(s);
        generateRobotsButton.setDisable(s);
    }

    /**
     * Enables or disables the simulation buttons based on the provided boolean value.
     * Simulation buttons include starting, pausing, and resetting the simulation.
     *
     * @param s True to disable the buttons, false to enable them.
     */
    private void handleEnablingSimulationButton(boolean s) {
        startSimulation.setDisable(s);
        startSimulationStepped.setDisable(s);
        resetButton.setDisable(s);
    }

    /**
     * Displays an alert dialog with the specified type, title, and message.
     *
     * @param alertType The type of alert dialog to display.
     * @param title     The title of the alert dialog.
     * @param message   The message content of the alert dialog.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a dialog for entering an integer number with the specified title.
     *
     * @param title The title of the number input dialog.
     * @return The entered integer number, or -1 if no valid number was provided.
     */
    private int showEnteringNumberDialogInt(String title) {
        TextInputDialog td = new TextInputDialog();
        td.setTitle(title);
        td.setHeaderText(title);
        Optional<String> text = td.showAndWait();
        return text.map(Integer::parseInt).orElse(-1);
    }

    /**
     * Displays a dialog for entering a double number with the specified title.
     *
     * @param title The title of the number input dialog.
     * @return The entered double number, or -1.0 if no valid number was provided.
     */
    private double showEnteringNumberDialogDouble(String title) {
        TextInputDialog td = new TextInputDialog();
        td.setTitle(title);
        td.setHeaderText(title);
        Optional<String> text = td.showAndWait();
        return text.map(Double::parseDouble).orElse(-1.0);
    }

    /**
     * Displays a file chooser dialog for selecting a file with the specified title.
     *
     * @param title The title of the file chooser dialog.
     * @return The selected File object, or null if no file was selected.
     */
    private File showFileChooser(String title) {
        fileChooser.setTitle(title);
        Stage stage = (Stage) loadShapeButton.getScene().getWindow();
        return fileChooser.showOpenDialog(stage);
    }

    /**
     * Loads shapes from the specified file into the simulation environment.
     *
     * @param file The file containing the shapes to be loaded.
     * @return True if the shapes were loaded successfully, false otherwise.
     */
    private boolean loadShapeFromFile(File file) {
        EnvironmentParser shapeParser = new EnvironmentParser(ShapeChecker.DEFAULT_CHECKER);
        try {
            shapeParser.parseEnvironment(file);
            this.simulationController.getEnvironment().getShapes().clear();
            shapeParser.getShapes().forEach(s -> this.simulationController.getEnvironment().getShapes().add(s));
        } catch (IOException | EnvironmentException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while parsing the file: " + e);
            return false;
        }
        return true;
    }

    /**
     * Loads robot program commands from the specified file into the simulation environment.
     *
     * @param file The file containing the robot program commands to be loaded.
     * @return True if the program was loaded successfully, false otherwise.
     */
    private boolean loadProgramFormFile(File file) {
        CommandParser commandParser = new CommandParser();
        try {
            commandParser.parseRobotProgram(file);
            this.simulationController.getProgram().clear();
            commandParser.getCommands().forEach(c -> this.simulationController.getProgram().add(c));
        } catch (CommandException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while parsing the file: " + e);
            return false;
        }
        return true;
    }

    /**
     * Updates the text area with information about the shapes loaded from the specified file.
     *
     * @param file The file containing the shapes' information.
     */
    private void updateShapeInfo(File file) {
        try {
            shapesLoadedDetails.clear();
            List<String> lines = Files.readAllLines(file.toPath());
            lines.forEach(s -> shapesLoadedDetails.appendText(s + "\n"));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while loading the file: " + e);
        }
    }

    /**
     * Updates the text area with information about the program loaded from the specified file.
     *
     * @param file The file containing the program information.
     */
    private void updateProgramInfo(File file) {
        try {
            programLoadedDetails.clear();
            List<String> lines = Files.readAllLines(file.toPath());
            lines.forEach(s -> programLoadedDetails.appendText(s + "\n"));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while loading the file: " + e);
        }
    }

    /**
     * Updates the chart display by running the updateChart method in the JavaFX application thread.
     */
    private void updateChart() {
        Platform.runLater(() -> chartController.updateChart());
    }

    /**
     * Callback method invoked when the simulation state changes. Updates the chart display.
     */
    @Override
    public void simulationStateChanged() {
        updateChart();
    }

    /**
     * Simulates the execution of the loaded program over a specified duration of time.
     * Enables the simulation buttons during the simulation and displays an alert dialog when the simulation finishes.
     *
     * @param dt   The duration of each simulation step.
     * @param time The total duration of the simulation.
     */
    @Override
    public void simulate(double dt, double time) {
        try {
            this.handleEnablingSimulationButton(true);
            Timeline simulationTimeline = new Timeline(new KeyFrame(
                    Duration.seconds(dt),
                    event -> simulationController.step()
            ));
            simulationTimeline.setCycleCount((int) Math.ceil(time / dt));

            simulationTimeline.setOnFinished(actionEvent ->
                    Platform.runLater(() -> {
                        showAlert(Alert.AlertType.INFORMATION, "Simulation", "Simulation finished.");
                        this.handleEnablingSimulationButton(false);
                    })
            );

            simulationTimeline.play();
        } catch (Exception e) {
            this.showAlert(Alert.AlertType.ERROR, "Simulation", "Error while simulating: " + e);
        }
    }
}