package app.ui.gui;

import app.controller.RecordVaccineAdministrationController;
import app.domain.shared.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class RecordVaccineAdministrationGUI {

    private final RecordVaccineAdministrationController controller = new RecordVaccineAdministrationController();

    @FXML
    private ComboBox<String> vaccinationCenterList;

    @FXML
    private ComboBox<String> userList;

    @FXML
    private ComboBox<String> vaccineList;

    @FXML
    private javafx.scene.control.TextField userNameTxt;

    @FXML
    private javafx.scene.control.TextField userAgeTxt;

    @FXML
    private javafx.scene.control.TextField vaccineTypeTxt;

    @FXML
    private javafx.scene.control.TextField lotNumberTxt;

    @FXML
    private javafx.scene.control.TextField dosageTxt;

    @FXML
    private javafx.scene.control.TextField doseTxt;

    @FXML
    private Button recordButton;

    @FXML
    private Button cancelButton;


    /**
     * Confirm center selection.
     *
     * @param event the event
     */
    public void confirmCenterSelection(ActionEvent event) {
        verifyCenter(event);
    }

    private void verifyCenter(ActionEvent event) {
        // Set selected Center
        setVaccinationCenter();

        // Disable the checkbox and the combo box
        disableComboBoxCenter();
        userList.setDisable(false);
    }

    /**
     * Confirm user selection.
     *
     * @param event the event
     */
    public void confirmUserSelection(ActionEvent event) {
        verifyUser(event);
    }

    private void verifyUser(ActionEvent event) {
        // Set selected User
        setUser();

        // Get User´s Name
        getUserName();

        // Get User´s Age
        getUserAge();

        // Disable the checkbox and the combo boxes
        disableComboBoxUser();
        vaccineList.setDisable(false);
        controller.setVaccineType(userList.getSelectionModel().getSelectedIndex());

        if (controller.findLastDoseOfVaccineType() != Constants.FIRST_DOSE) initializeVaccineNotFirstDose();
    }

    /**
     * Confirm vaccine selection.
     *
     * @param event the event
     */
    public void confirmVaccineSelection(ActionEvent event) {
        verifyVaccine(event);
    }

    private void verifyVaccine(ActionEvent event) {
        // Set selected Vaccine or Previous Vaccine
        initializeVaccine();

        // Disable the checkbox and the combo boxes
        disableComboBoxVaccine();

        // Set Vaccine Type
        getVaccineTypeName();

        // Set Dosage
        getDosageQuantity();

        // Set Dose Number
        getDoseNumber();

        controller.setLocalDateTime();
    }

    @FXML
    private void recordVaccineAdministrationConfirmed(javafx.event.ActionEvent event) throws IOException {
        if (lotNumberTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You must introduce a lot number");
            alert.showAndWait();
        } else {
            if (!controller.validateLotNumber(lotNumberTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Lot Number is not valid");
                alert.showAndWait();
            } else {
                controller.setLotNumber(lotNumberTxt.getText());
                recordVaccineAdministration(event);
                returnToNurseGUI(event);
                printRecoveryTimeSMS();
            }
        }
    }

    @FXML
    private void returnToNurseGUI(javafx.event.ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/nurse-menu.fxml"));
        root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialize center.
     */
    @FXML
    public void initializeCenter() {
        ObservableList<String> vaccinationCenterNameList = FXCollections.observableArrayList(controller.vaccinationCentersAvailable());
        vaccinationCenterList.setItems(vaccinationCenterNameList);
    }

    @FXML
    private void initializeUser() {
        ObservableList<String> userSnsNumberList = FXCollections.observableArrayList(controller.fillListWithUserSnsNumber());
        userList.setItems(userSnsNumberList);
    }

    /**
     * Initialize vaccine.
     */
    public void initializeVaccine() {
        if (controller.getUserNumberOfDoses() == Constants.FIRST_DOSE) {
            ObservableList<String> vaccineNameList = FXCollections.observableArrayList(controller.vaccineAvailableName());
            vaccineList.setItems(vaccineNameList);
            if (vaccineList.getSelectionModel().getSelectedIndex() >= 0)
                controller.setVaccine(vaccineList.getSelectionModel().getSelectedIndex());
        }
    }

    /**
     * Initialize vaccine not first dose.
     */
    public void initializeVaccineNotFirstDose() {
        int currentAppointment = controller.findLastDoseOfVaccineType();
        controller.setVaccine(currentAppointment);
        vaccineList.setValue(controller.getVaccineName());
        vaccineList.setDisable(true);
    }

    private void setVaccinationCenter() {
        controller.setVaccinationCenter(vaccinationCenterList.getSelectionModel().getSelectedIndex());
    }

    private void setUser() {
        controller.setSnsUser(controller.getSnsUserInformation(userList.getSelectionModel().getSelectedIndex()));
    }

    private void getUserName() {
        if (controller.getSnsUserName() != null)
            userNameTxt.setText(controller.getSnsUserName());
    }

    private void getUserAge() {
        if (controller.getUserAge() != 0)
            userAgeTxt.setText(String.valueOf(controller.getUserAge()));
    }

    private void getVaccineTypeName() {
        vaccineTypeTxt.setText(controller.getVaccineTypeName());
    }

    private void getDosageQuantity() {
        if (controller.getUserNumberOfDoses() == Constants.FIRST_DOSE)
            dosageTxt.setText(String.valueOf(controller.dosageForDose(Constants.INVALID_VALUE, vaccineList.getSelectionModel().getSelectedIndex())));
        else
            dosageTxt.setText(String.valueOf(controller.dosageForDose(controller.getUserNumberOfDoses(), controller.findLastDoseOfVaccineType())));
    }

    private void getDoseNumber() {
        doseTxt.setText(String.valueOf(controller.getDoseNumber()));
    }

    private void disableComboBoxCenter() {
        vaccinationCenterList.setDisable(true);
    }

    private void disableComboBoxUser() {
        userList.setDisable(true);
    }

    private void disableComboBoxVaccine() {
        vaccineList.setDisable(true);
    }

    private void recordVaccineAdministration(javafx.event.ActionEvent event) {
        controller.registerVaccineInVaccineBulletin();
    }

    private void printRecoveryTimeSMS() {
        controller.setRecoveryTimeSMS();
    }
}