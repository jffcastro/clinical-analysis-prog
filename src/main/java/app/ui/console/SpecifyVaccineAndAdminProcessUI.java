package app.ui.console;

import app.controller.SpecifyVaccineAndAdminProcessController;
import app.domain.model.VaccineType;
import app.ui.console.utils.Utils;
import app.dto.VaccineAndAdminProcessDto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * US013 - Specify Vaccine and its Administration Process UI
 *
 * @author Gustavo Jorge <1211061@isep.ipp.pt>
 */
public class SpecifyVaccineAndAdminProcessUI implements Runnable {


    /**
     * Instantiates a new Specify vaccine and admin process ui.
     */
    public SpecifyVaccineAndAdminProcessUI() {
    }

    private SpecifyVaccineAndAdminProcessController ctrl = new SpecifyVaccineAndAdminProcessController();

    public void run() {

        if (!ctrl.getVaccineTypesList().isEmpty()) {
            Scanner sc = new Scanner(System.in);
            VaccineAndAdminProcessDto dto = new VaccineAndAdminProcessDto();
            System.out.println("------Specify Vaccine and its Administration Process------");
            System.out.println();


            System.out.println("--What's the Vaccine Type?");
            ArrayList<VaccineType> vTs = new ArrayList<>(ctrl.getVaccineTypesList());
            int options = 1;
            for (VaccineType vt : vTs) {
                System.out.printf("%d- %s %n", options, vt);
                options++;
            }
            System.out.println("Type your option:");
            int option;
            int check = 0;

            do {
                try {
                    option = sc.nextInt();
                    sc.nextLine();
                    if (option >= 1 && option <= options - 1) {
                        dto.vt = vTs.get(option - 1);
                        check = 1;
                    } else {
                        System.out.println("Insert a valid option: ");
                    }

                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Insert a valid option: ");
                }
            } while (check == 0);


            System.out.println("--What's the vaccine id?");
            check = 0;

            do {
                try {
                    dto.id = sc.nextInt();
                    sc.nextLine();
                    check = 1;
                } catch (InputMismatchException e) {
                    System.out.println("Insert a valid Vaccine ID:");
                    sc.nextLine();
                }
            } while (check == 0);


            System.out.println("--What's the vaccine name?");
            dto.name = sc.next();
            sc.nextLine();


            System.out.println("--What's the vaccine brand?");
            dto.brand = sc.next();
            sc.nextLine();


            System.out.println();


            int keepGoing = 1;
            ArrayList<Integer> minAges = new ArrayList<>();
            ArrayList<Integer> maxAges = new ArrayList<>();
            ArrayList<Integer> intervalFirstDose = new ArrayList<>();
            ArrayList<Integer> intervalSecondDose = new ArrayList<>();

            do {

                System.out.println();
                System.out.println("------Define the age group------ ");
                System.out.println();
                System.out.println("Insert minimum age of the group: ");
                check = 0;
                do {
                    try {
                        minAges.add(sc.nextInt());
                        sc.nextLine();
                        check = 1;
                    } catch (InputMismatchException e) {
                        System.out.println("Insert a valid age:");
                        sc.nextLine();
                    }
                } while (check == 0);
                check = 0;
                System.out.println("Insert maximum age of the group: ");
                do {
                    try {
                        maxAges.add(sc.nextInt());
                        sc.nextLine();
                        check = 1;
                    } catch (InputMismatchException e) {
                        System.out.println("Insert a valid age:");
                        sc.nextLine();
                    }
                } while (check == 0);


                System.out.println();
                int numberOfDoses = chooseNumberOfDoses();
                dto.numberOfDoses.add(numberOfDoses);

                check = 0;


                System.out.println("Insert the dosage, in ml, for the respective age group: ");
                check = 0;
                do {
                    try {
                        dto.dosage.add(sc.nextDouble());
                        sc.nextLine();
                        check = 1;
                    } catch (InputMismatchException e) {
                        System.out.println("Insert a valid dosage:");
                        sc.nextLine();
                    }
                } while (check == 0);


                System.out.println("Insert the interval, in days, between the 1st and 2nd vaccine: ");
                check = 0;
                if (numberOfDoses == 2) {
                    do {
                        try {
                            intervalFirstDose.add(sc.nextInt());
                            intervalSecondDose.add(null);
                            sc.nextLine();
                            check = 1;
                        } catch (InputMismatchException e) {
                            System.out.println("Insert a valid a valid number of days:");
                            sc.nextLine();
                        }
                    } while (check == 0);


                } else if (numberOfDoses == 3) {
                    do {
                        try {
                            intervalFirstDose.add(sc.nextInt());
                            sc.nextLine();
                            check = 1;
                        } catch (InputMismatchException e) {
                            System.out.println("Insert a valid number of days:");
                            sc.nextLine();
                        }
                    } while (check == 0);

                    System.out.println("Insert the interval, in days, between the 2nd and 3rd vaccine: ");
                    check = 0;
                    do {
                        try {
                            intervalSecondDose.add(sc.nextInt());
                            sc.nextLine();
                            check = 1;
                        } catch (InputMismatchException e) {
                            System.out.println("Insert a valid number of days:");
                            sc.nextLine();
                        }
                    } while (check == 0);

                }

                System.out.println();
                System.out.println("Do you want to add another age group?");
                System.out.println("1 - Yes");
                System.out.println("0 - No");
                int answer = -1;
                check = 0;
                do {
                    try {

                        do {
                            System.out.println("Type your option: ");
                            answer = sc.nextInt();
                            sc.nextLine();
                        } while (answer != 1 && answer != 0);

                        check = 1;
                    } catch (InputMismatchException e) {
                        System.out.println("Insert a valid option:");
                        sc.nextLine();
                    }
                } while (check == 0);


                if (answer == 0) {
                    keepGoing = 0;
                }

            } while (keepGoing == 1);
            dto.ageGroups.add(minAges);
            dto.ageGroups.add(maxAges);
            dto.timeIntervalBetweenVaccines.add(intervalFirstDose);
            dto.timeIntervalBetweenVaccines.add(intervalSecondDose);


            if (ctrl.specifyNewVaccineAndAdminProcess(dto)) {
                showVaccineAndAdminProcessData(dto);
                if (Utils.confirmCreation()) {
                    ctrl.saveVaccine(dto);
                    System.out.println();
                    System.out.println("The new Vaccine with its administration process was added to the Company Vaccines with success.");
                } else {
                    System.out.println("No Vaccine was added.");

                }
            } else {
                System.out.println();
                System.out.println("Invalid data.");
                System.out.println("Check what's wrong: ");
                showVaccineAndAdminProcessData(dto);
            }


        } else {
            System.out.println();
            System.out.println("|-----------------------------------------------------------------------------|");
            System.out.println("| There are no Vaccine Types yet. Please add at least one Vaccine Type first. |");
            System.out.println("|-----------------------------------------------------------------------------|");
        }


    }


    /**
     * Shows the User all the data referent to a Vaccine and to its Administration Process.
     *
     * @param dto A data transfer object with all the necessary information in order to specify both the Administration Process and the Vaccine.
     */
    private void showVaccineAndAdminProcessData(VaccineAndAdminProcessDto dto) {
        System.out.println();
        System.out.println();
        System.out.println("---- The new Vaccine: ----");
        System.out.println("Name: " + dto.name);
        System.out.println("Id: " + dto.id);
        System.out.println("Brand: " + dto.brand);
        System.out.println("Vaccine Type: " + dto.vt);
        System.out.println();
        System.out.println("---- Its Administration Process: ----");
        for (int i = 0; i < dto.ageGroups.get(0).size(); i++) {
            System.out.printf("From ages %d to %d:%n", dto.ageGroups.get(0).get(i), dto.ageGroups.get(1).get(i));
            System.out.println("Number of doses: " + dto.numberOfDoses.get(i));
            System.out.println("Dosage: " + dto.dosage.get(i) + "ml");
            if (dto.numberOfDoses.get(i) == 2) {
                System.out.println("Interval between 1st and 2nd dose: " + dto.timeIntervalBetweenVaccines.get(0).get(i) + " days");
                System.out.println();
            } else {
                System.out.println("Interval between 1st and 2nd dose: " + dto.timeIntervalBetweenVaccines.get(0).get(i) + " days");
                System.out.println("Interval between 2nd and 3rd dose: " + dto.timeIntervalBetweenVaccines.get(1).get(i) + " days");
                System.out.println();

            }

        }

    }

    /**
     * Asks the user how many doses should be considered for an age group.
     *
     * @return number of doses for a given age group
     */
    private int chooseNumberOfDoses() {
        Scanner sc = new Scanner(System.in);
        int numberOfDoses = 0;
        boolean check = true;
        do {
            check = true;

            System.out.println("Select the number of doses for the respective age group: ");
            System.out.println("1- 2 doses");
            System.out.println("2- 3 doses");
            System.out.println();
            System.out.println("Type your option: ");
            int option = sc.nextInt();


            switch (option) {
                case 1:
                    numberOfDoses = 2;
                    break;
                case 2:
                    numberOfDoses = 3;
                    break;
                case 0:
                    System.out.print("Insert number of doses: ");
                    numberOfDoses = sc.nextInt();
                    sc.nextLine();
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid option.");
                    check = false;
            }

        } while (!check);
        return numberOfDoses;
    }

}
