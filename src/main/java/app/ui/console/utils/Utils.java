package app.ui.console.utils;

import app.controller.*;
import app.domain.model.*;
import app.domain.shared.Constants;
import app.domain.shared.GenericClass;

import app.stores.SNSUsersStore;
import app.stores.VaccinationCentersStore;
import app.stores.VaccineTypesStore;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 * @author Pedro Monteiro <1211076@isep.ipp.pt>
 * @author Gustavo Jorge <1211061@isep.ipp.pt>
 * @author Guilherme Sousa <1211073@isep.ipp.pt>
 * @author João Castro <1210816@isep.ipp.pt>
 * @author João Leitão <12110632isep.ipp.pt>
 */
public class Utils {

    private static final Company company = App.getInstance().getCompany();
    private static final VaccinationCentersStore VACCINATION_CENTERS_STORE = company.getVaccinationCentersStore();
    private static final VaccineTypesStore VACCINE_TYPE_STORE = company.getVaccineTypesStore();

    private static final SNSUsersStore SNS_USERS_STORE = company.getSnsUsersStore();

    public static void bootstrapEmployees() {
        company.getEmployeesStore().readBinaryFileEmployees();
        company.getEmployeesStore().authenticateEmployees();
    }

    public static void bootstrapVaccineTypes() {
        VACCINE_TYPE_STORE.readBinaryFileVaccineTypes();
    }

    public static void bootstrapVaccinationCenters() {
        VACCINATION_CENTERS_STORE.readBinaryFileCenters();
    }

    public static void bootstrapSnsUsers() {
        company.getSnsUsersStore().readBinaryFileSnsUsers();
        company.getSnsUsersStore().authenticateSNSUser();
    }

    public static void bootstrapVaccines() {
        company.readBinaryFileVaccines();
    }

    public static void bootstrapScheduledAppointments() {
        for (VaccinationCenter center : VACCINATION_CENTERS_STORE.getVaccinationCenters()) {
            center.readBinaryFilesAppointments();
        }
    }

    public static void bootstrapFullyVaccinated() {
        company.readBinaryFileVaccineBulletins();
        for (VaccinationCenter center : VACCINATION_CENTERS_STORE.getVaccinationCenters()) {
            center.readBinaryFilesFullyVaccinated();
        }
    }

    public static void bootstrapAdministeredVaccinesTheRealOne() {
        GenericClass<VaccineBulletin> genericsClass = new GenericClass<>();
        try {
            for (SnsUser snsUser : SNS_USERS_STORE.getSnsUserList()) {
                genericsClass.binaryFileRead(Constants.FILE_PATH_VACCINE_BULLETIN_SNS_USER, snsUser.administratedVaccines());
            }
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    public static void bootstrapArrivals() {
        GenericClass<Arrival> genericsClass = new GenericClass<>();
        try {
            for (VaccinationCenter vaccinationCenter : VACCINATION_CENTERS_STORE.getVaccinationCenters()) {
                genericsClass.binaryFileRead(Constants.FILE_PATH_ARRIVALS, vaccinationCenter.getArrivalsList());
            }
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    public static void bootstrapDepartures() {
        GenericClass<Departure> genericsClass = new GenericClass<>();
        try {
            for (VaccinationCenter vaccinationCenter : VACCINATION_CENTERS_STORE.getVaccinationCenters()) {
                genericsClass.binaryFileRead(Constants.FILE_PATH_DEPARTURES, vaccinationCenter.getDeparturesList());
            }
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    /**
     * It creates and adds everything that the App needs as soon as it runs, so it is not needed to create something prior to using one functionality
     */

    public static void bootstrap() {
        bootstrapEmployees();
        bootstrapVaccineTypes();
        bootstrapVaccines();
        bootstrapVaccinationCenters();
        bootstrapSnsUsers();
        bootstrapScheduledAppointments();
        bootstrapArrivals();
        bootstrapAdministeredVaccinesTheRealOne();
        bootstrapFullyVaccinated();
        bootstrapDepartures();
    }

    /**
     * Reads a String from the console
     *
     * @param prompt Read String
     * @return String
     */
    static public String readLineFromConsole(String prompt) {
        try {
            System.out.print("\n" + prompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads a String from the console and converts it into an integer
     *
     * @param prompt Read integer
     * @return Integer
     */
    static public int readIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Reads a String from the console and converts it into a double
     *
     * @param prompt Read double
     * @return Double
     */
    static public double readDoubleFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);


                return Double.parseDouble(input);
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Read a date from the console
     *
     * @param prompt Read dates from console
     * @return Read date
     */
    static public Date readDateFromConsole(String prompt) {
        do {
            try {
                String strDate = readLineFromConsole(prompt);

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                return df.parse(strDate);
            } catch (ParseException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Method that assures that the input is an Integer.
     *
     * @param errorMessage the error message
     * @return the int read
     */
    public static int insertInt(String errorMessage) {
        int check = 0;
        Scanner sc = new Scanner(System.in);
        int input = -1;
        do {
            try {
                input = sc.nextInt();
                sc.nextLine();

                if (input >= 0) {
                    check = 1;
                } else {
                    System.out.print(errorMessage);
                }
            } catch (InputMismatchException e) {
                System.out.print(errorMessage);
                sc.nextLine();
            }
        } while (check == 0);
        return input;
    }

    /**
     * Prints the chosen list with options and let the user choose one
     *
     * @param list A chosen list
     * @return Object - the chosen option from a list
     */
    static public Object selectsObject(List list) {
        String input;
        int value;
        do {
            input = Utils.readLineFromConsole("Type your option: ");
            value = Integer.parseInt(input);
        } while (value < 0 || value > list.size());

        if (value == 0) {
            return null;
        } else {
            return list.get(value - 1);
        }
    }

    /**
     * Prints the chooen list with options and let the user choose one
     *
     * @param list A chosen list
     * @return Integer - The chosen option from the list
     */
    static public int selectsIndex(List list) {
        String input;
        int value = -1;
        do {
            try {
                input = Utils.readLineFromConsole("Type your option: ");
                value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid option");
            }
        } while (value < 0 || value > list.size());

        return value - 1;
    }

    /**
     * Asks the User for its confirmation.
     *
     * @return true if the User confirms the creation of the Vaccine.
     */
    public static boolean confirmCreation() {
        System.out.println("Do you confirm this data?");
        System.out.println("1 - Yes");
        System.out.println("0 - No");
        Scanner sc = new Scanner(System.in);
        System.out.printf("%nType your option: ");
        int check = 0;
        int option = 0;
        do {
            try {
                option = sc.nextInt();
                sc.nextLine();
                check = 1;
            } catch (InputMismatchException e) {
                System.out.println("Insert a valid option.");
                sc.nextLine();
            }
        } while (check == 0);

        return option == 1;
    }

    /**
     * Shows the chosen list (showList) and let the user choose an option
     *
     * @param list   A chosen list
     * @param header A chosen header
     * @return Object - the option chosen by the user
     */
    static public Object showAndSelectOne(List list, String header) {
        showList(list, header);
        return selectsObject(list);
    }

    /**
     * Shows the chosen list (showList) and let the user choose an option
     *
     * @param list   A chosen list
     * @param header A chosen header
     * @return Integer - the option chosen by the user
     */
    static public int showAndSelectIndex(List list, String header) {
        showList(list, header);
        return selectsIndex(list);
    }

    static public int showAndSelectFromList(List list, int value) {
        showDaysList(list, value);
        return selectsIndex(list);
    }

    /**
     * Select one option from a list and returns the index in the list.
     *
     * @param list   the list
     * @param header the header
     * @return the int
     */
    public static int selectFromList(List list, String header) {

        System.out.println(header + ":");
        System.out.println();
        int optionNumber = 1;
        for (Object o : list) {
            System.out.println(optionNumber + " - " + o);
            optionNumber++;
        }

        do {
            System.out.println();
            System.out.print("Insert your option: ");
            int option = Utils.insertInt("Insert a valid option: ");

            if ((option > 0) && (option < list.size() + 1))
                return option - 1;
            else
                System.out.println("Invalid option.");

        } while (true);

    }

    /**
     * Generates a random password with 7 alphanumeric characters, being 3 capital Letters and 2 numbers
     *
     * @return String - a random password
     */
    static public String passwordGenerator() {
        final int PASSWORD_LENGTH = 7;
        final String alphabetLetters = "abcdefghijklmnopqrstuvwyxzABCDEFGHIJKLMNOPQRSTUVWYXZ0123456789";
        StringBuilder password = new StringBuilder();
        Random generate = new Random();

        StringBuilder employeePassword = new StringBuilder();

        for (int position = 0; position < PASSWORD_LENGTH; position++) {
            if (position <= 2)
                password.append(Character.toUpperCase(alphabetLetters.charAt(generate.nextInt(25))));
            else if (position <= 4)
                password.append((generate.nextInt(9)));
            else
                password.append(alphabetLetters.charAt(generate.nextInt(alphabetLetters.length())));
        }

        for (int position = 0; position < PASSWORD_LENGTH; position++) {
            int index = (generate.nextInt(password.length()));
            char passwordAux = password.charAt(index);
            employeePassword.append(passwordAux);
            password.deleteCharAt(index);
        }
        return String.valueOf(employeePassword);
    }

    /**
     * Format a date in YYYY-MM-DD format to print a string.
     *
     * @param date the date
     * @return the string in DD/MM/YYYY
     */
    public static String formatDateToPrint(LocalDate date) {
        String[] newDate = date.toString().split("-");
        return (newDate[2] + "/" + newDate[1] + "/" + newDate[0]);
    }

    /**
     * Creates a vaccine with the information, without needing the object Administration Process.
     * Class used to help in the testing process
     *
     * @param name             the name
     * @param id               the id
     * @param brand            the brand
     * @param dosage           the dosage
     * @param minAge           the min age
     * @param maxAge           the max age
     * @param timeBetweenDoses the time between doses
     * @return the vaccine
     */
    public static Vaccine createVaccine(String name, int id, String brand, double dosage, int minAge, int maxAge, int timeBetweenDoses) {
        Company c = App.getInstance().getCompany();
        bootstrapVaccineTypes();
        AdministrationProcess aP = new AdministrationProcess(new ArrayList<>(Arrays.asList(new ArrayList<>(List.of(minAge)), new ArrayList<>(List.of(maxAge)))), new ArrayList<>(List.of(2)), new ArrayList<>(List.of(dosage)), new ArrayList<>(Arrays.asList(new ArrayList<>(List.of(timeBetweenDoses)))));

        Vaccine v = new Vaccine(name, id, brand, aP, c.getVaccineTypesStore().getVaccineTypes().get(0));


        return v;
    }

    /**
     * Validates emails
     * Checks if there is a "@" and is it ends with a valid domain
     *
     * @param strEmail The email to be validated
     * @return returns true if the email is valid
     */
    static public boolean validateEmail(String strEmail) {
        if (!strEmail.contains("@") && !strEmail.contains("."))
            return false;

        String[] emailSplitter = strEmail.split("@");
        String[] validEmailDomain = {"gmail.com", "hotmail.com", "isep.ipp.pt", "sapo.pt", "outlook.com"};

        for (String s : validEmailDomain) {
            if (Objects.equals(emailSplitter[1], s))
                return true;
        }
        return false;
    }

    /**
     * Validates phone numbers
     * Checks if the first number is a 9 and if the second is whether a 1, 2, 3 or 6
     * Checks if there are only 9 numbers
     *
     * @param strPhoneNumber The email to be validated
     * @return returns true if the email is valid
     */
    public static boolean validatePhoneNumber(String strPhoneNumber) {
        final int NUMBER_OF_PHONE_NUMBER_DIGITS = 9;
        final int STARTING_NUMBER_PORTUGUESE_PHONE = 9;
        final int FIRST_SECOND_NUMBER_PORTUGUESE_PHONE = 1;
        final int SECOND_SECOND_NUMBER_PORTUGUESE_PHONE = 2;
        final int THIRD_SECOND_NUMBER_PORTUGUESE_PHONE = 3;
        final int FOURTH_SECOND_NUMBER_PORTUGUESE_PHONE = 6;

        if (strPhoneNumber.length() == NUMBER_OF_PHONE_NUMBER_DIGITS && Integer.parseInt(strPhoneNumber) % 1 == 0) {
            int ch1 = Integer.parseInt(String.valueOf(strPhoneNumber.charAt(0)));
            if (ch1 != STARTING_NUMBER_PORTUGUESE_PHONE)
                return false;

            int ch2 = Integer.parseInt(String.valueOf(strPhoneNumber.charAt(1)));
            if (ch2 != FIRST_SECOND_NUMBER_PORTUGUESE_PHONE && ch2 != SECOND_SECOND_NUMBER_PORTUGUESE_PHONE && ch2 != THIRD_SECOND_NUMBER_PORTUGUESE_PHONE && ch2 != FOURTH_SECOND_NUMBER_PORTUGUESE_PHONE) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean validatePhoneNumberSimple(String phoneNumber) {
        return phoneNumber.matches("^[0-9]{9}$");
    }

    /**
     * Validates citizen card number
     *
     * @param strCitizenCardNumber The citizen card number to be validated
     * @return true if the email is valid
     */
    public static boolean validateCitizenCardNumber(String strCitizenCardNumber) {
        final int NUMBER_OF_CITIZEN_CARD_DIGITS = 12;
        final int FIRST_SECOND_DIGIT_CC = 10;
        String noBlankSpotsCitizenCardNumber = strCitizenCardNumber.replaceAll("\\s", "");
        int sum = 0;
        if (noBlankSpotsCitizenCardNumber.length() != NUMBER_OF_CITIZEN_CARD_DIGITS)
            return false;

        boolean secondDigit = true;

        for (int digit = 0; digit < noBlankSpotsCitizenCardNumber.length(); digit++) {
            String toUpperCase = String.valueOf(noBlankSpotsCitizenCardNumber.charAt(digit)).toUpperCase();
            int value = getValueFromCitizenCardNumberDigit(toUpperCase);

            if (secondDigit) {
                value *= 2;

                if (value >= 10)
                    value -= 9;
            }
            sum += value;
            secondDigit = !secondDigit;
        }
        return (sum % FIRST_SECOND_DIGIT_CC) == 0;
    }

    public static boolean validateCitizenCardNumberSimple(String citizenCardNumber) {
        return citizenCardNumber.matches("^[0-9]{8}");
    }

    /**
     * Validates an Address
     *
     * @param strAddress The address to be validated
     * @return true if the Address is valid
     */
    static public boolean validateAddress(String strAddress) {
        String[] splitAddress = strAddress.split("/");
        if (splitAddress.length != 3)
            return false;

        String zipCode = splitAddress[1].trim();
        if (zipCode.length() != 8 || zipCode.charAt(4) != '-')
            return false;

        return true;
    }

    /**
     * Validates a Sex
     *
     * @param strSex The sex to be validated
     * @return true if the sex is valid
     */
    static public boolean validateSex(String strSex) {
        return strSex.equals("Male") || strSex.equals("Female") || strSex.equals("NA") || strSex.isEmpty()
                || strSex.equals("Feminino") || strSex.equals("Masculino");
    }

    /**
     * Validates a birthdate
     *
     * @param strBirthDate The birthdate to be validated
     * @return true if the birthdate is valid
     */
    static public boolean validateBirthDate(String strBirthDate) {
        String dateFormat = "dd/MM/yyyy";
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(strBirthDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Validates a Sns user number .
     *
     * @param snsUserNumber the Sns user number
     * @return true if the Sns number is valid
     */
    public static boolean validateSnsUserNumber(int snsUserNumber) {
        String strSnsUserNumber = String.valueOf(snsUserNumber);
        return strSnsUserNumber.trim().matches("^[0-9]*$") && strSnsUserNumber.length() == Constants.NUMBER_OF_PHONE_NUMBER_DIGITS;
    }

    /**
     * Gets the Value from the citizen card number
     *
     * @param letter The letter to get the respective value
     * @return an int
     */
    static public int getValueFromCitizenCardNumberDigit(String letter) {
        switch (letter) {
            case "0":
                return 0;
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "A":
                return 10;
            case "B":
                return 11;
            case "C":
                return 12;
            case "D":
                return 13;
            case "E":
                return 14;
            case "F":
                return 15;
            case "G":
                return 16;
            case "H":
                return 17;
            case "I":
                return 18;
            case "J":
                return 19;
            case "K":
                return 20;
            case "L":
                return 21;
            case "M":
                return 22;
            case "N":
                return 23;
            case "O":
                return 24;
            case "P":
                return 25;
            case "Q":
                return 26;
            case "R":
                return 27;
            case "S":
                return 28;
            case "T":
                return 29;
            case "U":
                return 30;
            case "V":
                return 31;
            case "W":
                return 32;
            case "X":
                return 33;
            case "Y":
                return 34;
            case "Z":
                return 35;
        }
        throw new IllegalArgumentException("Invalid Value in the Document.");
    }

    /**
     * Prints the chosen list with a header and options
     *
     * @param list   - A chosen list
     * @param header - A chosen header
     */
    static public void showList(List list, String header) {
        System.out.println(header);
        System.out.println();
        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + o.toString());
        }
        System.out.println("");
        System.out.println("0 - Cancel");
    }

    static public void showDaysList(List list, int value) {
        int index = 0;
        for (Object o : list) {
            index++;
            System.out.println(index + ". " + o.toString());
        }
        System.out.println();
        if (value == 0)
            System.out.println("0 - Next Month");
        else if (value == 1)
            System.out.println("0 - Previous Month");
    }

    /**
     * Presents a list of vaccination centers and allows the selection of a vaccination center.
     *
     * @return the chosen vaccination center
     */
    public static int selectVaccinationCenterIndex() {
        Company company = App.getInstance().getCompany();
        return Utils.selectFromList(VACCINATION_CENTERS_STORE.getVaccinationCenters(), "\nSelect a Vaccination Center");
    }


    public static void fillListsUsingBinaryFileInformation(List listToBeFilled, List listFromFile) {
        for (Object o : listFromFile) {
            listToBeFilled.add(o);
        }
    }

    public static String getLoggedCoordinatorId() {
        Email email = company.getAuthFacade().getCurrentUserSession().getUserId();

        return company.getEmployeesStore().getCoordinatorId(email);
    }

}