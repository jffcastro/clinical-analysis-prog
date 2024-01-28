package app.dto;

/**
 * This class is just a DTO (Data Transfer Object), responsible for helping to transfer data from the UI to the Domain.
 * DTO related to the US013 - Specify a Vaccine and its Administration Process
 * @author Gustavo Jorge <1211061@isep.ipp.pt>
 */


import app.domain.model.VaccineType;

import java.util.ArrayList;

public class VaccineAndAdminProcessDto {

    public VaccineAndAdminProcessDto() {
    }

    public int id;

    public String name;

    public String brand;
    public VaccineType vt;

    public ArrayList <ArrayList<Integer>> ageGroups= new ArrayList<>();

    public ArrayList<Integer> numberOfDoses = new ArrayList<>();

    public ArrayList<Double> dosage= new ArrayList<>();

    public ArrayList <ArrayList<Integer>> timeIntervalBetweenVaccines= new ArrayList<>();
}
