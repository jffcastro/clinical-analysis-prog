package app.domain.model;

import app.controller.App;
import app.dto.VaccineAndAdminProcessDto;
import app.ui.console.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VaccineTest {
    private static List<Vaccine> vaccines;

    private Company c = App.getInstance().getCompany();

    @Test
    /**
     * Verifies if a Vaccine with all the right attributes is created
     */
    public void createVaccine() {
        Vaccine v = Utils.createVaccine("Test", 12, "Brand1", 20.0, 12, 15, 16);
        assertTrue(v.validateVaccine());

    }

    @Test
    /**
     * Verifies if a Vaccine with all the attributes null is created
     */
    public void createNullVaccine() {
        assertFalse(Utils.createVaccine("", 0, "", 20.0, 12, 15, 12).validateVaccine());
    }

    @Test
    /**
     * Verifies if a Vaccine with empty name is created
     */
    public void createVaccineWithNullName() {
        assertFalse(Utils.createVaccine("", 1234, "Brand", 20.0, 12, 15, 12).validateVaccine());
    }

    @Test
    /**
     * Verifies if a Vaccine with invalid id is created
     */
    public void createVaccineWithNullId() {
        assertFalse(Utils.createVaccine("Test", 0, "Brand", 20.0, 12, 15, 12).validateVaccine());
    }

    @Test
    /**
     * Verifies if a Vaccine with empty brand is created
     */
    public void createVaccineWithNullBrand() {
        assertFalse(Utils.createVaccine("Test", 123, "", 20.0, 12, 15, 12).validateVaccine());
    }

    @Test
    public void createTwoVaccinesWithSameId(){
        Company c = App.getInstance().getCompany();

        Vaccine v1 = Utils.createVaccine("Test", 123, "Brand", 20.0, 12, 15, 12);
        c.saveVaccineTest(v1);
        VaccineAndAdminProcessDto dto = new VaccineAndAdminProcessDto();
        dto.id = 123;
        dto.name = "Test1";
        dto.brand = "brand";
        dto.dosage = new ArrayList<>(List.of(20.0));
        VaccineType vt= new VaccineType("TYPE1","asjiasdj","Tech");
        dto.vt = vt;
        dto.ageGroups = new ArrayList<>(Arrays.asList(new ArrayList<>(List.of(1)), new ArrayList<>(List.of(18))));
        dto.timeIntervalBetweenVaccines = new ArrayList<>(Arrays.asList(new ArrayList<>(List.of(15))));
        dto.numberOfDoses = new ArrayList<>(List.of(2));
        Vaccine v2 = Utils.createVaccine("Test2", 123, "Brand2", 20.0, 12, 15, 12);

        assertFalse(c.specifyNewVaccineAndAdminProcess(dto));

    }
}
