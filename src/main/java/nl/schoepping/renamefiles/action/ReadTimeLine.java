package nl.schoepping.renamefiles.action;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.schoepping.renamefiles.domain.Config;
import nl.schoepping.renamefiles.domain.TimeLine;
import nl.schoepping.renamefiles.domain.TimeLines;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class ReadTimeLine {

    @Getter
    private TimeLines timeLines;

    static class SortByDate implements Comparator<TimeLine> {
        @Override
        public int compare(TimeLine o1, TimeLine o2) {
            return o1.getStartDate().compareTo(o2.getStartDate());
        }
    }

    public ReadTimeLine(String fileName) {
        this.timeLines = new TimeLines();
        String timeLineFile = "../config/" +  fileName;
        int lineCount = 0;
        try {
            InputStream input = new FileInputStream(timeLineFile);
            LoaderOptions options = new LoaderOptions();
            int maxNodes = 1000;
            try {
                if (System.getenv("MAX_NODES") != null) {
                    maxNodes = Integer.parseInt(System.getenv("MAX_NODES"));
                }
            }
            catch (Exception e) {
                log.log(Level.WARNING, "Could not parse max nodes. Using default value " + maxNodes);
            }
            options.setMaxAliasesForCollections(maxNodes);
            Yaml yaml = new Yaml(options);
            Map timeLine = yaml.load(input);
//            retrieve values for config
            if (timeLine.get("enabled") != null) {
                this.timeLines.setEnabled((Boolean) timeLine.get("enabled"));
            }

            if (timeLine.get("timeLines") != null) {
                ArrayList<Map> timelineArray = (ArrayList<Map>) timeLine.get("timeLines");
                for (Map timelineItem : timelineArray) {
                    lineCount++;
                    setTimeLine(timelineItem);
                }
            }
            log.info("Reading " + lineCount + " timelines");
        }
        catch (Exception e) {
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
                log.log(Level.SEVERE, String.format("Error on line %d, column %d: %s", line, column, sentence));
                throw new IllegalStateException(String.format("Error on line %d, column %d: %s", line, column, sentence));
            }
            else if (errorType.equals("java.io.FileNotFoundException")) {
                log.log(Level.SEVERE, String.format("%s not found",timeLineFile));
                throw new IllegalStateException(String.format("%s not found",timeLineFile));
            }
            else if (errorType.equals("java.text.ParseException")) {
                String regexDateParser = "^Unparseable date: \"(.+)\"$";
                Pattern pattern = Pattern.compile(regexDateParser);
                Matcher matcher = pattern.matcher(e.getMessage());
                String sentence = "";
                if (matcher.find()) {
                    sentence = matcher.group(1);
                }
                log.log(Level.SEVERE, String.format("Error in timeLine %d, incorrect dateFormat: %s", lineCount, sentence));
                throw new IllegalStateException(String.format("Error in timeLine %d, incorrect dateFormat: %s", lineCount, sentence));
            }
            else if (errorType.equals("java.lang.Exception")) {
                log.log(Level.SEVERE, String.format("Error in timeLine %d, %s", lineCount, e.getMessage()));
                throw new IllegalStateException(String.format("Error in timeLine %d, %s", lineCount, e.getMessage()));
            }
            else if (errorType.contains("org.yaml.snakeyaml")) {
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
                log.log(Level.SEVERE, String.format("Error on line %d, column %d: undefined alias %s", line, column, sentence));
                throw new IllegalStateException(String.format("Error on line %d, column %d: undefined alias %s", line, column, sentence));
            }
            else {
                log.log(Level.SEVERE, String.format("Error in timeline %d, %s", lineCount, e.getMessage()));
                throw new IllegalStateException(String.format("Error in timeline %d, %s", lineCount, e.getMessage()));
            }
        }
    }

    private void setTimeLine(Map item)  {
        String value;
        Boolean boolValue;
        TimeLine timeline = new TimeLine();
        if (item.get("startDate") != null) {
            value = (String) item.get("startDate");
            timeline.setStartDate(value);
        }
        if (timeline.getStartDate() == null) {
            throw new IllegalStateException("startDate is not filled");
        }
        if (item.get("countryCode") != null) {
            value = (String) item.get("countryCode");
            timeline.setCountryCode(value);
        }
        if (item.get("province") != null) {
            value = (String) item.get("province");
            timeline.setProvince(value);
        }
        if (item.get("city") != null) {
            value = (String) item.get("city");
            timeline.setCity(value);
        }
        if (item.get("author") != null) {
            value = (String) item.get("author");
            timeline.setAuthor(value);
        }
        if (item.get("website") != null) {
            value = (String) item.get("website");
            timeline.setWebsite(value);
        }
        if (item.get("copyRight") != null) {
            value = (String) item.get("copyRight");
            timeline.setCopyRight(value);
        }
        if (item.get("title") != null) {
            value = (String) item.get("title");
            timeline.setTitle(value);
        }
        if (item.get("location") != null) {
            value = (String) item.get("location");
            timeline.setLocation(value);
        }
        if (item.get("description") != null) {
            value = (String) item.get("description");
            timeline.setDescription(value);
        }
        if (item.get("comment") != null) {
            value = (String) item.get("comment");
            timeline.setComment(value);
        }
        if (item.get("instructions") != null) {
            value = (String) item.get("instructions");
            timeline.setInstructions(value);
        }
        if (item.get("keys") != null) {
            value = (String) item.get("keys");
            timeline.setKeys(value);
        }
        if (item.get("overrideTitle") != null) {
            boolValue = (Boolean) item.get("overrideTitle");
            timeline.setOverrideTitle(boolValue);
        }
        if (item.get("overrideLocation") != null) {
            boolValue = (Boolean) item.get("overrideLocation");
            timeline.setOverrideLocation(boolValue);
        }
        addTimeline(timeline);
    }

    private void addTimeline(TimeLine timeline) {
        for (TimeLine element : this.timeLines.getTimeLines()) {
            if (timeline.getStartDate().equals(element.getStartDate())) {
                throw new IllegalStateException(String.format("startDate: %tF %tT already exists", timeline.getStartDate(), timeline.getStartDate()));
            }
        }
        this.timeLines.addTimeLine(timeline);
    }

    private void setEndDate() {
        LocalDateTime date = null;
        for (int count = this.timeLines.getTimeLines().size() - 1; count >= 0; count--) {
            if (count < this.timeLines.getTimeLines().size() - 1) {
                this.timeLines.getTimeLines().get(count).setEndDate(date);
            }
            date = this.timeLines.getTimeLines().get(count).getStartDate();
        }
    }

    public List<TimeLine> getTimeLinesSorted() {
        this.timeLines.getTimeLines().sort(new SortByDate());
        setEndDate();
        return this.timeLines.getTimeLines();
    }

    public TimeLine getTimeLine(LocalDateTime date) {
        TimeLine result = null;
        for (TimeLine timeline : this.getTimeLinesSorted()) {
            if ((!date.isBefore(timeline.getStartDate())) &&
                    ((timeline.getEndDate() == null) ||
                            (date.isBefore(timeline.getEndDate())))){
                result = timeline;
                break;
            }
        }
        if (result == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            throw new IllegalStateException(String.format("%s is outside range timelines", date.format(formatter)));
        }
        return result;
    }



}
