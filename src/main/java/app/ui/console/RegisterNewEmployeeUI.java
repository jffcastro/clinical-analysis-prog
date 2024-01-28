package app.ui.console;

import app.controller.RegisterNewEmployeeController;
import app.domain.model.Employee;
import app.ui.console.utils.Utils;
import app.dto.RegisterNewEmployeeDto;

import java.util.ArrayList;

/**
 * US010 - Register New Employee UI
 *
 * @author Guilherme Sousa <1211073@isep.ipp.pt>
 */

public class RegisterNewEmployeeUI implements Runnable {

    public final int INVALID = -1;

    public RegisterNewEmployeeUI() {
    }

    public void run() {
        RegisterNewEmployeeController controller = new RegisterNewEmployeeController();
        RegisterNewEmployeeDto dto = new RegisterNewEmployeeDto();
        System.out.printf("%n-----------------------%n|Employee Registration|%n-----------------------%n%n");

        ArrayList<String> roles = new ArrayList<>();
        roles.add("Nurse");
        roles.add("Receptionist");
        roles.add("Center Coordinator");
        int index = Utils.showAndSelectIndex(roles, "Select a Role: ");
        if (index == INVALID) {
        } else {
            String selectedRole = roles.get(index);
            dto.id = String.valueOf(controller.idGenerator());
            dto.password = Employee.passwordGenerator();
            dto.name = Utils.readLineFromConsole("- Insert Name: ");
            dto.address = Utils.readLineFromConsole("- Insert Address (Street / Zip Code / Location): ");
            dto.phoneNumber = Utils.readLineFromConsole("- Insert Phone Number (9 chars, only numbers): (+351) ");
            dto.email = Utils.readLineFromConsole("- Insert Email (@ and . are required): ");


            dto.citizenCardNumber = Utils.readLineFromConsole("- Insert Citizen Card Number (Format - XXXXXXXX X LLX): ");


            if (controller.registerNewEmployee(dto) && controller.duplicatedEmployee(dto)) {
                showNewEmployeeData(dto, selectedRole);
                if (Utils.confirmCreation()) {
                    controller.saveCreatedEmployee(dto, selectedRole);

                    System.out.printf("%n-----------------------------%n|The Employee was registered|%n-----------------------------%n");
                } else
                    System.out.printf("%n---------------------------------%n|The Employee was not registered|%n---------------------------------%n");
            } else {
                System.out.printf("%n-------------------------------%n|Invalid Data for the Employee|%n-------------------------------");
                System.out.printf("%n----------------------------------%n|The Employee was not registered|%n---------------------------------%n");
                showNewEmployeeData(dto, selectedRole);
            }
        }
    }

    /**
     * Shows all the data relative to the new Employee.
     *
     * @param dto          A data transfer object with all the necessary information about the new Employee
     * @param selectedRole Selected role for the new Employee by the user
     */
    public void showNewEmployeeData(RegisterNewEmployeeDto dto, String selectedRole) {
        System.out.printf("%n----------------------------------------------------------------------------%n");
        System.out.printf("%n---------------%n|Employee Data|%n---------------%n%n- Role: |%s|%n%n- Name: |%s|%n%n- Address: |%s|%n%n- Phone Number: |%s|%n%n- Email: |%s|%n%n- Citizen Card Number: |%s|%n%n- ID: |%s|%n%n- Password: |%s|%n%n", selectedRole, dto.name, dto.address, dto.phoneNumber, dto.email, dto.citizenCardNumber, dto.id, dto.password);
    }
}