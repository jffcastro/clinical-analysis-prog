package app.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the creation of a SNSUser
 *
 * @author 1210816@isep.ipp.pt
 */
class SnsUserTest {

    /**
     * Validate sns user.
     */
    @Test
    void validateSNSUser() {
        SnsUser user = new SnsUser("User Default2","", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user1 = new SnsUser("User Default2","", "", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");

        assertTrue(user.validateSNSUser());
        assertFalse(user1.validateSNSUser());
    }

    /**
     * Validate sns user with null name.
     */
    @Test
    void validateSNSUserWithNullName() {
        SnsUser user = new SnsUser("","", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        assertFalse(user.validateSNSUser());
    }

    /**
     * Validate sns user with one letter.
     */
    @Test
    void validateSNSUserWithOneLetter() {
        SnsUser user = new SnsUser("J","", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        assertTrue(user.validateSNSUser());
    }

    /**
     * Validate sns user with complete name.
     */
    @Test
    void validateSNSUserWithCompleteName() {
        SnsUser user = new SnsUser("Joao Castro","", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        assertTrue(user.validateSNSUser());
    }

    /**
     * Validate sns user with null sex.
     */
    @Test
    void validateSNSUserWithNulLSex(){
        SnsUser user = new SnsUser("User Default2","", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        assertTrue(user.validateSNSUser());
    }

    /**
     * Validate sns user with all sex options.
     */
    @Test
    void validateSNSUserSexAllOptions(){
        SnsUser user = new SnsUser("User Default2","Male", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user1 = new SnsUser("User Default2","Female", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user2 = new SnsUser("User Default2","NA", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        assertTrue(user.validateSNSUser());
        assertTrue(user1.validateSNSUser());
        assertTrue(user2.validateSNSUser());
    }

    /**
     * Validate sns user birthdate options.
     */
    @Test
    void validateSNSUserBirthDateOptions(){
        SnsUser user = new SnsUser("User Default2","Male", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user1 = new SnsUser("User Default2","Female", "01/10/1992", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user2 = new SnsUser("User Default2","NA", "01/13/2012", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user3 = new SnsUser("User Default2","NA", "27/12/2044", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user4 = new SnsUser("User Default2","NA", "31/02/2044", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        assertTrue(user.validateSNSUser());
        assertTrue(user1.validateSNSUser());
        assertFalse(user2.validateSNSUser());
        assertTrue(user3.validateSNSUser());
        assertFalse(user4.validateSNSUser());
    }

    /**
     * Validate sns user address options.
     */
    @Test
    void validateSNSUserAddressOptions(){
        SnsUser user = new SnsUser("User Default2","Male", "29/10/2000", "Ds # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user1 = new SnsUser("User Default2","Female", "01/10/1992", "Default # 3000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user2 = new SnsUser("User Default2","NA", "01/11/2012", "", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user3 = new SnsUser("User Default2","NA", "27/12/2010", "Default # 4000-003 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user4 = new SnsUser("User Default2","NA", "23/02/2009", "Default # 4000-2002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        assertTrue(user.validateSNSUser());
        assertTrue(user1.validateSNSUser());
        assertFalse(user2.validateSNSUser());
        assertTrue(user3.validateSNSUser());
        assertTrue(user4.validateSNSUser());
    }

    /**
     * Validate sns user phone number.
     */
    @Test
    void validateSNSUserPhoneNumber(){
        SnsUser user = new SnsUser("User Default2","Male", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user1 = new SnsUser("User Default2","Female", "01/10/1992", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user2 = new SnsUser("User Default2","NA", "01/13/2012", "Default # 4000-002 # Default", "", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user3 = new SnsUser("User Default2","NA", "27/12/2044", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user4 = new SnsUser("User Default2","NA", "31/02/2044", "Default # 4000-002 # Default", "999111333", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        assertTrue(user.validateSNSUser());
        assertTrue(user1.validateSNSUser());
        assertFalse(user2.validateSNSUser());
        assertTrue(user3.validateSNSUser());
        assertFalse(user4.validateSNSUser());
    }

    /**
     * Validate sns user with null email.
     */
    @Test
    void validateSNSUserWithNullEmail() {
        SnsUser user = new SnsUser("User Default2","", "29/10/2000", "Default # 4000-002 # Default", "915604430", "", 123332998, "35906158 3 ZZ5", "AAA22aa");
        assertFalse(user.validateSNSUser());
    }

    /**
     * Validate sns user with invalid email.
     */
    @Test void validateSNSUserWithInvalidEmail(){
        SnsUser user = new SnsUser("User Default2","", "29/10/2000", "Default # 4000-002 # Default", "915604430", "akhdakdh@gmail.pt", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user1 = new SnsUser("User Default2","", "29/10/2000", "Default # 4000-002 # Default", "915604430", "akhdakdh@gmail", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user2 = new SnsUser("User Default2","", "29/10/2000", "Default # 4000-002 # Default", "915604430", "akhdakdh@.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        assertFalse(user.validateSNSUser());
        assertFalse(user1.validateSNSUser());
        assertFalse(user2.validateSNSUser());
    }

    /**
     * Validate sns user with valid email.
     */
    @Test void validateSNSUserWithValidEmail(){
        SnsUser user = new SnsUser("User Default2","", "29/10/2000", "Default # 4000-002 # Default", "915604430", "akhdakdh@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        assertTrue(user.validateSNSUser());
    }

    /**
     * Validate sns user number.
     */
    @Test
    void validateSNSUserNumber(){
        SnsUser user = new SnsUser("User Default2","Male", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user1 = new SnsUser("User Default2","Female", "01/10/1992", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user2 = new SnsUser("User Default2","NA", "01/13/2012", "Default # 4000-002 # Default", "915604433", "kajd@gmail.com", 12333299, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user3 = new SnsUser("User Default2","NA", "27/12/2044", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user4 = new SnsUser("User Default2","NA", "31/02/2044", "Default # 4000-002 # Default", "933398881", "kajd@gmail.com",1, "35906158 3 ZZ5", "AAA22aa");
        assertTrue(user.validateSNSUser());
        assertTrue(user1.validateSNSUser());
        assertFalse(user2.validateSNSUser());
        assertTrue(user3.validateSNSUser());
        assertFalse(user4.validateSNSUser());
    }

    /**
     * Validate sns user citizen card number.
     */
    @Test
    void validateSNSUserCitizenCard(){
        SnsUser user = new SnsUser("User Default2","Male", "29/10/2000", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user1 = new SnsUser("User Default2","Female", "01/10/1992", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user2 = new SnsUser("User Default2","NA", "01/13/2012", "Default # 4000-002 # Default", "915604433", "kajd@gmail.com", 123332993, "35906358 3 OX5", "AAA22aa");
        SnsUser user3 = new SnsUser("User Default2","NA", "27/12/2044", "Default # 4000-002 # Default", "915604430", "kajd@gmail.com", 123332998, "35906158 3 ZZ5", "AAA22aa");
        SnsUser user4 = new SnsUser("User Default2","NA", "31/02/2044", "Default # 4000-002 # Default", "933398881", "kajd@gmail.com",133112312, "35906158 3 ZH5", "AAA22aa");
        assertTrue(user.validateSNSUser());
        assertTrue(user1.validateSNSUser());
        assertFalse(user2.validateSNSUser());
        assertTrue(user3.validateSNSUser());
        assertFalse(user4.validateSNSUser());
    }

}