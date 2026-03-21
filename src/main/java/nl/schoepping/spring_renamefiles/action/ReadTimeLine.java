package nl.schoepping.spring_renamefiles.action;

import lombok.Getter;
import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.domain.TimeLine;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
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
    private Boolean Enabled = true;
    private Boolean AvoidNonAscii = true;
    private final List<TimeLine> timeLines = new ArrayList<>();

    class SortByDate implements Comparator<TimeLine> {
        @Override
        public int compare(TimeLine o1, TimeLine o2) {
            return o1.getStartDate().compareTo(o2.getStartDate());
        }
    }

    public ReadTimeLine(String filename) {
        String timeLineFile = "../config/" +  filename;
        int lineCount = 0;
        try {
            InputStream input = new FileInputStream(new File(timeLineFile));
            LoaderOptions options = new LoaderOptions();
            int maxNodes = 1000;
            try {
                if (System.getenv("MAX_NODES") != null) {
                    maxNodes = Integer.parseInt(System.getenv("MAX_NODES"));
                }
            }
            catch (Exception e) {}
            options.setMaxAliasesForCollections(maxNodes);
            Yaml yaml = new Yaml(options);
            Map timeLine = yaml.load(input);
//            retrieve values for config
            if (timeLine.get("config") != null) {
                Map config = (Map) timeLine.get("config");
                if (config.get("enabled") != null) {
                    this.Enabled = (Boolean) config.get("enabled");
                }
                if (config.get("avoidnonascii") != null) {
                    this.AvoidNonAscii = (Boolean) config.get("avoidnonascii");
                }
            }

            if (timeLine.get("timeline") != null) {
                ArrayList<Map> timelineArray = (ArrayList<Map>) timeLine.get("timeline");
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
                log.log(Level.SEVERE, String.format("Error in timeline %d, incorrect dateformat: %s", lineCount, sentence));
                throw new IllegalStateException(String.format("Error in timeline %d, incorrect dateformat: %s", lineCount, sentence));
            }
            else if (errorType.equals("java.lang.Exception")) {
                log.log(Level.SEVERE, String.format("Error in timeline %d, %s", lineCount, e.getMessage()));
                throw new IllegalStateException(String.format("Error in timeline %d, %s", lineCount, e.getMessage()));
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
        if (item.get("startdate") != null) {
            value = (String) item.get("startdate");
            timeline.setStartDate(value);
        }
        if (timeline.getStartDate() == null) {
            throw new IllegalStateException("startdate is not filled");
        }
        if (item.get("countrycode") != null) {
            value = (String) item.get("countrycode");
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
        if (item.get("creator") != null) {
            value = (String) item.get("creator");
            timeline.setAuthor(value);
        }
        if (item.get("website") != null) {
            value = (String) item.get("website");
            timeline.setWebsite(value);
        }
        if (item.get("copyright") != null) {
            value = (String) item.get("copyright");
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
        for (TimeLine element : this.timeLines) {
            if (timeline.getStartDate().equals(element.getStartDate())) {
                throw new IllegalStateException(String.format("startdate: %tF %tT already exists", timeline.getStartDate(), timeline.getStartDate()));
            }
        }
        this.timeLines.add(timeline);
    }

    private void setEndDate() {
        LocalDateTime date = null;
        for (int count = this.timeLines.size() - 1; count >= 0; count--) {
            if (count < this.timeLines.size() - 1) {
                this.timeLines.get(count).setEndDate(date);
            }
            date = this.timeLines.get(count).getStartDate();
        }
    }

    public List<TimeLine> getTimeLines() {
        this.timeLines.sort(new SortByDate());
        setEndDate();
        return this.timeLines;
    }

    public TimeLine getTimeLine(LocalDateTime date) {
        TimeLine result = null;
        for (TimeLine timeline : this.getTimeLines()) {
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
