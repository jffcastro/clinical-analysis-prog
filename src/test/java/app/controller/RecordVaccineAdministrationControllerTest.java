package app.controller;

import app.domain.model.*;
import app.domain.shared.Constants;
import app.dto.RegisterNewEmployeeDto;
import app.dto.VaccinationCenterDto;
import app.mapper.SnsUserMapper;
import app.mapper.VaccinationCenterMapper;
import app.stores.VaccinationCentersStore;
import app.ui.console.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.NotSerializableException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecordVaccineAdministrationControllerTest {
    private Company company;
    private VaccinationCentersStore vaccinationCentersStore;

    @BeforeEach
    void setUp() {
        company = App.getInstance().getCompany();
        company.getVaccinesList().clear();
        company.getVaccineTypesStore().getVaccineTypes().clear();
        company.getVaccinationCentersStore().getVaccinationCenters().clear();
        company.getSnsUsersStore().getSnsUserList().clear();
        company.getAppointments().clear();
        vaccinationCentersStore = company.getVaccinationCentersStore();
        //Creating a center coordinator
        RegisterNewEmployeeController ctrlEmp = new RegisterNewEmployeeController();
        RegisterNewEmployeeDto dtoEmp = new RegisterNewEmployeeDto();
        dtoEmp.id = "00001";
        dtoEmp.name = "Test";
        dtoEmp.password = "AAA11aa";
        dtoEmp.phoneNumber = "912345679";
        dtoEmp.citizenCardNumber = "11960343 8 ZW1";
        dtoEmp.email = "test@gmail.com";
        dtoEmp.address = "Via Diagonal / 1234-123 / Porto";
        ctrlEmp.saveCreatedEmployee(dtoEmp, "Center Coordinator");
        //Logging the center coordinator
        company.getAuthFacade().doLogin("test@gmail.com", "AAA11aa");

        VaccinationCenterDto centerDto = new VaccinationCenterDto();
        centerDto.strID = "1234";
        centerDto.strName = "CVC Matosinhos";
        centerDto.strPhoneNumber = "915607321";
        centerDto.strFax = "915607321";
        centerDto.strEmail = "cvcmatosinhos@gmail.com";
        centerDto.strOpeningHour = "9";
        centerDto.strClosingHour = "21";
        centerDto.strVaccinesPerSlot = "1";
        centerDto.strSlotDuration = "30";
        centerDto.strWebsite = "www.cvcmatosinhos.com";
        centerDto.strRoad = "Rua do Amial";
        centerDto.strZipCode = "4460-098";
        centerDto.strLocal = "Matosinhos";
        centerDto.strCenterCoordinatorID = "00001";
        VaccinationCenterMapper mapper = new VaccinationCenterMapper();
        vaccinationCentersStore.getVaccinationCenters().add(mapper.dtoToDomain(centerDto));

        ArrayList<Integer> minAge = new ArrayList<>(List.of(1, 20, 50, 100));
        ArrayList<Integer> maxAge = new ArrayList<>(List.of(19, 49, 99, 120));
        ArrayList<Integer> numberOfDoses = new ArrayList<>(List.of(2, 3, 3, 2));
        ArrayList<Double> dosage = new ArrayList<>(List.of(25.0, 35.0, 30.0, 20.5));
        ArrayList<Integer> timeIntervalBetween1stAnd2nd = new ArrayList<>(List.of(15, 15, 15, 15));
        ArrayList<Integer> timeIntervalBetween2ndAnd3rd = new ArrayList<>(List.of(0, 150, 180, 0));
        AdministrationProcess administrationProcess = new AdministrationProcess(new ArrayList<>(List.of(minAge, maxAge)), numberOfDoses, dosage, new ArrayList<>(List.of(timeIntervalBetween1stAnd2nd, timeIntervalBetween2ndAnd3rd)));
        VaccineType vaccineType = new VaccineType("COVID", "description", "Tech");
        company.getVaccineTypesStore().getVaccineTypes().add(vaccineType);
        Vaccine vaccine = new Vaccine("Spikevax", 1000, "SpikeBrand", administrationProcess, company.getVaccineTypesStore().getVaccineTypes().get(0));
        company.saveVaccineBs(vaccine);

        SnsUser snsUser = new SnsUser("Test", "Male", "05/12/2000", "Rua # 1234-123 # Valongo", "912321123", "testUser@gmail.com", 123456789, "15925823 5 ZX3", "AAA11aa");
        company.getSnsUsersStore().getSnsUserList().add(snsUser);
    }

    @Test
    void firstDoseVaccine() throws IOException {

        ScheduledVaccine scheduledVaccine = new ScheduledVaccine(123456789, company.getVaccineTypesStore().getVaccineTypes().get(0), LocalDateTime.of(2022, 6, 16, 23, 30));
        try {
            company.getVaccinationCentersStore().getVaccinationCenters().get(0).addAppointment(scheduledVaccine);
        } catch (NotSerializableException e) {
            throw new RuntimeException(e);
        }

        Arrival arrival = new Arrival(123456789, company.getVaccineTypesStore().getVaccineTypes().get(0), LocalDateTime.of(2022, 6, 16, 23, 30));
        try {
            company.getVaccinationCentersStore().getVaccinationCenters().get(0).addArrival(arrival,false);
        } catch (NotSerializableException e) {
            throw new RuntimeException(e);
        }

        RecordVaccineAdministrationController controller = new RecordVaccineAdministrationController();
        SnsUserMapper snsUserMapper = new SnsUserMapper();
        controller.setVaccinationCenter(0);
        controller.fillListWithUserSnsNumber();
        controller.setSnsUser(snsUserMapper.domainToSNSUserDto(company.getSnsUsersStore().getSnsUserList().get(0)));
        controller.setVaccineType(0);
        if (controller.getUserNumberOfDoses() == Constants.FIRST_DOSE && controller.checkIfArrivalsListEmpty()) {
            controller.setVaccine(0);
            controller.setLocalDateTime();
            controller.setLotNumber("12345-12");
            if (controller.validateLotNumber("12345-12")) {
                controller.registerVaccineInVaccineBulletin();
                controller.setRecoveryTimeSMS();
                controller.printRecoveryTime();
            }
        }
        assertFalse(company.getSnsUsersStore().getSnsUserList().get(0).administratedVaccines().isEmpty());
    }


    @Test
    void invalidLotNumber() throws IOException {

        ScheduledVaccine scheduledVaccine = new ScheduledVaccine(123456789, company.getVaccineTypesStore().getVaccineTypes().get(0), LocalDateTime.of(2022, 6, 16, 23, 30));
        try {
            company.getVaccinationCentersStore().getVaccinationCenters().get(0).addAppointment(scheduledVaccine);
        } catch (NotSerializableException e) {
            throw new RuntimeException(e);
        }

        Arrival arrival = new Arrival(123456789, company.getVaccineTypesStore().getVaccineTypes().get(0), LocalDateTime.of(2022, 6, 16, 23, 30));
        try {
            company.getVaccinationCentersStore().getVaccinationCenters().get(0).addArrival(arrival, false);
        } catch (NotSerializableException e) {
            throw new RuntimeException(e);
        }

        RecordVaccineAdministrationController controller = new RecordVaccineAdministrationController();
        SnsUserMapper snsUserMapper = new SnsUserMapper();
        controller.vaccinationCentersAvailable();
        controller.setVaccinationCenter(0);
        controller.setSnsUser(snsUserMapper.domainToSNSUserDto(company.getSnsUsersStore().getSnsUserList().get(0)));
        controller.setVaccineType(0);
        controller.vaccineTypeAvailableVaccines();
        controller.vaccineAvailableName();
        if (controller.getUserNumberOfDoses() == Constants.FIRST_DOSE) {
            if (controller.validateLotNumber("12345")) {
                controller.setVaccine(0);
                controller.vaccineInfo();
                controller.vaccineTypeInfo();
                controller.setLocalDateTime();
                controller.setLotNumber("12345");
                controller.registerVaccineInVaccineBulletin();
                controller.setRecoveryTimeSMS();
                controller.printRecoveryTime();
            }
        }
        assertTrue(company.getSnsUsersStore().getSnsUserList().get(0).administratedVaccines().isEmpty());
    }

    @Test
    void notFirstDoseVaccine() throws IOException {

        VaccineBulletin vaccineBulletin = new VaccineBulletin(company.getVaccinesList().get(0), LocalDateTime.of(2022, 6, 16, 23, 30), 1, "12345-12");
        company.getSnsUsersStore().getSnsUserList().get(0).administratedVaccines().add(vaccineBulletin);

        ScheduledVaccine scheduledVaccine = new ScheduledVaccine(123456789, company.getVaccineTypesStore().getVaccineTypes().get(0), LocalDateTime.of(2022, 9, 16, 23, 30));
        try {
            company.getVaccinationCentersStore().getVaccinationCenters().get(0).addAppointment(scheduledVaccine);
        } catch (NotSerializableException e) {
            throw new RuntimeException(e);
        }

        Arrival arrival = new Arrival(123456789, company.getVaccineTypesStore().getVaccineTypes().get(0), LocalDateTime.of(2022, 9, 16, 23, 30));
        try {
            company.getVaccinationCentersStore().getVaccinationCenters().get(0).addArrival(arrival, false);
        } catch (NotSerializableException e) {
            throw new RuntimeException(e);
        }

        RecordVaccineAdministrationController controller = new RecordVaccineAdministrationController();
        SnsUserMapper snsUserMapper = new SnsUserMapper();
        controller.setVaccinationCenter(0);
        controller.setSnsUser(snsUserMapper.domainToSNSUserDto(company.getSnsUsersStore().getSnsUserList().get(0)));
        controller.getSnsUserInformation(0);
        controller.fillListWithUserSnsNumber();
        controller.setVaccineType(0);
        if (controller.getUserNumberOfDoses() != Constants.FIRST_DOSE) {
            controller.setVaccine(0);
            controller.setLocalDateTime();
            controller.setLotNumber("12345-12");
            if (controller.validateLotNumber("12345-12")) {
                controller.registerVaccineInVaccineBulletin();
                controller.setRecoveryTimeSMS();
            }
        }
        assertEquals(company.getSnsUsersStore().getSnsUserList().get(0).administratedVaccines().size(), 2);
    }

    @Test
    void ageGroupNotFittingForUser() throws IOException {

        SnsUser snsUser = new SnsUser("Test", "Male", "05/12/2020", "Rua # 1234-124 # Valongo", "912345123", "testUser2@gmail.com", 123451234, "35899118 8 ZZ0", "AAA11aa");
        company.getSnsUsersStore().getSnsUserList().add(snsUser);

        ScheduledVaccine scheduledVaccine = new ScheduledVaccine(123451234, company.getVaccineTypesStore().getVaccineTypes().get(0), LocalDateTime.of(2022, 6, 16, 23, 30));
        try {
            company.getVaccinationCentersStore().getVaccinationCenters().get(0).addAppointment(scheduledVaccine);
        } catch (NotSerializableException e) {
            throw new RuntimeException(e);
        }

        Arrival arrival = new Arrival(123451234, company.getVaccineTypesStore().getVaccineTypes().get(0), LocalDateTime.of(2022, 6, 16, 23, 30));
        try {
            company.getVaccinationCentersStore().getVaccinationCenters().get(0).addArrival(arrival, false);
        } catch (NotSerializableException e) {
            throw new RuntimeException(e);
        }

        RecordVaccineAdministrationController controller = new RecordVaccineAdministrationController();
        SnsUserMapper snsUserMapper = new SnsUserMapper();
        controller.setVaccinationCenter(0);
        controller.setSnsUser(snsUserMapper.domainToSNSUserDto(company.getSnsUsersStore().getSnsUserList().get(1)));
        controller.setVaccineType(0);
        if (controller.getUserNumberOfDoses() == Constants.FIRST_DOSE) {
            if (!controller.vaccineTypeAvailableVaccines().isEmpty()) {
                controller.setVaccine(0);
                controller.setLocalDateTime();
                controller.setLotNumber("12345-12");
                if (controller.validateLotNumber("12345-12")) {
                    controller.registerVaccineInVaccineBulletin();
                    controller.setRecoveryTimeSMS();
                    controller.printRecoveryTime();
                }
            }
        }
        assertTrue(company.getSnsUsersStore().getSnsUserList().get(1).administratedVaccines().isEmpty());
    }
}