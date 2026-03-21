package nl.schoepping.spring_renamefiles.action;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.domain.ConfigExif;
import nl.schoepping.spring_renamefiles.domain.ReadFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class WriteExifInfo {

    private final String exiftool;
    private final String tempFile;
    @Getter
    private final List<String> arguments = new ArrayList<>();
    private final ReadFile readFile;
    private final ConfigExif config;
    private final boolean removeOriginal;

    public WriteExifInfo(ReadFile readFile, ConfigExif config, boolean removeOriginal) {
        this.readFile = readFile;
        this.config = config;
        this.removeOriginal = removeOriginal;
        this.exiftool = "exiftool";
        this.tempFile = System.getProperty("user.dir") + "/arguments.txt";
        if (removeOriginal) {
            this.arguments.add("-overwrite_original");
        }
        setAuthor(readFile.getTimeLine().getAuthor());
        setCopyright(readFile.getTimeLine().getCopyRight());
        setUrl(readFile.getTimeLine().getWebsite());
        setComment(readFile.getTimeLine().getComment());
        if (readFile.getTimeLine().getKeys() != null) {
            setKeys1(readFile.getTimeLine().getKeys().split(","));
            setKeys2(readFile.getTimeLine().getKeys().split(","));
        }
        setInstructions(readFile.getTimeLine().getInstructions());
        setCountryCode2(readFile.getAddress().getCountryCode());
        setCountryCode3(readFile.getAddress().getCountryCode());
        setCountry(readFile.getAddress().getCountry());
        setProvince(readFile.getAddress().getProvince());
        setCity(readFile.getAddress().getCity());
        setLocation(readFile.getAddress().getLocation());
        setTitle(readFile.getAddress().getTitle());
        setDescription(readFile.getAddress().getDescription());
    }

    public void writeExifInfoToFile() {
        try {
            writeArguments();
            boolean noError = false;
            char postfix = ' ';
            String newFileName;
            while (! noError) {
                if (postfix == ' ') {
                    newFileName = String.format(readFile.getNewFileName(), "");
                } else {
                    newFileName = String.format(readFile.getNewFileName(), postfix);
                }
                try {
                    writeFile(readFile.getResultsPath() + "/" + newFileName, false);
                    noError = true;
                    log.info("Rename file " + readFile.getFileName() + " to " + newFileName);
                } catch (Exception e) {
                    if (e.getMessage().matches("^(.* already exists)$")) {
                        if (postfix == ' ') {
                            postfix = 'a';
                        } else {
                            postfix += 1;
                        }
                    } else {
                        log.log(Level.SEVERE, "Error while writing exif to " + readFile.getNewFileName(), e);
                        noError = true;
                    }
                }
            }
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "Error while writing arguments to " + tempFile, e);
        }
    }

    private void setTag(String[] tags, String value) {
        for (String tag : tags) {
            this.arguments.add(String.format("-%s=%s", tag, value));
        }
    }

    private void setKeys(String[] tags, String[] values) {
        for (String value : values) {
            for (String tag : tags) {
                this.arguments.add(String.format("-%s=%s", tag, value));
            }
        }
    }

    private void setAuthor(String author) {
        if (author != null) {
            setTag(this.config.getAuthor(), author);
        }
    }

    private void setCopyright(String copyright) {
        if (copyright != null) {
            setTag(this.config.getCopyright(), copyright);
        }
    }

    private void setComment(String comment) {
        if (comment != null) {
            setTag(this.config.getComment(), comment);
        }
    }

    private void setCountryCode2(String countryCode) {
        if (countryCode != null) {
            setTag(this.config.getCountryCode2(), countryCode.toUpperCase());
        }
    }

    private void setCountryCode3(String countryCode) {
        if (countryCode != null) {
            Locale locale = new Locale.Builder().setRegion(countryCode).build();
            setTag(this.config.getCountryCode3(), locale.getISO3Country());
        }
    }

    private void setCountry(String country) {
        if (country != null) {
            setTag(this.config.getCountry(), country);
        }
    }

    private void setProvince(String province) {
        if (province != null) {
            setTag(this.config.getProvince(), province);
        }
    }

    private void setCity(String city) {
        if (city != null) {
            setTag(this.config.getCity(), city);
        }
    }

    private void setLocation(String location) {
        if (location != null) {
            setTag(this.config.getLocation(), location);
        }
    }

    private void setTitle(String title) {
        if (title != null) {
            setTag(this.config.getTitle(), title);
        }
    }

    private void setUrl(String url) {
        if (url != null) {
            setTag(this.config.getUrl(), url);
        }
    }

    private void setDescription(String description) {
        if (description != null) {
            setTag(this.config.getDescription(), description);
        }
    }

    private void setKeys1(String[] keys) {
        if (keys.length > 0) {
            setKeys(this.config.getKeys1(), keys);
        }
    }

    private void setKeys2(String[] keys) {
        if (keys.length > 0) {
            setTag(config.getKeys2(), String.join(", ", keys));
        }
    }

    private void setInstructions(String instructions) {
        if (instructions != null) {
            setTag(this.config.getInstructions(), instructions);
        }
    }

    public String getTag(String tag) {
        boolean found = false;
        int index = 0;
        String value = null;
        Pattern pattern = Pattern.compile("-" + tag + "=(.*)");
        Matcher matcher;
        while (!found && index < this.arguments.size()) {
            matcher = pattern.matcher(this.arguments.get(index));
            if (matcher.find()) {
                value = matcher.group(1);
                found = true;
            }
            index++;
        }
        return value;
    }

    private void writeArguments() throws IOException {
        FileWriter fileWriter = new FileWriter(tempFile);
        for (String argument : this.arguments) {
            fileWriter.write(argument + "\n");
        }
        fileWriter.close();
    }

    private void writeFile(String newFileName, boolean overWrite) throws IOException {
        File destinationFile = new File(readFile.getResultsPath() + "/" + newFileName);
        if (overWrite) {
            if (destinationFile.exists()) {
                if (! destinationFile.delete()) throw new IOException(String.format("%s can't be deleted", destinationFile.getPath()));
            }
        }
        else {
            if (destinationFile.exists()) throw new IOException(String.format("%s already exists", destinationFile.getPath()));
        }

        if (readFile.getFileType().getIsWritable()) {
            writeArguments();
            String[] cmdString = new String[] { exiftool, "-m", "-charset", "FileName=UTF8", "-api", "largefilesupport=1", "-@", tempFile, readFile.getFilePath() + "/" + readFile.getFileName(), "-o", readFile.getResultsPath() +"/" + newFileName };
            log.config("Executing " + String.join(" ", cmdString));
            Process process = Runtime.getRuntime().exec(cmdString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // read result lines, if any contains files is updated then continue
            boolean fileIsUpdated = false;
            String result = reader.readLine();
            while (result != null) {
                log.config("writeFile: " + result);
                if (result.matches("\\s*1 image files (created|updated)")) {
                    fileIsUpdated = true;
                    break;
                }
                result = reader.readLine();
            }
            if (! fileIsUpdated) {
                throw new IOException("Update of file " + newFileName + " failed");
            }
            reader.close();
        }
        else {
            if (this.removeOriginal) {
//                FileUtils.moveFile(new File(this.mediaFile), destinationFile);
                Files.move(Paths.get(readFile.getFilePath() + "/" + readFile.getFileName()), destinationFile.toPath());
            }
            else {
                Files.copy(Paths.get(readFile.getFilePath() + "/" + readFile.getFileName()), destinationFile.toPath());
            }

        }
    }
}
