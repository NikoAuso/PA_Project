package it.unicam.cs.pa.app;

import it.unicam.cs.pa.environment.Environment;
import it.unicam.cs.pa.environment.EnvironmentException;
import it.unicam.cs.pa.environment.EnvironmentParser;
import it.unicam.cs.pa.environment.shapes.ShapeChecker;
import it.unicam.cs.pa.robot.commands.CommandException;
import it.unicam.cs.pa.robot.commands.CommandParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public class SimulationController {
    private Environment environment;

    private ChartController chartController;

    @FXML
    private VBox window;

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
    private TextArea programLoadedDetails;

    @FXML
    private TextArea shapesLoadedDetails;

    @FXML
    private FileChooser fileChooser;

    @FXML
    private ScatterChart<Number, Number> chart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private void initialize() {
        loadShapeButton.setDisable(false);
        loadProgramButton.setDisable(false);
        generateRobotsButton.setDisable(false);
        startSimulation.setDisable(true);
        startSimulationStepped.setDisable(true);
        resetButton.setDisable(true);

        environment = new Environment();

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.txt", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        chartController = new ChartController(chart, xAxis, yAxis);
    }

    @FXML
    private void handleLoadShapeButton(ActionEvent event) throws IOException {
        fileChooser.setTitle("Load environment file");
        Stage stage = (Stage) loadShapeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null && selectedFile.exists()) {
            if (selectedFile.length() > 0) {
                if (loadDataFromFile(selectedFile)) {
                    updateShapeInfo(selectedFile);
                    chartController.drawFigures(environment.getShapes());
                }
            } else
                showAlert(Alert.AlertType.INFORMATION, "Info", "The selected file seems to be empty.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "No file selected.");
        }
        event.consume();
    }

    @FXML
    private void handleLoadProgramButton(ActionEvent event) {
        fileChooser.setTitle("Load program file");
        Stage stage = (Stage) loadShapeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

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
        event.consume();
    }

    @FXML
    private void handleGenerateRobotsButton(ActionEvent event) {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Enter the number of robots to spawn.");
        td.initOwner(window.getScene().getWindow());
        Optional<String> text = td.showAndWait();
        int numRobots;
        if (text.isPresent()) {
            numRobots = text.map(Integer::parseInt).orElse(-1);
            if (numRobots > 0) {
                this.environment.generateRandomRobots(numRobots);
                this.chartController.drawRobots(environment.getRobots());
            }else
                showAlert(Alert.AlertType.WARNING, "Warning", "You must define at least 1 robot to spawn");
        } else
            showAlert(Alert.AlertType.WARNING, "Warning", "No robots generated");
    }

    @FXML
    private void handleStartSimulation(ActionEvent event) {
        // Replace this with the actual action for starting the simulation
        System.out.println("Start Simulation Button Clicked");
    }

    @FXML
    private void handleStartSimulationStepped(ActionEvent event) {
        // Replace this with the actual action for starting the simulation step by step
        System.out.println("Start Simulation Stepped Button Clicked");
    }

    @FXML
    private void handleResetButton(ActionEvent event) {
        // Replace this with the actual action for resetting the simulation
        System.out.println("Reset Button Clicked");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean loadDataFromFile(File file) {
        try {
            EnvironmentParser shapeParser = new EnvironmentParser(ShapeChecker.DEFAULT_CHECKER);
            shapeParser.parseEnvironment(file);
            shapeParser.getShapes().forEach(s -> environment.getShapes().add(s));
            return true;
        } catch (IOException | EnvironmentException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while parsing the file: " + e);
            return false;
        }
    }

    private boolean loadProgramFormFile(File file) {
        try {
            CommandParser commandParser = new CommandParser();
            commandParser.parseRobotProgram(file);
            return true;
        } catch (CommandException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while parsing the file: " + e);
            return false;
        }
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
}
