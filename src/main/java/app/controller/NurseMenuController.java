package app.controller;

import app.domain.model.Company;

/**
 * The Nurse menu controller.
 *
 * @author Guilherme Sousa
 */

public class NurseMenuController {

    private final Company company = App.getInstance().getCompany();

    /**
     * Checks if the company has enough info for vaccine administration.
     *
     * @return the int related to the error or success of the operation
     */
    public int vaccineAdministrationRequirements() {
        if (company.getVaccinationCentersStore().getVaccinationCenters().isEmpty())
            return 1;
        if (company.getVaccinesList().isEmpty())
            return 2;
        if (company.getVaccineTypesStore().getVaccineTypes().isEmpty())
            return 3;
        return 0;
    }
}