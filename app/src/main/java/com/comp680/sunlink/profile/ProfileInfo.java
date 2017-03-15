package com.comp680.sunlink.profile;

class ProfileInfo {

    //Personal Strings
    private String header;
    private String info;

    ProfileInfo(String newHeader, String newInfo) {
        this.header = newHeader;
        this.info = newInfo;
    }
    //Getters:
    String getHeader() {
        return this.header;
    }

    public String getInfo() {
        return this.info;
    }

    private String userName;
    private String userEmail;
    private String userPhone;
    private String userAddress;
    private String userAddress1;
    private String userAddressCity;
    private String userJobSearchStatus;
    private String userGeoPreference;
    private String userWorkAuthorization;

    ProfileInfo(String newName, String newEmail, String newPhone,
                       String newAddress,String newAddress1,String newAddressCity,
                       String newStatus, String newGeoPref, String newWorkAuth) {
        this.userName = newName;
        this.userEmail = newEmail;
        this.userPhone = newPhone;
        this.userAddress = newAddress;
        this.userAddress1 = newAddress1;
        this.userAddressCity = newAddressCity;
        this.userJobSearchStatus = newStatus;
        this.userGeoPreference = newGeoPref;
        this.userWorkAuthorization = newWorkAuth;
    }

    //Getters
    public String getName(){
        return this.userName;
    }
    public String getEmail(){
        return this.userEmail;
    }
    public String getPhone(){
        return this.userPhone;
    }
    public String getUserAddress(){
        return this.userAddress;
    }
    public String getUserAddress1(){
        return this.userAddress1;
    }
    public String getUserAddressCity(){
        return this.userAddressCity;
    }
}
