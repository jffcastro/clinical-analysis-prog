package app.controller;

import app.domain.model.*;
import app.domain.shared.Constants;
import app.domain.shared.GenericClass;
import app.stores.VaccinationCentersStore;

import java.io.NotSerializableException;
import java.time.LocalDateTime;
import java.util.List;

public class RegisterTheArrivalOfAnSnsUserController {

    private VaccinationCenter vaccinationCenter;
    private ScheduledVaccine appointment;
    private Arrival arrival;
    private final Company company = App.getInstance().getCompany();
    GenericClass<Arrival> generics = new GenericClass<>();
    private final VaccinationCentersStore vaccinationCentersStore = company.getVaccinationCentersStore();

    public RegisterTheArrivalOfAnSnsUserController() {}

    /**
     * Sets the Receptionist vaccination center
     *
     * @param index Position of the vaccination center
     */
    public void setVaccinationCenter(int index) {
        vaccinationCenter = vaccinationCentersStore.getVaccinationCenters().get(index);
    }



    /**
     * Check if a User has an appointment
     *
     * @param snsNumber Number that identifies the SNS user
     * @return ScheduleVaccine - return an appointment of a user
     */
    public boolean checkAndSetUserAppointment(int snsNumber) {
        List<ScheduledVaccine> vaccineAppointments = vaccinationCenter.getScheduledVaccineList();

        for (ScheduledVaccine vaccineAppointment : vaccineAppointments)
            if (vaccineAppointment.getSnsNumber() == snsNumber) {
                appointment = vaccineAppointment;
                return true;
            }

        appointment = null;
        return false;
    }

    /**
     * Checks if a user has already been registered
     *
     * @param snsNumber The number that identifies an SNS user
     * @return boolean - true if the user is already registered
     */
    public boolean checkIfAlreadyRegistered(int snsNumber) {
        return vaccinationCenter.checkIfAlreadyRegistered(snsNumber);
    }

    /**
     * Checks if the user is on the vaccination center on the right day and time
     *
     * @return boolean - true if day and time match
     */
    public boolean validateDateAndTime() {
        return arrival.validateDateAndTime(appointment.getDate(), vaccinationCenter);
    }

    /**
     * Registers the arrival of an SNS user
     *
     */
    public void registerArrival() {
        vaccinationCenter.registerArrival(arrival);
        vaccinationCenter.removeAppointment(appointment);
    }


    /**
     * Sets the arrival
     *
     * @param snsNumber The number that identifies an SNS user
     */
    public void setArrival(int snsNumber) {
        arrival = new Arrival(snsNumber, appointment.getVaccineType(), LocalDateTime.now());
    }

    public int getUserIndexInUsersList(int snsNumber) {
        return company.getUserIndexInUsersList(snsNumber);
    }

    /**
     * Exports the list of Users that arrived at a vaccination center to a binary file.
     * @throws NotSerializableException
     */
    public void exportDataToFile() throws NotSerializableException {
        for (VaccinationCenter vaccinationCenter : vaccinationCentersStore.getVaccinationCenters()) {
            generics.binaryFileWrite(Constants.FILE_PATH_ARRIVALS, vaccinationCenter.getArrivalsList());
        }
    }
}
