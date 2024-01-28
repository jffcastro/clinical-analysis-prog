package app.ui.console;

import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class NurseUI implements Runnable {
    @Override
    public void run() {

        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Consult the users in the waiting room of a Vaccination Centre.", new ConsultUsersInTheWaitingRoomUI()));
        options.add(new MenuItem("Record Vaccine Administration.", new RecordVaccineAdministrationUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\nNurse Menu:");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        }
        while (option != -1);
    }


}

