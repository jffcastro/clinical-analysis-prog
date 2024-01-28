package app.ui.gui;

import app.controller.CenterCoordinatorMenuController;
import app.controller.CheckAndExportVaccinationStatsController;
import app.controller.DataFromLegacySystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Center coordinator gui.
 */
public class CenterCoordinatorGUI {

    private final LoginMenuGUI loginMenuGUI = new LoginMenuGUI();
    private final CenterCoordinatorMenuController controller = new CenterCoordinatorMenuController();


    @FXML
    private Pane tittlePane;


    /**
     * Logout.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    public void logout(ActionEvent event) throws IOException {
        loginMenuGUI.logout(event);
    }


    /**
     * Check and export stats.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void checkAndExportStats(ActionEvent event) throws IOException {
        switch (controller.companyHasEnoughInfoForVaccinationStats()) {
            case 0:
                toCheckAndExportStatsGUI(event);
                break;
            case 1:
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Vaccination Centers");
                alert.setContentText("There are no Vaccination Centers yet.");
                alert.showAndWait();
                break;
            case 2:
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Statistics");
                alert.setContentText("There are no Statistics for this Vaccination Center, yet.");
                alert.showAndWait();
                break;

        }

    }

    private void toCheckAndExportStatsGUI(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/check-export-vac-stats-ui.fxml"));
        root = loader.load();

        CheckAndExportVacStatsGUI mainScene = loader.getController();
        mainScene.setController(new CheckAndExportVaccinationStatsController());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Analyze center performance.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void analyzeCenterPerformance(ActionEvent event) throws IOException {
        switch (controller.companyHasEnoughDataToAnalyzeThePerformance()) {
            case 0:
                toAnalyzeCenterPerformanceGUI(event);
                break;
            case 1:
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Vaccination Centers");
                alert.setContentText("There are no Vaccination Centers yet.");
                alert.showAndWait();
                break;
            case 2:
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Arrivals");
                alert.setContentText("There are no Arrivals registered on this Center, yet.");
                alert.showAndWait();
                break;
            case 3:
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Departures");
                alert.setContentText("There are no Departures registered on this Center, yet.");
                alert.showAndWait();
                break;
        }

    }

    private void toAnalyzeCenterPerformanceGUI(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/analyze-center-performance.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * To import data from legacy system.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void toImportDataFromLegacySystem(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/read-legacy-data-file.fxml"));
        root = loader.load();

        ReadLegacyDataFileGUI mainScene = loader.getController();
        mainScene.setController(new DataFromLegacySystemController());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
