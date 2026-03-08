package nl.schoepping.spring_renamefiles.controller;

import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.action.ReadConfig;
import nl.schoepping.spring_renamefiles.action.ReadFiles;
import nl.schoepping.spring_renamefiles.domain.ReadFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log
public class FileController {

    @GetMapping(path = "/files/root")
    public List<ReadFile> getFiles() {
        ReadConfig config = new ReadConfig();
        ReadFiles readFiles = new ReadFiles("../Mediafiles", config.getRegexMedia(true));
        return readFiles.getFiles();
    }

}
