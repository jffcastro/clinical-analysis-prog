package app.domain.model;

import app.controller.App;
import app.controller.ConsultUsersInTheWaitingRoomController;
import app.controller.RegisterTheArrivalOfAnSnsUserController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * US005 - Consult the users in the waiting room of a Vaccination Center.
 *
 * @author João Leitão <1211063@isep.ipp.pt>
 */

class ConsultUsersInTheWaitingRoomTest {

    private Company company = App.getInstance().getCompany();
    private ConsultUsersInTheWaitingRoomController ctrl = new ConsultUsersInTheWaitingRoomController();
    private RegisterTheArrivalOfAnSnsUserController ctrl2 = new RegisterTheArrivalOfAnSnsUserController();


    private void setup() {

        VaccineType vt1 = new VaccineType("COVID", "A vaccine to prevent serious infections of the Covid-19 Virus", VaccineType.vaccineTechnologies[5]);

        VaccinationCenter vc = new VaccinationCenter("1234", "CVC Matosinhos", "915607321", "cvcmatosinhos@gmail.com", "915607321", "www.cvcmatosinhos.com", "1", "9", "30", "16", "Rua do Amial", "4460-098", "Matosinhos", "CC-95634");
        company.getVaccinationCentersStore().getVaccinationCenters().clear();
        company.getVaccinationCentersStore().getVaccinationCenters().add(vc);
        ctrl.setVaccinationCenter(0);

        SnsUser snsuser1 = new SnsUser("User Default", "Male", "01/01/1998", "Default # 4000-000 # Default", "915604428", "u@gmail.com", 100000000, "14698413 7 ZY7", "AAA00aa");
        SnsUser snsuser2 = new SnsUser("User Default1", "Male", "01/01/2003", "Default # 4000-001 # Default", "915604429", "u1@gmail.com", 200000000, "16068893 0 ZX7", "AAA11aa");
        company.getSnsUsersStore().getSnsUserList().add(snsuser1);
        company.getSnsUsersStore().getSnsUserList().add(snsuser2);

        ScheduledVaccine appointment1 = new ScheduledVaccine(100000000, vt1, LocalDateTime.now());
        ScheduledVaccine appointment2 = new ScheduledVaccine(200000000, vt1, LocalDateTime.now());

        vc.getScheduledVaccineList().add(appointment1);
        vc.getScheduledVaccineList().add(appointment2);

        ctrl2.setVaccinationCenter(0);

        ctrl2.checkAndSetUserAppointment(100000000);
        ctrl2.setArrival(100000000);
        ctrl2.checkAndSetUserAppointment(200000000);
        ctrl2.setArrival(200000000);

        Arrival arrival1 = new Arrival(100000000, vt1, LocalDateTime.now());
        Arrival arrival2 = new Arrival(200000000, vt1, LocalDateTime.now());

        vc.getArrivalsList().add(arrival1);
        vc.getArrivalsList().add(arrival2);
    }

    /**
     * Verifies if the list of users in the waiting room of a Vaccination Center is well filled.
     */
    @Test
    void listOfUsersInTheWaitingRoom() {
        setup();

        ArrayList<String> checkList = new ArrayList<>();

        checkList.add(
                "Name: " + "User Default" + '\n' +
                        "Sex: " + "Male" + '\n' +
                        "Birth Date: " + "01/01/1998" + '\n' +
                        "SNS User Number: " + "100000000" + '\n' +
                        "Phone Number: " + "915604428" + '\n');
        checkList.add(
                "Name: " + "User Default1" + '\n' +
                        "Sex: " + "Male" + '\n' +
                        "Birth Date: " + "01/01/2003" + '\n' +
                        "SNS User Number: " + "200000000" + '\n' +
                        "Phone Number: " + "915604429" + '\n');

        assertEquals(checkList, ctrl.listOfUsersInTheWaitingRoom());
    }

    /**
     * Verifies if the list of users in the waiting room of a Vaccination Center is not well filled.
     */

    @Test
    void listOfUsersInTheWaitingRoomFalse() {
        setup();
        ArrayList<String> checkList = new ArrayList<>();

        assertNotEquals(checkList, ctrl.listOfUsersInTheWaitingRoom());

    }
}