package app.ui.console;


import app.controller.App;
import app.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.AuthFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class MainMenuUI implements Runnable {

    public MainMenuUI() {
    }

 AuthFacade aF = App.getInstance().getCompany().getAuthFacade();

    @Override
    public void run() {

        if (aF.getCurrentUserSession().getUserId() !=null) {
            aF.getCurrentUserSession().doLogout();
            System.out.printf("%nLogout was successfully completed!%n");
        }

        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Do Login", new AuthUI()));
        options.add(new MenuItem("Know the Development Team", new DevTeamUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nMain Menu");
            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        }
        while (option != -1);

    }


}

