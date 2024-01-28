package app.mapper;


import app.domain.model.VaccinationCenter;
import app.dto.VaccinationCenterDto;


public class VaccinationCenterMapper {

    public VaccinationCenterDto domainToDto(VaccinationCenter vaccinationCenter) {
        if (vaccinationCenter == null) {
            return null;
        }
        VaccinationCenterDto dto = new VaccinationCenterDto(vaccinationCenter.getStrID(), vaccinationCenter.getStrName(), vaccinationCenter.getStrPhoneNumber(),vaccinationCenter.getStrEmail(),vaccinationCenter.getStrFax(),vaccinationCenter.getStrWebsite(),vaccinationCenter.getStrOpeningHour(),vaccinationCenter.getStrClosingHour(),vaccinationCenter.getStrSlotDuration(),vaccinationCenter.getStrVaccinesPerSlot(),vaccinationCenter.getStrRoad(),vaccinationCenter.getStrZipCode(),vaccinationCenter.getStrLocal(),vaccinationCenter.getStrCenterCoordinatorID());
        dto.scheduledVaccineList = vaccinationCenter.getScheduledVaccineList();
        dto.slotsPerDay = vaccinationCenter.getSlotsPerDay();
        dto.arrivalList = vaccinationCenter.getArrivalsList();
        return dto;
    }

    public VaccinationCenter dtoToDomain(VaccinationCenterDto dto) {
        if (dto == null) {
            return null;
        }

        VaccinationCenter vaccinationCenter = new VaccinationCenter(dto.strID, dto.strName, dto.strPhoneNumber,dto.strEmail,dto.strFax,dto.strWebsite,dto.strOpeningHour,dto.strClosingHour,dto.strSlotDuration,dto.strVaccinesPerSlot,dto.strRoad,dto.strZipCode,dto.strLocal,dto.strCenterCoordinatorID);
        vaccinationCenter.setScheduledVaccineList(dto.scheduledVaccineList);
        vaccinationCenter.setArrivalsList(dto.arrivalList);
        return vaccinationCenter;
    }
}
