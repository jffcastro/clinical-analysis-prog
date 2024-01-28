package app.miscellaneous;

import javafx.beans.property.SimpleStringProperty;

public class VaccinationCenterStats {

    private final SimpleStringProperty date;

    private final SimpleStringProperty  totalVaccinated;

    public VaccinationCenterStats(String date, String totalVaccinated) {
        this.date = new SimpleStringProperty(date);
        this.totalVaccinated = new SimpleStringProperty(totalVaccinated);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTotalVaccinated() {
        return totalVaccinated.get();
    }

    public SimpleStringProperty totalVaccinatedProperty() {
        return totalVaccinated;
    }

    public void setTotalVaccinated(String totalVaccinated) {
        this.totalVaccinated.set(totalVaccinated);
    }
}
