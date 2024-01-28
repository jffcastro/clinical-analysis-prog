package app.domain.model;

import app.controller.App;
import app.controller.CreateVaccinationCenterController;
import app.controller.RegisterTheArrivalOfAnSnsUserController;
import app.domain.shared.Constants;
import app.dto.MassVaccinationCenterDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class registerDailyTotalOfVaccinatedPeopleTest {

    private final Company company = App.getInstance().getCompany();
    private final RegisterTheArrivalOfAnSnsUserController controllerArrivals = new RegisterTheArrivalOfAnSnsUserController();
    private final CreateVaccinationCenterController controllerVaccinationCenters = new CreateVaccinationCenterController();


    private void setup() {
        //Vaccine Type
        VaccineType vt1 = new VaccineType("COVID", "A vaccine to prevent serious infections of the Covid-19 Virus", VaccineType.vaccineTechnologies[5]);
        company.getVaccineTypesStore().getVaccineTypes().add(vt1);

        //Vaccination Center
        MassVaccinationCenterDto mvcDto = new MassVaccinationCenterDto();
        mvcDto.strID = "1234";
        mvcDto.strName = "CVC Matosinhos";
        mvcDto.strPhoneNumber = "915607321";
        mvcDto.strFax = "915607321";
        mvcDto.strEmail = "cvcmatosinhos@gmail.com";
        mvcDto.strClosingHour = "21";
        mvcDto.strOpeningHour = "9";
        mvcDto.strVaccinesPerSlot = "1";
        mvcDto.strSlotDuration = "30";
        mvcDto.strWebsite = "www.cvcmatosinhos.com";
        mvcDto.strRoad = "Rua do Amial";
        mvcDto.strZipCode = "4460-098";
        mvcDto.strLocal = "Matosinhos";
        mvcDto.strCenterCoordinatorID = "00001";
        mvcDto.vaccineType = new VaccineType("COVID", "To prevent serious COVID-19 infections", VaccineType.vaccineTechnologies[5]);
        company.getVaccinationCentersStore().getVaccinationCenters().clear();
        controllerVaccinationCenters.saveMassVaccinationCenter(mvcDto);
        controllerArrivals.setVaccinationCenter(0);

        //SNSUsers
        SnsUser snsUser = new SnsUser("User Default", "Male", "01/01/1998", "Default # 4000-000 # Default", "915604428", "u@gmail.com", 100000000, "14698413 7 ZY7", "AAA00aa");
        company.getSnsUsersStore().getSnsUserList().add(snsUser);

        //Appointment
        ScheduledVaccine appointment = new ScheduledVaccine(100000000, vt1, LocalDateTime.of(2022, 6, 7, 18, 20));
        company.getVaccinationCentersStore().getVaccinationCenters().get(0).getScheduledVaccineList().add(appointment);

        //Arrivals
        controllerArrivals.checkAndSetUserAppointment(100000000);
        controllerArrivals.setArrival(100000000);
        Arrival arrival1 = new Arrival(100000000, vt1, LocalDateTime.now());
        company.getVaccinationCentersStore().getVaccinationCenters().get(0).getArrivalsList().add(arrival1);

        //Administer Vaccine
        ArrayList<Integer> minAge1 = new ArrayList<>(List.of(1, 19));
        ArrayList<Integer> maxAge1 = new ArrayList<>(List.of(18, 30));
        ArrayList<Integer> timeBetween1stAnd2ndDose1 = new ArrayList<>(List.of(15, 15));
        ArrayList<Integer> timeBetween2ndAnd3rdDose1 = new ArrayList<>(List.of(0, 150));
        AdministrationProcess administrationProcess1 = new AdministrationProcess(new ArrayList<>(Arrays.asList(minAge1, maxAge1)), new ArrayList<>(List.of(2, 3)), new ArrayList<>(List.of(20.0, 30.0)), new ArrayList<>(Arrays.asList(timeBetween1stAnd2ndDose1, timeBetween2ndAnd3rdDose1)));
        Vaccine vaccine1 = new Vaccine("Test", 12, "Brand1", administrationProcess1, company.getVaccineTypesStore().getVaccineTypes().get(0));
        company.getVaccinesList().add(vaccine1);
        VaccineBulletin vaccineBulletin1 = new VaccineBulletin(vaccine1, LocalDateTime.of(2022, 6, 7, 17, 30), 1, "54321-21");
        company.getVaccinationCentersStore().getVaccinationCenters().get(0).addAdministeredVaccine(vaccineBulletin1);
    }

   @Test
    void registerDailyTotalOfVaccinatedPeople() {
        setup();
        List<String> expectedFileList = new ArrayList<>();
        List<String> actualFileList = new ArrayList<>();
        File expectedFile = new File(Constants.DAILY_REGISTERS_FILE_NAME_EXPECTED_TEST);

        try {
            company.getVaccinationCentersStore().registerDailyTotalOfVaccinatedPeople(Constants.DAILY_REGISTERS_FILE_NAME_ACTUAL_TEST);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Scanner readExpected = new Scanner(expectedFile);
            while (readExpected.hasNextLine()) {
                String[] expectedFileArray = readExpected.nextLine().split(";");
                expectedFileList.addAll(List.of(expectedFileArray));
            }
            Scanner readActual = new Scanner(new File(Constants.DAILY_REGISTERS_FILE_NAME_ACTUAL_TEST));
            while (readActual.hasNextLine()) {
                String[] actualFileArray = readActual.nextLine().split(";");
                actualFileList.addAll(List.of(actualFileArray));
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        assertArrayEquals(expectedFileList.toArray(), actualFileList.toArray());
    }

    @Test
    void registerDailyTotalOfVaccinatedPeopleFalse() {
        List<String> expectedFileList = new ArrayList<>();
        List<String> actualFileList = new ArrayList<>();
        File expectedFile = new File(Constants.DAILY_REGISTERS_FILE_NAME_TEST_FALSE);
        File actualFile = new File(Constants.DAILY_REGISTERS_FILE_NAME_ACTUAL_TEST);
        try {
            company.getVaccinationCentersStore().registerDailyTotalOfVaccinatedPeople(Constants.DAILY_REGISTERS_FILE_NAME_ACTUAL_TEST);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Scanner readExpected = new Scanner(expectedFile);
            while (readExpected.hasNextLine()) {
                String[] expectedFileArray = readExpected.nextLine().split(";");
                expectedFileList.addAll(List.of(expectedFileArray));
            }
            Scanner readActual = new Scanner(actualFile);
            while (readActual.hasNextLine()) {
                String[] actualFileArray = readActual.nextLine().split(";");
                actualFileList.addAll(List.of(actualFileArray));
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        assertNotEquals(expectedFileList.toArray(), actualFileList.toArray());
    }
}