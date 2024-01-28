package app.miscellaneous;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

public class SortedListInfo {

    private SimpleStringProperty name;
    private SimpleStringProperty snsNumber;
    private SimpleStringProperty vaccineName;
    private SimpleStringProperty dose;
    private SimpleStringProperty lotNumber;
    private SimpleStringProperty scheduleDate;
    private SimpleStringProperty arrivalTime;
    private SimpleStringProperty nurseAdminstrationTime;
    private SimpleStringProperty departureTime;

    public SortedListInfo(String name, String snsNumber, String vaccineName, String dose, String lotNumber, String scheduleDate, String arrivalTime, String nurseAdminstrationTime, String departureTime) {
        this.name = new SimpleStringProperty(name);
        this.snsNumber = new SimpleStringProperty(snsNumber);
        this.vaccineName = new SimpleStringProperty(vaccineName);
        this.dose = new SimpleStringProperty(dose);
        this.lotNumber = new SimpleStringProperty(lotNumber);
        this.scheduleDate = new SimpleStringProperty(scheduleDate);
        this.arrivalTime =new SimpleStringProperty(arrivalTime);
        this.nurseAdminstrationTime = new SimpleStringProperty(nurseAdminstrationTime);
        this.departureTime = new SimpleStringProperty(departureTime);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getSnsNumber() {
        return snsNumber.get();
    }

    public SimpleStringProperty snsNumberProperty() {
        return snsNumber;
    }

    public String getVaccineName() {
        return vaccineName.get();
    }

    public SimpleStringProperty vaccineNameProperty() {
        return vaccineName;
    }

    public String getDose() {
        return dose.get();
    }

    public SimpleStringProperty doseProperty() {
        return dose;
    }

    public String getLotNumber() {
        return lotNumber.get();
    }

    public SimpleStringProperty lotNumberProperty() {
        return lotNumber;
    }

    public String getScheduleDate() {
        return scheduleDate.get();
    }

    public SimpleStringProperty scheduleDateProperty() {
        return scheduleDate;
    }

    public String getArrivalTime() {
        return arrivalTime.get();
    }

    public SimpleStringProperty arrivalTimeProperty() {
        return arrivalTime;
    }

    public String getNurseAdminstrationTime() {
        return nurseAdminstrationTime.get();
    }

    public SimpleStringProperty nurseAdminstrationTimeProperty() {
        return nurseAdminstrationTime;
    }

    public String getDepartureTime() {
        return departureTime.get();
    }

    public SimpleStringProperty departureTimeProperty() {
        return departureTime;
    }
}
