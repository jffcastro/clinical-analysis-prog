package app.domain.model;

import app.controller.App;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * US011 - Get a list of Employees with a given role.
 *
 * @author João Leitão <1211063@isep.ipp.pt>
 */

class GetListOfEmployeesTest {


    Company company = App.getInstance().getCompany();



    /**
     * Verifies if the list of nurses is filled.
     */
    @Test
    public void fillListOfNurses() {
        Employee nurse = new Nurse("NR-12345", "Nurse", "Rua / 1111-111 / Portugal", "912345678", "nurse@gmail.com", "11960343 8 ZW1", "AAA11aa");

        company.getEmployeesStore().getEmployees().add(nurse);

        company.getEmployeesStore().fillListOfEmployeesWithAGivenRole();

        assertFalse(company.getEmployeesStore().getNurseList().isEmpty());
    }



/**
 * Verifies if the list of nurses is empty.
 */
@Test
public void fillListOfNursesFalse(){

    company.getEmployeesStore().fillListOfEmployeesWithAGivenRole();

    assertTrue(company.getEmployeesStore().getReceptionistList().isEmpty());
}



    /**
     * Verifies if the list of receptionists is filled.
     */
    @Test
    public void fillListOfReceptionists() {
        Employee receptionist = new Receptionist("RC-12345", "Receptionist", "Rua / 2222-222 / Portugal", "913456789",  "receptionist@gmail.com", "14268862 2 ZX8", "BBB22bb");

        company.getEmployeesStore().getEmployees().add(receptionist);

        company.getEmployeesStore().fillListOfEmployeesWithAGivenRole();

        assertFalse(company.getEmployeesStore().getReceptionistList().isEmpty());

    }


    /**
     * Verifies if the list of receptionists is empty.
     */
    @Test
    public void fillListOfReceptionistsFalse() {

        company.getEmployeesStore().fillListOfEmployeesWithAGivenRole();

        assertTrue(company.getEmployeesStore().getReceptionistList().isEmpty());
    }


    /**
     * Verifies if the list of centre coordinators is filled
     */
    @Test
    public void fillListOfCentreCoordinators() {
        Employee centreCoordinator = new CenterCoordinator("CC-12345", "Centre Coordinator", "Rua / 3333-333 / Portugal", "914567894", "centrecoordinator@gmail.com", "35619927 4 ZX6", "CCC33cc");

        company.getEmployeesStore().getEmployees().add(centreCoordinator);

        company.getEmployeesStore().fillListOfEmployeesWithAGivenRole();

        assertFalse(company.getEmployeesStore().getCenterCoordinatorList().isEmpty());

    }


    /**
     * Verifies if the list of centre coordinators is empty.
     */
    @Test
    public void fillListOfCentreCoordinatorsFalse() {
        Company company1 = new Company("Comps");
        company1.getEmployeesStore().fillListOfEmployeesWithAGivenRole();
        assertTrue(company1.getEmployeesStore().getCenterCoordinatorList().isEmpty());
    }

}