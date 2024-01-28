package app.domain.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PerformanceAnalyzer implements Serializable {

    private final VaccinationCenter vaccinationCenter;

    private int[] listToBeAnalyzed;
    private int[] maxSumSubList;


    /**
     * Instantiates a new performance analyzer.
     *
     * @param vaccinationCenter the vaccination center of the Center Coordinator
     */
    public PerformanceAnalyzer(VaccinationCenter vaccinationCenter) {
        this.vaccinationCenter = vaccinationCenter;
    }


    /**
     * Gets the statistics daily list.
     *
     * @param date the chosen date
     * @param timeInterval the chosen time interval
     * @return the statistics daily list
     */
    public int[] getTheStatisticsDailyList(LocalDate date, int timeInterval) {
        listToBeAnalyzed = formatListToBeAnalyzed(date, timeInterval);
        return listToBeAnalyzed;
    }

    /**
     * Gets a list with the time intervals for which the center was most effective in responding.
     *
     * @return the list with maximum sum
     */
    public int[] getMaxSumSubList() {
        maxSumSubList = findMaxSumSublist(listToBeAnalyzed);
        return maxSumSubList;
    }

    /**
     * Gets the sublist with the maximum sum.
     *
     * @return the sum
     */
    public int getMaxSum() {
        return calculateSum(maxSumSubList);
    }


    private int[] findMaxSumSublist(int[] listToBeAnalyzed) {
        int maxSum = 0;

        int startIndex = 0;
        int endIndex = 0;

        for (int firstPositionList = 0; firstPositionList < listToBeAnalyzed.length; firstPositionList++) {

            for (int lastPositionList = firstPositionList; lastPositionList < listToBeAnalyzed.length; lastPositionList++) {

                int sum = 0;
                for (int sumPosition = firstPositionList; sumPosition < lastPositionList; sumPosition++) {
                    sum += listToBeAnalyzed[sumPosition];
                }

                if (sum > maxSum) {
                    maxSum = sum;
                    startIndex = firstPositionList;
                    endIndex = lastPositionList ;
                }


            }
        }

        int[] maxSubList = new int[endIndex - startIndex ];

        int index = startIndex;
        int position = 0;
        if (maxSubList.length != 0){
            while (index < endIndex) {
                maxSubList[position] = listToBeAnalyzed[index];
                position++;
                index++;
            }
        }

        return maxSubList;
    }

    private int calculateSum(int[] maxSumSublist) {
        int sum = 0;
        for (int position : maxSumSublist)
            sum += position;

        return sum;
    }



    private int[] formatListToBeAnalyzed(LocalDate date, int timeInterval) {

        int[] listOfArrivalsAndDepartures = new int[getLengthOfList(timeInterval)];
        for (int position = 0; position < listOfArrivalsAndDepartures.length; position++) {
            int[] arrivalsAndDepartures = countArrivalsAndDepartures(date, timeInterval, position);
            listOfArrivalsAndDepartures[position] = arrivalsAndDepartures[0] - arrivalsAndDepartures[1];
        }
        return listOfArrivalsAndDepartures;
    }

    private int getLengthOfList(int timeInterval) { return vaccinationCenter.getMinutesOpenCenterPerDay() / timeInterval; }


    /**
     * Gets the time interval in minutes.
     *
     * @param timeInterval the chosen time interval
     * @return A list with the different time intervals
     */
    private List<String> setUpTimeIntervals(int timeInterval) {
        List<String> timeIntervals = new ArrayList<>();
        LocalDateTime date = LocalDateTime.of(2022, 1, 1, Integer.parseInt(vaccinationCenter.getStrOpeningHour()), 0);

        for (int position = 0; position < getLengthOfList(timeInterval); position++) {
            String timeInterval1 = checkTimeFormat(date);
            String timeInterval2 = getNextSlot(date, timeInterval);
            StringBuilder stringBuilder = new StringBuilder().append(timeInterval1).append(timeInterval2);
            timeIntervals.add(stringBuilder.toString());
            date = date.plusMinutes(timeInterval);
        }

        return timeIntervals;
    }

    public List<String> getTimeInterval(int timeInterval){
        return setUpTimeIntervals(timeInterval);
    }

    private String checkTimeFormat(LocalDateTime date) {
        StringBuilder stringBuilder = new StringBuilder();

        if (date.getHour() < 10)
            stringBuilder.append("0").append(date.getHour());
        else
            stringBuilder.append(date.getHour());

        stringBuilder.append(":");

        if (date.getMinute() < 10)
            stringBuilder.append("0").append(date.getMinute());
        else
            stringBuilder.append(date.getMinute());

        return stringBuilder.toString();
    }

    private String getNextSlot(LocalDateTime date, int timeInterval) {
        return timeInterval != 1 ? " - " + checkTimeFormat(date.plusMinutes(timeInterval - 1)) : " ";
    }


    private int[] countArrivalsAndDepartures(LocalDate date, int timeInterval, int slot) {
        int counterArrivals = 0;
        int counterDepartures = 0;

        LocalDateTime time = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), Integer.parseInt(vaccinationCenter.getStrOpeningHour()), 0);
        LocalDateTime beginningSlot;
        LocalDateTime endSlot;

        if (slot == 0)
            beginningSlot = time;
        else
            beginningSlot = time.plusMinutes((long) timeInterval * slot);
        endSlot = time.plusMinutes((long) timeInterval * (slot + 1));

        for (Arrival arrival : vaccinationCenter.getArrivalsList())
            if ((arrival.getArrivalTime().isAfter(beginningSlot) || arrival.getArrivalTime().isEqual(beginningSlot)) && (arrival.getArrivalTime().isBefore(endSlot) ))
                counterArrivals++;

        for (Departure departure : vaccinationCenter.getDeparturesList())
            if ((departure.getDepartureTime().isAfter(beginningSlot) || departure.getDepartureTime().isEqual(beginningSlot)) && departure.getDepartureTime().isBefore(endSlot))
                counterDepartures++;

        return new int[] {counterArrivals, counterDepartures};
    }

    /**
     * Check if time interval is valid boolean.
     *
     * @param timeInterval the time interval
     * @return true, if the time interval is valid
     */
    public boolean checkIfTimeIntervalIsValid(String timeInterval) {

        if (checkIfTimeIntervalIsEmpty(timeInterval)) {
            return false;
        }
        try {
            int timeIntervalInt = Integer.parseInt(timeInterval);

            if (!checkIfTimeIntervalIsInAValidRange(timeIntervalInt)) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private boolean checkIfTimeIntervalIsEmpty(String timeInterval) {
        return timeInterval.isEmpty();
    }

    private boolean checkIfTimeIntervalIsInAValidRange(int timeInterval) {
        return (timeInterval > 0 && timeInterval <= vaccinationCenter.getMinutesOpenCenterPerDay());
    }

}
