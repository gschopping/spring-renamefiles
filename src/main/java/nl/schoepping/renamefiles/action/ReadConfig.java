package nl.schoepping.renamefiles.action;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.schoepping.renamefiles.domain.*;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;

@Log
public class ReadConfig {

    @Getter
    private Config config;
    public enum FileFormat {
        PHOTO,
        VIDEO,
        ALL
    }

    public ReadConfig(String fileName) {
        try {
            InputStream input = new FileInputStream("../config/" + fileName);
            Yaml yaml = new Yaml(new Constructor(Config.class, new LoaderOptions()));
            this.config = yaml.load(input);
        }
        catch (FileNotFoundException e) {
            log.log(Level.SEVERE, "Could not open file: " + fileName, e);
        }

    }

    public FileType getFileType(String fileType) {
        for (FileType filetype : this.config.getFileTypes()) {
            if (filetype.getFileType().equalsIgnoreCase(fileType)) {
                return filetype;
            }
        }
        return null;
    }

    public String getRegexMedia(ReadConfig.FileFormat fileFormat) {
        List<FileType> items = this.config.getFileTypes();
        if (fileFormat == ReadConfig.FileFormat.PHOTO) {
            // remove all but isPhotoFormat filetypes
            items.removeIf(item -> !item.getIsPhotoFormat());
        } else if (fileFormat == ReadConfig.FileFormat.VIDEO) {
            items.removeIf(FileType::getIsPhotoFormat);
        }

        StringBuilder result = new StringBuilder("^(");
        for (int i = 0; i < items.size(); i++ ) {
            result.append(".*\\.").append(items.get(i).getExtension());
            if (i < items.size()-1) {
                result.append("|");
            }
        }
        result.append(")$");
        return result.toString();
    }

    public ArrayList<String> getTags() {
        List<String> tags = new ArrayList<String>();
        String item = null;
        for (FileType fileType : this.config.getFileTypes()) {
            item = fileType.getDateTime();
            if (item != null && !item.isEmpty()) {
                tags.add(item);
            }
            item = fileType.getTimeZone();
            if (item != null && !item.isEmpty()) {
                tags.add(item);
            }
            item = fileType.getGpsLatitude();
            if (item != null && !item.isEmpty()) {
                tags.add(item);
            }
            item = fileType.getGpsLongitude();
            if (item != null && !item.isEmpty()) {
                tags.add(item);
            }
        }
        return new ArrayList<String>(new HashSet<>(tags));
    }


}
