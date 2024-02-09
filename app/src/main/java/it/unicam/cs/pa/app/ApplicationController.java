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
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public class ApplicationController implements SimulationListener, Simulator {
    private SimulationController simulationController;

    private ChartController chartController;

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

    @FXML
    private void handleStartSimulationStepped(ActionEvent event) {
        this.simulationController.step();
        event.consume();
    }

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

    private int showEnteringNumberDialogInt(String title) {
        TextInputDialog td = new TextInputDialog();
        td.setTitle(title);
        td.setHeaderText(title);
        Optional<String> text = td.showAndWait();
        return text.map(Integer::parseInt).orElse(-1);
    }

    private double showEnteringNumberDialogDouble(String title) {
        TextInputDialog td = new TextInputDialog();
        td.setTitle(title);
        td.setHeaderText(title);
        Optional<String> text = td.showAndWait();
        return text.map(Double::parseDouble).orElse(-1.0);
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
            this.simulationController.getEnvironment().getShapes().clear();
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
            this.simulationController.getProgram().clear();
            commandParser.getCommands().forEach(c -> this.simulationController.getProgram().add(c));
        } catch (CommandException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while parsing the file: " + e);
            return false;
        }
        return true;
    }

    private void updateShapeInfo(File file) {
        try {
            shapesLoadedDetails.clear();
            List<String> lines = Files.readAllLines(file.toPath());
            lines.forEach(s -> shapesLoadedDetails.appendText(s + "\n"));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while loading the file: " + e);
        }
    }

    private void updateProgramInfo(File file) {
        try {
            programLoadedDetails.clear();
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