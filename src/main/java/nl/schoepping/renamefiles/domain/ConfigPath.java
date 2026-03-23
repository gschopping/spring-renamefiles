package nl.schoepping.renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigPath {
    private String pathForTimeLaps = "^(Timelaps\\d+)$";
    private String pathForGps = "^(GPS\\d+)$";
    private String pathForResults = "results";
    private String pathForDng = "DNG";
}
