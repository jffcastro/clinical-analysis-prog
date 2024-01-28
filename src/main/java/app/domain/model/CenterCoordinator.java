package app.domain.model;

import pt.isep.lei.esoft.auth.domain.model.Email;

public class CenterCoordinator extends Employee {
    public CenterCoordinator(String id, String name, String address, String phoneNumber, String email, String citizenCardNumber, String password) {
        super(id, name, address, phoneNumber, email, citizenCardNumber, password);
    }

    @Override
    public String toString() {
        return "Name: " + super.getName() +
                " | ID: " + super.getId() +
                " | Address: " + super.getAddress() +
                " | Phone Number: " + super.getPhoneNumber() +
                " | Email: " + super.getEmail() +
                " | Citizen Card Number: " + super.getCitizenCardNumber();
    }
}