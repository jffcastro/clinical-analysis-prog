package app.ui;


import app.ui.console.utils.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Utils.bootstrap();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login-menu.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("VaxCare - DGS Vaccination Management System");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    public static void main(String[] args) {
        launch();
    }


}
