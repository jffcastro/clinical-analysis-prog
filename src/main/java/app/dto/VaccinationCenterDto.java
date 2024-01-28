package app.dto;

import app.domain.model.Arrival;
import app.domain.model.ScheduledVaccine;
import app.domain.model.VaccineType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is just a DTO (Data Transfer Object), responsible for helping to transfer data from the UI to the Domain.
 * DTO related to the US009 - As an administrator I want to register a Vaccination Center
 * @author João Castro <1210816@isep.ipp.pt>
 */

public class VaccinationCenterDto {

    public VaccinationCenterDto() {
    }
    public String strID;
    public String strName;
    public String strPhoneNumber;
    public String strEmail;
    public String strFax;
    public String strWebsite;
    public String strOpeningHour;
    public String strClosingHour;
    public String strSlotDuration;
    public String strVaccinesPerSlot;
    public String strRoad;
    public String strZipCode;
    public String strLocal;
    public String strCenterCoordinatorID;
    public List<ScheduledVaccine> scheduledVaccineList = new ArrayList<>();
    public int slotsPerDay;
    public List<Arrival> arrivalList =  new ArrayList<>();
    
    
    
    public VaccinationCenterDto(String strID, String strName, String strPhoneNumber, String strEmail, String strFax, String strWebsite,
                                String strOpeningHour, String strClosingHour, String strSlotDuration, String strVaccinesPerSlot,
                                String strRoad, String strZipCode, String strLocal, String strCenterCoordinatorID) {
        this.strID=strID;
        this.strName = strName;
        this.strPhoneNumber = strPhoneNumber;
        this.strEmail = strEmail;
        this.strFax = strFax;
        this.strWebsite = strWebsite;
        this.strOpeningHour = strOpeningHour;
        this.strClosingHour = strClosingHour;
        this.strSlotDuration = strSlotDuration;
        this.strVaccinesPerSlot = strVaccinesPerSlot;
        this.strRoad=strRoad;
        this.strZipCode=strZipCode;
        this.strLocal=strLocal;
        this.strCenterCoordinatorID=strCenterCoordinatorID;
    }

    @Override
    public String toString() {
        return "VaccinationCenterDto{" +
                "strID=" + strID +
                ", strName='" + strName + '\'' +
                ", strPhoneNumber='" + strPhoneNumber + '\'' +
                ", strEmail='" + strEmail + '\'' +
                ", strFax='" + strFax + '\'' +
                ", strWebsite='" + strWebsite + '\'' +
                ", strOpeningHour='" + strOpeningHour + '\'' +
                ", strClosingHour='" + strClosingHour + '\'' +
                ", strSlotDuration='" + strSlotDuration + '\'' +
                ", strVaccinesPerSlot='" + strVaccinesPerSlot + '\'' +
                ", strRoad='" + strRoad + '\'' +
                ", strZipCode='" + strZipCode + '\'' +
                ", strLocal='" + strLocal + '\'' +
                ", strCenterCoordinatorID='" + strCenterCoordinatorID + '\'' +
                '}';
    }
}
