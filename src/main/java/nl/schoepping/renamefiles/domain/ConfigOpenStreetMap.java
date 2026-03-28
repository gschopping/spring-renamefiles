package nl.schoepping.renamefiles.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigOpenStreetMap {
    private String title = "town city village municipality";
    private String description = "display_name";
    private String location = "suburb neighbourhood city_district quarter district road footway";
    private String city = "town city village municipality";
    private String province = "state county";
    private String country = "country";
    private String countryCode = "country_code";

    public String[] getTitleList() {
        return this.title.split(" ");
    }

    public String[] getDescriptionList() {
        return this.description.split(" ");
    }

    public String[] getLocationList() {
        return this.location.split(" ");
    }

    public String[] getCityList() {
        return this.city.split(" ");
    }

    public String[] getCountryList() {
        return this.country.split(" ");
    }

    public String[] getProvinceList() {
        return this.province.split(" ");
    }

    public String[] getCountryCodeList() {
        return this.countryCode.split(" ");
    }
}
