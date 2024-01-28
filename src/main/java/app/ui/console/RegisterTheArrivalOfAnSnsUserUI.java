package app.ui.console;

import app.controller.RegisterTheArrivalOfAnSnsUserController;
import app.ui.console.utils.Utils;

import java.io.NotSerializableException;

public class RegisterTheArrivalOfAnSnsUserUI implements Runnable {


    private final RegisterTheArrivalOfAnSnsUserController controller = new RegisterTheArrivalOfAnSnsUserController();

    @Override
    public void run() {
        System.out.printf("%n------Register the Arrival of an SNS user------%n");

        int vaccinationCenterReceptionist = Utils.selectVaccinationCenterIndex();
        controller.setVaccinationCenter(vaccinationCenterReceptionist);

        int snsNumber;

        do {
            snsNumber = Utils.readIntegerFromConsole("Introduce SNS Number: ");
        } while (!Utils.validateSnsUserNumber(snsNumber) || controller.getUserIndexInUsersList(snsNumber) < 0);

        boolean flag;
        if (checkRequirementsForRegistration(snsNumber)) {
            System.out.printf("%nThe user meets all the requirements to be registered. Do you confirm this arrival?%n%n1. Yes%n2. No%n");
            do {
                int option = Utils.readIntegerFromConsole("Insert your option: ");
                if (option == 1) {
                    controller.registerArrival();
                    try {
                        controller.exportDataToFile();
                    } catch (NotSerializableException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("%n------------------------------%n|The user has been registered|%n------------------------------%n");
                    flag = true;
                } else if (option == 0) {
                    System.out.printf("%n----------------------------------%n|The user has not been registered|%n----------------------------------%n");
                    flag = true;
                } else
                    flag = false;
            } while (!flag);
        }
    }

    /**
     * Checks all the requirements needed in order to register an arrival
     *
     * @param snsNumber The number that identifies an SNS user
     * @return boolean - true if all the requirements are met
     */
    public boolean checkRequirementsForRegistration(int snsNumber) {

        if (!controller.checkAndSetUserAppointment(snsNumber)) {
            System.out.printf("%n----------------------------------------%n|The user does not have any appointment|%n----------------------------------------%n");
            return false;
        } else
            controller.setArrival(snsNumber);

       /* if (!controller.validateDateAndTime()) {
            System.out.printf("%n----------------%n|Wrong Day/Time|%n----------------%n");
            return false;
        }*/

        if (!controller.checkIfAlreadyRegistered(snsNumber)) {
            System.out.printf("%n----------------------------------%n|User has already been registered|%n---------------------------------- %n");
            return false;
        }

        return true;
    }
}