package app.controller;

import app.domain.model.Company;
import app.domain.model.SnsUser;
import app.domain.model.VaccinationCenter;
import app.domain.model.Vaccine;
import app.domain.shared.Constants;
import app.domain.shared.GenericClass;
import app.miscellaneous.ReadLegacyDataFile;
import app.stores.VaccinationCentersStore;
import app.ui.console.utils.Utils;
import app.ui.gui.SortedListGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Medium between classes that will be used for US017
 *
 * @author João Castro <1210816@isep.ipp.pt>
 */
public class DataFromLegacySystemController {

    private final Company company = App.getInstance().getCompany();
    private List<String> sortedList;
    private VaccinationCenter center;
    private final ReadLegacyDataFile readLegacyDataFile;

    private DataFromLegacySystemController controllerInfo;

    private int optionArrivalOrDeparture;
    private int optionAscendingOrDescending;
    private File file;


    /**
     * Sets controller info.
     *
     * @param controllerInfo the controller info
     */
    public void setControllerInfo(DataFromLegacySystemController controllerInfo) {
        this.controllerInfo = controllerInfo;
    }


    /**
     * Instantiates a new Data from legacy system controller.
     */
    public DataFromLegacySystemController() {
        final Company company = App.getInstance().getCompany();
        final VaccinationCentersStore store = company.getVaccinationCentersStore();
        String id = Utils.getLoggedCoordinatorId();
        center = store.getVaccinationCenterAssociatedToCoordinator(id);
        readLegacyDataFile = new ReadLegacyDataFile(center);
    }


    /**
     * Gets sorting algorithm.
     *
     * @return the sorting algorithm
     */
    public String getSortingAlgorithm() {
        return App.getInstance().getSortingAlgorithm();
    }


    /**
     * Gets sns user list.
     *
     * @return the sns user list
     */
    public ArrayList<SnsUser> getSNSUserList() {
        return company.getSnsUsersStore().getSnsUserList();
    }

    /**
     * Gets vaccines.
     *
     * @return the vaccines
     */
    public List<Vaccine> getVaccines() {
        return company.getVaccinesList();
    }

    /**
     * Export data to file.
     *
     * @param csvLegacyData the csv legacy data
     * @throws NotSerializableException the not serializable exception
     */
    public void exportDataToFile(List<String> csvLegacyData) throws NotSerializableException {
        GenericClass<String> generics = new GenericClass<>();
        generics.binaryFileWrite(Constants.FILE_PATH_UPDATEDLEGACY, csvLegacyData);
    }

    /**
     * Read file.
     *
     * @throws Exception the exception
     */
    public void readFile() throws Exception {
        readLegacyDataFile.readFile(this.file.getAbsolutePath());
    }

    /**
     * Update file boolean.
     *
     * @return the boolean
     * @throws NotSerializableException the not serializable exception
     */
    public List<String> updateFile() throws NotSerializableException {
       return readLegacyDataFile.updateLegacyFile();
    }

    /**
     * Set list.
     *
     * @param position the position
     */
    public void setList(int position){
        readLegacyDataFile.setList(position);
    }

    /**
     * Merge ascending list.
     *
     * @param list the list
     * @return the list
     */
    public List<String> insertionSortAscending(List<LocalDateTime> list){
       return readLegacyDataFile.insertionSortAscending(list);
    }

    /**
     * Merge descending list.
     *
     * @param list the list
     * @return the list
     */
    public List<String> insertionSortDescending(List<LocalDateTime> list){
         return readLegacyDataFile.insertionSortDescending(list);
    }

    /**
     * Get list to sort list.
     *
     * @return the list
     */
    public List<LocalDateTime> getListToSort(){
        return readLegacyDataFile.listToSort;
    }

    /**
     * Heap ascending list.
     *
     * @return the list
     */
    public List<String> heapAscending(){
     return    readLegacyDataFile.heapSortAscending();
    }

    /**
     * Heap descending list.
     *
     * @return the list
     */
    public List<String> heapDescending(){
      return readLegacyDataFile.heapSortDescending();
    }

    /**
     * Gets the algorithm set in the config file.
     *
     * @return the string
     */
    public String getAlgorithm(){
        return App.getInstance().getSortingAlgorithm();
    }

    /**
     * Choose criteria to sort.
     *
     * @param option the option
     */
    public void chooseCriteriaToSort(int option) {
        final int ArrivalOption = 6;
        final int LeaveOption = 8;
        switch (option) {
            case 0:
                setList(ArrivalOption);
                break;
            case 1:
                setList(LeaveOption);
                break;
            case 2:
                break;
        }
    }

    /**
     * Sort list with algo list.
     *
     * @param algorithm the algorithm
     * @param option    the option
     * @return the list
     */
    public List<String> sortListWithAlgo(String algorithm, int option) {
        switch (algorithm) {
            case "HeapSort":
                switch (option) {
                    case 0:
                        sortedList = heapAscending();
                        return sortedList;
                    case 1:
                        sortedList = heapDescending();
                        return sortedList;
                    case 2:
                        break;
                }
                break;
            case "InsertionSort":
                switch (option) {
                    case 0:
                        sortedList = insertionSortAscending(getListToSort());
                        return sortedList;
                    case 1:
                        sortedList =  insertionSortDescending(getListToSort());
                        return sortedList;
                    case 2:
                        break;
                }
                break;
        }
        return null;
    }


    /**
     * Gets option arrival or departure.
     *
     * @return the option arrival or departure
     */
    public int getOptionArrivalOrDeparture() {
        return optionArrivalOrDeparture;
    }

    /**
     * Sets option arrival or departure.
     *
     * @param option the option
     */
    public void setOptionArrivalOrDeparture(int option) {
        optionArrivalOrDeparture = option;
    }


    /**
     * Gets option ascending or descending.
     *
     * @return the option ascending or descending
     */
    public int getOptionAscendingOrDescending() {
        return optionAscendingOrDescending;
    }

    /**
     * Sets option ascending or descending.
     *
     * @param option the option
     */
    public void setOptionAscendingOrDescending(int option) {
        optionAscendingOrDescending = option;
    }


    /**
     * To check vaccination stats scene.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void toSortedListScene(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sorted-list.fxml"));
        root = loader.load();

        SortedListGUI nextSceneUi = loader.getController();
        nextSceneUi.setController(controllerInfo);
        nextSceneUi.setStatsTable();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Sets file.
     *
     * @param file the file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Gets file.
     *
     * @return the file
     */
    public File getFile() {
        return file;
    }
}
