package app.ui.console;

import app.controller.LoadCSVController;
import app.domain.model.SnsUser;
import app.ui.console.utils.Utils;
import app.dto.SnsUserDto;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Asks the User for a csv file, verifies if the path exists and if the file is valid, if so then it saves the Users into the system.
 *
 * @author João Castro <1210816@isep.ipp.pt>
 */
public class LoadCSVUI implements Runnable {

    LoadCSVController controller = new LoadCSVController();

    /**
     * Gives the Admin the option to get a list of all Users or load a CSV file with SNS User info
     *
     */
    public void run() {
        System.out.println();
        System.out.println("--------------CHOOSE THE OPTION:--------------");
        System.out.println();
        System.out.println("0 - Load a CSV File with SNS User Info");
        System.out.println();
        System.out.println("1 - Get a list of all SNS Users");
        System.out.println();
        System.out.println("2 - Go Back");
        System.out.println();
        System.out.println("Choose the option:");
        System.out.println();
        Scanner choice = new Scanner(System.in);
        try {
            int option = choice.nextInt();
            if (option == 0) {
                runLoadCSV();
            } else if (option == 1) {
                getListOfSNSUsers();
            } else if (option == 2) {
                return;
            } else {
                System.out.println("Option is Invalid.");
                run();
            }
        } catch (InputMismatchException e) {
            System.out.println("Only Numbers.");
            run();
        }
    }

    /**
     * Asks for the path and fills it's data inside an Array List of Strings.
     */
    public void runLoadCSV() {

            System.out.println("");
        try {
            Scanner readPath = new Scanner(System.in);
            System.out.println("File Location: ");
            System.out.println();
            String path = readPath.nextLine();
            ArrayList<String> csvData = new ArrayList<>();
            if (validateFileFormat(path)) {
                String line = null;
                BufferedReader br = new BufferedReader(new FileReader(path));
                String delimiter = null;
                br.mark(2000);
                if (br.readLine().contains(";")) {
                    delimiter = ";";
                } else {
                    delimiter = ",";
                    br.reset();
                }
                while ((line = br.readLine()) != null) {
                    String password = Utils.passwordGenerator();
                    line = line.replaceAll("\"", "");
                    String[] values = line.split(delimiter);
                    if (validateCSVData(values)) {
                        csvData.add(values[0] + "_" + values[1] + "_" + values[2] + "_" + values[3] + "_" + values[4] + "_" + values[5] + "_"
                                + values[6] + "_" + values[7] + "_" + password);
                    } else {
                        throw new IllegalArgumentException("The CSV data is invalid, e.g., the Name of the User has non-word character.");
                    }
                }
                br.close();
                fillSNSUserDto(csvData);
                System.out.println();


                if (confirmAnotherCSV()){
                    runLoadCSV();
                } else run();

            } else {
                System.out.println();
                if (confirmCreationCSV()){
                    runLoadCSV();
                } else run();

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            if (confirmCreationCSV()){
                runLoadCSV();
            } else run();
        }
    }

    /**
     * Validates file format.
     *
     * @param path the path of the file
     * @return the boolean
     */
    public boolean validateFileFormat(String path) {
        return path.endsWith(".csv");
    }

    /**
     * Validate csv data boolean.
     *
     * @param values parameters of the CSV file
     * @return the boolean
     * @throws IOException the io exception
     */
    public boolean validateCSVData(String[] values) throws IOException {

        int MAXNUMBEROFCHARSSNSUSERNUMBER = 9;
        return !values[0].isEmpty() && Utils.validateSex(values[1]) && !values[2].isEmpty() && Utils.validateBirthDate(values[2]) &&
                !values[3].isEmpty() && (SnsUser.validateAddress(values[3]) || SnsUser.validateAddressSimple(values[3])) && !values[4].isEmpty() && (Utils.validatePhoneNumber(values[4]) || Utils.validatePhoneNumberSimple(values[4])) &&
                !values[5].isEmpty() && Utils.validateEmail(values[5]) && values[6].trim().matches("^[0-9]*$") && values[6].length() == MAXNUMBEROFCHARSSNSUSERNUMBER &&
                !values[7].isEmpty() && (Utils.validateCitizenCardNumber(values[7]) || Utils.validateCitizenCardNumberSimple(values[7]));
    }

    /**
     * Fill sns user dto.
     *
     * @param csvData the Array list of Strings with all the information of the CSV
     */
    public void fillSNSUserDto(ArrayList<String> csvData) throws IOException {
        LoadCSVController controller = new LoadCSVController();
        String[] values;
        int createCounter = 0;
        int saveCounter = 0;
        int counter = 0;
        for (int i = 0; i < csvData.size(); i++) {
            counter++;
            float percentage = (float)counter*100/csvData.size();
            System.out.printf("\n%.1f%% complete...", percentage);
            SnsUserDto dto = new SnsUserDto();
            values = csvData.get(i).split("_");
            dto.strName = values[0];
            dto.strSex = values[1];
            dto.strBirthDate = values[2];
            dto.strAddress = values[3];
            dto.strPhoneNumber = values[4];
            dto.strEmail = values[5];
            dto.snsUserNumber = Integer.parseInt(values[6]);
            dto.strCitizenCardNumber = values[7];
            dto.strPassword = values[8];
            controller.createSNSUser(dto);
            createCounter++;
            if (!controller.saveSNSUser(dto)) {
                saveCounter++;
            }
        }
        System.out.printf("Saved %d Users out of %d, because %d had duplicated information.",createCounter - saveCounter, createCounter, saveCounter);
    }

    /**
     * Asks the User for the loading of another file.
     *
     * @return a boolean
     */
    public static boolean confirmCreationCSV() {
        System.out.printf("%nData is invalid, the file does not exist or it's not a CSV file.%nDo you want to load another file?%n%n");
        System.out.printf("1 - Yes%n0 - No%n");
         final Scanner sc = new Scanner(System.in);

        System.out.printf("%nType your option: ");
        boolean check = false;
        int option = 0;
        do {
            try {
                option = sc.nextInt();
                sc.nextLine();
                check = true;
            } catch (InputMismatchException e) {
                System.out.println("Insert a valid option.");
                sc.nextLine();
            }
        } while (!check);

        return option == 1;
    }

    /**
     * Asks the User for the loading of another file.
     *
     * @return a boolean
     */
    public static boolean confirmAnotherCSV() {
        System.out.printf("%nDo you want to load another file?%n%n");
        System.out.printf("1 - Yes%n0 - No%n");
        final Scanner sc = new Scanner(System.in);

        System.out.printf("%nType your option: ");
        boolean check = false;
        int option = 0;
        do {
            try {
                option = sc.nextInt();
                sc.nextLine();
                check = true;
            } catch (InputMismatchException e) {
                System.out.println("Insert a valid option.");
                sc.nextLine();
            }
        } while (!check);

        return option == 1;
    }

    /**
     * Prints a list with all saved SNS Users
     *
     */
    public void getListOfSNSUsers() {
        LoadCSVController controller = new LoadCSVController();
        if (!controller.getSNSUserList().isEmpty()) {
            for (int i = 0; i < controller.getSNSUserList().size(); i++) {
                System.out.println("\nPosition " + i + ": " + "\n" + controller.getSNSUserList().get(i));
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println("There aren't any registered SNS Users.");
        }
    }
}




