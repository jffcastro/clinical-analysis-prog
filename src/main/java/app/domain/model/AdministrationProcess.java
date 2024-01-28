package app.domain.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the Administration Process of a certain Vaccine.
 *
 * @author Gustavo Jorge <1211061@isep.ipp.pt>
 */
public class AdministrationProcess implements Serializable {

    private ArrayList<ArrayList<Integer>> ageGroups;

    private ArrayList<Integer> numberOfDoses;

    private ArrayList<Double> dosage;

    private ArrayList<ArrayList<Integer>> timeIntervalBetweenVaccines;


    /**
     * Creates an Administration Process with the following attributes:
     *
     * @param ageGroups                   The age groups that are considered in the Administration Process.
     * @param numberOfDoses               The number of doses a person should take, corresponding to each age group.
     * @param dosage                      The dosage, in ml, that should be in the vaccine in one take.
     * @param timeIntervalBetweenVaccines The interval, in days, between doses.
     */
    public AdministrationProcess(ArrayList<ArrayList<Integer>> ageGroups, ArrayList<Integer> numberOfDoses, ArrayList<Double> dosage, ArrayList<ArrayList<Integer>> timeIntervalBetweenVaccines) {
        this.ageGroups = ageGroups;
        this.numberOfDoses = numberOfDoses;
        this.dosage = dosage;
        this.timeIntervalBetweenVaccines = timeIntervalBetweenVaccines;
    }

    /**
     * Validates an AdministrationProcess.
     *
     * @return true if the Administration Process is validated.
     */
    public boolean validateAdministrationProcess() {
        if (checksIfInfoNotEmpty()) return validateAgeGroups();

        return false;
    }

    /**
     * Validates the age groups inserted by the user.
     *
     * @return true if the age groups are validated.
     */
    private boolean validateAgeGroups() {
        if (ageGroups.get(0).get(0) < 0) return false;

        for (int j = 0; j < ageGroups.get(0).size(); j++) {
            if (ageGroups.get(0).get(j) >= ageGroups.get(1).get(j)) return false;

            if (j < ageGroups.get(0).size() - 1) {
                if (ageGroups.get(1).get(j) >= ageGroups.get(0).get(j + 1)) return false;
            }
        }


        return true;
    }

    /**
     * Checks if all the info is filled.
     *
     * @return true if none of the attributes are empty.
     */
    private boolean checksIfInfoNotEmpty() {

        if (ageGroups.isEmpty() || numberOfDoses.isEmpty() || dosage.isEmpty() || timeIntervalBetweenVaccines.isEmpty())
            return false;

        return true;
    }

    public ArrayList<ArrayList<Integer>> getTimeIntervalBetweenVaccines() {
        return timeIntervalBetweenVaccines;
    }

    public ArrayList<ArrayList<Integer>> getAgeGroups() {
        return ageGroups;
    }

    public ArrayList<Integer> getNumberOfDoses() {
        return numberOfDoses;
    }

    public ArrayList<Double> getDosage() {
        return dosage;
    }
}
