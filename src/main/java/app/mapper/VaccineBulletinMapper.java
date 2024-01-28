package app.mapper;

import app.domain.model.VaccineBulletin;
import app.dto.VaccineBulletinDto;

public class VaccineBulletinMapper {

    public VaccineBulletinDto domainToVaccineBulletinDto(VaccineBulletin vaccineBulletin) {
        if (vaccineBulletin == null) return null;
        return new VaccineBulletinDto(vaccineBulletin.getVaccine(), vaccineBulletin.getDateTimeOfLastDose(), vaccineBulletin.getDose(), vaccineBulletin.getLotNumber());
    }

    public VaccineBulletin VaccineBulletinDtoToDomain(VaccineBulletinDto vaccineBulletinDto) {
        if (vaccineBulletinDto == null) return null;
        return new VaccineBulletin(vaccineBulletinDto.vaccine, vaccineBulletinDto.dateTimeOfLastDose, vaccineBulletinDto.doseNumber, vaccineBulletinDto.lotNumber);
    }
}
