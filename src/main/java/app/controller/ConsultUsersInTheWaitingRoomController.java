package app.controller;

import app.domain.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * US005 - Consult the users in the waiting room of a Vaccination Center.
 *
 * @author João Leitão <1211063@isep.ipp.pt>
 */

public class ConsultUsersInTheWaitingRoomController {

    private VaccinationCenter vaccinationCenter;
    private Company company = App.getInstance().getCompany();

    /**
     * Constructor of the Controller that intermediates the info to the UI
     */

    public ConsultUsersInTheWaitingRoomController() {
    }

    /**
     * Gets the vaccination center according to the selected index.
     *
     * @param index is the option selected by the nurse containing the vaccination center they work at.
     */

    public void setVaccinationCenter(int index) {
        vaccinationCenter = company.getVaccinationCentersStore().getVaccinationCenters().get(index);
    }

    /**
     * Gets the Arrivals List.
     *
     * @return a list of Users that arrived in the selected vaccination center.
     */

    public List<Arrival> getArrivalsList() {
        return vaccinationCenter.getArrivalsList();
    }


    /**
     * Searches in the SNS Users' list for the users from the arrivals list, using the sns user number, and adds them to the waiting room list.
     *
     * @return the list of users in the waiting room.
     */

    public ArrayList<String> listOfUsersInTheWaitingRoom() {
        ArrayList<String> listOfUsersInTheWaitingRoom = new ArrayList<>();
        for (int arrivalListPosition = 0; arrivalListPosition < getArrivalsList().size(); arrivalListPosition++) {
            StringBuilder snsUserInfo = new StringBuilder();
            for (int snsUserListPosition = 0; snsUserListPosition < company.getSnsUsersStore().getSnsUserList().size(); snsUserListPosition++) {
                if (getArrivalsList().get(arrivalListPosition).getSnsNumber() == company.getSnsUsersStore().getSnsUserList().get(snsUserListPosition).getSnsUserNumber()) {
                    snsUserInfo.append("Name: ").append(company.getSnsUsersStore().getSnsUserList().get(snsUserListPosition).getStrName()).append('\n');
                    snsUserInfo.append("Sex: ").append(company.getSnsUsersStore().getSnsUserList().get(snsUserListPosition).getStrSex()).append('\n');
                    snsUserInfo.append("Birth Date: ").append(company.getSnsUsersStore().getSnsUserList().get(snsUserListPosition).getStrBirthDate()).append('\n');
                    snsUserInfo.append("SNS User Number: ").append(company.getSnsUsersStore().getSnsUserList().get(snsUserListPosition).getSnsUserNumber()).append('\n');
                    snsUserInfo.append("Phone Number: ").append(company.getSnsUsersStore().getSnsUserList().get(snsUserListPosition).getStrPhoneNumber()).append('\n');
                    listOfUsersInTheWaitingRoom.add(snsUserInfo.toString());
                }
            }
        }
        return listOfUsersInTheWaitingRoom;
    }


}