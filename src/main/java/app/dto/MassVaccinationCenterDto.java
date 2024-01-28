package app.dto;


import app.domain.model.VaccineType;

/**
 * This class is just a DTO (Data Transfer Object), responsible for helping to transfer data from the UI to the Domain.
 * DTO related to the US009 - As an administrator I want to register a Vaccination Center
 * @author João Castro <1210816@isep.ipp.pt>
 */

public class MassVaccinationCenterDto {
    public MassVaccinationCenterDto() {
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
    public VaccineType vaccineType;

    public MassVaccinationCenterDto(String strID, String strName, String strPhoneNumber, String strEmail, String strFax, String strWebsite,
                                String strOpeningHour, String strClosingHour, String strSlotDuration, String strVaccinesPerSlot,
                                String strRoad, String strZipCode, String strLocal, String strCenterCoordinatorID, VaccineType vaccineType) {
        this.strID = strID;
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
        this.vaccineType =vaccineType ;
    }

    @Override
    public String toString() {
        return  "ID of the  Mass Vaccination Center: " + strID + '\n' +
                "Name of the  Mass Vaccination Center: " + strName + '\n' +
                "Phone Number of the  Mass Vaccination Center: " + strPhoneNumber+ '\n' +
                "Email of the  Mass Vaccination Center: " + strEmail + '\n' +
                "Fax of the  Mass Vaccination Center: " + strFax + '\n' +
                "Website of the  Mass Vaccination Center: " + strWebsite + '\n' +
                "Opening Hour of the  Mass Vaccination Centerr: " + strOpeningHour + '\n' +
                "Closing Hour of the  Mass Vaccination Center: " + strClosingHour + '\n' +
                "Slot Duration of the  Mass Vaccination Center: " + strSlotDuration + '\n' +
                "Maximum number of Vaccines per slot of the  Mass Vaccination Center: " + strVaccinesPerSlot + '\n' +
                "Road of the  Mass Vaccination Center: " + strRoad + '\n' +
                "Zip Code of the  Mass Vaccination Center: " + strZipCode + '\n' +
                "Local of the  Mass Vaccination Center: " + strLocal + '\n' +
                "Center Coordinator of the  Mass Vaccination Center: " + strCenterCoordinatorID + '\n' +
                "Vaccine Types administered in the Mass Vaccination Center: " + vaccineType;
    }
}
