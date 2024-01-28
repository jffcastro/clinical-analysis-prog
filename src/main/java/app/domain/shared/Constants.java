package app.domain.shared;


import app.domain.model.Company;

/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Constants {

    public static final String DAILY_REGISTERS_FILE_NAME = "DailyTotalOfVaccinatedPeople.csv";
    public static final int LOT_NUMBER_LENGTH = 8;
    public static final int ID_LENGTH = 5;
    public static final String ROLE_ADMIN = "ADMINISTRATOR";
    public static final String ROLE_RECEPTIONIST = "RECEPTIONIST";
    public static final String ROLE_NURSE = "NURSE";
    public static final String ROLE_SNS_USER = "SNS_USER";
    public static final String ROLE_CENTRE_COORDINATOR = "CENTRE_COORDINATOR";
    public static final String PARAMS_FILENAME = "config.properties";
    public static final String PARAMS_COMPANY_DESIGNATION = "Company.Designation";



    public static final String SUGGESTED_VACCINE_TYPE_ONGOING_OUTBREAK = "COVID";

    public static final String PATH_SMS_MESSAGE = "SMS.txt";

    public static final String PATH_RECOVERY_TIME_MESSAGE = "RecoveryTime/RecoveryTime.txt";
    public static final int PASSWORD_LENGTH = 7;
    public static final int PARAMS_MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;

    public static final int NUMBER_OF_PHONE_NUMBER_DIGITS = 9;

    public static final int STARTING_NUMBER_PORTUGUESE_PHONE = 9;

    public static final int FIRST_SECOND_NUMBER_PORTUGUESE_PHONE = 1;

    public static final int SECOND_SECOND_NUMBER_PORTUGUESE_PHONE = 2;

    public static final int THIRD_SECOND_NUMBER_PORTUGUESE_PHONE = 3;

    public static final int FOURTH_SECOND_NUMBER_PORTUGUESE_PHONE = 6;

    public static final int NUMBER_OF_CITIZEN_CARD_DIGITS = 12;

    public static final int FIRST_SECOND_DIGIT_CC = 10;

    public static final int NUMBER_OF_DAYS_FOR_VACCINATION_SCHEDULE = 365;

    public static final int OPENING_CLOSING_HOURS = 2;

    public static final int FIRST_USER_WAITING_ROOM = 0;

    public static final int FIRST_DOSE = -1;

    public static final int INVALID_VALUE = -1;

    public static final int NEW_EMPLOYEE = 1;

    public static final int SEPTEMBER = 10;

    public static final int LOT_NUMBER_LENGHT = 8;

    public static final int FIT_AGE_GROUP = -1;

    public static final int DOSAGE_FIRST_DOSE = 35;

    /**
     * Names for the binary files.
     */
    public static final String FILE_PATH_VACCINE_TYPES = "Serialization/vaccinetypes";

    public static final String FILE_PATH_SNS_USERS = "Serialization/snsusers";

    public static final String FILE_PATH_EMPLOYEES = "Serialization/employees";

    public static final String FILE_PATH_VACCINATION_CENTERS = "Serialization/vaccinationcenters";

    public static final String FILE_PATH_ARRIVALS = "Serialization/arrivals";

    public static final String FILE_PATH_DEPARTURES = "Serialization/departures";

    public static final String FILE_PATH_VACCINES = "Serialization/vaccine";

    public static final String FILE_PATH_APPOINTMENTS = "Serialization/appointments";


    public static final String FILE_PATH_UPDATEDLEGACY = "Serialization/updatedlegacy";

    public static final String FILE_PATH_VACCINE_BULLETIN = "Serialization/vaccinebulletin";

    public static final String FILE_PATH_VACCINE_BULLETIN_SNS_USER = "Serialization/vaccinebulletinsnsuser";

    public static final String FILE_PATH_FULLY_VACCINATED_LIST = "Serialization/fullyvaccinatedlist";

    public static final String DAILY_TOTAL_VACCINATIONS_FILE_HEADER = "Date;Vaccination Center;Total Vaccinated People";

    public static final String DAILY_REGISTERS_FILE_NAME_EXPECTED_TEST = "DailyTotalOfVaccinatedPeopleExpectedTest.csv";

    public static final String DAILY_REGISTERS_FILE_NAME_TEST_FALSE = "DailyTotalOfVaccinatedPeopleTestFalse.csv";

    public static String DAILY_REGISTERS_FILE_NAME_ACTUAL_TEST = "DailyTotalOfVaccinatedPeopleActualTest.csv";

    public static final int VACCINE_ADMINISTRATION = 0;

    public static final int VACCINATION = 1;

    public static final int END_VACCINATION = 2;

    public static final int ADD_DOSE = 1;
    public static final String[] OPTIONS = {"Yes", "No"};

}
