package app.controller;

import app.domain.model.Company;
import app.domain.model.Employee;

import java.util.ArrayList;

/**
 * US011 - Get a list of Employees with a given role.
 *
 * @author João Leitão <1211063@isep.ipp.pt>
 */

public class GetListOfEmployeesController {

    private Company company = App.getInstance().getCompany();

    public GetListOfEmployeesController() {
    }

    /**
     * Gets the list containing all employees registered in the system.
     *
     * @return the list of those employees.
     */

    public ArrayList<Employee> getEmployees(){
        return company.getEmployeesStore().getEmployees();
    }

    /**
     * Fills the array lists with the types of employees through the list that contains all employees.
     */

    public void fillListOfEmployeesWithAGivenRole(){
        company.getEmployeesStore().fillListOfEmployeesWithAGivenRole();
    }


    /**
     * Gets the Nurses registered in the Company.
     *
     * @return An ArrayList of Nurses.
     */

    public ArrayList<Employee> getNurseList(){
        return company.getEmployeesStore().getNurseList();
    }

    /**
     * Gets the Receptionists registered in the Company.
     *
     * @return An ArrayList of Receptionists.
     */

    public ArrayList<Employee> getReceptionistList() {
        return company.getEmployeesStore().getReceptionistList();
    }

    /**
     * Gets the Centre Coordinators registered in the Company.
     *
     * @return An ArrayList of Centre Coordinator.
     */

    public ArrayList<Employee> getCentreCoordinatorList() {
        return company.getEmployeesStore().getCenterCoordinatorList();
    }


}



