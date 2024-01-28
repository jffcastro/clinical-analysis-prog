package app.controller;

import app.domain.model.*;
import app.dto.RegisterNewEmployeeDto;
import app.dto.VaccinationCenterDto;
import app.mapper.VaccinationCenterMapper;
import app.stores.VaccinationCentersStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckAndExportVaccinationStatsControllerTest {
    private Company company;
    private  VaccinationCentersStore vaccinationCentersStore;

    @BeforeEach
    void setUp() {
        company = App.getInstance().getCompany();
        vaccinationCentersStore = company.getVaccinationCentersStore();
        //Creating a center coordinator
        RegisterNewEmployeeController ctrlEmp = new RegisterNewEmployeeController();
        RegisterNewEmployeeDto dtoEmp = new RegisterNewEmployeeDto();
        dtoEmp.id = "00001";
        dtoEmp.name = "Ana";
        dtoEmp.password = "AAA11aa";
        dtoEmp.phoneNumber = "915604427";
        dtoEmp.citizenCardNumber = "11960343 8 ZW1";
        dtoEmp.email = "ana@gmail.com";
        dtoEmp.address = "Via Diagonal / 4475-079 / Porto";
        ctrlEmp.saveCreatedEmployee(dtoEmp, "Center Coordinator");
        //Logging the center coordinator
        company.getAuthFacade().doLogin("ana@gmail.com", "AAA11aa");

        VaccinationCenterDto centerDto = new VaccinationCenterDto();
        centerDto.strID = "1234";
        centerDto.strName = "CVC Matosinhos";
        centerDto.strPhoneNumber = "915607321";
        centerDto.strFax = "915607321";
        centerDto.strEmail = "cvcmatosinhos@gmail.com";
        centerDto.strClosingHour = "21";
        centerDto.strOpeningHour = "9";
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
        Vaccine vaccine = new Vaccine("Spikevax", 1000, "SpikeBrand", administrationProcess, new VaccineType("COVID", "description", "Tech"));
        company.saveVaccineBs(vaccine);

    }

    @Test
    void getVaccinationStatsListBetweenDates() {

        VaccineBulletin vaccineBulletin = new VaccineBulletin(company.getVaccinesList().get(0), LocalDateTime.now().minusDays(1), 2, "12345-12");
        vaccinationCentersStore.getVaccinationCenters().get(0).addFullyVaccinated(vaccineBulletin);
        VaccineBulletin vaccineBulletin1 = new VaccineBulletin(company.getVaccinesList().get(0), LocalDateTime.now(), 2, "12345-12");
        vaccinationCentersStore.getVaccinationCenters().get(0).addFullyVaccinated(vaccineBulletin1);
        CheckAndExportVaccinationStatsController controller = new CheckAndExportVaccinationStatsController();

        LocalDate firstDate = LocalDate.now().minusDays(5);
        LocalDate secondDate = LocalDate.now();
controller.setFirstDate(firstDate);
        controller.setLastDate(secondDate);
        assertEquals(2, controller.getVaccinationStatsListBetweenDates().size());
    }


    @Test
    void exportVaccinationStats() {
        CheckAndExportVaccinationStatsController controller = new CheckAndExportVaccinationStatsController();
        LocalDate firstDate = LocalDate.now().minusDays(5);
        LocalDate secondDate = LocalDate.now();

        VaccineBulletin vaccineBulletin = new VaccineBulletin(company.getVaccinesList().get(0), LocalDateTime.now().minusDays(1), 2, "54321-12");
        vaccinationCentersStore.getVaccinationCenters().get(0).addFullyVaccinated(vaccineBulletin);
        controller.setFirstDate(firstDate);
        controller.setLastDate(secondDate);
        assertTrue(controller.exportVaccinationStats("fileTest"));
    }



    @Test
    void checkIfDatesAreValid() {
        LocalDate firstDate = LocalDate.now().minusDays(3);
        LocalDate secondDate = LocalDate.now();
        CheckAndExportVaccinationStatsController ctrl = new CheckAndExportVaccinationStatsController();
        ctrl.setFirstDate(firstDate);
        ctrl.setLastDate(secondDate);
        assertEquals(0, ctrl.checkIfDatesAreValid());
    }

    @Test
    void checkIfDatesAreValidWithTwoNullDates() {
        LocalDate firstDate = null;
        LocalDate secondDate = null;
        CheckAndExportVaccinationStatsController ctrl = new CheckAndExportVaccinationStatsController();
        ctrl.setFirstDate(firstDate);
        ctrl.setLastDate(secondDate);
        assertEquals(1, ctrl.checkIfDatesAreValid());
    }

    @Test
    void checkIfDatesAreValidWithFirstDateAfterLastDate() {
        LocalDate firstDate = LocalDate.now();
        LocalDate secondDate = LocalDate.now().minusDays(1);
        CheckAndExportVaccinationStatsController ctrl = new CheckAndExportVaccinationStatsController();
        ctrl.setFirstDate(firstDate);
        ctrl.setLastDate(secondDate);
        assertEquals(2, ctrl.checkIfDatesAreValid());
    }

    @Test
    void checkIfDatesAreValidWithFirstDateBefore1stJanuary2021() {
        LocalDate firstDate = LocalDate.of(2020, 1, 1);
        LocalDate secondDate = LocalDate.now().minusDays(1);
        CheckAndExportVaccinationStatsController ctrl = new CheckAndExportVaccinationStatsController();
        ctrl.setFirstDate(firstDate);
        ctrl.setLastDate(secondDate);
        assertEquals(3, ctrl.checkIfDatesAreValid());
    }

    @Test
    void checkIfDatesAreValidWithLastDateAfterToday() {
        LocalDate firstDate = LocalDate.now().minusDays(5);
        LocalDate secondDate = LocalDate.now().plusDays(1);
        CheckAndExportVaccinationStatsController ctrl = new CheckAndExportVaccinationStatsController();
        ctrl.setFirstDate(firstDate);
        ctrl.setLastDate(secondDate);
        assertEquals(4, ctrl.checkIfDatesAreValid());
    }


}