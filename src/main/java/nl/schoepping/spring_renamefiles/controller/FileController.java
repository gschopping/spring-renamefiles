package nl.schoepping.spring_renamefiles.controller;

import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.action.*;
import nl.schoepping.spring_renamefiles.domain.ReadFile;
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

    @GetMapping(path = "/files/{subfolder}")
    public List<ReadFile> getFiles(@PathVariable String subfolder) {
        ReadConfig config = new ReadConfig("config.yml");
        if (subfolder.matches(config.getPathForGPS())) {
            // GPS files, has the same geo location
            ReadFiles readFiles = new ReadFiles("../files/" + subfolder, config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
            readFiles.setFiles();
            readFiles.updateFiles();
            return readFiles.getFiles();
        } else if (subfolder.matches(config.getPathForTimelaps())) {
            // Timelaps folder only contain photos, and should be numbered.
            ReadFiles readFiles = new ReadFiles("../files/" + subfolder, config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.PHOTO), ReadFiles.Divider.COUNTER);
            return readFiles.getFiles();
        }
        else  return Collections.emptyList();
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

}
