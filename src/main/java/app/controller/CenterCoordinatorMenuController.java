package app.controller;

import app.domain.model.Company;
import app.domain.model.VaccinationCenter;
import app.stores.VaccinationCentersStore;
import app.ui.console.utils.Utils;

/**
 * The Center coordinator menu controller.
 *
 * @author Gustavo Jorge
 * @author Pedro Monteiro
 */
public class CenterCoordinatorMenuController {

    private final VaccinationCentersStore store;
    private final VaccinationCenter center;

    /**
     * Instantiates a new Center coordinator menu controller.
     */
    public CenterCoordinatorMenuController() {
        final Company company = App.getInstance().getCompany();
        store = company.getVaccinationCentersStore();
        center = store.getVaccinationCenterAssociatedToCoordinator(Utils.getLoggedCoordinatorId());
    }


    /**
     * Checks if the company has enough info for vaccination stats.
     *
     * @return the int related to the error or success of the operation
     */
    public int companyHasEnoughInfoForVaccinationStats() {
        if (store.getVaccinationCenters() ==null || store.getVaccinationCenters().isEmpty()) {
            return 1;
        }else if (center.getFullyVaccinatedList() == null || center.getFullyVaccinatedList().isEmpty()) {
            return 2;
        }
        return 0;
    }

    /**
     * Company has enough data to analyze the performance.
     *
     * @return the int related to the error or success of the operation
     */
    public int companyHasEnoughDataToAnalyzeThePerformance() {
        if (store.getVaccinationCenters() ==null || store.getVaccinationCenters().isEmpty()) {
            return 1;
        }else if (center.getArrivalsList() == null || center.getArrivalsList().isEmpty()) {
            return 2;
        } else if (center.getDeparturesList() == null || center.getDeparturesList().isEmpty()) {
            return 3;
        }
        return 0;
    }

}
