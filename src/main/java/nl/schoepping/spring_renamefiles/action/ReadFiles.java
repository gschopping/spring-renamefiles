package nl.schoepping.spring_renamefiles.action;

import com.google.common.io.Files;
import nl.schoepping.spring_renamefiles.domain.ReadFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadFiles {
    private String path;
    private String regexMedia;

    public ReadFiles(String path, String regexMedia) {
        this.path = path;
        this.regexMedia = regexMedia;
    }

    public List<ReadFile> getFiles() {
        ReadConfig config =  new ReadConfig();
        ReadTimeLine timeLine = new ReadTimeLine();
        File dir = new File(this.path);
        File[] files = dir.listFiles();
        List<ReadFile> fileList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile() && file.getName().toUpperCase().matches(this.regexMedia)) {
                ReadExifInfo exifInfo = new ReadExifInfo(file.getPath(), config);
                fileList.add(ReadFile.builder()
                        .fileName(file.getName())
                        .filePath(this.path)
                        .exifInfo(exifInfo.getExifInfo())
                        .timeLine(timeLine.getTimeLine(exifInfo.getExifInfo().getCreationDate()))
                        .fileType(config.getFileType(Files.getFileExtension(file.getName())))
                        .build()
                );
            }
        }
        return fileList;
    }
}
