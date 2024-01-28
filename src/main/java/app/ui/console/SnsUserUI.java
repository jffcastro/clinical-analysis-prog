package app.ui.console;

import app.domain.model.VaccinationCenter;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;
/**
 * Shows a list of all SNS Users.
 *
 * @author João Castro <1210816@isep.ipp.pt>
 */
public class SnsUserUI implements Runnable {
    @Override
    public void run() {

        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Schedule a vaccine.", new ScheduleVaccineUI()));



        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nSNS User Menu:");

            if ( (option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        }
        while (option != -1 );


    }
}
