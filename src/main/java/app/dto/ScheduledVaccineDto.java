package app.dto;

import app.domain.model.VaccineType;

import java.time.LocalDateTime;

public class ScheduledVaccineDto {
    public int snsNumber;

    public VaccineType vaccineType;

    public LocalDateTime date;

    /**
     * Creates a scheduled vaccine with the following attributes:
     *
     * @param snsNumber   The SNS Number related to the User with the scheduled Vaccine.
     * @param vaccineType The Vaccine's Type.
     * @param date        The date of the appointment.
     */
    public ScheduledVaccineDto(int snsNumber, VaccineType vaccineType, LocalDateTime date) {
        this.snsNumber = snsNumber;
        this.vaccineType = vaccineType;
        this.date = date;

    }

    public ScheduledVaccineDto() {
    }
}
