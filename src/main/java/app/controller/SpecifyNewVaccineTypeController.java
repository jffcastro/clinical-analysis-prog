package app.controller;


import app.domain.model.*;

import java.io.Serializable;
import java.util.List;


/**
 * US012 - Specify Vaccine Type
 *
 * @author Pedro Monteiro <1211076@isep.ipp.pt>
 */

public class SpecifyNewVaccineTypeController implements Serializable {

    private final Company  company = App.getInstance().getCompany();


    /**
     * Specifies a new Vaccine Type:
     * <p>
     * <p>
     * The method should create a vaccine type that should be validated.
     *
     * @param description a String to validate
     * @param code       a String to validate
     * @param technology a String to validate
     * @return true if the type is valid
     */
    public boolean specifyNewVaccineType(String code, String description, String technology) {
        return company.getVaccineTypesStore().specifyNewVaccineType(code, description, technology);
    }


    public List<VaccineType> getVaccineTypes() {
        return company.getVaccineTypesStore().getVaccineTypes();
    }


    /**
     * Saves a Vaccine Type into the Company storage.
     * Company Vaccines Storage: vaccineTypes
     */
    public void saveVaccineType(String code, String description, String technology) {
        company.getVaccineTypesStore().saveVaccineType(code, description, technology);
    }


}