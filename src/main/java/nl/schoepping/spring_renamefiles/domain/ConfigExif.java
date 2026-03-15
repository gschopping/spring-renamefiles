package nl.schoepping.spring_renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigExif {

    private String[] author;
    private String[] copyright;
    private String[] comment;
    private String[] countryCode2;
    private String[] countryCode3;
    private String[] country;
    private String[] province;
    private String[] city;
    private String[] location;
    private String[] title;
    private String[] url;
    private String[] description;
    private String[] keys1;
    private String[] keys2;
    private String[] instructions;
}
