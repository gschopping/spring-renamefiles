package nl.schoepping.spring_renamefiles.domain;

import lombok.Getter;

public class Address {
    @Getter
    private Boolean isSet = false;
    @Getter
    private String countryCode;
    @Getter
    private String country;
    @Getter
    private String province;
    @Getter
    private String city;
    @Getter
    private String location;
    @Getter
    private String postcode ;
    @Getter
    private String street;
    @Getter
    private String address;

    void setCountrycode(String countryCode) {
        this.isSet = true;
        this.countryCode = countryCode.toUpperCase();
    }

    void setCountry(String country) {
        this.isSet = true;
        this.country = country;
    }

    void setProvince(String province) {
        this.isSet = true;
        this.province = province;
    }

    void setCity(String city) {
        this.isSet = true;
        this.city = city;
    }

    void setLocation(String location) {
        this.isSet = true;
        this.location = location;
    }

    void setPostcode(String postcode) {
        this.isSet = true;
        this.postcode = postcode;
    }

    void setStreet(String street) {
        this.isSet = true;
        this.street = street;
    }

    void setAddress(String address) {
        this.isSet = true;
        this.address = address;
    }

}
