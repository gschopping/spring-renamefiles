package nl.schoepping.renamefiles.controller;

import lombok.extern.java.Log;
import nl.schoepping.renamefiles.action.ReadTimeLine;
import nl.schoepping.renamefiles.domain.TimeLine;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;

@RestController
@Log
public class TimeLineController {

    @GetMapping(path = "/timeline")
    public ResponseEntity<List<TimeLine>> getTimeLines() {
        try {
            ReadTimeLine reader = new ReadTimeLine("timeline.yml");
            return new ResponseEntity<>(reader.getTimeLines(), HttpStatus.OK);
        }
        catch (Exception e) {
            log.log(Level.WARNING, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/timeline/{datetime}")
    public ResponseEntity<TimeLine> getTimeLine(@PathVariable("datetime") String datetime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            ReadTimeLine reader = new ReadTimeLine("timeline.yml");
            LocalDateTime date = LocalDateTime.parse(datetime, formatter);
            TimeLine timeLine = reader.getTimeLine(date);
            if (timeLine != null) {
                return new ResponseEntity<>(reader.getTimeLine(date), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            log.log(Level.WARNING, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
