package nl.schoepping.spring_renamefiles.action;

import com.google.common.io.Files;
import nl.schoepping.spring_renamefiles.domain.Address;
import nl.schoepping.spring_renamefiles.domain.ReadFile;
import nl.schoepping.spring_renamefiles.domain.TimeLine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadFiles {
    private String path;
    private String regexMedia;
    public enum Divider {
        COUNTER,
        TIME
    }
    private Divider divider;

    public ReadFiles(String path, String regexMedia, Divider divider) {
        this.path = path;
        this.regexMedia = regexMedia;
        this.divider = divider;
    }

    public List<ReadFile> getFiles() {
        ReadConfig config =  new ReadConfig();
        ReadTimeLine timeLines = new ReadTimeLine();
        File dir = new File(this.path);
        File[] files = dir.listFiles();
        ReadAddress address = new ReadAddress();
        List<ReadFile> fileList = new ArrayList<>();
        int counter = 1;
        for (File file : files) {
            if (file.isFile() && file.getName().toUpperCase().matches(this.regexMedia)) {
                ReadExifInfo exifInfo = new ReadExifInfo(file.getPath(), config);
                TimeLine timeLine = timeLines.getTimeLine(exifInfo.getExifInfo().getCreationDate());
                String newFileName = "";
                String title = "";
                Address location = new Address();
                if (exifInfo.getExifInfo().getLatitude() != null && exifInfo.getExifInfo().getLongitude() != null) {
                    MapOpenStreetMap mapOSM = new MapOpenStreetMap(config, address.getLocation(exifInfo.getExifInfo()));
                    if (timeLine.getOverrideTitle()) {
                        title = timeLine.getTitle();
                    }
                    else {
                        title = mapOSM.getAddress().getTitle();
                    }
                    if (timeLine.getOverrideLocation()) {
                        location.setTitle(timeLine.getTitle());
                        location.setDescription(timeLine.getDescription());
                        location.setLocation(timeLine.getLocation());
                        location.setCity(timeLine.getCity());
                        location.setProvince(timeLine.getProvince());
                        location.setCountry(timeLine.getCountry());
                        location.setCountrycode(timeLine.getCountryCode());
                    }
                    else {
                        location = mapOSM.getAddress();
                    }
                }
                else {
                    title = timeLine.getTitle();
                    location.setTitle(timeLine.getTitle());
                    location.setDescription(timeLine.getDescription());
                    location.setLocation(timeLine.getLocation());
                    location.setCity(timeLine.getCity());
                    location.setProvince(timeLine.getProvince());
                    location.setCountry(timeLine.getCountry());
                    location.setCountrycode(timeLine.getCountryCode());
                }
                if (divider == Divider.TIME) {
                    newFileName = String.format("%s-%s %s.%s",
                            exifInfo.getExifInfo().getCreationDateString(),
                            exifInfo.getExifInfo().getCreationTimeString(),
                            title,
                            Files.getFileExtension(file.getName())
                            );
                } else if (divider == Divider.COUNTER) {
                    newFileName = String.format("%s-%04d %s.%s",
                            exifInfo.getExifInfo().getCreationDateString(),
                            counter,
                            title,
                            Files.getFileExtension(file.getName())
                    );
                }
                if (exifInfo.getExifInfo().getLatitude() != null && exifInfo.getExifInfo().getLongitude() != null) {
                    fileList.add(ReadFile.builder()
                            .fileName(file.getName())
                            .newFileName(newFileName)
                            .filePath(this.path)
                            .resultsPath(this.path + "/" + config.getPathForResults())
                            .exifInfo(exifInfo.getExifInfo())
                            .timeLine(timeLine)
                            .fileType(config.getFileType(exifInfo.getExifInfo().getFileType()))
                            .location(address.getLocation(exifInfo.getExifInfo()))
                            .address(location)
                            .build()
                    );
                }
                else {
                    fileList.add(ReadFile.builder()
                            .fileName(file.getName())
                            .newFileName(newFileName)
                            .filePath(this.path)
                            .resultsPath(this.path + "/" + config.getPathForResults())
                            .exifInfo(exifInfo.getExifInfo())
                            .timeLine(timeLine)
                            .fileType(config.getFileType(exifInfo.getExifInfo().getFileType()))
                            .address(location)
                            .build()
                    );
                }
                counter++;
            }
        }
        return fileList;
    }
}
