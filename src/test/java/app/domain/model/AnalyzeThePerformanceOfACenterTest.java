package app.domain.model;

import app.controller.AnalyzeCenterPerformanceController;
import app.controller.App;
import app.controller.CreateVaccinationCenterController;
import app.controller.RegisterNewEmployeeController;
import app.dto.MassVaccinationCenterDto;
import app.dto.RegisterNewEmployeeDto;
import app.stores.VaccinationCentersStore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class AnalyzeThePerformanceOfACenterTest {

    private PerformanceAnalyzer performanceAnalyzer;
    private VaccinationCenter center;
    private String timeInterval;
    LocalDate date;
    AnalyzeCenterPerformanceController controller;


    @BeforeAll
    private void setUp() {
        final Company company = App.getInstance().getCompany();
        final VaccinationCentersStore vaccinationCentersStore = company.getVaccinationCentersStore();

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
        company.getAuthFacade().doLogin("ana@gmail.com", "AAA11aa");

        CreateVaccinationCenterController ctrlVc = new CreateVaccinationCenterController();
        MassVaccinationCenterDto mvcDto = new MassVaccinationCenterDto();
        mvcDto.strID = "1234";
        mvcDto.strName = "CVC Matosinhos";
        mvcDto.strPhoneNumber = "915607321";
        mvcDto.strFax = "915607321";
        mvcDto.strEmail = "cvcmatosinhos@gmail.com";
        mvcDto.strClosingHour = "21";
        mvcDto.strOpeningHour = "8";
        mvcDto.strVaccinesPerSlot = "1";
        mvcDto.strSlotDuration = "30";
        mvcDto.strWebsite = "www.cvcmatosinhos.com";
        mvcDto.strRoad = "Rua do Amial";
        mvcDto.strZipCode = "4460-098";
        mvcDto.strLocal = "Matosinhos";
        mvcDto.strCenterCoordinatorID = "00001";
        mvcDto.vaccineType = new VaccineType("COVID", "To prevent serious COVID-19 infections", VaccineType.vaccineTechnologies[5]);
        ctrlVc.saveMassVaccinationCenter(mvcDto);

        center = vaccinationCentersStore.getVaccinationCenterAssociatedToCoordinator(dtoEmp.id);
        performanceAnalyzer = new PerformanceAnalyzer(center);
        controller = new AnalyzeCenterPerformanceController();

        timeInterval = "60";
        date = LocalDate.of(2022, 5, 30);

        VaccineType vt1 = new VaccineType("12345" ,"Covid", VaccineType.vaccineTechnologies[0]);


        center.getArrivalsList().add(new Arrival(161597040, vt1, LocalDateTime.of(2022, 5, 30, 9, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 9, 0)));
        center.getArrivalsList().add(new Arrival(161597042, vt1, LocalDateTime.of(2022, 5, 30, 9, 0)));
        center.getArrivalsList().add(new Arrival(161597043, vt1, LocalDateTime.of(2022, 5, 30, 10, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 10, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 11, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 12, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 12, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 12, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 15, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 15, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 17, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 17, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 18, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 18, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 18, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 18, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 19, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 19, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 19, 0)));
        center.getArrivalsList().add(new Arrival(161597041, vt1, LocalDateTime.of(2022, 5, 30, 20, 0)));


        center.getDeparturesList().add(new Departure(161597040, LocalDateTime.of(2022, 5, 30, 9, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 9, 0)));
        center.getDeparturesList().add(new Departure(161597042, LocalDateTime.of(2022, 5, 30, 10, 0)));
        center.getDeparturesList().add(new Departure(161597043, LocalDateTime.of(2022, 5, 30, 10, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 10, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 11, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 12, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 12, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 13, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 14, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 15, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 15, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 16, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 17, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 18, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 19, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 19, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 19, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 20, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 20, 0)));
        center.getDeparturesList().add(new Departure(161597041, LocalDateTime.of(2022, 5, 30, 20, 0)));
    }



    /*
    * Test for the lenght of the list of arrivals and departures
     */
    @Test
    public void lengthOfList() {
        int minutesOfTheWorkingHoursOfTheCenter = Integer.parseInt(center.getStrClosingHour()) - Integer.parseInt(center.getStrOpeningHour());
        int lengthOfTheList = minutesOfTheWorkingHoursOfTheCenter * 60 / Integer.parseInt(timeInterval);

        int expectedValue = performanceAnalyzer.getTheStatisticsDailyList(date, Integer.parseInt(timeInterval)).length;

        assertEquals(lengthOfTheList, expectedValue);
    }


    @Test
    public void analyzeThePerformanceCorrect() {
        assertTrue(controller.checkIfTimeIntervalIsValid(timeInterval));

        int[] listToBeAnalyzed = {0,1, -1, 0, 1, -1, -1, 0, -1, 1, 3,0, -2};
        int[] expectedListToBeAnalyze = performanceAnalyzer.getTheStatisticsDailyList(date, Integer.parseInt(timeInterval));
        assertArrayEquals(listToBeAnalyzed, expectedListToBeAnalyze);

        int[] maxSumSubList = {1, 3};
        int[] expectedMaxSumSubList = performanceAnalyzer.getMaxSumSubList();
        assertArrayEquals(maxSumSubList, expectedMaxSumSubList);

        int sum = 4;
        int expectedSum = performanceAnalyzer.getMaxSum();
        assertEquals(sum, expectedSum);
    }

    @Test
    public void analyzeThePerformanceWithWrongTimeInterval() {
        timeInterval = "800";

        assertFalse(controller.checkIfTimeIntervalIsValid(timeInterval));

        int[] listToBeAnalyzed = {};
        int[] expectedListToBeAnalyze = performanceAnalyzer.getTheStatisticsDailyList(date, Integer.parseInt(timeInterval));
        assertArrayEquals(listToBeAnalyzed, expectedListToBeAnalyze);

        int[] maxSumSubList = {0};
        int[] expectedMaxSumSubList = performanceAnalyzer.getMaxSumSubList();
        assertArrayEquals(maxSumSubList, expectedMaxSumSubList);

        int sum = 0;
        int expectedSum = performanceAnalyzer.getMaxSum();
        assertEquals(sum, expectedSum);
    }


}