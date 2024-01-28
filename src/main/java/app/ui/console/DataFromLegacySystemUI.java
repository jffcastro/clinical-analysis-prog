package app.ui.console;

import app.controller.App;
import app.controller.DataFromLegacySystemController;
import app.domain.model.Company;
import app.domain.model.SnsUser;
import app.domain.shared.Constants;
import app.domain.shared.GenericClass;
import app.miscellaneous.ReadLegacyDataFile;

import java.io.*;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.*;

/**
 * Asks the User for csv file with legacy data, sorts it according to Coordinator's preference.
 *
 * @author João Castro <1210816@isep.ipp.pt>
 */
public class DataFromLegacySystemUI implements Runnable {
    private final DataFromLegacySystemController controller = new DataFromLegacySystemController();
    private List<String> sortedListUI;

    public void run() {
        try {
            Scanner readPath = new Scanner(System.in);
            Scanner scPos = new Scanner(System.in);
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("File Location: ");
            System.out.println();
            String path = readPath.nextLine();
            controller.setFile(new File(path));
            controller.readFile();
            if (!controller.updateFile().isEmpty()) {
                System.out.println("File updated successfully");
                System.out.println();
                System.out.println("Choose the option you want to sort.");
                System.out.println("0 - Arrival Date Time");
                System.out.println("1 - Leaving Date Time");
                System.out.println("2 - Back to Menu");
                int optionPosition = scPos.nextInt();
                if (optionPosition==0||optionPosition==1){
                    controller.chooseCriteriaToSort(optionPosition);
                    System.out.printf("%nChoose the way you want to sort.%n0 - Ascending%n1 - Descending%n2 - Back to Menu%n");
                    int option = scanner.nextInt();
                    if (option==0||option==1){
                        String algorithmToBeUsed = controller.getAlgorithm();
                        long startTime = System.nanoTime();
                        sortedListUI=controller.sortListWithAlgo(algorithmToBeUsed,option);
                        long endTime = System.nanoTime();
                        long duration = (endTime - startTime) ;
                        System.out.println();
                        printSortedArray(sortedListUI);
                        System.out.println(duration+" nano seconds");
                        System.out.println(algorithmToBeUsed);
                    }
                }

            } else {
                System.out.println("File update failed, probable cause: No User Number/Vaccine is registered in the system.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Print sorted array.
     *
     * @param sortedList the sorted list
     */
    public void printSortedArray(List<String> sortedList) {
        System.out.println("Sorted Array:");

        System.out.format("%-40s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-60s\n", "Name","SNSUSerNumber", "VaccineName", "Dose", "LotNumber","ScheduledDateTime",
                "ArrivalDateTime","NurseAdmDateTime","LeavingDateTime","VaccineDesc");
        for (int position = 0; position < sortedList.size(); position++) {
            String[] values;
            values = sortedList.get(position).split("\\|");

            System.out.format("%-40s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-60s\n",
                    values[0], values[1], values[2], values[3],values[4],values[5],values[6],values[7],
                    values[8],values[9]);
        }
        System.out.println();
        System.out.println(sortedList.size()+" entries.");
    }

}
