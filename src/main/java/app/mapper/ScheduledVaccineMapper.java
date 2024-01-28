package app.mapper;

import app.domain.model.ScheduledVaccine;
import app.dto.ScheduledVaccineDto;

public class ScheduledVaccineMapper {

    public ScheduledVaccine createScheduledVaccine(ScheduledVaccineDto scheduledVaccineDto) {
        if (scheduledVaccineDto == null) {
            return null;
        }
        return new ScheduledVaccine(scheduledVaccineDto.snsNumber, scheduledVaccineDto.vaccineType, scheduledVaccineDto.date);
    }

    public ScheduledVaccineDto domainToDto(ScheduledVaccine object) {
        if (object == null) {
            return null;
        }
        return new ScheduledVaccineDto(object.getSnsNumber(), object.getVaccineType(), object.getDate());
    }

}
