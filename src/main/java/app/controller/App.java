package app.controller;

import app.domain.model.Company;
import app.domain.shared.Constants;
import app.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.System.getProperties;

/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class App {

    private Company company;
    private transient AuthFacade authFacade;


    private App() {
        Properties props = getProperties();
        this.company = new Company(props.getProperty(Constants.PARAMS_COMPANY_DESIGNATION));
        this.authFacade = this.company.getAuthFacade();
        bootstrap();
    }

    public Company getCompany() {
        return this.company;
    }


    public UserSession getCurrentUserSession() {
        return this.authFacade.getCurrentUserSession();
    }

    public boolean doLogin(String email, String pwd) {
        return this.authFacade.doLogin(email, pwd).isLoggedIn();
    }

    public void doLogout() {
        this.authFacade.doLogout();
    }

    private Properties getProperties() {
        Properties props = new Properties();

        // Add default properties and values
        props.setProperty(Constants.PARAMS_COMPANY_DESIGNATION, "DGS/SNS");
        props.setProperty("Timer.Hour", "23");
        props.setProperty("Timer.Minutes", "59");
        props.setProperty("Sorting.Algorithm", "BubbleSort");
        props.setProperty("Sorting.Algorithm", "InsertionSort");
        // Read configured values
        try {
            InputStream in = new FileInputStream(Constants.PARAMS_FILENAME);
            props.load(in);
            in.close();
        } catch (IOException ex) {

        }
        return props;
    }

    private void bootstrap() {
        this.authFacade.addUserRole(Constants.ROLE_ADMIN, Constants.ROLE_ADMIN);
        this.authFacade.addUserRole(Constants.ROLE_CENTRE_COORDINATOR, Constants.ROLE_CENTRE_COORDINATOR);
        this.authFacade.addUserRole(Constants.ROLE_NURSE, Constants.ROLE_NURSE);
        this.authFacade.addUserRole(Constants.ROLE_RECEPTIONIST, Constants.ROLE_RECEPTIONIST);
        this.authFacade.addUserRole(Constants.ROLE_SNS_USER, Constants.ROLE_SNS_USER);

        this.authFacade.addUserWithRole("Main Administrator", "admin@lei.sem2.pt", "123456", Constants.ROLE_ADMIN);
        this.authFacade.addUserWithRole("Main Administrator", "1@gmail.com", "1", Constants.ROLE_ADMIN);

    }

    // Extracted from https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    private static App singleton = null;

    public static App getInstance() {
        if (singleton == null) {
            synchronized (App.class) {
                singleton = new App();
            }
        }
        return singleton;
    }

    public void runDailyTasks() {
        Properties properties = getProperties();
        Calendar calendar = Calendar.getInstance();
        int hour = Integer.parseInt(properties.getProperty("Timer.Hour"));
        int minutes = Integer.parseInt(properties.getProperty("Timer.Minutes"));

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    company.getVaccinationCentersStore().registerDailyTotalOfVaccinatedPeople(Constants.DAILY_REGISTERS_FILE_NAME);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        calendar.set(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue() - 1, LocalDateTime.now().getDayOfMonth(), hour, minutes);

        timer.scheduleAtFixedRate(timerTask, Date.from(calendar.toInstant()), Constants.PARAMS_MILLISECONDS_IN_DAY);

    }

    public String getSortingAlgorithm() {
        Properties properties = getProperties();
        return properties.getProperty("Sorting.Algorithm");
    }

    public int getRecoveryTime() {
        Properties props = getProperties();
        return Integer.parseInt(props.getProperty("Recovery.Time"));
    }
}
