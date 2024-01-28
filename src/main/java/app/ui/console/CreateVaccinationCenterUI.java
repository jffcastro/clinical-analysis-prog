package app.ui.console;

import app.controller.CreateVaccinationCenterController;
import app.domain.model.VaccineType;
import app.ui.console.utils.Utils;
import app.dto.HealthcareCenterDto;
import app.dto.MassVaccinationCenterDto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Asks the user to create one mass vaccination center or one healthcare center.
 * You can get a list of all centers created regardless of their type.
 *
 * @author João Castro <1210816@isep.ipp.pt>
 */
public class CreateVaccinationCenterUI implements Runnable {

    public CreateVaccinationCenterUI() {
    }

    CreateVaccinationCenterController controller = new CreateVaccinationCenterController();

    /**
     * Runs the main menu that allows the user to choose what option he wants to follow
     */
    public void run() {
        Scanner choice = new Scanner(System.in);
        System.out.println();
        System.out.println("--------------CHOOSE THE TYPE:--------------");
        System.out.println("0 - Mass Vaccination Center");
        System.out.println("1 - Healthcare Center");
        System.out.println("2 - Get a list of all all centers created");
        System.out.println("3 - Go Back");
        System.out.println();
        System.out.println("Choose the option:");
        System.out.println();
        try {
            int typeOfCenter = choice.nextInt();
            if (typeOfCenter == 0) {
                massVaccinationCenterUI(typeOfCenter);
            } else if (typeOfCenter == 1) {
                healthcareCenterUI(typeOfCenter);
            } else if (typeOfCenter == 2) {
                getListOfVaccinationCentersUI();
            } else if (typeOfCenter == 3) {
                return;
            } else {
                System.out.println("Option is Invalid.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Only Numbers.");
        }

    }

    /**
     * Prints the list of all centers that were created till its execution.
     */
    public void getListOfVaccinationCentersUI() {
        CreateVaccinationCenterController controller = new CreateVaccinationCenterController();
        if (!controller.getVaccinationCenters().isEmpty()) {
            for (int i = 0; i < controller.getVaccinationCenters().size(); i++) {
                System.out.println("\nPosition " + i + ": " + "\n" + controller.getVaccinationCenters().get(i).fullInfo());
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println("There aren't registered centers of any kind.");
        }
    }

    /**
     * Asks the user for information about the center and fills the DTO, after that asks the user for confirmation.
     *
     * @param typeOfCenter is the option that the user previously chose on the main menu, it's going to be used in order to generate an ID for the center.
     */
    public void massVaccinationCenterUI(int typeOfCenter) {
        CreateVaccinationCenterController controller = new CreateVaccinationCenterController();
        controller.fillListOfEmployeesWithAGivenRole();
        controller.centerCoordinatorIDList();
        if (!(controller.getCenterCoordinatorIDs().isEmpty()) && !(controller.getVaccineTypeList().isEmpty())) {

            Scanner sc = new Scanner(System.in);
            Scanner sc1 = new Scanner(System.in);
            MassVaccinationCenterDto dto = new MassVaccinationCenterDto();
            dto.strID = String.valueOf(idGeneratorMass(typeOfCenter));
            dto.strName = Utils.readLineFromConsole("Name of the Mass Vaccination Center: ");
            dto.strPhoneNumber = Utils.readLineFromConsole("Phone Number of the Mass Vaccination Center (Portuguese rules apply): ");
            dto.strEmail = Utils.readLineFromConsole("Email of the Mass Vaccination Center (Needs an @, a . and a valid domain): ");
            dto.strFax = Utils.readLineFromConsole("Fax Number of the Mass Vaccination Center (Same rules as Phone Numbers): ");
            dto.strWebsite = Utils.readLineFromConsole("Website address of the Mass Vaccination Center (Needs a valid prefix and domain): ");
            dto.strOpeningHour = Utils.readLineFromConsole("Opening hour of the Mass Vaccination Center (Between 0 and 24, < Closing Hour): ");
            dto.strClosingHour = Utils.readLineFromConsole("Closing hour of the Mass Vaccination Center (Between 0 and 24, > Opening Hour): ");
            dto.strSlotDuration = Utils.readLineFromConsole("Slot duration (In minutes, no more than three numerical chars): ");
            dto.strVaccinesPerSlot = Utils.readLineFromConsole("Maximum number of vaccines per slot (No more than three numerical chars): ");
            System.out.println();
            System.out.println("Information about the Mass Vaccination Center Address: ");
            dto.strRoad = Utils.readLineFromConsole("Road of the Mass Vaccination Center: ");
            dto.strZipCode = Utils.readLineFromConsole("Zip Code of the Mass Vaccination Center (1234-123 format): ");
            dto.strLocal = Utils.readLineFromConsole("Local of the Mass Vaccination Center: ");
            System.out.println();
            System.out.println("Information about the Coordinator: ");
            System.out.println();
            System.out.println("Choose one Coordinator from the list, type it's position.");
            System.out.println();

            do {
                try {
                    for (int i = 0; i < controller.getCenterCoordinatorIDs().size(); i++) {
                        boolean check = true;
                        for (int j = 0; j < controller.getVaccinationCenters().size(); j++) {
                            if (controller.getCenterCoordinatorIDs().get(i).equals(controller.getVaccinationCenters().get(j).getStrCenterCoordinatorID()))
                                check = false;

                        }
                        if (check)
                            System.out.println("Position " + i + ": " + controller.getCenterCoordinatorIDs().get(i));


                    }
                    System.out.println();
                    System.out.print("Choose your option: ");
                    int option = sc.nextInt();
                    System.out.println();

                    dto.strCenterCoordinatorID = controller.getCenterCoordinatorIDs().get(option);
                    break;
                } catch (IndexOutOfBoundsException | InputMismatchException a) {
                    System.out.println("Position Invalid");
                    sc.nextLine();
                    System.out.println();
                }
            } while (true);


            System.out.println();
            System.out.println("Information about the Vaccine Type: ");
            System.out.println("Choose one vaccine type from the list, type it's position.");
            System.out.println();

            do {
                try {
                    for (int i = 0; i < controller.getVaccineTypeList().size(); i++) {
                        System.out.println("Position " + i + ": " + controller.getVaccineTypeList().get(i));
                    }
                    System.out.println();
                    System.out.print("Choose your option: ");
                    int vaccineTypeOption = sc.nextInt();
                    System.out.println();
                    dto.vaccineType = controller.getVaccineTypeList().get(vaccineTypeOption);
                    controller.createMassVaccinationCenter(dto);
                    break;
                } catch (IndexOutOfBoundsException | InputMismatchException b) {
                    System.out.println("Position Invalid");
                    sc.nextLine();
                    System.out.println();
                }
            } while (true);

            System.out.println();
            System.out.println("------------------------------------------------------------------------------------");
            System.out.println("----------------------PLEASE VERIFY THE DATA----------------------------------------");
            System.out.println();
            System.out.println(dto);
            System.out.println();

            if (Utils.confirmCreation()) {
                controller.saveMassVaccinationCenter(dto);
                System.out.println();
                System.out.println("The Mass Vaccination Center was saved.");
            } else {
                System.out.println();
                System.out.println("You chose not to save the Mass Vaccination Center.");
            }
        } else {
            System.out.println("Can't create a Mass Vaccination Center without a registered Center Coordinator and a Vaccine Type added onto the system.");
        }
    }

    /**
     * Asks the user for information about the center and fills the DTO, after that asks the user for confirmation.
     *
     * @param typeOfCenter is the option that the user previously chose on the main menu, it's going to be used in order to generate an ID for the center.
     */
    public void healthcareCenterUI(int typeOfCenter) {
        CreateVaccinationCenterController controller = new CreateVaccinationCenterController();

        controller.fillListOfEmployeesWithAGivenRole();
        controller.centerCoordinatorIDList();
        if (!(controller.getCenterCoordinatorIDs().isEmpty()) && !(controller.getVaccineTypeList().isEmpty())) {

            Scanner sc = new Scanner(System.in);
            Scanner sc1 = new Scanner(System.in);
            HealthcareCenterDto dto = new HealthcareCenterDto();
            dto.strID = String.valueOf(idGeneratorMass(typeOfCenter));
            dto.strName = Utils.readLineFromConsole("Name of the Healthcare Center: ");
            dto.strPhoneNumber = Utils.readLineFromConsole("Phone Number of the Healthcare Center (Portuguese rules apply): ");
            dto.strEmail = Utils.readLineFromConsole("Email of the Healthcare Center (Needs an @, a . and a valid domain): ");
            dto.strFax = Utils.readLineFromConsole("Fax Number of the Healthcare Center (Same rules as Phone Numbers): ");
            dto.strWebsite = Utils.readLineFromConsole("Website address of the Healthcare Center (Needs a valid prefix and domain): ");
            dto.strOpeningHour = Utils.readLineFromConsole("Opening hour of the Healthcare Center (Between 0 and 24, < Closing Hour): ");
            dto.strClosingHour = Utils.readLineFromConsole("Closing hour of the Healthcare Center (Between 0 and 24, > Opening Hour): ");
            dto.strSlotDuration = Utils.readLineFromConsole("Slot duration (In minutes, no more than three numerical chars): ");
            dto.strVaccinesPerSlot = Utils.readLineFromConsole("Maximum number of vaccines per slot (No more than three numerical chars): ");
            System.out.println();
            System.out.println("Information about the Healthcare Center Address: ");
            dto.strRoad = Utils.readLineFromConsole("Road of the Healthcare Center: ");
            dto.strZipCode = Utils.readLineFromConsole("Zip Code of the Healthcare Center (1234-123 format): ");
            dto.strLocal = Utils.readLineFromConsole("Local of the Healthcare Center: ");
            dto.strARS = Utils.readLineFromConsole("Regional Health Administration of the Healthcare Center: ");
            dto.strAGES = Utils.readLineFromConsole("Grouping of the Healthcare Center: ");
            System.out.println();
            System.out.println("Information about the Coordinator");
            System.out.println();
            System.out.println("Choose one Coordinator from the list, type it's position.");
            System.out.println();

            do {
                try {
                    for (int i = 0; i < controller.getCenterCoordinatorIDs().size(); i++) {
                        boolean check = true;
                        for (int j = 0; j < controller.getVaccinationCenters().size(); j++) {
                            if (controller.getCenterCoordinatorIDs().get(i).equals(controller.getVaccinationCenters().get(j).getStrCenterCoordinatorID()))
                                check = false;
                        }
                        if (check)
                            System.out.println("Position " + i + ": " + controller.getCenterCoordinatorIDs().get(i));
                    }
                    System.out.println();
                    System.out.print("Choose your option: ");
                    int option = sc.nextInt();
                    System.out.println();
                    dto.strCenterCoordinatorID = controller.getCenterCoordinatorIDs().get(option);
                    break;
                } catch (IndexOutOfBoundsException | InputMismatchException r) {
                    System.out.println("Position Invalid");
                    sc.nextLine();
                    System.out.println();
                }
            } while (true);

            System.out.println();
            System.out.println("Information about the Vaccine Type");
            System.out.println("Choose one vaccine type from the list, type it's position.");


            ArrayList<VaccineType> vts = new ArrayList<>(controller.getVaccineTypeList());


            int optiontest = 0;

            do {
                try {
                    System.out.println();
                    System.out.println("Choose one:");

                    for (int i = 1; i <= vts.size(); i++) {
                        System.out.println(i + " - " + vts.get(i - 1));

                    }
                    System.out.println();
                    System.out.println(0 + "- Stop");
                    System.out.print("Type your option: ");
                    optiontest = sc.nextInt();
                    if (optiontest != 0) {
                        dto.vaccineTypes.add(vts.get(optiontest - 1));
                        vts.remove(optiontest - 1);
                    }
                } catch (IndexOutOfBoundsException | InputMismatchException x) {
                    sc.nextLine();
                    System.out.println("Position Invalid");
                    System.out.println();
                }
            } while (optiontest > 0 && vts.size() != 0);

            controller.createHealthcareCenter(dto);

            System.out.println("------------------------------------------------------------------------------------");
            System.out.println("----------------------PLEASE VERIFY THE DATA----------------------------------------");
            System.out.println();
            System.out.println(dto);
            System.out.println();

            if (Utils.confirmCreation()) {
                controller.saveHealthcareCenter(dto);
                System.out.println();
                System.out.println("The Healthcare Center was saved.");
            } else {
                System.out.println();
                System.out.println("You chose not to save the Healthcare Center.");
            }
        } else {
            System.out.println("Can't create a Healthcare Center without a registered Center Coordinator and a Vaccine Type added onto the system.");
        }
    }

    /**
     * @param vaccinationCenterOption is the option that the user previously chose on the main menu, it's going to represent the type of center,
     *                                it will be used to generate different prefix for the IDs.
     * @return a randomized ID.
     */
    public static StringBuilder idGeneratorMass(int vaccinationCenterOption) {
        int ID_LENGTH = 3;
        StringBuilder orderedID = new StringBuilder();
        Random generate = new Random();

        for (int position = 0; position < ID_LENGTH; position++) {
            orderedID.append(String.valueOf(generate.nextInt(9)));
        }

        switch (vaccinationCenterOption) {
            case 0:
                orderedID = new StringBuilder("MVC-" + orderedID);
                break;
            case 1:
                orderedID = new StringBuilder("HC-" + orderedID);
                break;
        }
        return orderedID;
    }

}