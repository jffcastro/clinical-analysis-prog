package app.domain.model;

import app.controller.App;
import app.controller.ScheduleVaccineController;
import app.stores.VaccinationCentersStore;
import app.ui.console.utils.Utils;
import app.dto.ScheduledVaccineDto;
import app.mapper.ScheduledVaccineMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;

class ScheduledVaccineTest {

    private final ScheduledVaccineMapper mapper = new ScheduledVaccineMapper();
    private final ScheduleVaccineController controller = new ScheduleVaccineController();
    private final Company company = App.getInstance().getCompany();
    private final VaccinationCentersStore vaccinationCentersStore = company.getVaccinationCentersStore();

    @Test
    void addTwoAppointmentForTheSameVaccine() {
        ScheduledVaccineDto scheduledVaccineDto1 = new ScheduledVaccineDto();
        ScheduledVaccineDto scheduledVaccineDto2 = new ScheduledVaccineDto();
        final int snsUserNumber = 999999999;
        final VaccineType vaccineType = new VaccineType("TEST1", "test", "test1");
        scheduledVaccineDto1.vaccineType = vaccineType;
        scheduledVaccineDto1.date = LocalDateTime.of(2022, 6, 22, 10, 30);
        scheduledVaccineDto1.snsNumber = snsUserNumber;

        ScheduledVaccine appointment1 = mapper.createScheduledVaccine(scheduledVaccineDto1);
        company.addAppointment(appointment1);

        scheduledVaccineDto2.vaccineType = vaccineType;
        scheduledVaccineDto2.date = LocalDateTime.of(2022, 6, 23, 10, 30);
        scheduledVaccineDto2.snsNumber = snsUserNumber;

        assertFalse(company.userIsEligibleForTheAppointment(scheduledVaccineDto2));
        company.cleanAppointments();
    }

    @Test
    void addNullAppointment() {
        ScheduledVaccineDto scheduledVaccineDto1 = new ScheduledVaccineDto();
        controller.setVaccinationCenter(0);

        assertFalse(controller.validateAppointment(scheduledVaccineDto1));
        company.cleanAppointments();
    }

    @Test
    void addAppointmentWithNullSnsNumber() {
        Utils.bootstrapVaccineTypes();
        Utils.bootstrapVaccinationCenters();
        ScheduledVaccineDto scheduledVaccineDto1 = new ScheduledVaccineDto();
        controller.setVaccinationCenter(0);
        scheduledVaccineDto1.vaccineType = company.getVaccineTypesStore().getVaccineTypes().get(0);
        scheduledVaccineDto1.date = LocalDateTime.of(2022, 6, 22, 10, 0);
        assertFalse(controller.validateAppointment(scheduledVaccineDto1));
        company.cleanAppointments();
    }

    @Test
    void addAppointmentWithNullVaccineType() {
        Utils.bootstrapVaccineTypes();
        Utils.bootstrapVaccinationCenters();
        ScheduledVaccineDto scheduledVaccineDto1 = new ScheduledVaccineDto();
        controller.setVaccinationCenter(0);
        scheduledVaccineDto1.snsNumber = 999999999;
        scheduledVaccineDto1.date = LocalDateTime.of(2022, 6, 22, 10, 0);
        assertFalse(controller.validateAppointment(scheduledVaccineDto1));
        company.cleanAppointments();
    }

    @Test
    void addAppointmentWithNullDate() {
        Utils.bootstrapVaccineTypes();
        Utils.bootstrapVaccinationCenters();
        ScheduledVaccineDto scheduledVaccineDto1 = new ScheduledVaccineDto();
        controller.setVaccinationCenter(0);
        scheduledVaccineDto1.snsNumber = 999999999;
        scheduledVaccineDto1.vaccineType = company.getVaccineTypesStore().getVaccineTypes().get(0);
        assertFalse(controller.validateAppointment(scheduledVaccineDto1));
        company.cleanAppointments();
    }

    @Test
    void addAppointmentToDayWithNoAvailability() {
        Utils.bootstrapVaccineTypes();
        Utils.bootstrapVaccinationCenters();

        ScheduledVaccineDto scheduledVaccineDto1 = new ScheduledVaccineDto();
        ScheduledVaccineDto scheduledVaccineDto2 = new ScheduledVaccineDto();
        VaccinationCenter vaccinationCenter = new VaccinationCenter("test", "test", "911111111", "test@gmail.com", "911111111", "www.test.com", "9", "16", "420", "1", "test", "4470-111", "test", company.getEmployeesStore().getCenterCoordinatorList().get(0).getId());
        vaccinationCentersStore.getVaccinationCenters().add(vaccinationCenter);

        controller.setVaccinationCenter(vaccinationCentersStore.getVaccinationCenters().size() - 1);

        final VaccineType vaccineType = new VaccineType("TEST1", "test", "test1");
        scheduledVaccineDto1.vaccineType = vaccineType;
        scheduledVaccineDto1.date = LocalDateTime.of(2022, 6, 22, 9, 0);
        scheduledVaccineDto1.snsNumber = 999999999;

        ScheduledVaccine appointment1 = mapper.createScheduledVaccine(scheduledVaccineDto1);
        assertTrue(controller.scheduleVaccine(scheduledVaccineDto1));
        company.addAppointment(appointment1);

        scheduledVaccineDto2.vaccineType = vaccineType;
        scheduledVaccineDto2.date = LocalDateTime.of(2022, 6, 22, 10, 30);
        scheduledVaccineDto2.snsNumber = 888888888;

        assertFalse(controller.scheduleVaccine(scheduledVaccineDto2));
        company.cleanAppointments();
    }

    @Test
    void addValidAppointment() {
        Utils.bootstrapVaccineTypes();
        Utils.bootstrapVaccinationCenters();

        ScheduledVaccineDto scheduledVaccineDto1 = new ScheduledVaccineDto();
        VaccinationCenter vaccinationCenter = new VaccinationCenter("test", "test", "911111111", "test@gmail.com", "911111111", "www.test.com", "9", "16", "420", "1", "test", "4470-111", "test", company.getEmployeesStore().getCenterCoordinatorList().get(0).getId());
        vaccinationCentersStore.getVaccinationCenters().add(vaccinationCenter);

        controller.setVaccinationCenter(vaccinationCentersStore.getVaccinationCenters().size()-1);


        scheduledVaccineDto1.vaccineType = new VaccineType("TEST1", "test", "test1");
        scheduledVaccineDto1.date = LocalDateTime.of(2022, 6, 22, 9, 0);
        scheduledVaccineDto1.snsNumber = 999999999;

        ScheduledVaccine appointment1 = mapper.createScheduledVaccine(scheduledVaccineDto1);
        assertTrue(controller.scheduleVaccine(scheduledVaccineDto1));
         company.addAppointment(appointment1);

        company.cleanAppointments();
    }

    @Test
    void addAppointmentToSlotWithNoAvailability() {
        Utils.bootstrapVaccineTypes();
        Utils.bootstrapVaccinationCenters();

        ScheduledVaccineDto scheduledVaccineDto1 = new ScheduledVaccineDto();
        ScheduledVaccineDto scheduledVaccineDto2 = new ScheduledVaccineDto();
        VaccinationCenter vaccinationCenter = new VaccinationCenter("test", "test", "911111111", "test@gmail.com", "911111111", "www.test.com", "9", "16", "30", "1", "test", "4470-111", "test", company.getEmployeesStore().getCenterCoordinatorList().get(0).getId());
        vaccinationCentersStore.getVaccinationCenters().add(vaccinationCenter);

        controller.setVaccinationCenter(vaccinationCentersStore.getVaccinationCenters().size()-1);

        final VaccineType vaccineType = new VaccineType("TEST1", "test", "test1");
        scheduledVaccineDto1.vaccineType = vaccineType;
        scheduledVaccineDto1.date = LocalDateTime.of(2022, 6, 22, 9, 30);
        scheduledVaccineDto1.snsNumber = 999999999;

        ScheduledVaccine appointment1 = mapper.createScheduledVaccine(scheduledVaccineDto1);
        assertTrue(controller.scheduleVaccine(scheduledVaccineDto1));
        company.addAppointment(appointment1);

        scheduledVaccineDto2.vaccineType = vaccineType;
        scheduledVaccineDto2.date = LocalDateTime.of(2022, 6, 22, 9, 30);
        scheduledVaccineDto2.snsNumber = 888888888;

        assertFalse(controller.scheduleVaccine(scheduledVaccineDto2));
        company.cleanAppointments();
    }
}

