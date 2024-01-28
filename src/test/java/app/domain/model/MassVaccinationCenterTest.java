package app.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests the creation of a mass vaccination center
 *
 * @author João Castro <1210816@isep.ipp.pt>
 */
class MassVaccinationCenterTest {

    MassVaccinationCenter mvc = new MassVaccinationCenter("HC-1213","kahsdd21","933398881","hasjd-2131@gmail.com",
            "933398881","www.akhjd.com","12","23","12","13","ajkshd",
            "7777-111","kahjsda","CC-1234",new VaccineType( "COVID", "To prevent serious COVID-19 infections", VaccineType.vaccineTechnologies[5]));

    /**
     * Verifies if the Mass Vaccination Center is valid
     */
    @Test
    void validateMassVaccinationCenters(){
        assertTrue(mvc.validateMassVaccinationCenters());
        assertNotNull(mvc);
    }

    /**
     * Verifies opening/closing hour possibilities
     */
    @Test
    void validateVaccinationCenterHours() {
        assertTrue(mvc.validateVaccinationCenterHours("2","4"));
        assertTrue(mvc.validateVaccinationCenterHours("0","24"));
        assertFalse(mvc.validateVaccinationCenterHours("4","4"));
        assertFalse(mvc.validateVaccinationCenterHours("4","2"));
        assertFalse(mvc.validateVaccinationCenterHours("25","2"));
        assertFalse(mvc.validateVaccinationCenterHours("24","2"));
        assertFalse(mvc.validateVaccinationCenterHours("0","0"));
        assertFalse(mvc.validateVaccinationCenterHours("1","25"));
        assertFalse(mvc.validateVaccinesPerSlot(""));
    }

    /**
     * Verifies website possibilities
     */
    @Test
    void validateWebsite() {
        String[] strTopLevelDomain = {".pt",".com"};
        assertTrue(mvc.validateWebsite("www.kajshdj.com",strTopLevelDomain,"www."));
        assertFalse(mvc.validateWebsite("wwwkajshdj.com",strTopLevelDomain,"www."));
    }



    /**
     * Verifies Phone/Fax Number possibilities
     */
    @Test
    void validatePhoneNumberAndFax() {
        assertTrue(mvc.validatePhoneNumberAndFax("933398881"));
        assertFalse(mvc.validatePhoneNumberAndFax("93113398881"));
        assertFalse(mvc.validatePhoneNumberAndFax("252"));
        assertFalse(mvc.validatePhoneNumberAndFax("93-1331112"));
    }

    /**
     * Verifies Zip Code possibilities
     */
    @Test
    void validateZipCode() {
        assertTrue(mvc.validateZipCode("1113-112"));
        assertFalse(mvc.validateZipCode("113-112"));
        assertFalse(mvc.validateZipCode("1113-1123"));
        assertFalse(mvc.validateZipCode("1113112"));
        assertFalse(mvc.validateZipCode("11131112"));
    }

    /**
     * Verifies Slot Duration possibilities
     */
    @Test
    void validateSlotDuration() {
        assertTrue(mvc.validateSlotDuration("231"));
        assertTrue(mvc.validateSlotDuration("23"));
        assertTrue(mvc.validateSlotDuration("1"));
        assertFalse(mvc.validateSlotDuration(""));
        assertFalse(mvc.validateSlotDuration("1425"));
        assertFalse(mvc.validateSlotDuration("a"));
    }

    /**
     * Verifies Maximum Number of Vaccines Per slot possibilities
     */
    @Test
    void validateVaccinesPerSlot() {
        assertTrue(mvc.validateVaccinesPerSlot("21"));
        assertTrue(mvc.validateSlotDuration("231"));
        assertTrue(mvc.validateSlotDuration("1"));
        assertFalse(mvc.validateSlotDuration(""));
        assertFalse(mvc.validateSlotDuration("1425"));
        assertFalse(mvc.validateSlotDuration("a"));
    }
}