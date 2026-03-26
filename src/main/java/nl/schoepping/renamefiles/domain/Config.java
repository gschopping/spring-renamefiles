package nl.schoepping.renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Config {
    private ConfigPath path;
//    private ConfigExif exif;
//    private ConfigOpenStreetMap openStreetMap;
//    private List<FileType> fileTypes;
}
