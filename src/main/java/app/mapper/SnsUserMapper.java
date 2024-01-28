package app.mapper;

import app.domain.model.SnsUser;
import app.dto.SnsUserDto;

public class SnsUserMapper {

    public SnsUserDto domainToSNSUserDto(SnsUser snsUser) {
        if (snsUser == null) return null;

        SnsUserDto snsUserDtodto = new SnsUserDto(snsUser.getStrName(), snsUser.getSnsUserNumber(), snsUser.getStrEmail(), snsUser.getStrBirthDate(), snsUser.getStrPhoneNumber(), snsUser.getStrSex(), snsUser.getStrAddress(), snsUser.getStrCitizenCardNumber(), snsUser.getStrPassword());
        snsUserDtodto.vaccineBulletins = snsUser.administratedVaccines();
        return snsUserDtodto;
    }

    public SnsUser SNSUserDtoToDomain(SnsUserDto snsUserDto) {
        if (snsUserDto == null) return null;

        SnsUser snsUser = new SnsUser(snsUserDto.strName, snsUserDto.strSex, snsUserDto.strBirthDate, snsUserDto.strAddress, snsUserDto.strPhoneNumber, snsUserDto.strEmail, snsUserDto.snsUserNumber, snsUserDto.strCitizenCardNumber, snsUserDto.strPassword);
        snsUser.setTakenVaccines(snsUserDto.vaccineBulletins);
        return snsUser;
    }
}
