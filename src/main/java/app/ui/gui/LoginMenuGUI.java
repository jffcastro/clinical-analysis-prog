package app.ui.gui;

import app.controller.AuthController;
import app.domain.shared.Constants;
import app.ui.console.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;


import java.io.IOException;
import java.util.List;


/**
 * The type Login menu gui.
 */
public class LoginMenuGUI {

    private final AuthController controller = new AuthController();

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private TextField emailTxtField;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField pwdTxtField;

    @FXML
    private Pane tittlePane;

    /**
     * Do login.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void doLogin(ActionEvent event) throws IOException {

        if (controller.doLogin(emailTxtField.getText(), pwdTxtField.getText())) {

            List<UserRoleDTO> roles = controller.getUserRoles();
            if ((roles != null) && (!roles.isEmpty())) {
                UserRoleDTO role = roles.get(0);
                redirectToRoleUI(role, event);
            } else {
                Alert alertNoRoles = new Alert(Alert.AlertType.ERROR);
                alertNoRoles.setTitle("Login Failed");
                alertNoRoles.setContentText("User has no roles");
                alertNoRoles.showAndWait();
            }


        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Login Unsuccessful");
            alert.setContentText("Invalid Login. Try again");
            alert.showAndWait();

        }

    }

    private void redirectToRoleUI(UserRoleDTO role, ActionEvent event) throws IOException {

        switch (role.getId()) {
            case Constants.ROLE_CENTRE_COORDINATOR:
                toCenterCoordinatorScene(event);
                break;
            case Constants.ROLE_NURSE:
                toNurseScene(event);
                break;

        }
    }

    private void toCenterCoordinatorScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/center-coordinator-menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    private void toNurseScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/nurse-menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    /**
     * Does the logout.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void logout(ActionEvent event) throws IOException {
        controller.doLogout();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout Successful");
        alert.setContentText("You have been logged out.");
        alert.showAndWait();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login-menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}