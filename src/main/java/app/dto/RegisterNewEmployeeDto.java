package app.dto;

public class RegisterNewEmployeeDto {


    public String id;

    public String name;

    public String address;

    public String phoneNumber;

    public String email;

    public String citizenCardNumber;

    public String password;

    public RegisterNewEmployeeDto() {
    }

    public RegisterNewEmployeeDto(String id, String name, String address, String phoneNumber, String email, String citizenCardNumber, String password) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.citizenCardNumber = citizenCardNumber;
        this.password = password;
    }
}

