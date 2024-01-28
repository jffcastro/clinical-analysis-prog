package app.controller;

import app.domain.model.*;
import app.domain.shared.Constants;
import app.miscellaneous.RecoveryPeriodTimer;
import app.stores.VaccinationCentersStore;
import app.ui.console.utils.Utils;
import app.dto.SnsUserDto;
import app.dto.VaccineBulletinDto;
import app.mapper.SnsUserMapper;
import app.mapper.VaccineBulletinMapper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Guilherme Sousa <1211073@isep.ipp.pt>
 */

public class RecordVaccineAdministrationController {

    private final Company company = App.getInstance().getCompany();

    private final VaccinationCentersStore vaccinationCentersStore = company.getVaccinationCentersStore();

    private VaccinationCenter vaccinationCenter;

    private VaccineType vaccineType;

    private Vaccine vaccine;

    private SnsUser snsUser;

    private LocalDateTime localDateTime;

    private String lotNumber;

    public RecordVaccineAdministrationController() {
    }

    /**
     * Sets vaccination center.
     *
     * @param index the index
     */
    public void setVaccinationCenter(int index) {
        VaccinationCentersStore vaccinationCentersStore = company.getVaccinationCentersStore();
        vaccinationCenter = vaccinationCentersStore.getVaccinationCenters().get(index);
    }

    /**
     * Sets sns user.
     *
     * @param snsUserDto the sns user dto
     */
    public void setSnsUser(SnsUserDto snsUserDto) {
        SnsUserMapper snsUserMapper = new SnsUserMapper();
        snsUser = snsUserMapper.SNSUserDtoToDomain(snsUserDto);
    }

    /**
     * Sets vaccine type.
     *
     * @param userIndexInList the user index in list
     */
    public void setVaccineType(int userIndexInList) {
        vaccineType = vaccinationCenter.getArrivalsList().get(userIndexInList).getVaccineType();
    }

    /**
     * Sets vaccine.
     *
     * @param currentAppointment the current appointment
     */
    public void setVaccine(int currentAppointment) {
        if (findLastDoseOfVaccineType() != Constants.FIRST_DOSE)
            vaccine = snsUser.administratedVaccines().get(currentAppointment).getVaccine();
        else
            vaccine = vaccineTypeAvailableVaccines().get(currentAppointment);
    }

    /**
     * Sets lot number.
     *
     * @param setLotNumber the set lot number
     */
    public void setLotNumber(String setLotNumber) {
        lotNumber = setLotNumber;
    }


    /**
     * Sets local date time.
     */
    public void setLocalDateTime() {
        localDateTime = LocalDateTime.now().plusMinutes(App.getInstance().getRecoveryTime());
    }

    // Functionalities

    /**
     * Vaccine type available vaccines list.
     *
     * @return the list with the available vaccines for vaccine type
     */
    public List<Vaccine> vaccineTypeAvailableVaccines() {
        ArrayList<Vaccine> vaccinesAvailable = new ArrayList<>();
        for (int index = 0; index < company.getVaccinesList().size(); index++) {
            if (vaccineType.equals(company.getVaccinesList().get(index).getVaccineType()) && userFirstDoseAgeGroup(company.getVaccinesList().get(index))) {
                vaccinesAvailable.add(company.getVaccinesList().get(index));
            }
        }
        return vaccinesAvailable;
    }


    /**
     * Vaccine available name list.
     *
     * @return the list of vaccine names for appointment vaccine type
     */
    public List<String> vaccineAvailableName() {
        ArrayList<String> vaccinesAvailable = new ArrayList<>();
        for (int index = 0; index < company.getVaccinesList().size(); index++) {
            if (vaccineType.equals(company.getVaccinesList().get(index).getVaccineType()) && userFirstDoseAgeGroup(company.getVaccinesList().get(index))) {
                vaccinesAvailable.add(company.getVaccinesList().get(index).getName());
            }
        }
        return vaccinesAvailable;
    }


    /**
     * Fill list with user sns number list.
     *
     * @return the list containing sns users sns number
     */
    public List<String> fillListWithUserSnsNumber() {
        ArrayList<String> userSnsNumber = new ArrayList<>();
        for (Arrival arrival : vaccinationCenter.getArrivalsList()) {
            for (int index = 0; index < company.getSnsUsersStore().getSnsUserList().size(); index++) {
                if (arrival.getSnsNumber() == company.getSnsUsersStore().getSnsUserList().get(index).getSnsUserNumber()) {
                    if (company.getSnsUsersStore().getSnsUserList().get(index).administratedVaccines().isEmpty())
                        userSnsNumber.add("SNS Number - " + arrival.getSnsNumber());
                    else if (!company.getSnsUsersStore().getSnsUserList().get(index).administratedVaccines().isEmpty() && company.getSnsUsersStore().getSnsUserList().get(index).administratedVaccines().get(company.getSnsUsersStore().getSnsUserList().get(index).administratedVaccines().size() - 1).getDateTimeOfLastDose().getMonthValue() != LocalDate.now().getMonthValue())
                        userSnsNumber.add("SNS Number - " + arrival.getSnsNumber());
                }
            }
        }
        return userSnsNumber;
    }


    /**
     * Gets sns user information.
     *
     * @param selectedUser the selected user in the waiting room list
     * @return the sns user information as Dto
     */
    public SnsUserDto getSnsUserInformation(int selectedUser) {
        SnsUserMapper snsUserMapper = new SnsUserMapper();
        return snsUserMapper.domainToSNSUserDto(company.getSnsUsersStore().getSnsUserList().get(snsUserIndexInList(selectedUser)));
    }

    private int snsUserIndexInList(int selectedUser) {
        for (int index = 0; index < company.getSnsUsersStore().getSnsUserList().size(); index++) {
            if (vaccinationCenter.getArrivalsList().get(selectedUser).getSnsNumber() == company.getSnsUsersStore().getSnsUserList().get(index).getSnsUserNumber()) {
                return index;
            }
        }
        return Constants.INVALID_VALUE;
    }

    /**
     * Check if user fits into any of the available vaccines age group
     *
     * @return the age grouop he fits
     */
    private boolean userFirstDoseAgeGroup(Vaccine vaccine) {
        int userAge = snsUser.getUserAge();
        for (int columns = 0; columns < vaccine.getAdminProcess().getAgeGroups().get(0).size(); columns++) {
            if ((userAge > vaccine.getAdminProcess().getAgeGroups().get(0).get(columns)) && userAge < vaccine.getAdminProcess().getAgeGroups().get(1).get(columns)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets user number of doses, for a given vaccine type.
     *
     * @return the number of doses
     */
    public int getUserNumberOfDoses() {
        if (findLastDoseOfVaccineType() != Constants.FIRST_DOSE)
            return (snsUser.administratedVaccines().get(findLastDoseOfVaccineType()).getDose());
        return Constants.FIRST_DOSE;
    }


    /**
     * Find last dose of the vaccine type of the appointment.
     *
     * @return the index of the last taken vaccine, since it contains the number of doses taken by each vaccine type
     */
    public int findLastDoseOfVaccineType() {
        if (snsUser.administratedVaccines() != null) {
            for (int index = snsUser.administratedVaccines().size() - 1; index >= 0; index--) {
                if (vaccineType.equals(snsUser.administratedVaccines().get(index).getVaccine().getVaccineType()))
                    return index;
            }
        }
        return Constants.FIRST_DOSE;
    }

    /**
     * Dosage for respective vaccine dose number.
     *
     * @param numberOfDoses the number of doses
     * @param indexVaccine  the index vaccine
     * @return the double
     */
    public Double dosageForDose(int numberOfDoses, int indexVaccine) {
        if (numberOfDoses == Constants.FIRST_DOSE)
            return vaccineTypeAvailableVaccines().get(indexVaccine).getAdminProcess().getDosage().get(vaccineTypeAvailableVaccines().get(indexVaccine).getUserAgeGroupIndex(getUserAge()));
        else {
            int userAge = snsUser.getUserAge();
            int userAgeGroupIndex = snsUser.administratedVaccines().get(indexVaccine).getVaccine().getUserAgeGroupIndex(userAge);
            return snsUser.administratedVaccines().get(indexVaccine).getVaccine().getAdminProcess().getDosage().get(userAgeGroupIndex);
        }

    }


    /**
     * Get dosage for respective dose vaccine administration process.
     *
     * @param numberOfDoses the number of doses taken already by the user
     * @param indexVaccine  the index of the selected vaccine in vaccines list
     * @return dosage for respective dose.
     */
    public String vaccineAdministrationProcess(int numberOfDoses, int indexVaccine) {
        return "Dosage: " + dosageForDose(numberOfDoses, indexVaccine) + "ml";
    }


    /**
     * Vaccine type Code for appointment vaccine type.
     *
     * @return the vaccine type code
     */
    public String vaccineTypeInfo() {
        return "Vaccine Type: " + vaccineType.getCode();
    }


    /**
     * Vaccine name for selected vaccine or for previously took vaccine.
     *
     * @return the vaccine name
     */
    public String vaccineInfo() {
        return "Vaccine: " + vaccine.getName();
    }


    /**
     * Validate vaccine´s lot number.
     *
     * @param lotNumber the vaccine´s lot number
     * @return true if vaccine´s lot number is validated
     */
    public boolean validateLotNumber(String lotNumber) {
        String numbers = "(.*\\d.*)";
        String upperCaseChars = "(.*[A-Z].*)";
        String lowerCaseChars = "(.*[a-z].*)";
        int counter = 0;
        if (lotNumber.length() == Constants.LOT_NUMBER_LENGTH) {
            for (int index = 0; index < lotNumber.length(); index++) {
                if (index <= 4 && (lotNumber.matches(numbers) || lotNumber.matches(upperCaseChars) || lotNumber.matches(lowerCaseChars)))
                    counter++;
                else if (index == 5 && lotNumber.charAt(index) == '-')
                    counter++;
                else if (index >= 6 && (lotNumber.matches(numbers)))
                    counter++;
            }
        }
        return counter == Constants.LOT_NUMBER_LENGHT;
    }

    private VaccineBulletinDto snsUserAddVaccineBulletin() {
        VaccineBulletinDto vaccineBulletinDto = new VaccineBulletinDto();
        vaccineBulletinDto.vaccine = vaccine;
        vaccineBulletinDto.doseNumber = getUserNumberOfDoses() + Constants.ADD_DOSE;
        vaccineBulletinDto.dateTimeOfLastDose = localDateTime;
        vaccineBulletinDto.lotNumber = lotNumber;
        return vaccineBulletinDto;
    }


    /**
     * Register vaccine in vaccine bulletin.
     */
    public void registerVaccineInVaccineBulletin() {
        VaccineBulletinMapper vaccineBulletinMapper = new VaccineBulletinMapper();

        if (vaccineBulletinMapper.VaccineBulletinDtoToDomain(snsUserAddVaccineBulletin()).isLastDose(vaccine.getUserAgeGroupIndex(snsUser.getUserAge()))) {
            vaccinationCenter.addFullyVaccinated(vaccineBulletinMapper.VaccineBulletinDtoToDomain(snsUserAddVaccineBulletin()));
        }
        vaccinationCenter.addAdministeredVaccine(vaccineBulletinMapper.VaccineBulletinDtoToDomain(snsUserAddVaccineBulletin()));
        snsUser.registerVaccine(vaccineBulletinMapper.VaccineBulletinDtoToDomain(snsUserAddVaccineBulletin()));
    }

    /**
     * Check if arrivals list is or not empty.
     *
     * @return true if the arrivals list is not empty
     */
    public boolean checkIfArrivalsListEmpty() {
        return !vaccinationCenter.getArrivalsList().isEmpty();
    }

    /**
     * Print Recovery Time is finished, since the UI layer can be replaced by an FX layer, this was the best way of acting.
     *
     * @throws IOException the io exception
     */
    public void printRecoveryTime() throws IOException {
        File file = new File(Constants.PATH_RECOVERY_TIME_MESSAGE);
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.printf("Received at: " + Utils.formatDateToPrint(LocalDate.now()) + "%n%nYour Recovery Time is now finished, stay safe.");
        printWriter.close();
    }

    /**
     * Sends a message saying that recovery time is over.
     */
    public void setRecoveryTimeSMS() {
        RecoveryPeriodTimer recoveryPeriodTimer = new RecoveryPeriodTimer();
        recoveryPeriodTimer.printUserRecoveryTimeSMS();
    }

    /**
     * Vaccination centers available list.
     *
     * @return the list of centers
     */
    public List<String> vaccinationCentersAvailable() {
        List<String> vaccinationCenterName = new ArrayList<>();
        for (int index = 0; index < vaccinationCentersStore.getVaccinationCenters().size(); index++) {
            vaccinationCenterName.add(vaccinationCentersStore.getVaccinationCenters().get(index).getStrName());
        }
        return vaccinationCenterName;
    }

    public String getSnsUserName() {
        return snsUser.getStrName();
    }

    public int getUserAge() {
        return snsUser.getUserAge();
    }

    public String getVaccineName() {
        return vaccine.getName();
    }

    public String getVaccineTypeName() {
        return vaccineType.getCode();
    }

    /**
     * Gets dose number.
     *
     * @return the dose number
     */
    public int getDoseNumber() {
        if (getUserNumberOfDoses() == Constants.FIRST_DOSE)
            return 1;
        else
            return getUserNumberOfDoses() + Constants.ADD_DOSE;
    }
}