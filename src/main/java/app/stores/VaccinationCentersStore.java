package app.stores;

import app.domain.model.HealthcareCenter;
import app.domain.model.MassVaccinationCenter;
import app.domain.model.VaccinationCenter;
import app.domain.shared.Constants;
import app.domain.shared.GenericClass;
import app.dto.HealthcareCenterDto;
import app.dto.MassVaccinationCenterDto;
import app.ui.console.utils.Utils;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VaccinationCentersStore implements Serializable {
    private final GenericClass<VaccinationCenter> genericsCenters = new GenericClass<>();
    private final List<VaccinationCenter> vaccinationCenters = new ArrayList<>();
    private final List<MassVaccinationCenter> massVaccinationCenters = new ArrayList<>();
    private final List<HealthcareCenter> healthcareCenters = new ArrayList<>();


    /**
     * Instantiates a mass vaccination center with information of the DTO to verify if the data is valid.
     *
     * @param dto copies the information from the DTO that has the data needed to instantiate the center.
     */
    public void createMassVaccinationCenter(MassVaccinationCenterDto dto) {
        MassVaccinationCenter mvc = new MassVaccinationCenter(dto.strID, dto.strName, dto.strPhoneNumber, dto.strEmail, dto.strFax, dto.strWebsite, dto.strOpeningHour,
                dto.strClosingHour, dto.strSlotDuration, dto.strVaccinesPerSlot, dto.strRoad, dto.strZipCode, dto.strLocal, dto.strCenterCoordinatorID, dto.vaccineType);
    }

    /**
     * Instantiates a healthcare center with information of the DTO to verify if the data is valid.
     *
     * @param dto copies the information from the DTO that has the data needed to instantiate the center.
     */
    public void createHealthcareCenter(HealthcareCenterDto dto) {
        HealthcareCenter hc = new HealthcareCenter(dto.strID, dto.strName, dto.strPhoneNumber, dto.strEmail, dto.strFax, dto.strWebsite, dto.strOpeningHour,
                dto.strClosingHour, dto.strSlotDuration, dto.strVaccinesPerSlot, dto.strRoad, dto.strZipCode, dto.strLocal, dto.strCenterCoordinatorID, dto.strARS, dto.strAGES,
                dto.vaccineTypes);
    }


    /**
     * Saves a Mass Vaccination Center into two lists, one comprised of only Mass Vaccination Centers and another that has both kinds.
     *
     * @param dto copies the information from the DTO that has the data needed to instantiate the center.
     */
    public void saveMassVaccinationCenter(MassVaccinationCenterDto dto) {
        MassVaccinationCenter massVaccinationCenter = new MassVaccinationCenter(dto.strID, dto.strName, dto.strPhoneNumber, dto.strEmail, dto.strFax, dto.strWebsite, dto.strOpeningHour,
                dto.strClosingHour, dto.strSlotDuration, dto.strVaccinesPerSlot, dto.strRoad, dto.strZipCode, dto.strLocal, dto.strCenterCoordinatorID, dto.vaccineType);
        massVaccinationCenters.add(massVaccinationCenter);
        vaccinationCenters.add(massVaccinationCenter);
        genericsCenters.binaryFileWrite(Constants.FILE_PATH_VACCINATION_CENTERS, vaccinationCenters);
    }

    /**
     * Saves a Healthcare Center into two lists, one comprised of only Healthcare Centers and another that has both kinds.
     *
     * @param dto copies the information from the DTO that has the data needed to instantiate the center.
     */
    public void saveHealthcareCenter(HealthcareCenterDto dto) {
        HealthcareCenter healthcareCenter = new HealthcareCenter(dto.strID, dto.strName, dto.strPhoneNumber, dto.strEmail, dto.strFax, dto.strWebsite, dto.strOpeningHour,
                dto.strClosingHour, dto.strSlotDuration, dto.strVaccinesPerSlot, dto.strRoad, dto.strZipCode, dto.strLocal, dto.strCenterCoordinatorID, dto.strARS, dto.strAGES,
                dto.vaccineTypes);
        healthcareCenters.add(healthcareCenter);
        vaccinationCenters.add(healthcareCenter);
        genericsCenters.binaryFileWrite(Constants.FILE_PATH_VACCINATION_CENTERS, vaccinationCenters);
    }

    /**
     * Gets a list of Vaccination Centers
     *
     * @return a list of Vaccination Centers
     */
    public List<VaccinationCenter> getVaccinationCenters() {
        return vaccinationCenters;
    }

    /**
     * Registers the daily total of people vaccinated in each vaccination center, and exports it to a file.
     *
     * @throws IOException the io exception
     */
    public void registerDailyTotalOfVaccinatedPeople(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            FileWriter out = new FileWriter(file);
            out.write(Constants.DAILY_TOTAL_VACCINATIONS_FILE_HEADER);
            out.close();
        }
        try {
            FileWriter out = new FileWriter(file, true);
            for (VaccinationCenter vaccinationCenter : vaccinationCenters) {
                StringBuilder  dailyTotalOfVaccinatedPeople = new StringBuilder();
                dailyTotalOfVaccinatedPeople.append(Utils.formatDateToPrint(LocalDate.now())).append(";").append(vaccinationCenter).append(";").append(vaccinationCenter.getVaccinesAdministeredList().size());
                if (dailyTotalOfVaccinatedPeopleCheckDuplicates(dailyTotalOfVaccinatedPeople.toString(), fileName)) {
                    out.write("\n" + dailyTotalOfVaccinatedPeople);
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean dailyTotalOfVaccinatedPeopleCheckDuplicates(String data, String fileName) {
        try {
            Scanner read = new Scanner(new File(fileName));
            while (read.hasNextLine()) {
                String check = read.nextLine();
                if (check.equals(data)) {
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Gets vaccination center associated to coordinator.
     *
     * @param coordinatorId the coordinator id
     * @return the vaccination center associated to coordinator
     */
    public VaccinationCenter getVaccinationCenterAssociatedToCoordinator(String coordinatorId) {
        for (VaccinationCenter vaccinationCenter : vaccinationCenters) {
            if (vaccinationCenter.getStrCenterCoordinatorID().equals(coordinatorId)) {
                return vaccinationCenter;
            }
        }
        return null;
    }
    /**
     * Read binary file centers.
     */
    public void readBinaryFileCenters() {
        try {
            genericsCenters.binaryFileRead(Constants.FILE_PATH_VACCINATION_CENTERS, vaccinationCenters);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

}
