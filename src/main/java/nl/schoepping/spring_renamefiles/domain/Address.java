package nl.schoepping.spring_renamefiles.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Setter(AccessLevel.NONE)
    private Boolean isSet = false;
    @Setter(AccessLevel.NONE)
    private String countryCode;
    @Setter(AccessLevel.NONE)
    private String country;
    @Setter(AccessLevel.NONE)
    private String province;
    @Setter(AccessLevel.NONE)
    private String city;
    @Setter(AccessLevel.NONE)
    private String location;
    @Setter(AccessLevel.NONE)
    private String title ;
    @Setter(AccessLevel.NONE)
    private String description ;

    public void setCountrycode(String countryCode) {
        this.isSet = true;
        this.countryCode = countryCode.toUpperCase();
    }

    public void setCountry(String country) {
        this.isSet = true;
        this.country = country;
    }

    public void setProvince(String province) {
        this.isSet = true;
        this.province = province;
    }

    public void setCity(String city) {
        this.isSet = true;
        this.city = city;
    }

    public void setLocation(String location) {
        this.isSet = true;
        this.location = location;
    }

    public void setTitle(String title) {
        this.isSet = true;
        this.title = title;
    }

    public void setDescription(String description) {
        this.isSet = true;
        this.description = description;
    }


}
