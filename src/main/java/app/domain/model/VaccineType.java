package app.domain.model;

import app.controller.App;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a Vaccine for a virus.
 *
 * @author Pedro Monteiro <1211076@isep.ipp.pt>
 */

public class VaccineType implements Serializable {


    private final String description;
    private final String code;
    private final String vaccineTechnology;

    public static String[] vaccineTechnologies = {"Live-attenuated vaccine", "Inactivated vaccine", "Subunit vaccine", "Toxoid vaccine", "Viral vector vaccine", "Messenger RNA (mRNA) vaccine"};


    /**
     * Creates a vaccine type with the following attributes:
     *
     * @param description       A short description of the Vaccine Type.
     * @param code              A code with five alphanumeric characters, corresponding to the Vaccine Type
     * @param vaccineTechnology The corresponding vaccine technology
     * @see <a href="https://www.pfizer.com/news/articles/understanding_six_types_of_vaccine_technologies">Understanding Six Types of Vaccine Technologies</a>
     */
    public VaccineType(String code, String description, String vaccineTechnology) {
        this.description = description;
        this.code = code;
        this.vaccineTechnology = vaccineTechnology;
    }

    /**
     * Gets the code of a VaccineType, in order to be printed
     *
     * @return code
     */

    @Override
    public String toString() {
        return code;
    }

    /**
     * Validates a Vaccine Type.
     *
     * @return true if the type is validated.
     */
    public boolean validateVaccineType() {
        if (code.isEmpty() || description.isEmpty() || vaccineTechnology.isEmpty()) return false;
        return validateCode();
    }

    /**
     * Validates a Vaccine Type Code.
     *
     * @return true if the  Vaccine Type Code is validated.
     */
    public boolean validateCode() {
        if (code.length() != 5)
            return false;

        Company c = App.getInstance().getCompany();
        for (VaccineType vt : c.getVaccineTypesStore().getVaccineTypes()) {
            if (code.equals(vt.code)) return false;
        }

        return true;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VaccineType)) return false;
        VaccineType that = (VaccineType) o;
        return Objects.equals(code, that.code) && Objects.equals(vaccineTechnology, that.vaccineTechnology);
    }


}



