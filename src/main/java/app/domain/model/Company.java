package app.domain.model;

import app.domain.shared.Constants;
import app.domain.shared.GenericClass;
import app.dto.*;
import app.stores.EmployeesStore;
import app.stores.SNSUsersStore;
import app.stores.VaccinationCentersStore;
import app.stores.VaccineTypesStore;
import app.mapper.ScheduledVaccineMapper;
import pt.isep.lei.esoft.auth.AuthFacade;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Company.
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 * @author Gustavo Jorge <1211061@isep.ipp.pt>
 * @author João Castro <1210816@isep.ipp.pt>
 * @author João Leitão <1211063@isep.ipp.pt>
 * @author Guilherme Sousa <1211073@isep.ipp.pt>
 * @author Pedro Monteiro <1211076@isep.ipp.pt>
 */
public class Company implements Serializable {


    private final String designation;
    private final transient AuthFacade authFacade;

    public final GenericClass<VaccineBulletin> genericsVaccineBulletin = new GenericClass<>();
    private final GenericClass<Vaccine> genericsVaccine = new GenericClass<>();


    private final ArrayList<Vaccine> vaccinesList = new ArrayList<>();
    private final List<ScheduledVaccine> appointmentsList = new ArrayList<>();

    private final VaccinationCentersStore vaccinationCentersStore = new VaccinationCentersStore();
    private final VaccineTypesStore vaccineTypesStore = new VaccineTypesStore();
    private final EmployeesStore employeesStore;
    private final SNSUsersStore snsUsersStore;

    /**
     * Gets vaccination centers store.
     *
     * @return the vaccination centers store
     */
    public VaccinationCentersStore getVaccinationCentersStore() {
        return vaccinationCentersStore;
    }

    /**
     * Gets vaccine types store.
     *
     * @return the vaccine types store
     */
    public VaccineTypesStore getVaccineTypesStore(){
        return vaccineTypesStore;
    }

    /**
     * Gets employees store.
     *
     * @return the employees store
     */
    public EmployeesStore getEmployeesStore() {
        return employeesStore;
    }


    public SNSUsersStore getSnsUsersStore(){return snsUsersStore;}
    /**
     * Instantiates a new Company.
     *
     * @param designation the designation
     */
    public Company(String designation) {
        if (StringUtils.isBlank(designation))
            throw new IllegalArgumentException("Designation cannot be blank.");

        this.designation = designation;
        this.authFacade = new AuthFacade();

       employeesStore = new EmployeesStore(authFacade);
       snsUsersStore = new SNSUsersStore(authFacade);



    }

    /**
     * Gets the designation
     *
     * @return designation designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Gets the Auth Facade
     *
     * @return Auth Facade
     */
    public AuthFacade getAuthFacade() {
        return authFacade;
    }


    /**
     * Gets the Vaccines that are stored in the Company.
     *
     * @return An ArrayList of Vaccines.
     */
    public List<Vaccine> getVaccinesList() {
        return vaccinesList;
    }

    /**
     * Specifies a new Vaccine and its Administration Process:
     * <p>
     * <p>
     * The method should create an Administration Process that should be validated, if so,
     * it creates a Vaccine that should be validated.
     *
     * @param dto A data transfer object with all the necessary information in order to specify both the Administration Process and the Vaccine.
     * @return true if the Vaccine is created and validated with success .
     */
    public boolean specifyNewVaccineAndAdminProcess(VaccineAndAdminProcessDto dto) {
        AdministrationProcess adminProcess = new AdministrationProcess(dto.ageGroups, dto.numberOfDoses, dto.dosage, dto.timeIntervalBetweenVaccines);
        if (adminProcess.validateAdministrationProcess()) {
            Vaccine vac = new Vaccine(dto.name, dto.id, dto.brand, adminProcess, dto.vt);
            if (vac.validateVaccine()) {
                boolean flag = true;
                for (Vaccine vaccine : vaccinesList) {
                    if (dto.id == vaccine.getId()) {
                        flag = false;
                        break;
                    }
                }
                return flag;

            }

        }
        return false;
    }

    /**
     * Saves a Vaccine into the Company storage.
     * Company Vaccines Storage: {@link #vaccinesList}
     *
     * @param dto the dto
     */
    public void saveVaccine(VaccineAndAdminProcessDto dto) {
        AdministrationProcess adminProcess = new AdministrationProcess(dto.ageGroups, dto.numberOfDoses, dto.dosage, dto.timeIntervalBetweenVaccines);
        Vaccine vac = new Vaccine(dto.name, dto.id, dto.brand, adminProcess, dto.vt);
        vaccinesList.add(vac);
        genericsVaccine.binaryFileWrite(Constants.FILE_PATH_VACCINES, vaccinesList);
    }

    /**
     * Save vaccine.
     *
     * @param vaccine the vaccine
     */
    public void saveVaccineBs(Vaccine vaccine){
        vaccinesList.add(vaccine);
        genericsVaccine.binaryFileWrite(Constants.FILE_PATH_VACCINES, vaccinesList);
    }

    /**
     * Save vaccine test.
     *
     * @param v the v
     */
    public void saveVaccineTest(Vaccine v) {
        vaccinesList.add(v);
        genericsVaccine.binaryFileWrite(Constants.FILE_PATH_VACCINES, vaccinesList);
    }


    /**
     * Adds appointment.
     *
     * @param scheduledVaccine the scheduled vaccine
     */
    public void addAppointment(ScheduledVaccine scheduledVaccine) {
        appointmentsList.add(scheduledVaccine);
    }

    /**
     * Gets appointments.
     *
     * @return the appointments
     */
    public List<ScheduledVaccine> getAppointments() {
        return appointmentsList;
    }

    /**
     * User is eligible for the appointment.
     *
     * @param scheduledVaccineDto the scheduled vaccine dto
     * @return true if the user doesn't have another appointment for the same Vaccine
     */
    public boolean userIsEligibleForTheAppointment(ScheduledVaccineDto scheduledVaccineDto) {
        ScheduledVaccineMapper mapper = new ScheduledVaccineMapper();
        ScheduledVaccine appointment = mapper.createScheduledVaccine(scheduledVaccineDto);
        for (ScheduledVaccine appointmentCheck : appointmentsList) {
            if ((appointment.getVaccineType().equals(appointmentCheck.getVaccineType()) && (appointment.getSnsNumber() == appointmentCheck.getSnsNumber())))
                return false;
        }
        return true;
    }

    /**
     * Clean appointments.
     */
    public void cleanAppointments() {
        appointmentsList.clear();
    }

    /**
     * Gets user index in users list.
     *
     * @param snsUserNumber the sns user number
     * @return the user index in users list
     */
    public int getUserIndexInUsersList(int snsUserNumber) {
        for (int position = 0; position < getSnsUsersStore().getSnsUserList().size(); position++) {
            if (snsUserNumber == (getSnsUsersStore().getSnsUserList().get(position).getSnsUserNumber())) return position;
        }
        return -1;
    }


    /**
     * Read binary file vaccines.
     */
    public void readBinaryFileVaccines() {
        try {
            genericsVaccine.binaryFileRead(Constants.FILE_PATH_VACCINES, vaccinesList);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read binary file vaccine bulletins.
     */
    public void readBinaryFileVaccineBulletins() {
        try {
            for (VaccinationCenter vaccinationCenter : getVaccinationCentersStore().getVaccinationCenters()) {
                genericsVaccineBulletin.binaryFileRead(Constants.FILE_PATH_VACCINE_BULLETIN, vaccinationCenter.getVaccinesAdministeredList());
            }
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    public boolean containsVaccine(String name) {
        ArrayList <Integer> minAge = new ArrayList<>(List.of(1,20,50,100));
        ArrayList <Integer> maxAge = new ArrayList<>(List.of(19,49,99,120));
        ArrayList <Integer> numberOfDoses = new ArrayList<>(List.of(2,3,3,2));
        ArrayList <Double> dosage = new ArrayList<>(List.of(25.0,35.0,30.0,20.5));
        ArrayList <Integer> timeIntervalBetween1stAnd2nd = new ArrayList<>(List.of(15,15,15,15));
        ArrayList <Integer> timeIntervalBetween2ndAnd3rd = new ArrayList<>(List.of(0,150,180,0));
        AdministrationProcess administrationProcess = new AdministrationProcess(new ArrayList<>( List.of(minAge,maxAge)),numberOfDoses,dosage, new ArrayList<>(List.of(timeIntervalBetween1stAnd2nd,timeIntervalBetween2ndAnd3rd)));
        Vaccine vaccine = new Vaccine(name,1000,"SpikeBrand",administrationProcess, new VaccineType("COVID","description","Tech"));

        return vaccinesList.contains(vaccine);
    }

}