package nl.schoepping.spring_renamefiles.controller;

import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.action.ReadTimeLine;
import nl.schoepping.spring_renamefiles.domain.TimeLine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Log
public class TimeLineController {

    @GetMapping(path = "/timeline")
    public List<TimeLine> getTimeLines() {
        try {
            ReadTimeLine reader = new ReadTimeLine("timeline.yml");
            return reader.getTimeLines();
        }
        catch (Exception e) {
            return null;
        }
    }

    @GetMapping(path = "/timeline/{datetime}")
    public TimeLine getTimeLine(@PathVariable("datetime") String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        ReadTimeLine reader = new ReadTimeLine("timeline.yml");
        LocalDateTime date = LocalDateTime.parse(datetime, formatter);
        return reader.getTimeLine(date);
    }
}
