package nl.schoepping.spring_renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadFile {
    private String fileName;
    private String newFileName;
    private String filePath;
    private String resultsPath;
    private ExifInfo exifInfo;
    private TimeLine timeLine;
    private FileType fileType;
    private OSMLocation location;
    private Address address;
}
