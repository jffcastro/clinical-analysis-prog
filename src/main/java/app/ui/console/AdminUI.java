package app.ui.console;

import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */

public class AdminUI implements Runnable{
    public AdminUI()
    {
    }

    public void run()
    {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register a vaccination center.", new CreateVaccinationCenterUI()));
        options.add(new MenuItem("Register an Employee.", new RegisterNewEmployeeUI()));
        options.add(new MenuItem("Get a list of Employees.", new GetListOfEmployeesUI()));
        options.add(new MenuItem("Specify a new vaccine type", new SpecifyNewVaccineTypeUI()));
        options.add(new MenuItem("Specify a new vaccine and its administration process.", new SpecifyVaccineAndAdminProcessUI()));
        options.add(new MenuItem("Load a CSV file or get a list of SNS Users", new LoadCSVUI()));

        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nAdmin Menu:");

            if ( (option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        }
        while (option != -1 );
    }
}
