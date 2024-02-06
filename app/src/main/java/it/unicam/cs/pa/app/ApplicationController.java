package it.unicam.cs.pa.app;

import it.unicam.cs.pa.SimulationController;
import it.unicam.cs.pa.SimulationListener;
import it.unicam.cs.pa.Simulator;
import it.unicam.cs.pa.environment.EnvironmentException;
import it.unicam.cs.pa.environment.EnvironmentParser;
import it.unicam.cs.pa.environment.shapes.ShapeChecker;
import it.unicam.cs.pa.robot.commands.CommandException;
import it.unicam.cs.pa.robot.commands.CommandParser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public class ApplicationController implements SimulationListener, Simulator {
    private SimulationController simulationController;

    private ChartController chartController;

    private final int MAX_THREADS = Runtime.getRuntime().availableProcessors() * 2;

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

    @FXML
    private void handleGenerateRobotsButton(ActionEvent event) {
        try {
            int n = showEnteringNumberDialog(String.format("Enter the number of robots to spawn (MAX = %s).", MAX_THREADS));
            if (n <= 0)
                showAlert(Alert.AlertType.WARNING, "Warning", "No robots generated.");
            else if (n <= MAX_THREADS) {
                this.simulationController.getEnvironment().getRobots().clear();
                this.simulationController.getEnvironment().generateRandomRobots(n, ChartController.INITIAL_GRAPH_BOUND);
                updateChart();
            } else
                showAlert(Alert.AlertType.ERROR, "Error", "Trying to generate too much robots.");
            checkIfSimulationCanStart();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No number of robots to generate provided.");
        }
        event.consume();
    }

    @FXML
    private void handleStartSimulation(ActionEvent event) {
        int time = showEnteringNumberDialog("Enter the duration of the simulation.");
        int dt = showEnteringNumberDialog("Enter the duration of each instruction.");
        this.simulate(dt, time);
        event.consume();
    }

    @FXML
    private void handleStartSimulationStepped(ActionEvent event) {
        this.simulationController.step();
        event.consume();
    }

    @FXML
    private void handleResetButton(ActionEvent event) {
        this.simulationController.interrupt();
        this.simulationController.getEnvironment().getRobots().clear();
        this.simulationController.getEnvironment().getShapes().clear();
        this.programLoadedDetails.clear();
        this.shapesLoadedDetails.clear();
        updateChart();
        checkIfSimulationCanStart();
        event.consume();
    }

    @FXML
    private void handleZoomInButton(ActionEvent event) {
        chartController.zoomIn();
        event.consume();
    }

    @FXML
    private void handleZoomOutButton(ActionEvent event) {
        chartController.zoomOut();
        event.consume();
    }

    @FXML
    private void handlePanLeftButton(ActionEvent event) {
        chartController.panLeft();
        event.consume();
    }

    @FXML
    private void handlePanRightButton(ActionEvent event) {
        chartController.panRight();
        event.consume();
    }

    @FXML
    private void handlePanUpButton(ActionEvent event) {
        chartController.panUp();
        event.consume();
    }

    @FXML
    private void handlePanDownButton(ActionEvent event) {
        chartController.panDown();
        event.consume();
    }

    @FXML
    private void handlePanCenterButton(ActionEvent event) {
        chartController.panCenter();
        event.consume();
    }

    private void checkIfSimulationCanStart() {
        if (!this.simulationController.getEnvironment().getShapes().isEmpty() &&
                !this.simulationController.getEnvironment().getRobots().isEmpty() &&
                !this.simulationController.getProgram().isEmpty()) {
            handleEnablingSimulationButton(false);
            handleEnablingConfigurationButton(true);
            this.simulationController.setUpSimulation();
        } else {
            handleEnablingSimulationButton(true);
            handleEnablingConfigurationButton(false);
        }
    }

    private void handleEnablingConfigurationButton(boolean s) {
        loadShapeButton.setDisable(s);
        loadProgramButton.setDisable(s);
        generateRobotsButton.setDisable(s);
    }

    private void handleEnablingSimulationButton(boolean s) {
        startSimulation.setDisable(s);
        startSimulationStepped.setDisable(s);
        resetButton.setDisable(s);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int showEnteringNumberDialog(String title) {
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Input Dialog");
        td.setHeaderText(title);
        Optional<String> text = td.showAndWait();
        return text.map(Integer::parseInt).orElse(-1);
    }

    private File showFileChooser(String title) {
        fileChooser.setTitle(title);
        Stage stage = (Stage) loadShapeButton.getScene().getWindow();
        return fileChooser.showOpenDialog(stage);
    }

    private boolean loadShapeFromFile(File file) {
        EnvironmentParser shapeParser = new EnvironmentParser(ShapeChecker.DEFAULT_CHECKER);
        try {
            shapeParser.parseEnvironment(file);
            shapeParser.getShapes().forEach(s -> this.simulationController.getEnvironment().getShapes().add(s));
        } catch (IOException | EnvironmentException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while parsing the file: " + e);
            return false;
        }
        return true;
    }

    private boolean loadProgramFormFile(File file) {
        CommandParser commandParser = new CommandParser();
        try {
            commandParser.parseRobotProgram(file);
            commandParser.getCommands().forEach(c -> this.simulationController.getProgram().add(c));
        } catch (CommandException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while parsing the file: " + e);
            return false;
        }
        return true;
    }

    private void updateShapeInfo(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            lines.forEach(s -> shapesLoadedDetails.appendText(s + "\n"));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while loading the file: " + e);
        }
    }

    private void updateProgramInfo(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            lines.forEach(s -> programLoadedDetails.appendText(s + "\n"));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while loading the file: " + e);
        }
    }

    private void updateChart() {
        Platform.runLater(() -> chartController.updateChart());
    }

    @Override
    public void simulationStateChanged() {
        updateChart();
    }

    public void simulate(double dt, double time) {
        double currentTime = 0;
        while (currentTime < time) {
            this.simulationController.step();
            currentTime += dt;
        }
    }
}