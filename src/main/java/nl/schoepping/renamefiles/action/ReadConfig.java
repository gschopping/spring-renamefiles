package nl.schoepping.renamefiles.action;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.schoepping.renamefiles.domain.ConfigExif;
import nl.schoepping.renamefiles.domain.ConfigOpenStreetMap;
import nl.schoepping.renamefiles.domain.ConfigPath;
import nl.schoepping.renamefiles.domain.FileType;
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

@Log
public class ReadConfig {

    @Getter
    private ConfigPath configPath;
    @Getter
    private ConfigExif configExif;
    @Getter
    private ConfigOpenStreetMap configOSM;
    @Getter
    private final ArrayList<FileType> fileTypes;
    public enum FileFormat {
        PHOTO,
        VIDEO,
        ALL
    }

    public ReadConfig(String fileName) {
        String configFile = "../config/" + fileName;
        configPath = new ConfigPath();
        configExif = new ConfigExif();
        configOSM = new ConfigOpenStreetMap();
        fileTypes = new ArrayList<>();
        int lineCount = 0;
        try {
            InputStream input = new FileInputStream(new File(configFile));
            Yaml yaml = new Yaml();
            Map config = yaml.load(input);
//            retrieve values for config
            if (config.get("config") != null) {
                Map configSection = (Map) config.get("config");
                if (configSection.get("pathTimelaps") != null) {
                    configPath.setPathForTimeLaps((String) configSection.get("pathTimelaps"));
                }
                if (configSection.get("pathGPS") != null) {
                    configPath.setPathForGps((String) configSection.get("pathGPS"));
                }
                if (configSection.get("pathResults") != null) {
                    configPath.setPathForResults((String) configSection.get("pathResults"));
                }
                if (configSection.get("pathDNG") != null) {
                    configPath.setPathForDng((String) configSection.get("pathDNG"));
                }
            }

            if (config.get("exif") != null) {
                Map exifSection = (Map) config.get("exif");
                if (exifSection.get("author") != null) {
                    String author = (String) exifSection.get("author");
                    configExif.setAuthor(author.split(" "));
                }
                if (exifSection.get("copyright") != null) {
                    String copyright = (String) exifSection.get("copyright");
                    configExif.setCopyright(copyright.split(" "));
                }
                if (exifSection.get("comment") != null) {
                    String comment = (String) exifSection.get("comment");
                    configExif.setComment(comment.split(" "));
                }
                if (exifSection.get("countryCode2") != null) {
                    String countryCode2 = (String) exifSection.get("countryCode2");
                    configExif.setCountryCode2(countryCode2.split(" "));
                }
                if (exifSection.get("countryCode3") != null) {
                    String countryCode3 = (String) exifSection.get("countryCode3");
                    configExif.setCountryCode3(countryCode3.split(" "));
                }
                if (exifSection.get("country") != null) {
                    String country = (String) exifSection.get("country");
                    configExif.setCountry(country.split(" "));
                }
                if (exifSection.get("province") != null) {
                    String province = (String) exifSection.get("province");
                    configExif.setProvince(province.split(" "));
                }
                if (exifSection.get("city") != null) {
                    String city = (String) exifSection.get("city");
                    configExif.setCity(city.split( " "));
                }
                if (exifSection.get("location") != null) {
                    String location = (String) exifSection.get("location");
                    configExif.setLocation(location.split(" "));
                }
                if (exifSection.get("title") != null) {
                    String title = (String) exifSection.get("title");
                    configExif.setTitle(title.split(" "));
                }
                if (exifSection.get("url") != null) {
                    String url = (String) exifSection.get("url");
                    configExif.setUrl(url.split(" "));
                }
                if (exifSection.get("description") != null) {
                    String description = (String) exifSection.get("description");
                    configExif.setDescription(description.split(" "));
                }
                if (exifSection.get("keys1") != null) {
                    String keys1 = (String) exifSection.get("keys1");
                    configExif.setKeys1(keys1.split(" "));
                }
                if (exifSection.get("keys2") != null) {
                    String keys2 = (String) exifSection.get("keys2");
                    configExif.setKeys2(keys2.split(" "));
                }
                if (exifSection.get("instructions") != null) {
                    String instructions = (String) exifSection.get("instructions");
                    configExif.setInstructions(instructions.split(" "));
                }
            }

            if (config.get("openStreetMap") != null) {
                Map exifSection = (Map) config.get("openStreetMap");
                if (exifSection.get("title") != null) {
                    String title = (String) exifSection.get("title");
                    configOSM.setTitle(title.split(" "));
                }
                if (exifSection.get("location") != null) {
                    String location = (String) exifSection.get("location");
                    configOSM.setLocation(location.split(" "));
                }
                if (exifSection.get("city") != null) {
                    String city = (String) exifSection.get("city");
                    configOSM.setCity(city.split(" "));
                }
                if (exifSection.get("province") != null) {
                    String province = (String) exifSection.get("province");
                    configOSM.setProvince(province.split(" "));
                }
                if (exifSection.get("country") != null) {
                    String country = (String) exifSection.get("country");
                    configOSM.setCountry(country.split(" "));
                }
                if (exifSection.get("countryCode") != null) {
                    String countryCode = (String) exifSection.get("countryCode");
                    configOSM.setCountryCode(countryCode.split(" "));
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
                throw new IllegalStateException(String.format("Error in config %d, incorrect dateformat: %s", lineCount, sentence));
            } else if (errorType.equals("java.lang.Exception")) {
                throw new IllegalStateException(String.format("Error in config %d, %s", lineCount, e.getMessage()));
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
                throw new IllegalStateException(String.format("Error in config %d: %s", lineCount, e.getMessage()));
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
                throw new IllegalStateException(String.format("filetype %s already exists", fileType.getFileType()));
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

    public String getRegexMedia(FileFormat fileFormat) {
        ArrayList<FileType> items = this.getFileTypes();
        if (fileFormat == FileFormat.PHOTO) {
            // remove all but isPhotoFormat filetypes
            items.removeIf(item -> !item.getIsPhotoFormat());
        } else if (fileFormat == FileFormat.VIDEO) {
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
