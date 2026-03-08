package nl.schoepping.spring_renamefiles.action;

import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.domain.ExifInfo;
import nl.schoepping.spring_renamefiles.domain.FileType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class ReadExifInfo {
    private String exiftool;
    private String filePath;
    private ReadConfig config;

    public ReadExifInfo(String filePath, ReadConfig config) {
        this.filePath = filePath;
        this.config = config;
        if (System.getProperty("os.name").contains("Windows")) {
            this.exiftool = "exiftool.bat";
        } else {
            this.exiftool = "exiftool";
        }
    }

    private String getSpaceReplacedFileName() {
        return replaceSpaces(this.filePath);
    }

    private String replaceSpaces(String fileName) {
        String result = fileName;
        if (System.getProperty("os.name").contains("Windows")) {
            result = "\"" +  fileName + "\"";
        }
        return result;
    }

    public ExifInfo getExifInfo() {
        ExifInfo result = new ExifInfo();
        Map<String, String> map = new HashMap<>();
        BufferedReader reader;

        // create list of options for exiftool to be executed
        ArrayList<String> items = new ArrayList<>();
        items.add(this.exiftool);
        items.add("-s1");
        items.add("-api");
        items.add("largefilesupport=1");
        items.add("-c");
        items.add("%.6f");
        items.add("-FileType");
        for (String tag : this.config.getTags()) {
            items.add("-" + tag);
        }
        items.add(getSpaceReplacedFileName());

        String[] cmdString = new String[items.size()];
        items.toArray(cmdString);
        try {
            Process process = Runtime.getRuntime().exec(cmdString);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                String regexEXIF = "^(\\S+)(\\s*): (.+)$";
                Pattern pattern = Pattern.compile(regexEXIF);
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    map.put(matcher.group(1),matcher.group(3));
                }
                line = reader.readLine();
            }
            reader.close();
        }
        catch  (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
        }

        // Read creationdate
        String dateString = null;
        String timeZone = "+00:00";
        String filetype = map.get("FileType");
//        ReadConfigYaml.FileType fileType = configYaml.getFileType(map.get("FileType"));
        FileType fileType = this.config.getFileType(filetype);
        dateString = map.get(fileType.getDateTime());
        boolean includeTimeZone = false;
        if (fileType.getTimeZone() != null) {
            if (fileType.getTimeZone().equalsIgnoreCase(fileType.getDateTime())) {
                includeTimeZone = true;
            }
            if (map.get(fileType.getTimeZone()) != null) {
                timeZone = map.get(fileType.getTimeZone());
            };
        }
        // if dateString is still empty then try another tag which includes the timezone
        if (dateString == null) {
            dateString = map.get("FileModifyDate");
            includeTimeZone = true;
        }

        if (dateString != null) {
            LocalDateTime date = null;
            if (includeTimeZone) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ssXXXXX");
                try {
                    date = LocalDateTime.parse(dateString, formatter);
                }
                catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                }
            }
            else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
                try {
                    date = LocalDateTime.parse(dateString, formatter);
                }
                catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                }

            }

            if (date != null) {
                String regexTimeZone = "([+-])(\\d{2}):(\\d{2})$";
                Pattern pattern = Pattern.compile(regexTimeZone);
                Matcher matcher = pattern.matcher(timeZone);
                int hours = 0;
                int minutes = 0;
                if (matcher.find()) {
                    String sign = matcher.group(1);
                    hours = Integer.parseInt(matcher.group(2));
                    if (sign.equals("-")) {
                        hours = -1 * hours;
                    }
                    minutes = Integer.parseInt(matcher.group(3));
                }
                date = date.plusHours(hours);
                result.setCreationDate(date.plusMinutes(minutes));
            }
        }

        // read GPS data

        String latitudeString = map.get(fileType.getGpsLatitude());
        String longitudeString = map.get(fileType.getGpsLongitude());
        if ((latitudeString != null) && (longitudeString != null)) {
            double latitude;
            double longitude;
            String regexGPS = "^([\\d.]+) ([NESW])$";
            Pattern pattern = Pattern.compile(regexGPS);
            Matcher matcher = pattern.matcher(latitudeString);
            if (matcher.matches()) {
                latitude = Double.parseDouble(matcher.group(1));
                if (matcher.group(2).equals("S")) {
                    latitude = latitude * -1;
                }
                result.setLatitude(latitude);
            }
            matcher = pattern.matcher(longitudeString);
            if (matcher.matches()) {
                longitude = Double.parseDouble(matcher.group(1));
                if (matcher.group(2).equals("S")) {
                    longitude = longitude * -1;
                }
                result.setLongitude(longitude);
            }
        }
        return result;

    }

}
