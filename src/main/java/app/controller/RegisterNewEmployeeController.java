package app.controller;


import app.domain.model.Company;
import app.dto.RegisterNewEmployeeDto;

import java.io.NotSerializableException;
import java.io.Serializable;


/**
 * US010 - Register New Employee Controller
 *
 * @author Guilherme Sousa <1211073@isep.ipp.pt>
 */

public class RegisterNewEmployeeController implements Serializable {

    private final Company company = App.getInstance().getCompany();

    public RegisterNewEmployeeController() {
    }

    /**
     * Register an Employee in the company storage
     *
     * @param dto A data transfer object with all the necessary information about the new Employee
     * @return true if the new Employee data is valid
     */

    public boolean registerNewEmployee(RegisterNewEmployeeDto dto) {
        return company.getEmployeesStore().registerNewEmployee(dto);
    }

    /**
     * Saves an Employee into the Company storage.
     *
     * @param dto A data transfer object with all the necessary information about the new Employee
     * @param selectedRole Selected role for the new Employee by the user
     */

    public void saveCreatedEmployee(RegisterNewEmployeeDto dto, String selectedRole) {
        try {
            company.getEmployeesStore().saveCreatedEmployee(dto, selectedRole);
        } catch (NotSerializableException e) {
            e.printStackTrace();
        }
    }

    public boolean duplicatedEmployee(RegisterNewEmployeeDto dto) {
        return company.getEmployeesStore().duplicatedEmployee(dto);
    }

    public StringBuilder idGenerator() {
        return  company.getEmployeesStore().idGenerator();
    }
}
