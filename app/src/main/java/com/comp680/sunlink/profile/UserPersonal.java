package com.comp680.sunlink.profile;

import java.io.Serializable;

public class UserPersonal implements Serializable{
    private String userID = " ";
    private String firstName = " ";
    private String lastName = " ";
    private String middleName = " ";
    private String email = " ";
    private String phone = " ";
    private String address = " ";
    private String address1 = " ";
    private String city = " ";
    private String state = " ";
    private String status = " ";
    private String geopref = " ";
    private String workAuth = " ";

    UserPersonal() {}

    void setUserID(String newID) {
        this.userID = newID;
    }

    void setFirstName(String newFirstName) {
        if(!newFirstName.isEmpty())
            this.firstName=newFirstName;
    }

    void setLastName(String newLastName) {
        if(!newLastName.isEmpty())
            this.lastName=newLastName;
    }

    void setMiddleName(String newMiddleName) {
            this.middleName=newMiddleName;
    }

    protected void setEmail(String newEmail) {
        if(!newEmail.isEmpty())
            this.email= newEmail;
    }

    void setPhone(String newPhone) {
        if(!newPhone.isEmpty())
            this.phone = newPhone;
    }

    protected void setAddress(String newAddress) {
        if(!newAddress.isEmpty())
            this.address = newAddress;
    }

    protected void setAddress1(String newAddress1) {
        if(!newAddress1.isEmpty())
            this.address1 = newAddress1;
    }

    protected void setCity(String newCity) {
        if(!newCity.isEmpty())
            this.city = newCity;
    }

    protected void setState(String newState) {
        if(!newState.isEmpty())
            this.address = newState;
    }

    void setStatus(String newStatus) {
        if(!newStatus.isEmpty())
            this.status = newStatus;
    }

    void setGeoPref(String newGeoPref) {
        if(!newGeoPref.isEmpty())
            this.geopref = newGeoPref;
    }

    void setWorkAuth(String newWorkAuth) {
        if(!newWorkAuth.isEmpty()){
            this.workAuth = newWorkAuth;
        }
    }

    String getID() {
        return this.userID;
    }
    String getFirstName() {
        return this.firstName;
    }
    protected String getEmail(){
        return this.email;
    }
    String getLastName() {
        return this.lastName;
    }
    String getMiddleName() {
        return this.middleName;
    }
    String getPhone() {
        return this.phone;
    }
    protected String getAddress() {
        return this.address;
    }
    protected String getAddress1() {
        return this.address1;
    }
    protected String getCity() {
        return this.city;
    }
    protected String getState() {
        return this.state;
    }
    String getStatus() {
        return this.status;
    }
    String getGeopref() {
        return this.geopref;
    }
    String getWorkAuth() {
        return this.workAuth;
    }
}








