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
    private String fileType;
    private String extension;
    private String dateTime = "DateTimeOriginal";
    private String timeZone;
    private String gpsLatitude = "GPSLatitude";
    private String gpsLongitude = "GPSLongitude";
    private Boolean isWritable = false;
    private Boolean isPhotoFormat = true;

    @Override
    public String toString() {
        return fileType;
    }

}

