package app.domain.model;

import app.controller.App;
import app.controller.RegisterTheArrivalOfAnSnsUserController;
import app.stores.VaccinationCentersStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterTheArrivalOfAnSnsUserTest {

    private final Company company = App.getInstance().getCompany();
    private final RegisterTheArrivalOfAnSnsUserController ctrl = new RegisterTheArrivalOfAnSnsUserController();
private  final VaccinationCentersStore vaccinationCentersStore = company.getVaccinationCentersStore();

    /**
     * Creations instances of what is necessary in order to test the User Story correctly
     */
    @BeforeEach
    private void setUp() {
        VaccinationCenter vcR = new VaccinationCenter("1234", "CVC Matosinhos", "915607321", "cvcmatosinhos@gmail.com", "915607321", "www.cvcmatosinhos.com", "9",
                "16", "30", "1", "Rua do Amial", "4460-098", "Matosinhos", "CC-95634");
        VaccinationCenter vcU = new VaccinationCenter("1234", "Isep", "915607321", "cvcmatosinhos@gmail.com", "915607321", "www.cvcmatosinhos.com", "9",
                "16", "30", "1", "Rua do Amial", "4460-098", "Matosinhos", "CC-95634");
        vaccinationCentersStore.getVaccinationCenters().clear();
        vaccinationCentersStore.getVaccinationCenters().add(vcR);
        vaccinationCentersStore.getVaccinationCenters().add(vcU);

        VaccineType vt1 = new VaccineType("12345" ,"Covid", VaccineType.vaccineTechnologies[0]);

        ScheduledVaccine appointment1 = new ScheduledVaccine(100000000, vt1, LocalDateTime.now());
        ScheduledVaccine appointment2 = new ScheduledVaccine(200000000, vt1, LocalDateTime.now());

        vcR.getScheduledVaccineList().add(appointment1);
        vcU.getScheduledVaccineList().add(appointment2);
    }


   @Test
    /**
     * Verifies if a valid arrival meets the requirements to be registered
     *
     * This test depends on the hour previously created on the creationOfTheNecessary() method
     */
    public void registerValidArrival() {

        ctrl.setVaccinationCenter(0);
        ctrl.checkAndSetUserAppointment(100000000);
        ctrl.setArrival(100000000);

        assertTrue(ctrl.checkAndSetUserAppointment(100000000) && ctrl.checkIfAlreadyRegistered(100000000) && ctrl.validateDateAndTime());

    }


    @Test
    /**
     * Verifies if a user with no appointment meets the requirements to be registered
     */
    public void registerArrivalWithNoAppointment() {



        ctrl.setVaccinationCenter(0);
        ctrl.checkAndSetUserAppointment(300000000);

        assertFalse(ctrl.checkAndSetUserAppointment(300000000) && ctrl.checkIfAlreadyRegistered(300000000) && ctrl.validateDateAndTime());
    }

    @Test
    /**
     * Verifies if an Arrival with a wrong date meets the requirements to be registered
     */
    public void registerArrivalWithWrongDate() {

        ctrl.setVaccinationCenter(0);
        ctrl.checkAndSetUserAppointment(100000000);
        ctrl.setArrival(100000000);

        assertFalse(ctrl.checkAndSetUserAppointment(200000000) && ctrl.checkIfAlreadyRegistered(200000000) && ctrl.validateDateAndTime());
    }


    @Test
    /**
     * Verifies if an already registered arrival meets the requirements to be registered
     */
    public void registerArrivalWithAnAlreadyRegisteredArrival() {



        ctrl.setVaccinationCenter(0);
        ctrl.checkAndSetUserAppointment(100000000);
        ctrl.setArrival(100000000);
        ctrl.registerArrival();

        assertFalse(ctrl.checkAndSetUserAppointment(100000000) && ctrl.checkIfAlreadyRegistered(100000000) && ctrl.validateDateAndTime());
    }




}