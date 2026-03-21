package nl.schoepping.spring_renamefiles.controller;


import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.action.ReadConfig;
import nl.schoepping.spring_renamefiles.domain.ConfigExif;
import nl.schoepping.spring_renamefiles.domain.ConfigOpenStreetMap;
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
        ReadConfig config = new ReadConfig("config.yml");
        return config.getFileType(fileType);
    }

    @GetMapping(path = "/config/path")
    public List<String> configPath() {
        List<String> paths = new ArrayList<>();
        ReadConfig config = new ReadConfig("config.yml");
        paths.add(config.getPathForTimelaps());
        paths.add(config.getPathForGPS());
        paths.add(config.getPathForResults());
        paths.add(config.getRegexMedia(ReadConfig.FileFormat.ALL));
        paths.add(config.getRegexMedia(ReadConfig.FileFormat.PHOTO));
        paths.add(config.getRegexMedia(ReadConfig.FileFormat.VIDEO));
        return paths;
    }

    @GetMapping(path = "config/exif")
    public ConfigExif configExif() {
        ReadConfig config = new ReadConfig("config.yml");
        return config.getConfigExif();
    }

    @GetMapping(path = "config/osm")
    public ConfigOpenStreetMap configOSM() {
        ReadConfig config = new ReadConfig("config.yml");
        return config.getConfigOSM();
    }
}
