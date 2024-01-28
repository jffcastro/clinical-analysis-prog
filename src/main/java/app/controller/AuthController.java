package app.controller;

import app.domain.model.Company;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class AuthController {

    private final App app;

    public AuthController() {
        this.app = App.getInstance();
    }

    public boolean doLogin(String email, String pwd) {
        try {
            return this.app.doLogin(email, pwd);
        } catch (IllegalArgumentException ex) {
            return false;
        }


    }

    public List<UserRoleDTO> getUserRoles() {
        if (this.app.getCurrentUserSession().isLoggedIn()) {
            return this.app.getCurrentUserSession().getUserRoles();
        }
        return null;
    }

    /**
     * Do logout.
     */
    public void doLogout() {
        this.app.doLogout();
    }
}
