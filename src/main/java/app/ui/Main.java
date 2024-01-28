package app.ui;

import app.controller.App;
import app.ui.console.MainMenuUI;
import app.ui.console.utils.Utils;

/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */

public class Main {

    public static void main(String[] args) {

        Utils.bootstrap();


        App.getInstance().runDailyTasks();
        try {

            MainMenuUI menu = new MainMenuUI();

            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}