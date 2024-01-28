package app.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Departure implements Serializable {

    private final int snsNumber;

    private final LocalDateTime departureTime;

    private static final long serialVersionUID = 5097142297744289963L;


    public Departure(int snsNumber, LocalDateTime departureTime) {
        this.snsNumber = snsNumber;
        this.departureTime = departureTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    @Override
    public String toString() {
        return "departureTime=" + departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departure)) return false;
        Departure departure = (Departure) o;
        return snsNumber == departure.snsNumber;
    }

}
