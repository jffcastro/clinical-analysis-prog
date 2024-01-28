package app.controller;

import app.domain.model.*;
import app.dto.RegisterNewEmployeeDto;
import app.dto.VaccinationCenterDto;
import app.mapper.VaccinationCenterMapper;
import app.stores.VaccinationCentersStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CenterCoordinatorMenuControllerTest {

    private Company company;
    private VaccinationCentersStore vaccinationCentersStore;

    @BeforeEach
    void setup(){
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


    }

    //Unit test to check if the company has enough info for vaccination stats.
    @Test
    void companyHasEnoughInfoForVaccinationStats() {

        vaccinationCentersStore.getVaccinationCenters().clear();
        CenterCoordinatorMenuController centerCoordinatorMenuController1 = new CenterCoordinatorMenuController();
        assertEquals(1, centerCoordinatorMenuController1.companyHasEnoughInfoForVaccinationStats());
       // Creating a new Vaccination Center
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
        CenterCoordinatorMenuController centerCoordinatorMenuController = new CenterCoordinatorMenuController();
        //Check if company has at least one center
        assertEquals(2, centerCoordinatorMenuController.companyHasEnoughInfoForVaccinationStats());

        //Adding a Vaccine to the company
        ArrayList<Integer> minAge = new ArrayList<>(List.of(1,20,50,100));
        ArrayList <Integer> maxAge = new ArrayList<>(List.of(19,49,99,120));
        ArrayList <Integer> numberOfDoses = new ArrayList<>(List.of(2,3,3,2));
        ArrayList <Double> dosage = new ArrayList<>(List.of(25.0,35.0,30.0,20.5));
        ArrayList <Integer> timeIntervalBetween1stAnd2nd = new ArrayList<>(List.of(15,15,15,15));
        ArrayList <Integer> timeIntervalBetween2ndAnd3rd = new ArrayList<>(List.of(0,150,180,0));
        AdministrationProcess administrationProcess = new AdministrationProcess(new ArrayList<>( List.of(minAge,maxAge)),numberOfDoses,dosage, new ArrayList<>(List.of(timeIntervalBetween1stAnd2nd,timeIntervalBetween2ndAnd3rd)));
        Vaccine vaccine = new Vaccine("Spikevax",1000,"SpikeBrand",administrationProcess, new VaccineType("COVID","description","Tech"));
        company.saveVaccineBs(vaccine);

        //Check if company has at least one fully vaccinated center
        VaccineBulletin vaccineBulletin =  new VaccineBulletin(company.getVaccinesList().get(0), LocalDateTime.now(), 2, "12345-12");
        vaccinationCentersStore.getVaccinationCenters().get(0).addFullyVaccinated(vaccineBulletin);

        assertEquals(0, centerCoordinatorMenuController.companyHasEnoughInfoForVaccinationStats());
    }


}

