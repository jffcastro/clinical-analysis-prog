package app.miscellaneous;

import javafx.beans.property.SimpleStringProperty;

public class VaccinationCenterDailyPerformance {

    private SimpleStringProperty timeInterval;

    private SimpleStringProperty differenceBetweenArrivalsAndDepartures;

    public VaccinationCenterDailyPerformance(String timeInterval, String differenceBetweenArrivalsAndDepartures) {
        this.timeInterval = new SimpleStringProperty(timeInterval);
        this.differenceBetweenArrivalsAndDepartures = new SimpleStringProperty(differenceBetweenArrivalsAndDepartures);
    }

    public String getTimeInterval() {
        return timeInterval.get();
    }

    public SimpleStringProperty timeIntervalProperty() {
        return timeInterval;
    }


    public String getDifferenceBetweenArrivalsAndDepartures() {
        return differenceBetweenArrivalsAndDepartures.get();
    }

    public SimpleStringProperty differenceBetweenArrivalsAndDeparturesProperty() {
        return differenceBetweenArrivalsAndDepartures;
    }

}
