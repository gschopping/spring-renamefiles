package nl.schoepping.renamefiles.action;

import com.google.common.io.Files;
import lombok.Getter;
import lombok.extern.java.Log;
import nl.schoepping.renamefiles.domain.Address;
import nl.schoepping.renamefiles.domain.ReadFile;
import nl.schoepping.renamefiles.domain.TimeLine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
public class ReadFiles {
    private final String path;
    private final String regexMedia;
    public enum Divider {
        COUNTER,
        TIME
    }
    private final Divider divider;
    private final ReadConfig config;
    private final ReadTimeLine timeLines;
    @Getter
    private List<ReadFile> files = new ArrayList<>();

    public ReadFiles(String path, ReadConfig config, ReadTimeLine timeLines,  String regexMedia, Divider divider) {
        this.path = path;
        this.regexMedia = regexMedia;
        this.divider = divider;
        this.config = config;
        this.timeLines = timeLines;
    }

    public ReadFile getFile(File file, ReadConfig config, ReadTimeLine timeLines, ReadAddress address, int counter) {
        log.info("Reading file " + file.getName());
        ReadExifInfo exifInfo = new ReadExifInfo(file.getPath(), config);
        TimeLine timeLine = timeLines.getTimeLine(exifInfo.getExifInfo().getCreationDate());
        String newFileName = "";
        String title;
        Address location = new Address();
        if (exifInfo.getExifInfo().getLatitude() != null && exifInfo.getExifInfo().getLongitude() != null) {
            MapOpenStreetMap mapOSM = new MapOpenStreetMap(config, address.getLocation(exifInfo.getExifInfo()));
            if (timeLine.getOverrideTitle()) {
                title = timeLine.getTitle();
            } else {
                title = mapOSM.getAddress().getTitle();
            }
            if (timeLine.getOverrideLocation()) {
                location.setTitle(timeLine.getTitle());
                location.setDescription(timeLine.getDescription());
                location.setLocation(timeLine.getLocation());
                location.setCity(timeLine.getCity());
                location.setProvince(timeLine.getProvince());
                location.setCountry(timeLine.getCountry());
                location.setCountryCode(timeLine.getCountryCode());
            } else {
                location = mapOSM.getAddress();
            }
        } else {
            title = timeLine.getTitle();
            location.setTitle(timeLine.getTitle());
            location.setDescription(timeLine.getDescription());
            location.setLocation(timeLine.getLocation());
            location.setCity(timeLine.getCity());
            location.setProvince(timeLine.getProvince());
            location.setCountry(timeLine.getCountry());
            location.setCountryCode(timeLine.getCountryCode());
        }
        if (divider == Divider.TIME) {
            newFileName = String.format("%s-%s%s %s.%s",
                    exifInfo.getExifInfo().getCreationDateString(),
                    exifInfo.getExifInfo().getCreationTimeString(),
                    "%s",
                    title,
                    Files.getFileExtension(file.getName())
            );
        } else if (divider == Divider.COUNTER) {
            newFileName = String.format("%s-%04d%s %s.%s",
                    exifInfo.getExifInfo().getCreationDateString(),
                    counter,
                    "%s",
                    title,
                    Files.getFileExtension(file.getName())
            );
        }
        if (exifInfo.getExifInfo().getLatitude() != null && exifInfo.getExifInfo().getLongitude() != null) {
            return ReadFile.builder()
                    .fileName(file.getName())
                    .newFileName(newFileName)
                    .filePath(this.path)
                    .resultsPath(this.path + "/" + config.getConfigPath().getPathForResults())
                    .exifInfo(exifInfo.getExifInfo())
                    .timeLine(timeLine)
                    .fileType(config.getFileType(exifInfo.getExifInfo().getFileType()))
                    .location(address.getLocation(exifInfo.getExifInfo()))
                    .address(location)
                    .build();
        } else {
            return ReadFile.builder()
                    .fileName(file.getName())
                    .newFileName(newFileName)
                    .filePath(this.path)
                    .resultsPath(this.path + "/" + config.getConfigPath().getPathForResults())
                    .exifInfo(exifInfo.getExifInfo())
                    .timeLine(timeLine)
                    .fileType(config.getFileType(exifInfo.getExifInfo().getFileType()))
                    .address(location)
                    .build();
        }
    }

    public void  setFiles() {
        try {
            File dir = new File(this.path);
            File[] files = dir.listFiles();
            ReadAddress address = new ReadAddress();
            int counter = 1;
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toUpperCase().matches(this.regexMedia)) {
                        try {
                            this.files.add(getFile(file, config, timeLines, address, counter));
                            counter++;
                        }
                        catch (Exception e) {
                            log.log(Level.SEVERE, "Error reading file " + file.getName() +  " : " + e.getMessage(), e);
                        }
                    }
                }
            }
        }
        catch  (Exception e) {
            log.log(Level.SEVERE, "Error reading files: " + e.getMessage(), e);
        }
    }

    private ReadFile getNearestGPSFile(ReadFile readFile) {
        int index = this.files.indexOf(readFile);
        index++;
        boolean found = false;
        while (index < this.files.size() - 1 && !found) {
            if (this.files.get(index).getExifInfo().getLongitude() != null) {
                found = true;
            }
            else {
                index++;
            }
        }
        if (found) {
            return this.files.get(index);
        }
        else {
            return null;
        }
    }

    private void updateFileWithGpsInfo(ReadFile currentFile, ReadFile nearestFile) {
        if (nearestFile != null) {
            currentFile.setLocation(nearestFile.getLocation());
            currentFile.setAddress(nearestFile.getAddress());
            currentFile.getExifInfo().setLongitude(nearestFile.getExifInfo().getLongitude());
            currentFile.getExifInfo().setLatitude(nearestFile.getExifInfo().getLatitude());
            String title = nearestFile.getAddress().getTitle();
            String newFileName = String.format("%s-%s %s.%s",
                    currentFile.getExifInfo().getCreationDateString(),
                    currentFile.getExifInfo().getCreationTimeString(),
                    title,
                    Files.getFileExtension(currentFile.getFileName())
            );
            currentFile.setNewFileName(newFileName);
        }
    }

    public void updateFiles() {
        for (ReadFile readFile : this.files) {
            if (readFile.getExifInfo().getLongitude() == null) {
                updateFileWithGpsInfo(readFile, getNearestGPSFile(readFile));
            }
        }
    }

}
