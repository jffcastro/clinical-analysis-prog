package app.dto;

import app.domain.model.VaccineType;

import java.util.ArrayList;

/**
 * This class is just a DTO (Data Transfer Object), responsible for helping to transfer data from the UI to the Domain.
 * DTO related to the US009 - As an administrator I want to register a Vaccination Center
 * @author João Castro <1210816@isep.ipp.pt>
 */


public class HealthcareCenterDto {

    public HealthcareCenterDto() {
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
    public String strARS;
    public String strAGES;
    public ArrayList <VaccineType> vaccineTypes = new ArrayList <>();

    public HealthcareCenterDto(String strID, String strName, String strPhoneNumber, String strEmail, String strFax, String strWebsite,
                                String strOpeningHour, String strClosingHour, String strSlotDuration, String strVaccinesPerSlot,
                                String strRoad, String strZipCode, String strLocal, String strCenterCoordinatorID, String strARS, String strAGES,
                               ArrayList <VaccineType> vaccineTypes) {
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
        this.strARS=strARS;
        this.strAGES=strAGES;
        this.vaccineTypes =vaccineTypes;
    }

    @Override
    public String toString() {
        return  "ID of the Healthcare Center: " + strID + '\n' +
                "Name of the Healthcare Center: " + strName + '\n' +
                "Phone Number of the Healthcare Center: " + strPhoneNumber + '\n' +
                "Email of the Healthcare Center: " + strEmail + '\n' +
                "Fax of the Healthcare Center: " + strFax + '\n' +
                "Website of the Healthcare Center: " + strWebsite + '\n' +
                "Opening Hour of the Healthcare Center: " + strOpeningHour + '\n' +
                "Closing Hour of the Healthcare Center: " + strClosingHour + '\n' +
                "Slot Duration of the Healthcare Center: " + strSlotDuration + '\n' +
                "Maximum number of Vaccines per slot of the Healthcare Center: " + strVaccinesPerSlot + '\n' +
                "Road of the Healthcare Center: " + strRoad + '\n' +
                "Zip Code of the Healthcare Center: " + strZipCode + '\n' +
                "Local of the Healthcare Center: " + strLocal + '\n' +
                "Center Coordinator of the Healthcare Center: " + strCenterCoordinatorID + '\n' +
                "Regional Health Administration of the Healthcare Center: " + strARS + '\n' +
                "Grouping of the Healthcare Center: " + strAGES + '\n' +
                "Vaccine Types administered in the Healthcare Center: " + vaccineTypes;
    }
}
