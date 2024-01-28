package app.ui.gui;

import app.controller.DataFromLegacySystemController;
import app.miscellaneous.SortedListInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * The type Sorted list gui.
 */
public class SortedListGUI {

    private DataFromLegacySystemController controller;

    /**
     * Sets controller.
     *
     * @param controller the controller
     */
    public void setController(DataFromLegacySystemController controller) { this.controller = controller; }

    @FXML
    private TableView<SortedListInfo> tableView;

    @FXML
    private TableColumn<SortedListInfo, String> clName;

    @FXML
    private TableColumn<SortedListInfo, String> clSnsNumber;

    @FXML
    private TableColumn<SortedListInfo, String> clVaccineName;

    @FXML
    private TableColumn<SortedListInfo, String> clDose;

    @FXML
    private TableColumn<SortedListInfo, String> clLotNumber;

    @FXML
    private TableColumn<SortedListInfo, String> clScheduleDate;

    @FXML
    private TableColumn<SortedListInfo, String> clArrivalTime;

    @FXML
    private TableColumn<SortedListInfo, String> clNurseAdministrationTime;

    @FXML
    private TableColumn<SortedListInfo, String> clDepartureTime;

    @FXML
    private Label lbSortingAlgorithm;


    /**
     * Sets stats table.
     */
    public void setStatsTable() {
        controller.chooseCriteriaToSort(controller.getOptionArrivalOrDeparture());
        List<String> sortedlist = controller.sortListWithAlgo(controller.getSortingAlgorithm(), controller.getOptionAscendingOrDescending());
        String[][] info = new String[sortedlist.size()][9];
        ObservableList<SortedListInfo> observableList = FXCollections.observableArrayList();

        for (int position = 0; position < sortedlist.size(); position++) {
            info[position] = sortedlist.get(position).split("\\|");
            observableList.add(new SortedListInfo(info[position][0], info[position][1], info[position][2], info[position][3], info[position][4], info[position][5], info[position][6], info[position][7], info[position][8]));
        }

        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clSnsNumber.setCellValueFactory(new PropertyValueFactory<>("snsNumber"));
        clVaccineName.setCellValueFactory(new PropertyValueFactory<>("vaccineName"));
        clDose.setCellValueFactory(new PropertyValueFactory<>("dose"));
        clLotNumber.setCellValueFactory(new PropertyValueFactory<>("lotNumber"));
        clScheduleDate.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        clArrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        clNurseAdministrationTime.setCellValueFactory(new PropertyValueFactory<>("nurseAdminstrationTime"));
        clDepartureTime.setCellValueFactory(new PropertyValueFactory<>("departureTime"));

        tableView.setItems(observableList);

        lbSortingAlgorithm.setText(controller.getSortingAlgorithm());
    }


    /**
     * Back option.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void back(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/read-legacy-data-file.fxml"));
        root = loader.load();

        ReadLegacyDataFileGUI mainScene = loader.getController();
        mainScene.setController(controller);
        try {
            mainScene.showOptions();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
