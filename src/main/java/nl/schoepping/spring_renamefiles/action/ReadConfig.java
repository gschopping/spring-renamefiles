package nl.schoepping.spring_renamefiles.action;

import lombok.Getter;
import nl.schoepping.spring_renamefiles.domain.FileType;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadConfig {

    @Getter
    private String pathForTimelaps = "^(Timelaps\\d+)$";
    @Getter
    private String pathForGPS = "^(GPS\\d+)$";
    @Getter
    private String pathForResults = "results";
    @Getter
    private final ArrayList<FileType> fileTypes = new ArrayList<>();

    public ReadConfig() {
        String configFile = "./config/config.yml";
        int lineCount = 0;
        try {
            InputStream input = new FileInputStream(new File(configFile));
            Yaml yaml = new Yaml();
            Map config = yaml.load(input);
//            retrieve values for config
            if (config.get("config") != null) {
                Map configSection = (Map) config.get("config");
                if (configSection.get("pathTimelaps") != null) {
                    pathForTimelaps = (String) configSection.get("pathTimelaps");
                }
                if (configSection.get("pathGPS") != null) {
                    pathForGPS = (String) configSection.get("pathGPS");
                }
                if (configSection.get("pathResults") != null) {
                    pathForResults = (String) configSection.get("pathResults");
                }
            }

            if (config.get("fileTypes") != null) {
                ArrayList<Map> fileTypeArray = (ArrayList<Map>) config.get("fileTypes");
                for (Map fileTypeItem : fileTypeArray) {
                    lineCount++;
                    setFileType(fileTypeItem);
                }
            }
        } catch (Exception e) {
            String errorType = e.getClass().getName();
            String regexParser = "line (\\d+), column (\\d+):\n^(\\s*)(.+)$";
            if (errorType.equals("org.yaml.snakeyaml.parser.ParserException")) {
                Pattern pattern = Pattern.compile(regexParser, Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(e.getMessage());
                int line = 0;
                int column = 0;
                String sentence = "";
                if (matcher.find()) {
                    line = Integer.parseInt(matcher.group(1));
                    column = Integer.parseInt(matcher.group(2));
                    sentence = matcher.group(4);
                }
                throw new IllegalStateException(String.format("Error on line %d, column %d: %s", line, column, sentence));
            } else if (errorType.equals("java.io.FileNotFoundException")) {
                throw new IllegalStateException(String.format("%s not found", configFile));
            } else if (errorType.equals("java.text.ParseException")) {
                String regexDateParser = "^Unparseable date: \"(.+)\"$";
                Pattern pattern = Pattern.compile(regexDateParser);
                Matcher matcher = pattern.matcher(e.getMessage());
                String sentence = "";
                if (matcher.find()) {
                    sentence = matcher.group(1);
                }
                throw new IllegalStateException(String.format("Error in timeline %d, incorrect dateformat: %s", lineCount, sentence));
            } else if (errorType.equals("java.lang.Exception")) {
                throw new IllegalStateException(String.format("Error in timeline %d, %s", lineCount, e.getMessage()));
            } else if (errorType.contains("org.yaml.snakeyaml")) {
                Pattern pattern = Pattern.compile(regexParser, Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(e.getMessage());
                int line = 0;
                int column = 0;
                String sentence = "";
                if (matcher.find()) {
                    line = Integer.parseInt(matcher.group(1));
                    column = Integer.parseInt(matcher.group(2));
                    sentence = matcher.group(4);
                }
                throw new IllegalStateException(String.format("Error on line %d, column %d: undefined alias %s", line, column, sentence));
            } else {
                throw new IllegalStateException(String.format("Error in timeline %d: %s", lineCount, e.getMessage()));
            }
        }
    }

    private void setFileType(Map item) {
        FileType fileType = new FileType();
        String value;
        Boolean boolValue;
        if (item.get("fileType") != null) {
            value = (String) item.get("fileType");
            fileType.setFileType(value);
        }
        if (item.get("extension") != null) {
            value = (String) item.get("extension");
            fileType.setExtension(value);
        }
        if (item.get("datetime") != null) {
            value = (String) item.get("datetime");
            fileType.setDateTime(value);
        }
        if (item.get("timezone") != null) {
            value = (String) item.get("timezone");
            fileType.setTimeZone(value);
        }
        if (item.get("gpslatitude") != null) {
            value = (String) item.get("gpslatitude");
            fileType.setGpsLatitude(value);
        }
        if (item.get("gpslongitude") != null) {
            value = (String) item.get("gpslongitude");
            fileType.setGpsLongitude(value);
        }
        if (item.get("isWritable") != null) {
            boolValue = (Boolean) item.get("isWritable");
            fileType.setIsWritable(boolValue);
        }
        if (item.get("isPhotoFormat") != null) {
            boolValue = (Boolean) item.get("isPhotoFormat");
            fileType.setIsPhotoFormat(boolValue);
        }

        addFileType(fileType);
    }

    private void addFileType (FileType fileType) {
        for (FileType element : this.fileTypes) {
            if (fileType.getFileType().equals(element.getFileType())) {
                throw new IllegalStateException(String.format("filetype: %s %s already exists", fileType.getFileType(), fileType.getFileType()));
            }
        }
        this.fileTypes.add(fileType);
    }

    public FileType getFileType(String fileType) {
        for (FileType filetype : this.getFileTypes()) {
            if (filetype.getFileType().equalsIgnoreCase(fileType)) {
                return filetype;
            }
        }
        return null;
    }

    public String getRegexMedia(Boolean All) {
        ArrayList<FileType> items = (ArrayList<FileType>) this.getFileTypes().clone();
        if (!All) {
            // remove all but isPhotoFormat filetypes
            items.removeIf(item -> !item.getIsPhotoFormat());
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
        for (FileType fileType : getFileTypes()) {
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
