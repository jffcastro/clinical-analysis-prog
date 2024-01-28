package app.stores;

import app.controller.App;
import app.domain.model.CenterCoordinator;
import app.domain.model.Employee;
import app.domain.model.Nurse;
import app.domain.model.Receptionist;
import app.domain.shared.Constants;
import app.domain.shared.GenericClass;
import app.dto.RegisterNewEmployeeDto;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.EOFException;
import java.io.NotSerializableException;
import java.util.ArrayList;
import java.util.Objects;

public class EmployeesStore {

    private final GenericClass<Employee> genericsEmployee = new GenericClass<>();
    private final ArrayList<Employee> employees = new ArrayList<>();
    private final ArrayList<Employee> nurseList = new ArrayList<>();
    private final ArrayList<Employee> receptionistList = new ArrayList<>();
    private final ArrayList<Employee> centerCoordinatorList = new ArrayList<>();
    private final ArrayList<String> centerCoordinatorIDs = new ArrayList<>();
    private final transient AuthFacade authFacade;

    public EmployeesStore(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    public void centerCoordinatorIDList() {
        ArrayList<Employee> centerCoordinators = getCenterCoordinatorList();
        for (Employee centerCoordinator : centerCoordinators) {
            if (!(centerCoordinatorIDs.contains(centerCoordinator.getId()))) {
                centerCoordinatorIDs.add(centerCoordinator.getId());
            }
        }
    }

    /**
     * Gets a list of Center Coordinators IDs
     *
     * @return a list of Center Coordinators IDs
     */
    public ArrayList<String> getCenterCoordinatorIDs() {
        return centerCoordinatorIDs;
    }

    /**
     * Generates a new id for each created Employee.
     *
     * @return new Employee generated id
     */
    public StringBuilder idGenerator() {
        int numberOfEmployees = getEmployees().size() + Constants.NEW_EMPLOYEE;
        if (numberOfEmployees > 0) {
            int lenght = (int) (Math.log10(numberOfEmployees) + 1);
            StringBuilder generatedID = new StringBuilder();
            generatedID.append("0".repeat(Math.max(0, Constants.ID_LENGTH - lenght)));
            return generatedID.append(numberOfEmployees);
        } else
            return new StringBuilder("00001");
    }

    /**
     * Register an Employee in the company storage
     *
     * @param dto A data transfer object with all the necessary information about the new Employee
     * @return true if the new Employee data is valid
     */
    public boolean registerNewEmployee(RegisterNewEmployeeDto dto) {
        Employee emp = new Employee(dto.id, dto.name, dto.address, dto.phoneNumber, dto.email, dto.citizenCardNumber, dto.password);
        return emp.validateEmployee();
    }

    /**
     * Saves an Employee into the Company storage.
     *
     * @param dto          A data transfer object with all the necessary information about the new Employee
     * @param selectedRole Selected role for the new Employee by the user                     Company Vaccines Storage: {@link #employees}
     * @throws NotSerializableException the not serializable exception
     */
    public void saveCreatedEmployee(RegisterNewEmployeeDto dto, String selectedRole) throws NotSerializableException {
        if (Objects.equals(selectedRole, "Nurse")) {
            Employee emp = new Nurse(dto.id, dto.name, dto.address, dto.phoneNumber, dto.email, dto.citizenCardNumber, dto.password);
            employees.add(emp);

            genericsEmployee.binaryFileWrite(Constants.FILE_PATH_EMPLOYEES, getEmployees());
            this.authFacade.addUserWithRole(dto.name, String.valueOf(dto.email), dto.password, Constants.ROLE_NURSE);
        } else if (Objects.equals(selectedRole, "Receptionist")) {
            Employee emp = new Receptionist(dto.id, dto.name, dto.address, dto.phoneNumber, dto.email, dto.citizenCardNumber, dto.password);
            employees.add(emp);
            this.authFacade.addUserWithRole(dto.name, String.valueOf(dto.email), dto.password, Constants.ROLE_RECEPTIONIST);
            genericsEmployee.binaryFileWrite(Constants.FILE_PATH_EMPLOYEES, getEmployees());
        } else if (Objects.equals(selectedRole, "Center Coordinator")) {
            Employee emp = new CenterCoordinator(dto.id, dto.name, dto.address, dto.phoneNumber, dto.email, dto.citizenCardNumber, dto.password);
            employees.add(emp);
            this.authFacade.addUserWithRole(dto.name, String.valueOf(dto.email), dto.password, Constants.ROLE_CENTRE_COORDINATOR);
            genericsEmployee.binaryFileWrite(Constants.FILE_PATH_EMPLOYEES, getEmployees());
        }
    }

    /**
     * Authenticate employees.
     */
    public void authenticateEmployees() {
        if (!employees.isEmpty()) {
            for (Employee emp : employees) {
                if (emp instanceof Nurse) {
                    this.authFacade.addUserWithRole(emp.getName(), emp.getEmail(), emp.getPassword(), Constants.ROLE_NURSE);
                } else if (emp instanceof Receptionist) {
                    this.authFacade.addUserWithRole(emp.getName(), String.valueOf(emp.getEmail()), emp.getPassword(), Constants.ROLE_RECEPTIONIST);
                } else if (emp instanceof CenterCoordinator) {
                    this.authFacade.addUserWithRole(emp.getName(), String.valueOf(emp.getEmail()), emp.getPassword(), Constants.ROLE_CENTRE_COORDINATOR);
                }
            }

        }

    }

    /**
     * Gets the Employees registered in the Company.
     *
     * @return An ArrayList of Employees.
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Fills the array lists with the types of employees through the list that contains all employees.
     */
    public void fillListOfEmployeesWithAGivenRole() {
        ArrayList<Employee> emp = getEmployees();
        for (int positionArrayListEmployees = 0; positionArrayListEmployees < emp.size(); positionArrayListEmployees++) {
            boolean check = false;
            if (emp.get(positionArrayListEmployees) instanceof Nurse) {
                fillListOfEmployeesChecker(emp, positionArrayListEmployees, check, nurseList);

            } else if (emp.get(positionArrayListEmployees) instanceof Receptionist) {
                fillListOfEmployeesChecker(emp, positionArrayListEmployees, check, receptionistList);

            } else if (emp.get(positionArrayListEmployees) instanceof CenterCoordinator) {
                fillListOfEmployeesChecker(emp, positionArrayListEmployees, check, centerCoordinatorList);
            }
        }
    }

    private void fillListOfEmployeesChecker(ArrayList<Employee> emp, int positionArrayListEmployees, boolean check, ArrayList<Employee> listToBeFilled) {
        for (Employee employee : listToBeFilled) {
            if (emp.get(positionArrayListEmployees).getEmail().equals(employee.getEmail()) && emp.get(positionArrayListEmployees).getCitizenCardNumber().equals(employee.getCitizenCardNumber())) {
                check = true;
                break;
            }
        }
        if (!check) {
            listToBeFilled.add(emp.get(positionArrayListEmployees));
        }
    }

    /**
     * Gets the Nurses registered in the Company.
     *
     * @return An ArrayList of Nurses.
     */
    public ArrayList<Employee> getNurseList() {
        fillListOfEmployeesWithAGivenRole();
        return nurseList;
    }

    /**
     * Gets the Receptionists registered in the Company.
     *
     * @return An ArrayList of Receptionists.
     */
    public ArrayList<Employee> getReceptionistList() {
        fillListOfEmployeesWithAGivenRole();
        return receptionistList;
    }

    /**
     * Gets the Centre Coordinators registered in the Company.
     *
     * @return An ArrayList of Centre Coordinator.
     */
    public ArrayList<Employee> getCenterCoordinatorList() {
        fillListOfEmployeesWithAGivenRole();
        return centerCoordinatorList;
    }

    public String getCoordinatorId(Email email) {

        for (Employee centerCoordinator : getEmployees()) {
            if (String.valueOf(email).equals(centerCoordinator.getEmail())) {
                return centerCoordinator.getId();
            }
        }

        return "";
    }

    /**
     * Verifies if the Employees are duplicated.
     *
     * @param registerNewEmployeeDto : An Employee.
     * @return true if the Employees are duplicated, or false if they are not.
     */
    public boolean duplicatedEmployee(RegisterNewEmployeeDto registerNewEmployeeDto) {
        for (int index = 0; index < getEmployees().size(); index++) {
            if (getEmployees().get(index).getEmail().equals(registerNewEmployeeDto.name))
                return false;
        }
        return true;
    }

    /**
     * Read binary file employees.
     */
    public void readBinaryFileEmployees() {
        try {
            genericsEmployee.binaryFileRead(Constants.FILE_PATH_EMPLOYEES, employees);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

}
