package app.dto;

import app.domain.model.Vaccine;

import java.time.LocalDateTime;

public class VaccineBulletinDto {

    public Vaccine vaccine;
    public LocalDateTime dateTimeOfLastDose;

    public int doseNumber;

    public String lotNumber;

    /**
     * Creates a vaccine bulletin with the following attributes:
     *
     * @param vaccine   the vaccine administered
     * @param dateTime  the date time of the administration
     * @param doses     the dose number of the administration
     * @param lotNumber the lot number of the adminstered vaccine
     */
    public VaccineBulletinDto(Vaccine vaccine, LocalDateTime dateTime, int doses, String lotNumber) {
        this.vaccine = vaccine;
        this.dateTimeOfLastDose = dateTime;
        this.doseNumber = doses;
        this.lotNumber = lotNumber;
    }

    public VaccineBulletinDto() {

    }
}
