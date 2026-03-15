package nl.schoepping.spring_renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigOpenStreetMap {
    private String[] title;
    private String[] location;
    private String[] city;
    private String[] province;
    private String[] country;
    private String[] countryCode;
}
