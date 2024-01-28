package app.domain.model;

import app.ui.console.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdministrationProcessTest {

    @Test
    void validateAdministrationProcess() {
        ArrayList <Integer> minAge = new ArrayList<>(List.of(1,19));
        ArrayList <Integer> maxAge = new ArrayList<>(List.of(18,30));

        ArrayList <Integer> timeBetween1stAnd2ndDose = new ArrayList<>(List.of(15,15));
        ArrayList <Integer> timeBetween2ndAnd3rdDose = new ArrayList<>(List.of(0,150));

        AdministrationProcess aP = new AdministrationProcess(new ArrayList<>(Arrays.asList(minAge, maxAge)),new ArrayList<>(List.of(2,3)),new ArrayList<>(List.of(20.0,30.0)),new ArrayList<>(Arrays.asList(timeBetween1stAnd2ndDose, timeBetween2ndAnd3rdDose)));

        assertTrue(aP.validateAdministrationProcess());
    }

    @Test
    void validateAdministrationProcessWithNullValues() {
        ArrayList <Integer> minAge = new ArrayList<>(List.of(0));
        ArrayList <Integer> maxAge = new ArrayList<>(List.of(0));

        ArrayList <Integer> timeBetween1stAnd2ndDose = new ArrayList<>(List.of(0,0));
        ArrayList <Integer> timeBetween2ndAnd3rdDose = new ArrayList<>(List.of(0,0));

        AdministrationProcess aP = new AdministrationProcess(new ArrayList<>(Arrays.asList(minAge, maxAge)),new ArrayList<>(List.of(0)),new ArrayList<>(List.of(0.0)),new ArrayList<>(Arrays.asList(timeBetween1stAnd2ndDose, timeBetween2ndAnd3rdDose)));

        assertFalse(aP.validateAdministrationProcess());
    }
    @Test
    void validateAdministrationProcessWithNullAgeGroup() {
        ArrayList <Integer> minAge = new ArrayList<>(List.of(0));
        ArrayList <Integer> maxAge = new ArrayList<>(List.of(0));

        ArrayList <Integer> timeBetween1stAnd2ndDose = new ArrayList<>(List.of(15));
        ArrayList <Integer> timeBetween2ndAnd3rdDose = new ArrayList<>(List.of(0));

        AdministrationProcess aP = new AdministrationProcess(new ArrayList<>(Arrays.asList(minAge, maxAge)),new ArrayList<>(List.of(2)),new ArrayList<>(List.of(20.0)),new ArrayList<>(Arrays.asList(timeBetween1stAnd2ndDose, timeBetween2ndAnd3rdDose)));

        assertFalse(aP.validateAdministrationProcess());
    }

    @Test
    void validateAdministrationProcessWithInvalidAgeGroup1() {
        ArrayList <Integer> minAge = new ArrayList<>(List.of(18));
        ArrayList <Integer> maxAge = new ArrayList<>(List.of(1));

        ArrayList <Integer> timeBetween1stAnd2ndDose = new ArrayList<>(List.of(15));
        ArrayList <Integer> timeBetween2ndAnd3rdDose = new ArrayList<>(List.of(0));

        AdministrationProcess aP = new AdministrationProcess(new ArrayList<>(Arrays.asList(minAge, maxAge)),new ArrayList<>(List.of(2)),new ArrayList<>(List.of(20.0)),new ArrayList<>(Arrays.asList(timeBetween1stAnd2ndDose, timeBetween2ndAnd3rdDose)));

        assertFalse(aP.validateAdministrationProcess());
    }

    @Test
    void validateAdministrationProcessWithInvalidAgeGroup2() {
        ArrayList <Integer> minAge = new ArrayList<>(List.of(18));
        ArrayList <Integer> maxAge = new ArrayList<>(List.of(18));

        ArrayList <Integer> timeBetween1stAnd2ndDose = new ArrayList<>(List.of(15,15));
        ArrayList <Integer> timeBetween2ndAnd3rdDose = new ArrayList<>(List.of(0,150));

        AdministrationProcess aP = new AdministrationProcess(new ArrayList<>(Arrays.asList(minAge, maxAge)),new ArrayList<>(List.of(2)),new ArrayList<>(List.of(20.0)),new ArrayList<>(Arrays.asList(timeBetween1stAnd2ndDose, timeBetween2ndAnd3rdDose)));

        assertFalse(aP.validateAdministrationProcess());
    }

    @Test
    void validateAdministrationProcessWithInvalidAgeGroup3() {
        ArrayList <Integer> minAge = new ArrayList<>(List.of(1,16));
        ArrayList <Integer> maxAge = new ArrayList<>(List.of(18,20));

        ArrayList <Integer> timeBetween1stAnd2ndDose = new ArrayList<>(List.of(15,15));
        ArrayList <Integer> timeBetween2ndAnd3rdDose = new ArrayList<>(List.of(0,150));

        AdministrationProcess aP = new AdministrationProcess(new ArrayList<>(Arrays.asList(minAge, maxAge)),new ArrayList<>(List.of(2,3)),new ArrayList<>(List.of(20.0)),new ArrayList<>(Arrays.asList(timeBetween1stAnd2ndDose, timeBetween2ndAnd3rdDose)));

        assertFalse(aP.validateAdministrationProcess());
    }

}