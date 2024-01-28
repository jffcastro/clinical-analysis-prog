package app.ui.gui;

import app.controller.CheckAndExportVaccinationStatsController;
import app.miscellaneous.VaccinationCenterStats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * UI for the Check and Export Vaccination Stats
 */
public class CheckListVacStatsGUI {

    private  CheckAndExportVaccinationStatsController controller;

    public void setController(CheckAndExportVaccinationStatsController controller) {
        this.controller = controller;
    }

    @FXML
    private TableView<VaccinationCenterStats> tableView;

    @FXML
    private TableColumn<VaccinationCenterStats, String> dateCollumn;

    @FXML
    private TableColumn<VaccinationCenterStats, String> totalVaccinatedCollumn;

    /**
     * Sets the list view with the Vaccination Statistics.
     *
     */
    public void setStatsTable() {
        String[][] stats = new String[controller.getVaccinationStatsListBetweenDates().size()][2];
        ObservableList<VaccinationCenterStats> statsList = FXCollections.observableArrayList();

        for (int position = 0; position < stats.length; position++) {
            stats[position] = controller.getVaccinationStatsListBetweenDates( ).get(position).split(";");
            statsList.add(new VaccinationCenterStats(stats[position][0], stats[position][1]));
        }

        dateCollumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalVaccinatedCollumn.setCellValueFactory(new PropertyValueFactory<>("totalVaccinated"));
        tableView.setItems(statsList);
    }

    /**
     * Back to main scene.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void backToMainScene(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/check-export-vac-stats-ui.fxml"));
        root = loader.load();

        CheckAndExportVacStatsGUI mainScene = loader.getController();
        mainScene.setController(controller);
        mainScene.setFirstDatePicker(controller.getFirstDate());
        mainScene.setLastDatePicker(controller.getLastDate());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }





}
