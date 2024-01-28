package app.controller;

import app.domain.model.*;
import app.dto.HealthcareCenterDto;
import app.dto.MassVaccinationCenterDto;
import app.stores.VaccinationCentersStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates the passage of information between the UI and the model
 *
 * @author João Castro <1210816@isep.ipp.pt>
 */
public class CreateVaccinationCenterController {

    private final Company company = App.getInstance().getCompany();
    private final VaccinationCentersStore store = company.getVaccinationCentersStore();


    public CreateVaccinationCenterController(){}

    /**
     * Calls the createMassVaccinationCenter method inside the company
     *
     */
    public void createMassVaccinationCenter(MassVaccinationCenterDto dto){
        company.getVaccinationCentersStore().createMassVaccinationCenter(dto);
    }

    /**
     * Calls the saveMassVaccinationCenter method inside the company
     *
     */
    public void saveMassVaccinationCenter(MassVaccinationCenterDto dto){
        store.saveMassVaccinationCenter(dto);
    }

    /**
     * Calls the createHealthcareCenter method inside the company
     *
     */
    public void createHealthcareCenter(HealthcareCenterDto dto){
        company.getVaccinationCentersStore().createHealthcareCenter(dto);
    }

    /**
     * Calls the saveHealthcareCenter method inside the company
     *
     */
    public void saveHealthcareCenter(HealthcareCenterDto dto){
        store.saveHealthcareCenter(dto);
    }

    /**
     * Gets a list of vaccination centers
     *
     * @return a List of Vaccination Centers
     */
    public List<VaccinationCenter> getVaccinationCenters() {
         return store.getVaccinationCenters();
    }


    /**
     * Calls the centerCoordinatorIDList method inside the company.
     *
     */
    public void centerCoordinatorIDList(){
        company.getEmployeesStore().centerCoordinatorIDList();
    }

    /**
     * Gets a list of all the vaccine types.
     *
     * @return a List of all Vaccine Types.
     */
    public List<VaccineType> getVaccineTypeList(){
        return company.getVaccineTypesStore().getVaccineTypes();
    }

    /**
     * Gets a list of center coordinator's IDs.
     *
     * @return a List of Center Coordinator's IDs.
     */
    public ArrayList<String> getCenterCoordinatorIDs(){
        return company.getEmployeesStore().getCenterCoordinatorIDs();
    }

    /**
     * Calls the fillListOfEmployeesWithAGivenRole method inside the company.
     *
     */
    public void fillListOfEmployeesWithAGivenRole(){
        company.getEmployeesStore().fillListOfEmployeesWithAGivenRole();
    }

}

