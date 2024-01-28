package app.ui.console;

import app.controller.GetListOfEmployeesController;

import java.util.Scanner;

/**
 * US011 - Get a list of Employees with a given role.
 *
 * @author João Leitão <1211063@isep.ipp.pt>
 */

public class GetListOfEmployeesUI implements Runnable {

    public GetListOfEmployeesUI() {
    }

    private GetListOfEmployeesController controller = new GetListOfEmployeesController();

        
    public void run() {
        int option;
        Scanner read = new Scanner(System.in);
        System.out.println("");
        System.out.println("**You have chosen to get a list of Employees**");

        do {
            System.out.println("Select the option you pretend to get a list of:");
            System.out.println("1- List of Nurses.");
            System.out.println("2- List of Receptionists.");
            System.out.println("3- List of Centre Coordinators.");
            System.out.println("");
            System.out.println("0- Return.");
            System.out.println("");
            controller.fillListOfEmployeesWithAGivenRole();
            System.out.print("Type your option: ");
            option = read.nextInt();
            System.out.println("");

            switch (option) {
                case 1:
                    if (!controller.getNurseList().isEmpty()) {
                        System.out.println("**These are the Nurses registered in the system:**");
                        for (int listPosition = 0; listPosition < controller.getNurseList().size(); listPosition++) {
                            System.out.println(controller.getNurseList().get(listPosition));
                        }
                    } else {
                        System.out.println("**There aren't any Nurses registered in the system.**");
                        System.out.println("");
                    }
                    break;
                case 2:
                    if (!controller.getReceptionistList().isEmpty()) {
                        System.out.println("**These are the Receptionists registered in the system:**");
                        for (int listPosition = 0; listPosition < controller.getReceptionistList().size(); listPosition++) {
                            System.out.println(controller.getReceptionistList().get(listPosition));
                        }
                    } else {
                        System.out.println("**There aren't any Receptionists registered in the system.**");
                        System.out.println("");
                    }
                    break;
                case 3:
                    if (!controller.getCentreCoordinatorList().isEmpty()) {
                        System.out.println("**These are the Centre Coordinators registered in the system:**");
                        for (int listPosition = 0; listPosition < controller.getCentreCoordinatorList().size(); listPosition++) {
                            System.out.println(controller.getCentreCoordinatorList().get(listPosition));
                        }
                    } else {
                        System.out.println("**There aren't any Centre Coordinators registered in the system.**");
                        System.out.println("");
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid option, please select one that's valid.");
            }
//
        } while (option == 1 || option == 2 || option == 3);

    }

}
