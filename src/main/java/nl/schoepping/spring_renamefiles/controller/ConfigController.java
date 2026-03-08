package nl.schoepping.spring_renamefiles.controller;


import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.action.ReadConfig;
import nl.schoepping.spring_renamefiles.domain.FileType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log
public class ConfigController {

    @GetMapping(path = "/config/{fileType}")
    public FileType config(@PathVariable("fileType") String fileType) {
        ReadConfig config = new ReadConfig();
        return config.getFileType(fileType);
    }

    @GetMapping(path = "/config/path")
    public List<String> configPath() {
        List<String> paths = new ArrayList<>();
        ReadConfig config = new ReadConfig();
        paths.add(config.getPathForTimelaps());
        paths.add(config.getPathForGPS());
        paths.add(config.getPathForResults());
        paths.add(config.getRegexMedia(true));
        paths.add(config.getRegexMedia(false));
        return paths;
    }
}
