package nl.schoepping.renamefiles.controller;


import lombok.extern.java.Log;
import nl.schoepping.renamefiles.action.ReadConfig;
import nl.schoepping.renamefiles.domain.ConfigExif;
import nl.schoepping.renamefiles.domain.ConfigOpenStreetMap;
import nl.schoepping.renamefiles.domain.ConfigPath;
import nl.schoepping.renamefiles.domain.FileType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class ConfigController {

    @GetMapping(path = "/config/{fileType}")
    public FileType config(@PathVariable("fileType") String fileType) {
        ReadConfig config = new ReadConfig("config.yml");
        return config.getFileType(fileType);
    }

    @GetMapping(path = "/config/path")
    public ConfigPath  configPath() {
        ReadConfig config = new ReadConfig("config.yml");
        return config.getConfigPath();
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
