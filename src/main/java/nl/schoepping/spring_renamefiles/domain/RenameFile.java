package nl.schoepping.spring_renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RenameFile {
    private String orgFileName;
    private String newFileName;
    private FileType fileTYpe;
    private ExifInfo exifInfo;
}
