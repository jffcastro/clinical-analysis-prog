package app.controller;

import app.domain.model.*;
import app.miscellaneous.ExportListToFile;
import app.stores.VaccinationCentersStore;
import app.ui.console.utils.Utils;
import app.ui.gui.CheckListVacStatsGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * The type Check and export vaccination stats controller.
 */
public class CheckAndExportVaccinationStatsController {

    private final VaccinationCenter center;
    private LocalDate firstDate;
    private LocalDate lastDate;
    private CheckAndExportVaccinationStatsController controllerInfo;

    /**
     * Sets controller info.
     *
     * @param controllerInfo the controller info
     */
    public void setControllerInfo(CheckAndExportVaccinationStatsController controllerInfo) {
        this.controllerInfo = controllerInfo;
    }

    /**
     * Sets first date.
     *
     * @param firstDate the first date
     */
    public void setFirstDate(LocalDate firstDate) {
        this.firstDate = firstDate;
    }

    /**
     * Sets last date.
     *
     * @param lastDate the last date
     */
    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    /**
     * Gets first date.
     *
     * @return the first date
     */
    public LocalDate getFirstDate() {
        return firstDate;
    }

    /**
     * Gets last date.
     *
     * @return the last date
     */
    public LocalDate getLastDate() {
        return lastDate;
    }

    /**
     * Instantiates a new Check and export vaccination stats controller.
     */
    public CheckAndExportVaccinationStatsController() {
        final Company company = App.getInstance().getCompany();
        final VaccinationCentersStore store = company.getVaccinationCentersStore();
        String id = Utils.getLoggedCoordinatorId();
        center = store.getVaccinationCenterAssociatedToCoordinator(id);
    }

    /**
     * Check if dates are valid.
     *
     * @return an int related to the outcome
     */
    public int checkIfDatesAreValid() {
        if (firstDate == null || lastDate == null)
            return 1;
        if (firstDate.isAfter(lastDate))
            return 2;
        if (firstDate.isBefore(LocalDate.of(2021, 1, 1)))
            return 3;
        if (lastDate.isAfter(LocalDate.now()))
            return 4;
        return 0;
    }

    /**
     * Export vaccination stats.
     *
     * @param fileName the file name
     * @return true if the file is exported successfully
     */
    public boolean exportVaccinationStats(String fileName) {
        List<String> vaccinationStats = center.getVaccinationStatsListBetweenDates(firstDate, lastDate);
        ExportListToFile exportListToFile = new ExportListToFile(fileName, vaccinationStats, ExportListToFile.FILE_TYPE_CSV);
        return exportListToFile.exportList("Date;Total");
    }

    /**
     * Gets vaccination stats list between dates.
     *
     * @return the vaccination stats list between dates
     */
    public List<String> getVaccinationStatsListBetweenDates() {
        return center.getVaccinationStatsListBetweenDates(firstDate, lastDate);
    }

    /**
     * To check vaccination stats scene.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void toCheckVaccinationStatsScene(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/check-vac-stats.fxml"));
        root = loader.load();

        CheckListVacStatsGUI nextSceneUi = loader.getController();
        nextSceneUi.setController(controllerInfo);
        nextSceneUi.setStatsTable();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}




