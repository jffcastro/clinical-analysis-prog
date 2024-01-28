package app.controller;

import app.domain.model.Company;
import app.domain.model.VaccinationCenter;
import app.domain.model.PerformanceAnalyzer;
import app.stores.VaccinationCentersStore;
import app.ui.console.utils.Utils;

import java.time.LocalDate;
import java.util.List;

/**
 * Analyze center performance controller.
 */
public class AnalyzeCenterPerformanceController {

    private final VaccinationCenter center;
    private LocalDate selectedDate;
    private int timeInterval;

    private final PerformanceAnalyzer analyzer ;


    /**
     * Sets time interval.
     *
     * @param timeInterval the time interval to divide the list in
     */
    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    /**
     * Sets selected date.
     *
     * @param selectedDate the selected date
     */
    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }

    /**
     * Instantiates a new controller.
     */
    public AnalyzeCenterPerformanceController() {
        final Company company = App.getInstance().getCompany();
        final VaccinationCentersStore store = company.getVaccinationCentersStore();
        String id = Utils.getLoggedCoordinatorId();
        center = store.getVaccinationCenterAssociatedToCoordinator(id);
        analyzer = center.getAnalyzer();
    }


    /**
     * Get the statistics daily list int [ ].
     *
     * @return the int [ ]
     */
    public int[] getTheStatisticsDailyList() {
        return analyzer.getTheStatisticsDailyList(selectedDate, timeInterval);
    }


    /**
     * Gets time intervals list.
     *
     * @return the time intervals
     */
    public List<String> getTimeIntervals() {
        return analyzer.getTimeInterval(timeInterval);
    }

    /**
     * Get max sum sub array.
     *
     * @return sub sum list
     */
    public int[] getMaxSumSubList() {
        return analyzer.getMaxSumSubList();
    }

    /**
     * Gets max sum.
     *
     * @return the max sum
     */
    public int getMaxSum() {
        return analyzer.getMaxSum();
    }

    /**
     * Check if time interval is valid boolean.
     *
     * @param timeInterval the time interval
     * @return true, if the time interval is valid
     */
    public boolean checkIfTimeIntervalIsValid(String timeInterval) {
           return analyzer.checkIfTimeIntervalIsValid(timeInterval);
    }


}
