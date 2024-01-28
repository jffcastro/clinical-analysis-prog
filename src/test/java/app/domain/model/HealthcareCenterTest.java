package app.domain.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests the creation of a healthcare center
 *
 * @author João Castro <1210816@isep.ipp.pt>
 */
class HealthcareCenterTest {

    ArrayList<VaccineType> strVaccineTypeTest = new ArrayList<VaccineType>();

    /**
     * Verifies if the Healthcare Center is valid
     */

    @Test
    void validateHealthcareCenters(){
        strVaccineTypeTest.add(new VaccineType("AAAAA","test123","tech1" ));
        strVaccineTypeTest.add(new VaccineType("ASDWR","test123","tech1" ));
        HealthcareCenter hc = new HealthcareCenter("HC-2132","asdad","933398881","hasjd-2131@gmail.com",
                "933398881","www.akhjd.pt","5","9","123","133","ajkshd",
                "7337-111","kahjsda","CC-1234","asd1ad","ad", strVaccineTypeTest);
        assertTrue(hc.validateHealthcareCenters());
        assertNotNull(hc);
    }

    /**
     * Verifies opening/closing hour possibilities
     */
    @Test
    void validateVaccinationCenterHours() {
        strVaccineTypeTest.add(new VaccineType("AAAAA","test123","tech1" ));
        strVaccineTypeTest.add(new VaccineType("ASDWR","test123","tech1" ));
        HealthcareCenter hc = new HealthcareCenter("HC-2132","asdad","933398881","hasjd-2131@gmail.com",
                "933398881","www.akhjd.pt","5","9","123","133","ajkshd",
                "7337-111","kahjsda","CC-1234","asd1ad","ad", strVaccineTypeTest);
        assertTrue(hc.validateVaccinationCenterHours("0","4"));
        assertTrue(hc.validateVaccinationCenterHours("0","24"));
        assertFalse(hc.validateVaccinationCenterHours("4","4"));
        assertFalse(hc.validateVaccinationCenterHours("4","2"));
        assertFalse(hc.validateVaccinationCenterHours("25","2"));
        assertFalse(hc.validateVaccinationCenterHours("24","2"));
        assertFalse(hc.validateVaccinationCenterHours("0","0"));
        assertFalse(hc.validateVaccinationCenterHours("1","25"));
        assertFalse(hc.validateVaccinesPerSlot(""));
    }

    /**
     * Verifies website possibilities
     */
    @Test
    void validateWebsite() {
        strVaccineTypeTest.add(new VaccineType("AAAAA","test123","tech1" ));
        strVaccineTypeTest.add(new VaccineType("ASDWR","test123","tech1" ));
        HealthcareCenter hc = new HealthcareCenter("HC-2132","asdad","933398881","hasjd-2131@gmail.com",
                "933398881","www.akhjd.pt","5","9","123","133","ajkshd",
                "7337-111","kahjsda","CC-1234","asd1ad","ad", strVaccineTypeTest);
        String[] strTopLevelDomain = {".pt",".com"};
        assertTrue(hc.validateWebsite("www.kajshdj.com",strTopLevelDomain,"www."));
        assertFalse(hc.validateWebsite("wwwkajshdj.com",strTopLevelDomain,"www."));
    }


    /**
     * Verifies Phone/Fax Number possibilities
     */
    @Test
    void validatePhoneNumberAndFax() {
        strVaccineTypeTest.add(new VaccineType("AAAAA","test123","tech1" ));
        strVaccineTypeTest.add(new VaccineType("ASDWR","test123","tech1" ));
        HealthcareCenter hc = new HealthcareCenter("HC-2132","asdad","933398881","hasjd-2131@gmail.com",
                "933398881","www.akhjd.pt","5","9","123","133","ajkshd",
                "7337-111","kahjsda","CC-1234","asd1ad","ad", strVaccineTypeTest);
        assertTrue(hc.validatePhoneNumberAndFax("933398881"));
        assertFalse(hc.validatePhoneNumberAndFax("93113398881"));
        assertFalse(hc.validatePhoneNumberAndFax("252"));
        assertFalse(hc.validatePhoneNumberAndFax("93-1331112"));
    }

    /**
     * Verifies Zip Code possibilities
     */
    @Test
    void validateZipCode() {
        strVaccineTypeTest.add(new VaccineType("AAAAA","test123","tech1" ));
        strVaccineTypeTest.add(new VaccineType("ASDWR","test123","tech1" ));
        HealthcareCenter hc = new HealthcareCenter("HC-2132","asdad","933398881","hasjd-2131@gmail.com",
                "933398881","www.akhjd.pt","5","9","123","133","ajkshd",
                "7337-111","kahjsda","CC-1234","asd1ad","ad", strVaccineTypeTest);
        assertTrue(hc.validateZipCode("1113-112"));
        assertFalse(hc.validateZipCode("113-112"));
        assertFalse(hc.validateZipCode("1113-1123"));
        assertFalse(hc.validateZipCode("1113112"));
        assertFalse(hc.validateZipCode("11131112"));
    }

    /**
     * Verifies Slot Duration possibilities
     */
    @Test
    void validateSlotDuration() {
        strVaccineTypeTest.add(new VaccineType("AAAAA","test123","tech1" ));
        strVaccineTypeTest.add(new VaccineType("ASDWR","test123","tech1" ));
        HealthcareCenter hc = new HealthcareCenter("HC-2132","asdad","933398881","hasjd-2131@gmail.com",
                "933398881","www.akhjd.pt","5","9","123","133","ajkshd",
                "7337-111","kahjsda","CC-1234","asd1ad","ad", strVaccineTypeTest);
        assertTrue(hc.validateSlotDuration("231"));
        assertTrue(hc.validateSlotDuration("23"));
        assertTrue(hc.validateSlotDuration("1"));
        assertFalse(hc.validateSlotDuration(""));
        assertFalse(hc.validateSlotDuration("1425"));
        assertFalse(hc.validateSlotDuration("a"));
    }

    /**
     * Verifies Maximum Number of Vaccines Per slot possibilities
     */
    @Test
    void validateVaccinesPerSlot() {
        strVaccineTypeTest.add(new VaccineType("AAAAA","test123","tech1" ));
        strVaccineTypeTest.add(new VaccineType("ASDWR","test123","tech1" ));
        HealthcareCenter hc = new HealthcareCenter("HC-2132","asdad","933398881","hasjd-2131@gmail.com",
                "933398881","www.akhjd.pt","5","9","123","133","ajkshd",
                "7337-111","kahjsda","CC-1234","asd1ad","ad", strVaccineTypeTest);
        assertTrue(hc.validateVaccinesPerSlot("21"));
        assertTrue(hc.validateSlotDuration("231"));
        assertTrue(hc.validateSlotDuration("1"));
        assertFalse(hc.validateSlotDuration(""));
        assertFalse(hc.validateSlotDuration("1425"));
        assertFalse(hc.validateSlotDuration("a"));
    }
}