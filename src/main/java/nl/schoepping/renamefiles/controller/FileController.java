package nl.schoepping.renamefiles.controller;

import lombok.extern.java.Log;
import nl.schoepping.renamefiles.action.*;
import nl.schoepping.renamefiles.domain.ReadFile;
import nl.schoepping.renamefiles.action.*;
import nl.schoepping.renamefiles.domain.RenameFiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@RestController
@Log
public class FileController {

    @GetMapping(path = "/files/root")
    public ResponseEntity<List<ReadFile>> getFiles() {
        try {
            ReadConfig config = new ReadConfig("config.yml");
            ReadFiles readFiles = new ReadFiles("../files", config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
            readFiles.setFiles();
            return new ResponseEntity<>(readFiles.getFiles(),  HttpStatus.OK);
        }
        catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/files/{subfolder}")
    public ResponseEntity<List<ReadFile>> getFiles(@PathVariable String subfolder) {
        try {
            ReadConfig config = new ReadConfig("config.yml");
            if (subfolder.matches(config.getConfigPath().getPathForGps())) {
                // GPS files, has the same geolocation
                ReadFiles readFiles = new ReadFiles("../files/" + subfolder, config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
                readFiles.setFiles();
                readFiles.updateFiles();
                return new ResponseEntity<>(readFiles.getFiles(), HttpStatus.OK);
            } else if (subfolder.matches(config.getConfigPath().getPathForTimeLaps())) {
                // Timelapse folder only contain photos, and should be numbered.
                ReadFiles readFiles = new ReadFiles("../files/" + subfolder, config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.PHOTO), ReadFiles.Divider.COUNTER);
                return new ResponseEntity<>(readFiles.getFiles(), HttpStatus.OK);
            }
            else  return new ResponseEntity<>(Collections.emptyList(),  HttpStatus.NO_CONTENT);
        }
        catch  (Exception e) {
            log.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/file/{filename}")
    public ResponseEntity<WriteExifInfo> getFile(@PathVariable String filename) {
        try {
            ReadConfig config = new ReadConfig("config.yml");
            ReadFiles readFiles = new ReadFiles("../files", config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
            File file = new File("../files/" + filename);
            ReadFile readFile = readFiles.getFile(file, config, new ReadTimeLine("timeline.yml"), new ReadAddress(), 1);
            if (readFile != null) {
                return new ResponseEntity<>(new WriteExifInfo(readFile, config.getConfigExif(), true), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "renamefiles")
    public ResponseEntity<List<ReadFile>> getRenameFiles(@RequestBody RenameFiles renameFiles) {
        try {
            // initialize
            ReadConfig config = new ReadConfig("config.yml");
            ReadTimeLine readTimeLine = new ReadTimeLine("timeline.yml");
            ReadFiles readFiles;
            List<ReadFile> files;
            List<ReadFile> totalFiles = new ArrayList<>();
            File dir = new File("../files");
            File[] dirs = dir.listFiles();
            boolean removeOriginal = false;
            if (renameFiles.getActionAction().equals(RenameFiles.Action.MOVE)) {
                removeOriginal = true;
            }
            renameFiles.setConfig(config.getConfigPath());

            if (readTimeLine.getEnabled()) {
                if (renameFiles.isRoot()) {
                    // root folder
                    readFiles = new ReadFiles("../files", config, readTimeLine, config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
                    readFiles.setFiles();
                    files = readFiles.getFiles();
                    totalFiles = files;
                    for (ReadFile file : files) {
                        WriteExifInfo writeExifInfo = new WriteExifInfo(file, config.getConfigExif(), removeOriginal);
                        writeExifInfo.writeExifInfoToFile();
                    }
                }

                if (dirs != null) {
                    for (File subdirectory : dirs) {
                        // GPS folders
                        if (subdirectory.isDirectory() && renameFiles.isGPS(subdirectory.getName())) {
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

                        // TimeLaps folders
                        if (subdirectory.isDirectory() && renameFiles.isTimeLaps(subdirectory.getName())) {
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
                }

            }

            return new ResponseEntity<>(totalFiles, HttpStatus.OK);
        }
        catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
