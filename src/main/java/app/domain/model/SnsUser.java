package app.domain.model;
/**
 * Creates an SNS User
 *
 * @author João Castro <1210816@isep.ipp.pt>
 */

import app.domain.shared.Constants;
import app.domain.shared.GenericClass;
import app.ui.console.utils.Utils;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;




/**
 * The type Sns user.
 */
public class SnsUser implements Serializable {
    private final String strName;
    private final String strSex;
    private final String strBirthDate;
    private final String strAddress;
    private final String strPhoneNumber;
    private final String strEmail;
    private final int snsUserNumber;
    private final String strCitizenCardNumber;
    private final String strPassword;
    private List<VaccineBulletin> vaccineBulletins;
    private final GenericClass<VaccineBulletin> genericsVaccineBulletin = new GenericClass<>();
    private static final long serialVersionUID = -6242190924713816484L;
    /**
     * Instantiates a new Sns user.
     *
     * @param strName              the str name
     * @param strSex               the str sex
     * @param strBirthDate         the str birthdate
     * @param strAddress           the str address
     * @param strPhoneNumber       the str phone number
     * @param strEmail             the str email
     * @param snsUserNumber        the sns user number
     * @param strCitizenCardNumber the str citizen card number
     * @param strPassword          the str password
     */
    public SnsUser(String strName, String strSex, String strBirthDate, String strAddress, String strPhoneNumber,
                   String strEmail, int snsUserNumber, String strCitizenCardNumber, String strPassword) {
        this.strName = strName;
        this.strSex = strSex;
        this.strBirthDate = strBirthDate;
        this.strAddress = strAddress;
        this.strPhoneNumber = strPhoneNumber;
        this.strEmail = strEmail;
        this.snsUserNumber = snsUserNumber;
        this.strCitizenCardNumber = strCitizenCardNumber;
        this.strPassword = strPassword;
        this.vaccineBulletins = new ArrayList<>();

        try {
            if (!validateSNSUser()) {
                throw new IllegalArgumentException("SNS User Info is Invalid");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets str name.
     *
     * @return the str name
     */
    public String getStrName() {
        return strName;
    }

    /**
     * Gets str sex.
     *
     * @return the str sex
     */
    public String getStrSex() {
        return strSex;
    }

    /**
     * Gets str address.
     *
     * @return the str address
     */
    public String getStrAddress() {
        return strAddress;
    }

    /**
     * Gets str password.
     *
     * @return the str password
     */
    public String getStrPassword() {
        return strPassword;
    }

    /**
     * Gets sns user number.
     *
     * @return the sns user number
     */
    public int getSnsUserNumber() {
        return snsUserNumber;
    }

    /**
     * Gets user email.
     *
     * @return the email
     */
    public String getStrEmail() {
        return strEmail;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getStrPhoneNumber() {
        return strPhoneNumber;
    }

    /**
     * Gets citizen card number.
     *
     * @return the citizen card number
     */
    public String getStrCitizenCardNumber() {
        return strCitizenCardNumber;
    }

    /**
     * Gets user birthdate.
     *
     * @return the user birthdate
     */
    public String getStrBirthDate() {
        return strBirthDate;
    }

    /**
     * Gets taken vaccines.
     *
     * @return the taken vaccines list
     */
    public List<VaccineBulletin> administratedVaccines() {
        return vaccineBulletins;
    }

    /**
     * Sets taken vaccines list.
     *
     * @param vaccineBulletins the taken vaccines
     */
    public void setTakenVaccines(List<VaccineBulletin> vaccineBulletins) {
        this.vaccineBulletins = vaccineBulletins;
    }

    /**
     * Validate address boolean.
     *
     * @param strAddress the str address
     * @return the boolean
     */
    public static boolean validateAddress(String strAddress) {
        String[] splitAddress = strAddress.split("#");
        if (splitAddress.length != 3)
            return false;

        String zipCode = splitAddress[1].trim();
        return zipCode.length() == 8 && zipCode.charAt(4) == '-';
    }

    /**
     * Validate address simple boolean.
     *
     * @param strAddress the str address
     * @return the boolean
     */
    public static boolean validateAddressSimple(String strAddress) {
        return !strAddress.isEmpty();
    }

    /**
     * Validate sex boolean.
     *
     * @param strSex the str sex
     * @return the boolean
     */
    public boolean validateSex(String strSex) {
        return strSex.equals("Male") || strSex.equals("Female") || strSex.equals("NA") || strSex.isEmpty()
                || strSex.equals("Feminino") || strSex.equals("Masculino");
    }

    /**
     * Validate birthdate boolean.
     *
     * @param strBirthDate the str birthdate
     * @return the boolean
     */
    public boolean validateBirthDate(String strBirthDate) {
        String dateFormat = "dd/MM/yyyy";
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(strBirthDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Validate sns user.
     *
     * @return true if the SNS User is valid.
     */
    public boolean validateSNSUser() {
        return strName != null && strEmail != null && strPassword != null && strBirthDate != null &&
                strPhoneNumber != null && strAddress != null && strCitizenCardNumber != null && !strName.isEmpty() && !strEmail.isEmpty() &&
                !strPassword.isEmpty() && !strBirthDate.isEmpty() && !strPhoneNumber.isEmpty() &&
                !strAddress.isEmpty() && !strCitizenCardNumber.isEmpty() && Utils.validateEmail(strEmail) &&
                Utils.validateSnsUserNumber(snsUserNumber) && validateSex(strSex) && (validateAddress(strAddress) || validateAddressSimple(strAddress)) &&
                (Utils.validateCitizenCardNumber(strCitizenCardNumber) || Utils.validateCitizenCardNumberSimple(strCitizenCardNumber))
                && (Utils.validatePhoneNumber(strPhoneNumber) || Utils.validatePhoneNumberSimple(strPhoneNumber)) && validateBirthDate(strBirthDate);
    }

    /**
     * To String for an SNS User (contains user´s information)
     *
     * @return String that contains all the information about a user
     */
    @Override
    public String toString() {
        return "Name: " + strName + '\n' +
                "Sex: " + strSex + '\n' +
                "Birth Date: " + strBirthDate + '\n' +
                "Address: " + strAddress + '\n' +
                "Phone Number: " + strPhoneNumber + '\n' +
                "Email: " + strEmail + '\n' +
                "SNS User Number: " + snsUserNumber + '\n' +
                "Citizen Card Number: " + strCitizenCardNumber + '\n' +
                "Password: " + strPassword;
    }

    /**
     * Register vaccine.
     *
     * @param vaccineBulletin the taken vaccine
     */
    public void registerVaccine(VaccineBulletin vaccineBulletin) {
        vaccineBulletins.add(vaccineBulletin);
        genericsVaccineBulletin.binaryFileWrite(Constants.FILE_PATH_VACCINE_BULLETIN_SNS_USER, vaccineBulletins);
    }

    /**
     * Gets user age.
     *
     * @return the user age
     */
    public int getUserAge() {
        String[] birthdateSplit = strBirthDate.split("/");
        LocalDate birthdate = LocalDate.of(Integer.parseInt(birthdateSplit[2]), Integer.parseInt(birthdateSplit[1]), Integer.parseInt(birthdateSplit[0]));
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    @Override
    public boolean equals(Object objects) {
        if (this == objects) return true;
        if (!(objects instanceof SnsUser)) return false;
        SnsUser snsUser = (SnsUser) objects;
        return snsUserNumber == snsUser.snsUserNumber;
    }



}