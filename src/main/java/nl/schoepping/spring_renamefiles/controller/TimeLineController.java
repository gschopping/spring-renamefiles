package nl.schoepping.spring_renamefiles.controller;

import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.domain.ReadFile;
import nl.schoepping.spring_renamefiles.domain.TimeLine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log
public class TimeLineController {

    @GetMapping(path = "/timeline")
    public List<TimeLine> getTimeLines() {
        List<TimeLine> timeLines = new ArrayList<>();
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        timeLines.add(TimeLine.builder()
                .city("Медовка")
                .title("Medovka")
                .startDate("2025-11-02 09:00:00")
                .endDate("2025-11-03 10:00:00")
                .countryCode("RU")
                .build()
        );
        timeLines.add(new TimeLine(LocalDateTime.parse("2025-11-02 09:00:00", formatter),
                LocalDateTime.parse("2025-11-03 10:00:00", formatter),
                "Medovka",
                "Medovka",
                "RU",
                "Область Воронеж",
                "Медовка",
                "Бобронариум",
                "Guido Schöpping",
                "www.schopping.net",
                "Guido Schöpping (2025)",
                "",
                "",
                "",
                false
        ));
        timeLines.add(new TimeLine(LocalDateTime.parse("2025-10-31 09:00:00", formatter),
                LocalDateTime.parse("2025-11-02 09:00:00", formatter),
                "Zagorodnyy Kompleks Vidok",
                "Zagorodnyy Kompleks Vidok",
                "RU",
                "Область Воронеж",
                "Нелжа",
                "Загородный комплекс Видок",
                "Guido Schöpping",
                "www.schopping.net",
                "Guido Schöpping (2025)",
                "",
                "",
                "",
                false
        ));
        return timeLines;

    }
}
