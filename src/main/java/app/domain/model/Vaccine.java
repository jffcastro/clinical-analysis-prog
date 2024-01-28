package app.domain.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a Vaccine for a virus.
 *
 * @author Gustavo Jorge <1211061@isep.ipp.pt>
 */
public class Vaccine implements Serializable {
    private final String name;
    private final int id;
    private final String brand;
    private final AdministrationProcess adminProcess;
    private final VaccineType vaccineType;

    /**
     * Creates a vaccine with the following attributes:
     *
     * @param name         The vaccine's name.
     * @param id           The vaccine's ID.
     * @param brand        The brand that created the vaccine.
     * @param adminProcess The Administration Process related to the vaccine.
     * @param vaccineType  The Vaccine Type of the vaccine (e.g.: Covid-19, Flu) - The disease related to the vaccine.
     */
    public Vaccine(String name, int id, String brand, AdministrationProcess adminProcess, VaccineType vaccineType) {
        this.name = name;
        this.id = id;
        this.brand = brand;
        this.adminProcess = adminProcess;
        this.vaccineType = vaccineType;
    }


    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets admin process.
     *
     * @return the admin process
     */
    public AdministrationProcess getAdminProcess() {
        return adminProcess;
    }

    /**
     * Gets vaccine type.
     *
     * @return the vaccine type
     */
    public VaccineType getVaccineType() {
        return vaccineType;
    }

    /**
     * Validates a Vaccine.
     *
     * @return true if the Vaccine is validated
     */
    public boolean validateVaccine() {
        return !name.isEmpty() && !brand.isEmpty() && validateId();
    }

    /**
     * Validates a Vaccine ID.
     *
     * @return true if the Vaccine ID is validated
     */
    private boolean validateId() {
        return (id > 1);
    }

    /**
     * Gets user age group index.
     *
     * @param userAge the user age
     * @return the user age group index
     */
    public int getUserAgeGroupIndex(int userAge) {
        for (int columns = 0; columns < getAdminProcess().getAgeGroups().get(0).size(); columns++) {
            if ((userAge > getAdminProcess().getAgeGroups().get(0).get(columns)) && userAge < getAdminProcess().getAgeGroups().get(1).get(columns)) {
                return columns;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vaccine)) return false;
        Vaccine vaccine = (Vaccine) o;
        return id == vaccine.id;
    }

}