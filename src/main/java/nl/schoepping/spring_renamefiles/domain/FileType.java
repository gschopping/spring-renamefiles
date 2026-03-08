package nl.schoepping.spring_renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileType {
    private String name;
    private String extension;
    private String dateTime;
    private String timeZone;
    private String gpsLatitude;
    private String gpsLongitude;
    private Boolean isWritable;
    private Boolean isPhotoFormat;
}
