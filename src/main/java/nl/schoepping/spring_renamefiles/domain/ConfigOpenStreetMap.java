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
    private String[] title = {"display_name"};
    private String[] description = {"display_name"};
    private String[] location = {"suburb", "neighbourhood", "city_district", "quarter", "district", "road", "footway"};
    private String[] city = {"town", "city", "village", "municipality"};
    private String[] province = {"state", "county"};
    private String[] country = {"country"};
    private String[] countryCode = {"country_code"};
}
