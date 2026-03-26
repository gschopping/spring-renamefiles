package nl.schoepping.renamefiles.action;

import lombok.Getter;
import nl.schoepping.renamefiles.domain.*;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadConfigNew {

    @Getter
    private Config config;

    public ReadConfigNew(String fileName) throws FileNotFoundException {

        Yaml yaml = new Yaml();
        try(InputStream in = ClassLoader.getSystemResourceAsStream("../config/" + fileName)) {
            this.config = yaml.loadAs(in, Config.class);
        } catch (Exception ex) {
        }
    }

//    public FileType getFileType(String fileType) {
//        for (FileType filetype : this.getConfig().getFileTypes()) {
//            if (filetype.getFileType().equalsIgnoreCase(fileType)) {
//                return filetype;
//            }
//        }
//        return null;
//    }

//    public String getRegexMedia(ReadConfig.FileFormat fileFormat) {
//        List<FileType> items = this.getConfig().getFileTypes();
//        if (fileFormat == ReadConfig.FileFormat.PHOTO) {
//            // remove all but isPhotoFormat filetypes
//            items.removeIf(item -> !item.getIsPhotoFormat());
//        } else if (fileFormat == ReadConfig.FileFormat.VIDEO) {
//            items.removeIf(FileType::getIsPhotoFormat);
//        }
//
//        StringBuilder result = new StringBuilder("^(");
//        for (int i = 0; i < items.size(); i++ ) {
//            result.append(".*\\.").append(items.get(i).getExtension());
//            if (i < items.size()-1) {
//                result.append("|");
//            }
//        }
//        result.append(")$");
//        return result.toString();
//    }

}
