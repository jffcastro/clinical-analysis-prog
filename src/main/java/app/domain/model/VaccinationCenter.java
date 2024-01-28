package app.domain.model;

import app.controller.App;
import app.domain.shared.Constants;
import app.domain.shared.GenericClass;
import app.ui.console.utils.Utils;
import app.dto.ScheduledVaccineDto;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Creates a Vaccination Center
 *
 * @author João Castro <1210816@isep.ipp.pt>
 */
public class VaccinationCenter implements Serializable {

    private final GenericClass<Arrival> genericsArrivals = new GenericClass<>();
    private final GenericClass<Departure> genericsDeparture = new GenericClass<>();
    private final GenericClass<ScheduledVaccine> genericsSchedules = new GenericClass<>();
    private final String strID;
    private final String strName;
    private final String strPhoneNumber;
    private final String strEmail;
    private final String strFax;
    private final String strWebsite;
    private final String strOpeningHour;
    private final String strClosingHour;
    private final String strSlotDuration;
    private final String strVaccinesPerSlot;
    private final String strRoad;
    private final String strZipCode;
    private final String strLocal;
    private final String strCenterCoordinatorID;
    private final GenericClass<VaccineBulletin> genericsVaccineBulletin = App.getInstance().getCompany().genericsVaccineBulletin;
    private List<ScheduledVaccine> scheduledVaccineList = new ArrayList<>();
    private List<Arrival> arrivalsList = new ArrayList<>();
    private final List<Departure> departuresList = new ArrayList<>();
    private final List<VaccineBulletin> vaccinesAdministeredList = new ArrayList<>();
    private final List<VaccineBulletin> listFullyVaccinated = new ArrayList<>();
    private static final long serialVersionUID = 0;
    private static final String[] strTopLevelDomain = {".com", ".pt", ".co.uk"};
    private static final String strWorldWideWeb = "www.";
    private final PerformanceAnalyzer analyzer = new PerformanceAnalyzer(this);

    /**
     * The Appointments generic class.
     */
    GenericClass<ScheduledVaccine> appointmentsGenericClass = new GenericClass<>();


    /**
     * Creates a vaccination center with the following attributes, also verifies inside the constructors the those attributes are valid.
     *
     * @param strID                  The vaccination center's ID.
     * @param strName                The vaccination center's name.
     * @param strPhoneNumber         The vaccination center's Phone Number.
     * @param strEmail               The vaccination center's Email.
     * @param strFax                 The vaccination center's Fax Number.
     * @param strWebsite             The vaccination center's Website.
     * @param strOpeningHour         The vaccination center's Opening Hour.
     * @param strClosingHour         The vaccination center's Closing Hour.
     * @param strSlotDuration        The vaccination center's Slot Duration.
     * @param strVaccinesPerSlot     The vaccination center's Maximum Number of Vaccines per Slot.
     * @param strRoad                The vaccination center's Road.
     * @param strZipCode             The vaccination center's Zip Code.
     * @param strLocal               The vaccination center's Local.
     * @param strCenterCoordinatorID The vaccination center's Center Coordinator's ID.
     */
    public VaccinationCenter(String strID, String strName, String strPhoneNumber, String strEmail, String strFax, String strWebsite,
                             String strOpeningHour, String strClosingHour, String strSlotDuration, String strVaccinesPerSlot,
                             String strRoad, String strZipCode, String strLocal, String strCenterCoordinatorID) {

        if (((strID == null) || (strName == null) || (strPhoneNumber == null) || (strEmail == null) || (strFax == null) || (strWebsite == null) || (strOpeningHour == null) ||
                (strClosingHour == null) || (strSlotDuration == null) || (strVaccinesPerSlot == null) || (strRoad == null) || (strZipCode == null) ||
                (strLocal == null) || (strCenterCoordinatorID == null) || (strID.isEmpty()) || (strName.isEmpty()) || (strPhoneNumber.isEmpty() || (strEmail.isEmpty()) ||
                (strFax.isEmpty()) || (strWebsite.isEmpty()) || (strOpeningHour.isEmpty()) || (strClosingHour.isEmpty()) || (strSlotDuration.isEmpty()) ||
                (strVaccinesPerSlot.isEmpty()) || (strRoad.isEmpty() || (strZipCode.isEmpty() || (strLocal.isEmpty() || (strCenterCoordinatorID.isEmpty())))))))
            throw new IllegalArgumentException("Arguments can't be null or empty.");

        if (!validatePhoneNumberAndFax(strPhoneNumber)) {
            throw new IllegalArgumentException("Only supports the Portuguese format, .e.i, 933398881.");
        }

        if (!validatePhoneNumberAndFax(strFax)) {
            throw new IllegalArgumentException("Only supports the Portuguese format, .e.i, 933398881.");
        }

        if (!Utils.validateEmail(strEmail)) {
            throw new IllegalArgumentException("Needs an @, a . and a valid domain,");
        }

        if (!validateWebsite(strWebsite, strTopLevelDomain, strWorldWideWeb))
            throw new IllegalArgumentException("Needs a valid prefix and domain.");

        if (!validateVaccinationCenterHours(strOpeningHour, strClosingHour))
            throw new IllegalArgumentException("Between 0 and 24, Opening Hour < Closing Hour.");

        if (!validateZipCode(strZipCode))
            throw new IllegalArgumentException("Zip Code format is invalid.");

        if (!validateSlotDuration(strSlotDuration)) {
            throw new IllegalArgumentException("No more than three numerical chars.");
        }

        if (!validateVaccinesPerSlot(strVaccinesPerSlot)) {
            throw new IllegalArgumentException("No more than three numerical chars.");
        }

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
        this.strRoad = strRoad;
        this.strZipCode = strZipCode;
        this.strLocal = strLocal;
        this.strCenterCoordinatorID = strCenterCoordinatorID;
    }


    /**
     * Gets str center coordinator id.
     *
     * @return the str center coordinator id
     */
    public String getStrCenterCoordinatorID() {
        return strCenterCoordinatorID;
    }

    /**
     * Gets str name.
     *
     * @return the str name
     */
    public String getStrName() {
        return strName;
    }

    /**
     * Gets the List with all the Scheduled Vaccines in the Vaccination Center
     *
     * @return A List
     */
    public List<ScheduledVaccine> getScheduledVaccineList() {
        return scheduledVaccineList;
    }

    /**
     * Sets scheduled vaccine list.
     *
     * @param scheduledVaccineList the scheduled vaccine list
     */
    public void setScheduledVaccineList(List<ScheduledVaccine> scheduledVaccineList) {
        this.scheduledVaccineList = scheduledVaccineList;
    }

    /**
     * Gets the List of the arrivals in the Vaccination Center
     *
     * @return List of arrivals
     */
    public List<Arrival> getArrivalsList() {
        return arrivalsList;
    }


    /**
     * Gets the Departure Store of the Vaccination Center
     *
     * @return The Departure Store
     */
    public List<Departure> getDeparturesList() {
        return departuresList;
    }

    /**
     * Gets vaccines administered.
     *
     * @return the vaccines administered
     */
    public List<VaccineBulletin> getVaccinesAdministeredList() {
        return vaccinesAdministeredList;
    }


    /**
     * Gets list fully vaccinated.
     *
     * @return the list fully vaccinated
     */
    public List<VaccineBulletin> getFullyVaccinatedList() {
        return listFullyVaccinated;
    }

    /**
     * Gets opening hour.
     *
     * @return the String with the opening hour
     */
    public String getStrOpeningHour() {
        return strOpeningHour;
    }

    /**
     * Gets closing hour.
     *
     * @return the string with the closing hour
     */
    public String getStrClosingHour() {
        return strClosingHour;
    }

    /**
     * Gets slot duration time.
     *
     * @return the String with the slot duration
     */
    public String getStrSlotDuration() {
        return strSlotDuration;
    }

    /**
     * Gets the number of vaccines per slot.
     *
     * @return the String with the number vaccines per slot
     */
    public String getStrVaccinesPerSlot() {
        return strVaccinesPerSlot;
    }

    @Override
    public String toString() {
        return strName;
    }

    /**
     * Validates the opening/closing hour of the centre, the opening hour has to be smaller than the closing hour, the interval of the hours allowed is between 0-24.
     *
     * @param strOpeningHour is the opening hour of the centre.
     * @param strClosingHour is the closing hour of the centre.
     * @return a true or a false
     */
    public boolean validateVaccinationCenterHours(String strOpeningHour, String strClosingHour) {
        if (Integer.parseInt(strOpeningHour) >= 0 && Integer.parseInt(strOpeningHour) < 24 && Integer.parseInt(strClosingHour) > 0 && Integer.parseInt(strClosingHour) <= 24) {
            return Integer.parseInt(strOpeningHour) < Integer.parseInt(strClosingHour);
        } else
            return false;
    }

    /**
     * Validates the website, the website needs to have the prefix "www." and one of the available domains as suffix.
     *
     * @param strWebsite        is the website of the centre
     * @param strTopLevelDomain is one of the domains allowed
     * @param strWorldWideWeb   is the prefix that is needed to create the website
     * @return a true or a false
     */
    public boolean validateWebsite(String strWebsite, String[] strTopLevelDomain, String strWorldWideWeb) {

        for (String s : strTopLevelDomain) {
            if (strWebsite.startsWith(strWorldWideWeb) && strWebsite.endsWith(s))
                return true;
        }
        return false;
    }

    /**
     * Validates the Phone and Fax Number of the centre, basically checks if it's in the Portuguese format
     *
     * @param strPhoneNumberOrFaxNumber is the Phone or the Fax Number of the centre since both follow the same rules.
     * @return a true or a false
     */
    public boolean validatePhoneNumberAndFax(String strPhoneNumberOrFaxNumber) {

        if (strPhoneNumberOrFaxNumber.length() == Constants.NUMBER_OF_PHONE_NUMBER_DIGITS && Integer.parseInt(strPhoneNumberOrFaxNumber) % 1 == 0) {
            int ch1 = Integer.parseInt(String.valueOf(strPhoneNumberOrFaxNumber.charAt(0)));
            if (ch1 != Constants.STARTING_NUMBER_PORTUGUESE_PHONE)
                return false;

            int ch2 = Integer.parseInt(String.valueOf(strPhoneNumberOrFaxNumber.charAt(1)));
            return ch2 == Constants.FIRST_SECOND_NUMBER_PORTUGUESE_PHONE || ch2 == Constants.SECOND_SECOND_NUMBER_PORTUGUESE_PHONE ||
                    ch2 == Constants.THIRD_SECOND_NUMBER_PORTUGUESE_PHONE || ch2 == Constants.FOURTH_SECOND_NUMBER_PORTUGUESE_PHONE;
        }
        return false;
    }

    /**
     * Validates the Zip Code, checks if it's in the Portuguese format.
     *
     * @param strZipCode is the Zip Code of the centre.
     * @return a true or a false
     */
    public boolean validateZipCode(String strZipCode) {
        return strZipCode.matches("^[0-9]{4}(?:-[0-9]{3})?$");
    }

    /**
     * Validates the Slot Duration, checks if its only numbers and if it has no more than 3 chars.
     *
     * @param strSlotDuration is slot duration of the centre.
     * @return a true or a false
     */
    public boolean validateSlotDuration(String strSlotDuration) {
        return strSlotDuration.matches("[0-9]{1,3}");
    }

    /**
     * Validates the maximum number of vaccines per slot, checks if its only numbers and if it has no more than 3 chars.
     *
     * @param strVaccinesPerSlot is the maximum number of vaccines per slot allowed by the centre.
     * @return a true or a false
     */
    public boolean validateVaccinesPerSlot(String strVaccinesPerSlot) {
        return strVaccinesPerSlot.matches("[0-9]{1,3}");
    }

    /**
     * It's a method that validates the Vaccination Center outside the constructor, so it can be called in order to do the tests.
     *
     * @return a true or a false
     */
    public boolean validateVaccinationCenters() {
        return strName != null && strID != null && strPhoneNumber != null && strEmail != null && strFax != null &&
                strWebsite != null && strOpeningHour != null && strClosingHour != null && strSlotDuration != null && strVaccinesPerSlot != null &&
                strRoad != null && strZipCode != null && strLocal != null && strCenterCoordinatorID != null &&
                !strName.isEmpty() && !strID.isEmpty() && !strPhoneNumber.isEmpty() && !strEmail.isEmpty() && !strFax.isEmpty() &&
                !strWebsite.isEmpty() && !strOpeningHour.isEmpty() && !strClosingHour.isEmpty() && !strSlotDuration.isEmpty() && !strVaccinesPerSlot.isEmpty() &&
                !strRoad.isEmpty() && !strZipCode.isEmpty() && !strLocal.isEmpty() && !strCenterCoordinatorID.isEmpty() && validatePhoneNumberAndFax(strPhoneNumber)
                && validatePhoneNumberAndFax(strFax) && Utils.validateEmail(strEmail) && validateWebsite(strWebsite, strTopLevelDomain, strWorldWideWeb) &&
                validateVaccinationCenterHours(strOpeningHour, strClosingHour) && validateZipCode(strZipCode) && validateSlotDuration(strSlotDuration) &&
                validateVaccinesPerSlot(strVaccinesPerSlot);
    }

    /**
     * Method to return a String with all the info corresponding to a Vaccination Center
     *
     * @return a String
     */
    public String fullInfo() {
        return "ID of the Vaccination Center: " + strID + '\n' +
                "Name of the Vaccination Center: " + strName + '\n' +
                "Phone Number of the Vaccination Center: " + strPhoneNumber + '\n' +
                "Email of the Vaccination Center: " + strEmail + '\n' +
                "Fax of the Vaccination Center: " + strFax + '\n' +
                "Website of the Vaccination Center: " + strWebsite + '\n' +
                "Opening Hour of the Vaccination Center: " + strOpeningHour + '\n' +
                "Closing Hour of the Vaccination Center: " + strClosingHour + '\n' +
                "Slot Duration of the Vaccination Center: " + strSlotDuration + '\n' +
                "Maximum number of Vaccines per slot of the Vaccination Center: " + strVaccinesPerSlot + '\n' +
                "Road of the Vaccination Center: " + strRoad + '\n' +
                "Zip Code of the Vaccination Center: " + strZipCode + '\n' +
                "Local of the Vaccination Center: " + strLocal + '\n' +
                "Center Coordinator of the Vaccination Center: " + strCenterCoordinatorID + '\n';
    }

    /**
     * Adds an appointment of a Vaccine to the List with all the Scheduled Vaccines
     *
     * @param newAppointment A Scheduled Vaccine object to be added to the List containing all the appointments
     * @throws NotSerializableException the not serializable exception
     */
    public void addAppointment(ScheduledVaccine newAppointment) throws NotSerializableException {
        this.scheduledVaccineList.add(newAppointment);
        appointmentsGenericClass.binaryFileWrite(Constants.FILE_PATH_APPOINTMENTS, scheduledVaccineList);
    }

    /**
     * Checks center availability.
     *
     * @param scheduledVaccineDto the scheduled vaccine dto
     * @return true if the center has availability
     */
    public boolean centerHasAvailability(ScheduledVaccineDto scheduledVaccineDto) {
        if (!dayHasAvailability(scheduledVaccineDto.date.toLocalDate()))
            return false;
        return slotHasAvailability(scheduledVaccineDto.date.toLocalDate(), scheduledVaccineDto.date.toLocalTime());
    }

    /**
     * Checks if a certain slot has availability for a certain day.
     *
     * @param date the date
     * @param slot the slot
     * @return true if slot has capacity to another appointment
     */
    public boolean slotHasAvailability(LocalDate date, LocalTime slot) {
        int counterAppointments = 0;
        for (ScheduledVaccine appointment : scheduledVaccineList) {
            if (((appointment.getDate().toLocalDate()).equals(date)) && (appointment.getDate().toLocalTime().equals(slot))) {
                counterAppointments++;
            }
        }
        return counterAppointments != Integer.parseInt(strVaccinesPerSlot);
    }

    /**
     * Day has availability.
     *
     * @param date the date
     * @return the boolean
     */
    public boolean dayHasAvailability(LocalDate date) {
        int vaccinesPerDay = getSlotsPerDay() * Integer.parseInt(strVaccinesPerSlot);
        int counterAppointments = 0;

        for (ScheduledVaccine appointment : scheduledVaccineList) {
            if ((appointment.getDate().toLocalDate()).equals(date))
                counterAppointments++;

            if (counterAppointments == vaccinesPerDay)
                return false;
        }

        return true;
    }

    /**
     * Gets slots per day.
     *
     * @return the slots per day
     */
    public int getSlotsPerDay() {
        int closingHour = Integer.parseInt(strClosingHour);
        int openingHour = Integer.parseInt(strOpeningHour);
        int slotDuration = Integer.parseInt(strSlotDuration);

        return (closingHour - openingHour) * 60 / slotDuration;
    }

    /**
     * Register the arrival of an SNS user
     *
     * @param arrival An object regarding the  arrival of a user
     */
    public void registerArrival(Arrival arrival) {
        getArrivalsList().add(arrival);
    }


    /**
     * Removes an appointment from the appointments list
     *
     * @param appointment The user's appointment
     */
    public void removeAppointment(ScheduledVaccine appointment) {
        getScheduledVaccineList().remove(appointment);
    }

    /**
     * Checks if a user has already been registered
     *
     * @param snsNumber The number that identifies an SNS user
     * @return boolean - true if the user is already registered
     */
    public boolean checkIfAlreadyRegistered(int snsNumber) {
        for (Arrival arrival : arrivalsList)
            if (arrival.getSnsNumber() == snsNumber)
                return false;

        return true;
    }

    /**
     * Gets str id.
     *
     * @return the str id
     */
    public String getStrID() {
        return strID;
    }

    /**
     * Gets str phone number.
     *
     * @return the str phone number
     */
    public String getStrPhoneNumber() {
        return strPhoneNumber;
    }

    /**
     * Gets str email.
     *
     * @return the str email
     */
    public String getStrEmail() {
        return strEmail;
    }

    /**
     * Gets str fax.
     *
     * @return the str fax
     */
    public String getStrFax() {
        return strFax;
    }

    /**
     * Gets str website.
     *
     * @return the str website
     */
    public String getStrWebsite() {
        return strWebsite;
    }

    /**
     * Gets str road.
     *
     * @return the str road
     */
    public String getStrRoad() {
        return strRoad;
    }

    /**
     * Gets str zip code.
     *
     * @return the str zip code
     */
    public String getStrZipCode() {
        return strZipCode;
    }

    /**
     * Gets str local.
     *
     * @return the str local
     */
    public String getStrLocal() {
        return strLocal;
    }

    /**
     * Sets arrivals list.
     *
     * @param arrivalsList the arrivals list
     */
    public void setArrivalsList(List<Arrival> arrivalsList) {
        this.arrivalsList = arrivalsList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VaccinationCenter)) return false;
        VaccinationCenter that = (VaccinationCenter) o;
        return strID.equals(that.strID) && strEmail.equals(that.strEmail) && strWebsite.equals(that.strWebsite) && strCenterCoordinatorID.equals(that.strCenterCoordinatorID);
    }


    /**
     * Read binary files appointments.
     */
    public void readBinaryFilesAppointments() {
        try {
            genericsSchedules.binaryFileRead(Constants.FILE_PATH_APPOINTMENTS, scheduledVaccineList);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add administered vaccine.
     *
     * @param newAdministration the new administration
     */
    public void addAdministeredVaccine(VaccineBulletin newAdministration) {
        vaccinesAdministeredList.add(newAdministration);
        genericsVaccineBulletin.binaryFileWrite(Constants.FILE_PATH_VACCINE_BULLETIN, vaccinesAdministeredList);
    }


    /**
     * Adds a Vaccine Bulletin to the list that contains all the fully vaccinated users correspondent bulletin.
     *
     * @param newAdministration the new administration
     */
    public void addFullyVaccinated(VaccineBulletin newAdministration) {
        listFullyVaccinated.add(newAdministration);
        genericsVaccineBulletin.binaryFileWrite(Constants.FILE_PATH_FULLY_VACCINATED_LIST, listFullyVaccinated);
    }

    /**
     * Read binary files fully vaccinated.
     */
    public void readBinaryFilesFullyVaccinated() {
        try {
            genericsVaccineBulletin.binaryFileRead(Constants.FILE_PATH_FULLY_VACCINATED_LIST, listFullyVaccinated);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a list with the total of fully vaccinated people per day (each day has a total) .
     *
     * @return the vaccination stats
     */
    private List<String> getVaccinationStatsList() {
        List<String> vaccinationStats = new ArrayList<>();
        LocalDate dayOfLastRegister = getFirstDateAvailable(listFullyVaccinated);
        int total = 0;
        StringBuilder stringBuilder;
        for (VaccineBulletin vaccineBulletin : listFullyVaccinated) {

            if (vaccineBulletin.getDateTimeOfLastDose().toLocalDate().isAfter(dayOfLastRegister)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(dayOfLastRegister).append(";").append(total);
                String statsOfOneDay = stringBuilder.toString();
                vaccinationStats.add(statsOfOneDay);
                dayOfLastRegister = vaccineBulletin.getDateTimeOfLastDose().toLocalDate();
            }
            total++;
            if (vaccineBulletin.equals(listFullyVaccinated.get(listFullyVaccinated.size() - 1))) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(dayOfLastRegister).append(";").append(total);
                String statsOfOneDay = stringBuilder.toString();
                vaccinationStats.add(statsOfOneDay);
            }
        }

        return vaccinationStats;
    }

    /**
     * Get vaccination stats list between dates list.
     *
     * @param firstDate the first date
     * @param lastDate  the last date
     * @return the list
     */
    public List<String> getVaccinationStatsListBetweenDates(LocalDate firstDate, LocalDate lastDate) {
        List<String> dailyStats = getVaccinationStatsList();
        List<String> statsBetweenDates = new ArrayList<>();

        for (String dailyStat : dailyStats) {
            String[] dailyStatArray = dailyStat.split(";");
            LocalDate date = LocalDate.parse(dailyStatArray[0]);
            if ((date.isEqual(firstDate) || date.isAfter(firstDate)) && (date.isBefore(lastDate) || date.isEqual(lastDate)))
                statsBetweenDates.add(dailyStat);
        }
        return statsBetweenDates;

    }

    private LocalDate getFirstDateAvailable(List<VaccineBulletin> listFullyVaccinated) {
        return listFullyVaccinated.get(0).getDateTimeOfLastDose().toLocalDate();
    }


    /**
     * Add arrival.
     *
     * @param arrival the arrival
     * @throws NotSerializableException the not serializable exception
     */
    public void addArrival(Arrival arrival, boolean serialize) throws NotSerializableException {
        if (!arrivalsList.contains(arrival)) {
            arrivalsList.add(arrival);
            if (serialize) genericsArrivals.binaryFileWrite(Constants.FILE_PATH_ARRIVALS, arrivalsList);
        }

    }

    /**
     * Add departure.
     *
     * @param departure the departure
     * @throws NotSerializableException the not serializable exception
     */
    public void addDeparture(Departure departure, boolean serialize) throws NotSerializableException {
        if (!departuresList.contains(departure)) {
            departuresList.add(departure);
            if (serialize) genericsDeparture.binaryFileWrite(Constants.FILE_PATH_DEPARTURES, departuresList);
        }

    }

    /**
     * Gets analyzer.
     *
     * @return the analyzer
     */
    public PerformanceAnalyzer getAnalyzer() {
        return analyzer;
    }

    /**
     * Calculates for how many minutes the center is open.
     *
     * @return the minutes the center is open per day
     */
    public int getMinutesOpenCenterPerDay() {
        int openingHour = Integer.parseInt(strOpeningHour);
        int closingHour = Integer.parseInt(strClosingHour);
        return (closingHour - openingHour) * 60;
    }
}
