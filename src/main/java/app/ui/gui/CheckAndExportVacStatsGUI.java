package app.ui.gui;

import app.controller.CheckAndExportVaccinationStatsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The type Check and export vac stats gui.
 */
public class CheckAndExportVacStatsGUI {

    private  CheckAndExportVaccinationStatsController controller;

    public void setController(CheckAndExportVaccinationStatsController controller) {
        this.controller = controller;
        controller.setControllerInfo(controller);
    }

    @FXML
    private DatePicker firstDatePicker;

    @FXML
    private DatePicker lastDatePicker;

    /**
     * Sets first date picker.
     *
     * @param firstDate the first date
     */
    public void setFirstDatePicker(LocalDate firstDate) {
        this.firstDatePicker.setValue(firstDate);
    }

    /**
     * Sets last date picker.
     *
     * @param lastDate the last date
     */
    public void setLastDatePicker(LocalDate lastDate) {
        this.lastDatePicker.setValue(lastDate);
    }

    @FXML
    private Button okBtn;
    @FXML
    private TextArea askFileNameTxt;

    /**
     * Gets the first date.
     *
     * @param event the event
     */
    @FXML
    void setFirstDate(ActionEvent event) {
        controller.setFirstDate(firstDatePicker.getValue());
    }

    /**
     * Gets the last date.
     *
     * @param event the event
     */
    @FXML
    void setLastDate(ActionEvent event) {
        controller.setLastDate(lastDatePicker.getValue());
    }

    /**
     * Goes to the previous scene.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    public void back(ActionEvent event) throws IOException {
        toCenterCoordinatorMenu(event);
    }

    /**
     * Check statistics.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void checkStatistics(ActionEvent event) throws IOException {

            switch (controller.checkIfDatesAreValid()) {
                case 0:
                    toCheckVaccinationStatsScene(event);
                    break;
                case 1:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("You must select both dates");
                    alert.showAndWait();
                    break;
                case 2:
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error");
                    alert2.setContentText("The first date must be before the last date");
                    alert2.showAndWait();
                    break;
                case 3:
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setTitle("Error");
                    alert3.setContentText("The first date must be after the 1st of January 2021");
                    alert3.showAndWait();
                    break;
                case 4:
                    Alert alert4 = new Alert(Alert.AlertType.ERROR);
                    alert4.setTitle("Error");
                    alert4.setContentText("There are no statistics for dates after today");
                    alert4.showAndWait();
                    break;
            }

    }

    /**
     * Export statistics.
     *
     * @param event the event
     */
    public void exportStatistics(ActionEvent event) {

        String fileName = askFileNameTxt.getText();
        if (!fileName.isEmpty()) {
            if (controller.exportVaccinationStats(fileName)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("The available statistics from " + controller.getFirstDate() + " to " + controller.getLastDate() + " were exported successfully");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("The statistics were not exported successfully");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please enter a file name");
            alert.showAndWait();
        }
    }

    /**
     * Export statistics option.
     *
     * @param event the event
     */
    public void exportStatisticsOption(ActionEvent event) {

            switch (controller.checkIfDatesAreValid()) {
                case 0:
                    askFileNameTxt.setVisible(true);
                    okBtn.setVisible(true);
                    break;
                case 1:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("You must select both dates");
                    alert.showAndWait();
                    break;
                case 2:
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error");
                    alert2.setContentText("The first date must be before the last date");
                    alert2.showAndWait();
                    break;
                case 3:
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setTitle("Error");
                    alert3.setContentText("The first date must be after the 1st of January 2021");
                    alert3.showAndWait();
                    break;
                case 4:
                    Alert alert4 = new Alert(Alert.AlertType.ERROR);
                    alert4.setTitle("Error");
                    alert4.setContentText("There are no statistics for dates after today");
                    alert4.showAndWait();
                    break;
            }


    }

    private void toCheckVaccinationStatsScene(ActionEvent event) throws IOException {
        controller.toCheckVaccinationStatsScene(event);
    }

    private void toCenterCoordinatorMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/center-coordinator-menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
