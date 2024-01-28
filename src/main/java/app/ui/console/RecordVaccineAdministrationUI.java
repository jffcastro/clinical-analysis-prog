package app.ui.console;

import app.controller.RecordVaccineAdministrationController;
import app.domain.shared.Constants;
import app.ui.console.utils.Utils;
import app.dto.SnsUserDto;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Guilherme Sousa <1211073@isep.ipp.pt>
 */
public class RecordVaccineAdministrationUI implements Runnable {

    private final RecordVaccineAdministrationController controller = new RecordVaccineAdministrationController();

    private final Scanner read = new Scanner(System.in);

    @Override
    public void run() {
        // Start User Story
        vaccineAdministrationPrompt(Constants.VACCINE_ADMINISTRATION);

        // Select Vaccination Center
        int vaccinationCenterIndexInList = Utils.selectVaccinationCenterIndex();
        controller.setVaccinationCenter(vaccinationCenterIndexInList);

        // Select User from Waiting Room List
        int selectUser = waitingRoomList();

        // Select a Vaccine (Verifies if it matches the Vaccine Type)
        if (selectUser != Constants.INVALID_VALUE) {
            controller.setVaccineType(selectUser);
            int vaccineHistory = controller.findLastDoseOfVaccineType();
            setDosageAndVaccine(vaccineHistory);
            controller.setLocalDateTime();

            // Allows Nurse to introduce the vaccine lot number
            lotNumber();

            // Clean User from Waiting Room List
            controller.registerVaccineInVaccineBulletin();

            try {
                controller.printRecoveryTime();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            vaccineAdministrationPrompt(Constants.END_VACCINATION);
        }
    }

    private int waitingRoomList() {
        if (controller.checkIfArrivalsListEmpty()) {
            int selectedUser = (Utils.selectFromList(controller.fillListWithUserSnsNumber(), "\nWaiting Room List (Select One User)"));
            SnsUserDto snsUserDto = controller.getSnsUserInformation(selectedUser);
            controller.setSnsUser(snsUserDto);
            return selectedUser;
        } else
            System.out.printf("%n|Waiting Room Is Currently Empty|%n");
        return Constants.INVALID_VALUE;
    }

    private int userFirstDose() {
        if (controller.findLastDoseOfVaccineType() == Constants.FIRST_DOSE)
            //If the user doesn´t fit in any of the age groups.
            return Utils.selectFromList(controller.vaccineAvailableName(), "\nSelect a Vaccine: ");
        return Constants.FIT_AGE_GROUP;
    }

    private void setDosageAndVaccine(int vaccineHistory) {
        if (vaccineHistory != Constants.FIRST_DOSE) {
            int numberOfDoses = controller.getUserNumberOfDoses();
            int currentAppointment = controller.findLastDoseOfVaccineType();
            controller.setVaccine(currentAppointment);
            vaccineAdministrationPrompt(Constants.VACCINATION);
            vaccineAndVaccineTypeInfo();
            System.out.printf("- " + controller.vaccineAdministrationProcess(numberOfDoses, currentAppointment) + "%n");
        } else {
            int vaccineIndex = userFirstDose();
            if (vaccineIndex != Constants.FIT_AGE_GROUP) {
                controller.setVaccine(vaccineIndex);
                vaccineAdministrationPrompt(Constants.VACCINATION);
                vaccineAndVaccineTypeInfo();
                System.out.printf("- " + controller.vaccineAdministrationProcess(Constants.INVALID_VALUE, vaccineIndex) + "%n");
            }
        }
    }

    private void vaccineAndVaccineTypeInfo() {
        System.out.printf("%n- " + controller.vaccineTypeInfo());
        System.out.printf("%n- " + controller.vaccineInfo() + "%n");
    }

    private void vaccineAdministrationPrompt(int prompt) {
        if (prompt == Constants.VACCINE_ADMINISTRATION)
            System.out.printf("%n------------------------%n|Vaccine Administration|%n------------------------%n");
        else if (prompt == Constants.VACCINATION)
            System.out.printf("%n|Vaccination|%n");
        else
            System.out.printf("%n|Vaccine Administration Complete|%n");
    }

    private void lotNumber() {
        String lotNumber;
        do {
            System.out.printf("%nIntroduce Lot Number: ");
            lotNumber = read.nextLine();
        } while (!controller.validateLotNumber(lotNumber));
        controller.setLotNumber(lotNumber);
    }
}