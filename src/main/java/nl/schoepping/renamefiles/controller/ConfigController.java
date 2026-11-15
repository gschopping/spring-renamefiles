package nl.schoepping.renamefiles.controller;


import lombok.extern.java.Log;
import nl.schoepping.renamefiles.action.ReadConfig;
import nl.schoepping.renamefiles.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;

@RestController
@Log
public class ConfigController {

    @GetMapping(path = "/config/{fileType}")
    public ResponseEntity<FileType> config(@PathVariable("fileType") String fileType) {
        try {
            log.log(Level.INFO, "Read config fileType for: "  + fileType);
            ReadConfig config = new ReadConfig("config.yml");
            FileType type = config.getFileType(fileType);
            if (type != null) {
                return new ResponseEntity<>(type, HttpStatus.OK);
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

    @GetMapping(path = "/config/path")
    public ResponseEntity<ConfigPath>  configPath() {
        try {
            log.log(Level.INFO, "Read config path");
            ReadConfig config = new ReadConfig("config.yml");
            return new ResponseEntity<>(config.getConfig().getPath(), HttpStatus.OK);
        }
        catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/config/exif")
    public ResponseEntity<ConfigExif> configExif() {
        try {
            log.log(Level.INFO, "Read config exif");
            ReadConfig config = new ReadConfig("config.yml");
            return new ResponseEntity<>(config.getConfig().getExif(), HttpStatus.OK);
        }
        catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/config/osm")
    public ResponseEntity<ConfigOpenStreetMap> configOSM() {
        try {
            log.log(Level.INFO, "Read config openStreetMaps");
            ReadConfig config = new ReadConfig("config.yml");
            return new ResponseEntity<>(config.getConfig().getOpenStreetMap(), HttpStatus.OK);
        }
        catch  (Exception e) {
            log.log(Level.WARNING, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
