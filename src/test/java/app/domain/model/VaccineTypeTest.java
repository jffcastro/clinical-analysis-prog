package app.domain.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VaccineTypeTest {


    @Test
    /*
     * Verifies if a Vaccine Type with all the right attributes is created
     */
    public void createVaccineType() {
        VaccineType v = new VaccineType("12345", "Covid", "Brand1");
        assertTrue(v.validateVaccineType());
    }

    @Test
    /*
     * Verifies if a Vaccine Type with all the attributes null is created
     */
    public void createNullVaccineType() {
        assertFalse(new VaccineType("", "", "").validateVaccineType());
    }

    @Test
    /*
     * Verifies if a Vaccine TYpe with invalid code is created
     */
    public void createVaccineTypeWithInvalidCode() {
        assertFalse(new VaccineType("", "Covid", "Brand1").validateVaccineType());
    }

}