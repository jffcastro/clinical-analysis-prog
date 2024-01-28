package app.stores;

import app.domain.model.VaccineType;
import app.domain.shared.Constants;
import app.domain.shared.GenericClass;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

public class VaccineTypesStore {

    private final ArrayList<VaccineType> vaccineTypes = new ArrayList<>();
    private final GenericClass<VaccineType> genericsVaccineType = new GenericClass<>();


    /**
     * Gets the Vaccine Types that are stored in the Company.
     *
     * @return An ArrayList of Vaccine Types.
     */
    public List<VaccineType> getVaccineTypes() {
        return vaccineTypes;
    }

    /**
     * Specifies a new Vaccine Type:
     * <p>
     * <p>
     * The method should create a vaccine type that should be validated, if so, it returns true
     *
     * @param code        a String to validate
     * @param description the description
     * @param technology  the technology
     * @return true if the type is valid
     */
    public boolean specifyNewVaccineType(String code, String description, String technology) {
        VaccineType vt = new VaccineType(code, description, technology);
        return vt.validateVaccineType();
    }

    /**
     * Saves a Vaccine Type into the Company storage.
     * Company Vaccines Storage: {@link #vaccineTypes}
     *
     * @param code        the code
     * @param description the description
     * @param technology  the technology
     */
    public void saveVaccineType(String code, String description, String technology) {
        VaccineType vt = new VaccineType(code, description, technology);
        vaccineTypes.add(vt);
        genericsVaccineType.binaryFileWrite(Constants.FILE_PATH_VACCINE_TYPES, vaccineTypes);
    }

    /**
     * Read binary file vaccine types.
     */
    public void readBinaryFileVaccineTypes() {
        try {
            genericsVaccineType.binaryFileRead(Constants.FILE_PATH_VACCINE_TYPES, vaccineTypes);
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

}
