package app.ui.console;

import app.controller.SpecifyNewVaccineTypeController;
import app.domain.model.VaccineType;
import app.ui.console.utils.Utils;

import java.io.NotSerializableException;
import java.util.Scanner;


/**
 * US012 - Specify Vaccine Type
 *
 * @author Pedro Monteiro <1211076@isep.ipp.pt>
 */


public class SpecifyNewVaccineTypeUI implements Runnable {

    private final SpecifyNewVaccineTypeController controller = new SpecifyNewVaccineTypeController();

    public SpecifyNewVaccineTypeUI() {
    }


    public void run() {


            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("------Specify Vaccine Type------");
            System.out.println();

            System.out.println("--Insert the new Vaccine Type Code:");
            String code = sc.next();
            sc.nextLine();


            System.out.println("--Insert a short description for the new Vaccine Type:");
            String description = sc.nextLine();
            System.out.println();


            System.out.println("--Select a technology:");
            String technology = null;
            int options = 1;
            for (String a : VaccineType.vaccineTechnologies) {
                System.out.printf("%d- %s %n", options, a);
                options++;
            }
            System.out.println();
            System.out.println("Type your option:");
            int option = Utils.insertInt("Insert a valid option: ");


            boolean check = false;

            while (!check) {
                try {
                    technology =  VaccineType.vaccineTechnologies[option - 1];
                    check = true;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Insert a valid option");
                    option = sc.nextInt();
                    sc.nextLine();
                }
            }

            if (controller.specifyNewVaccineType(code, description, technology)) {
                showVaccineTypeData(code, description, technology);
                if (Utils.confirmCreation()) {
                    controller.saveVaccineType(code, description, technology);

                    System.out.println();
                    System.out.println("New Vaccine Type added");

                } else {
                    System.out.println("No Vaccine Type was added.");
                }
            } else {
                System.out.println("Invalid data.");
                System.out.println("Check what's wrong: ");
                showVaccineTypeData(code, description, technology);
            }





    }


    /**
     * Shows the User all the data referent to a Vaccine Type.
     */
    private void showVaccineTypeData(String code, String description, String technology) {
        System.out.println();
        System.out.println("---- Vaccine Type: ----");
        System.out.println("Code: " + code);
        System.out.println("Description: " + description);
        System.out.println("Technology: " + technology);
        System.out.println();
    }

}




