package nl.schoepping.renamefiles.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigOpenStreetMap {
    @Getter(AccessLevel.NONE)
    private String title = "town city village municipality";
    @Getter(AccessLevel.NONE)
    private String description = "display_name";
    @Getter(AccessLevel.NONE)
    private String location = "suburb neighbourhood city_district quarter district road footway";
    @Getter(AccessLevel.NONE)
    private String city = "town city village municipality";
    @Getter(AccessLevel.NONE)
    private String province = "state county";
    @Getter(AccessLevel.NONE)
    private String country = "country";
    @Getter(AccessLevel.NONE)
    private String countryCode = "country_code";

    public String[] getTitle() {
        return this.title.split(" ");
    }

    public String[] getDescription() {
        return this.description.split(" ");
    }

    public String[] getLocation() {
        return this.location.split(" ");
    }

    public String[] getCity() {
        return this.city.split(" ");
    }

    public String[] getCountry() {
        return this.country.split(" ");
    }

    public String[] getProvince() {
        return this.province.split(" ");
    }

    public String[] getCountryCode() {
        return this.countryCode.split(" ");
    }
}
