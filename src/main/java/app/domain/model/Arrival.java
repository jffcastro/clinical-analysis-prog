package app.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Pedro Monteiro <1211076@isep.ipp.pt>
 */

public class Arrival implements Serializable {

    private final int snsNumber;

    private final LocalDateTime arrivalTime;

    private final VaccineType vaccineType;

    private static final long serialVersionUID = -7896654909260539336L;

    /**
     * Creates and instance of the Arrival class with the following attributes
     *
     * @param snsNumber Number that identifies the SNS user
     * @param vaccineType The type of vaccine
     */
    public Arrival(int snsNumber, VaccineType vaccineType, LocalDateTime arrivalTime) {
        this.snsNumber = snsNumber;
        this.arrivalTime = arrivalTime;
        this.vaccineType = vaccineType;
    }

    /**
     * Gets the SNS number
     *
     * @return int - snsNumber
     */
    public int getSnsNumber() {
        return snsNumber;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public VaccineType getVaccineType() {
        return vaccineType;
    }


    /**
     * Information about the arrival of a user
     *
     * @return String
     */
    @Override
    public String toString() {
        return "SNS Number: " + snsNumber + " | Day: " + arrivalTime.getDayOfMonth() + "/" + arrivalTime.getMonth() + "/" + arrivalTime.getYear() + " , at " + arrivalTime.getHour() + ":" + arrivalTime.getMinute() + " |  Vaccine Type: " + vaccineType;
    }

    /**
     *
     * @param date Date of the appointment
     * @param vaccinationCenter Vaccination Center where the user has an appointment
     * @return boolean - true if the user is on time
     */
    public boolean validateDateAndTime(LocalDateTime date, VaccinationCenter vaccinationCenter) {
        if (!validateDate(date))
            return false;

        return validateTime(date, vaccinationCenter);
    }


    private boolean validateDate(LocalDateTime appointmentDay) {

        if (appointmentDay.getDayOfMonth() != arrivalTime.getDayOfMonth())
            return false;

        if (appointmentDay.getMonth() != arrivalTime.getMonth())
            return false;

        return appointmentDay.getYear() == arrivalTime.getYear();
    }

    private boolean validateTime(LocalDateTime appointmentTime, VaccinationCenter vaccinationCenter) {
        int slotDuration = Integer.parseInt(vaccinationCenter.getStrSlotDuration());
        LocalDateTime minusTime = subtractTimes(slotDuration, appointmentTime);
        LocalDateTime plusTime = sumTimes(appointmentTime);

        return arrivalTime.isAfter(minusTime) && arrivalTime.isBefore(plusTime);
    }

    private LocalDateTime subtractTimes(int slotDuration, LocalDateTime appointmentTime) {
        return appointmentTime.minusMinutes(slotDuration);
    }

    private LocalDateTime sumTimes(LocalDateTime appointmentTime) {
        return appointmentTime.plusMinutes(10);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrival arrival = (Arrival) o;
        return snsNumber == arrival.snsNumber;
    }

}
