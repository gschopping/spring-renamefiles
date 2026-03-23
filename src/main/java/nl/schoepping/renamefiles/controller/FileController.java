package nl.schoepping.renamefiles.controller;

import lombok.extern.java.Log;
import nl.schoepping.renamefiles.action.*;
import nl.schoepping.renamefiles.domain.ReadFile;
import nl.schoepping.renamefiles.action.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Collections;
import java.util.List;

@RestController
@Log
public class FileController {

    @GetMapping(path = "/files/root")
    public List<ReadFile> getFiles() {
        ReadConfig config = new ReadConfig("config.yml");
        ReadFiles readFiles = new ReadFiles("../files", config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
        readFiles.setFiles();
        return readFiles.getFiles();
    }

    @GetMapping(path = "renamefiles/root/{move}")
    public List<ReadFile> getRenameFiles(@PathVariable String move) {
        boolean removeOriginal = false;
        if (move.equals("move")) {
            removeOriginal = true;
        }
        ReadConfig config = new ReadConfig("config.yml");
        ReadFiles readFiles = new ReadFiles("../files", config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
        readFiles.setFiles();
        List<ReadFile> files = readFiles.getFiles();
        for (ReadFile file : files) {
            WriteExifInfo writeExifInfo = new WriteExifInfo(file, config.getConfigExif(), removeOriginal);
            writeExifInfo.writeExifInfoToFile();
        }
        return files;
    }

    @GetMapping(path = "/files/{subfolder}")
    public List<ReadFile> getFiles(@PathVariable String subfolder) {
        ReadConfig config = new ReadConfig("config.yml");
        if (subfolder.matches(config.getConfigPath().getPathForGps())) {
            // GPS files, has the same geolocation
            ReadFiles readFiles = new ReadFiles("../files/" + subfolder, config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
            readFiles.setFiles();
            readFiles.updateFiles();
            return readFiles.getFiles();
        } else if (subfolder.matches(config.getConfigPath().getPathForTimeLaps())) {
            // Timelapse folder only contain photos, and should be numbered.
            ReadFiles readFiles = new ReadFiles("../files/" + subfolder, config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.PHOTO), ReadFiles.Divider.COUNTER);
            return readFiles.getFiles();
        }
        else  return Collections.emptyList();
    }

    @GetMapping(path = "/renamefiles/{subfolder}/{move}")
    public List<ReadFile> getRenameFiles(@PathVariable String subfolder,  @PathVariable String move) {
        ReadConfig config = new ReadConfig("config.yml");
        ReadFiles readFiles;
        boolean removeOriginal = false;
        if (move.equals("move")) {
            removeOriginal = true;
        }
        if (subfolder.matches(config.getConfigPath().getPathForGps())) {
            // GPS files, has the same geolocation
            readFiles = new ReadFiles("../files/" + subfolder, config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
            readFiles.setFiles();
            readFiles.updateFiles();
        } else if (subfolder.matches(config.getConfigPath().getPathForTimeLaps())) {
            // Timelapse folder only contain photos, and should be numbered.
            readFiles = new ReadFiles("../files/" + subfolder, config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.PHOTO), ReadFiles.Divider.COUNTER);
            readFiles.setFiles();
        }
        else  {
            return Collections.emptyList();
        }
        List<ReadFile> files = readFiles.getFiles();
        for (ReadFile file : files) {
            WriteExifInfo writeExifInfo = new WriteExifInfo(file, config.getConfigExif(), removeOriginal);
            writeExifInfo.writeExifInfoToFile();
        }

        return files;
    }

    @GetMapping(path = "/file/{filename}")
    public WriteExifInfo getFile(@PathVariable String filename) {
        ReadConfig config = new ReadConfig("config.yml");
        ReadFiles readFiles = new ReadFiles("../files", config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
        File file = new File("../files/" + filename);
        ReadFile readFile = readFiles.getFile(file, config, new ReadTimeLine("timeline.yml"), new ReadAddress(), 1);
        if (readFile != null) {
            return new WriteExifInfo(readFile, config.getConfigExif(), true);
        }
        else {
            return null;
        }
    }

    @GetMapping(path = "/renamefiles/all/{move}")
    public List<ReadFile> getRenameFilesAll(@PathVariable String move) {
        ReadConfig config = new ReadConfig("config.yml");
        ReadTimeLine readTimeLine = new ReadTimeLine("timeline.yml");
        ReadFiles readFiles;
        List<ReadFile> files;
        List<ReadFile> totalFiles;
        File dir = new File("../files");
        File[] dirs = dir.listFiles();
        boolean removeOriginal = false;
        if (move.equals("move")) {
            removeOriginal = true;
        }

        // root folder
        readFiles = new ReadFiles("../files", config, readTimeLine, config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
        readFiles.setFiles();
        files = readFiles.getFiles();
        totalFiles = files;
        for (ReadFile file : files) {
            WriteExifInfo writeExifInfo = new WriteExifInfo(file, config.getConfigExif(), removeOriginal);
            writeExifInfo.writeExifInfoToFile();
        }

        // GPS folders
        for (File subdirectory : dirs) {
            if (subdirectory.isDirectory() && subdirectory.getName().matches(config.getConfigPath().getPathForGps())) {
                readFiles = new ReadFiles("../files/" + subdirectory.getName(), config, readTimeLine, config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
                readFiles.setFiles();
                readFiles.updateFiles();
                files = readFiles.getFiles();
                totalFiles.addAll(files);
                for (ReadFile file : files) {
                    WriteExifInfo writeExifInfo = new WriteExifInfo(file, config.getConfigExif(), removeOriginal);
                    writeExifInfo.writeExifInfoToFile();
                }
            }
        }

        // Timelaps folders
        for (File subdirectory : dirs) {
            if (subdirectory.isDirectory() && subdirectory.getName().matches(config.getConfigPath().getPathForTimeLaps())) {
                readFiles = new ReadFiles("../files/" + subdirectory.getName(), config, readTimeLine, config.getRegexMedia(ReadConfig.FileFormat.PHOTO), ReadFiles.Divider.COUNTER);
                readFiles.setFiles();
                files = readFiles.getFiles();
                totalFiles.addAll(files);
                for (ReadFile file : files) {
                    WriteExifInfo writeExifInfo = new WriteExifInfo(file, config.getConfigExif(), removeOriginal);
                    writeExifInfo.writeExifInfoToFile();
                }
            }
        }

        return totalFiles;
    }

}
