package app.stores;

import app.controller.App;
import app.domain.model.Company;
import app.domain.model.SnsUser;
import app.domain.shared.Constants;
import app.domain.shared.GenericClass;
import app.dto.SnsUserDto;
import pt.isep.lei.esoft.auth.AuthFacade;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SNSUsersStore {
    private final transient AuthFacade authFacade;
    private final GenericClass<SnsUser> genericsSnsUsers = new GenericClass<>();
    private ArrayList<SnsUser> snsUsersList = new ArrayList<>();

    public SNSUsersStore(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    public ArrayList<SnsUser> getSnsUserList() {
        return snsUsersList;
    }

    /**
     * Create sns user with information from the DTO
     *
     * @param dto the dto
     * @return the created SNS User
     */
    public SnsUser createSNSUser(SnsUserDto dto) {
        return new SnsUser(dto.strName, dto.strSex, dto.strBirthDate, dto.strAddress, dto.strPhoneNumber,
                dto.strEmail, dto.snsUserNumber, dto.strCitizenCardNumber, dto.strPassword);
    }
    /**
     * Save sns user inside an array of SNS Users
     *
     * @param dto the dto
     * @return a string in order to know if the users was saved or not.
     */
    public boolean saveSNSUser(SnsUserDto dto) {
        if (snsUsersList.isEmpty()) {
            snsUsersList.add(createSNSUser(dto));
            genericsSnsUsers.binaryFileWrite(Constants.FILE_PATH_SNS_USERS, snsUsersList);
            this.authFacade.addUserWithRole(dto.strName, dto.strEmail, dto.strPassword, Constants.ROLE_SNS_USER);
            return true;
        } else {
            if (snsUsersList.contains(createSNSUser(dto))) return false;
        }
        snsUsersList.add(createSNSUser(dto));
        genericsSnsUsers.binaryFileWrite(Constants.FILE_PATH_SNS_USERS, snsUsersList);
        this.authFacade.addUserWithRole(dto.strName, dto.strEmail, dto.strPassword, Constants.ROLE_SNS_USER);
        return true;
    }
    /**
     * Authenticate sns user.
     */
    public void authenticateSNSUser() {
        if (!snsUsersList.isEmpty()) {
            for (SnsUser snsUser : snsUsersList) {
                this.authFacade.addUserWithRole(snsUser.getStrName(), snsUser.getStrEmail(), snsUser.getStrPassword(), Constants.ROLE_SNS_USER);
            }
        }
    }
    /**
     * Read binary file sns users.
     */
    public void readBinaryFileSnsUsers() {
        try {
            genericsSnsUsers.binaryFileRead(Constants.FILE_PATH_SNS_USERS, snsUsersList);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    public boolean containsUserWithNumber(int snsUserNumber) {
        SnsUser snsUser = new SnsUser("p", "Male", "16/04/2000", "a", "932782461", "ampedro2003@gmail.com", snsUserNumber, "30035797", "AAA11aa");
        return snsUsersList.contains(snsUser);
    }

}
