package app.domain.model;

import app.domain.shared.Constants;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.IllegalFormatCodePointException;
import java.util.Objects;

/**
 * Has all the info about a Given Vaccine
 *
 * @author Gustavo Jorge <1211061@isep.ipp.pt>
 * @author Guilherme Sousa <1211073@isep.ipp.pt>
 */

/**
 * The Bulletin is the object that is used to record the administration of a vaccine.
 */
public class VaccineBulletin implements Serializable {

    private static final long serialVersionUID = -4651776023952598771L;

    /**
     * Vaccine taken by the user in previous appointment
     */
    private final Vaccine vaccine;

    /**
     * Date and Time of when the user took the last dose of the vaccine
     */
    private final LocalDateTime dateTimeOfLastDose;

    /**
     * Tracks the number of doses the user has taken so far
     */
    private int doseNumber;

    private final String lotNumber;

    /**
     * Instantiates a new Vaccine bulletin.
     *
     * @param vaccine   the vaccine
     * @param dateTime  the date time
     * @param doses     the doses
     * @param lotNumber the lot number
     */
    public VaccineBulletin(Vaccine vaccine, LocalDateTime dateTime, int doses, String lotNumber) {
        this.vaccine = vaccine;
        this.dateTimeOfLastDose = dateTime;
        this.doseNumber = doses;
        this.lotNumber = lotNumber;
    }

    /**
     * Gets vaccine the user took.
     *
     * @return the vaccine taken by the user
     */
    public Vaccine getVaccine() {
        return vaccine;
    }

    /**
     * Gets the date and time
     *
     * @return the date and time of when the user took the previous vaccine
     */
    public LocalDateTime getDateTimeOfLastDose() {
        return dateTimeOfLastDose;
    }

    /**
     * Gets number of doses
     *
     * @return the number of doses the user has taken
     */
    public int getDose() {
        return doseNumber;
    }

    /**
     * Gets lot number.
     *
     * @return the lot number
     */
    public String getLotNumber() {
        return lotNumber;
    }

    /**
     * Sets number of doses taken by the user.
     *
     * @param doses the doses
     */
    public void setDose(int doses) {
        this.doseNumber = doses;
    }

    /**
     * Checks if it is the last dose.
     *
     * @param ageGroupIndex the age group index
     * @return the boolean
     */
    public boolean isLastDose(int ageGroupIndex) {
        int totalDoses = vaccine.getAdminProcess().getNumberOfDoses().get(ageGroupIndex);
        return doseNumber == totalDoses;
    }


}