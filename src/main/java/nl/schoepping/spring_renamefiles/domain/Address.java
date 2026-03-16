package nl.schoepping.spring_renamefiles.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private String countryCode;
    private String country;
    private String province;
    private String city;
    private String location;
    private String title;
    private String description;


}
