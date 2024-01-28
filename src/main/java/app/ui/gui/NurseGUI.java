package app.ui.gui;

import app.controller.NurseMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class NurseGUI {

    private final LoginMenuGUI loginMenuGUI = new LoginMenuGUI();

    private final NurseMenuController controller = new NurseMenuController();

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

    public void recordVaccineAdministration(ActionEvent event) throws IOException {
        switch (controller.vaccineAdministrationRequirements()){
            case 0:
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/record-vaccine-administration.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
                break;
            case 1:
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Vaccination Centers");
                alert.setContentText("There are no Vaccination Centers yet.");
                alert.showAndWait();
                break;
            case 2:
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Vaccines");
                alert.setContentText("There are no Vaccines yet.");
                alert.showAndWait();
                break;
            case 3:
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Vaccine Types");
                alert.setContentText("There are no Vaccines Types yet.");
                alert.showAndWait();
        }
    }

}
