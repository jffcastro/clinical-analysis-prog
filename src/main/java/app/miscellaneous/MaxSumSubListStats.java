package app.miscellaneous;

import javafx.beans.property.SimpleStringProperty;

public class MaxSumSubListStats {

    private SimpleStringProperty number;

    public MaxSumSubListStats(String number) {
        this.number = new SimpleStringProperty(number);
    }

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }
}
