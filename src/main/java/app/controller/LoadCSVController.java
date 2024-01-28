package app.controller;

import app.domain.model.Company;
import app.domain.model.SnsUser;
import app.domain.shared.GenericClass;
import app.dto.SnsUserDto;

import java.util.ArrayList;

/**
 * US014
 *
 * @author 1210816@isep.ipp.pt
 */
public class LoadCSVController {
    private final Company company = App.getInstance().getCompany();

    /**
     * Create a sns user
     *
     * @param dto the dto
     * @return the sns user
     */
    public SnsUser createSNSUser(SnsUserDto dto) {
        return company.getSnsUsersStore().createSNSUser(dto);
    }

    /**
     * Saves a sns user.
     *
     * @param dto the dto
     * @return a string that confirms if the User was saved or not
     */
    public boolean saveSNSUser(SnsUserDto dto){
        return company.getSnsUsersStore().saveSNSUser(dto);
    }

    /**
     * Get sns user list
     *
     * @return the array list of SNS Users
     */
    public ArrayList<SnsUser> getSNSUserList(){
        return company.getSnsUsersStore().getSnsUserList();
    }


}
