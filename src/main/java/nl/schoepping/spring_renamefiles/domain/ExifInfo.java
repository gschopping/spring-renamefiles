package nl.schoepping.spring_renamefiles.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExifInfo {
    private LocalDateTime creationDate;
    @Getter(AccessLevel.NONE)
    private String creationDateString;
    @Getter(AccessLevel.NONE)
    private String creationTimeString;
    private Double latitude;
    private Double longitude;

    public String getCreationDateString() {
        String dateFormat = "yyyyMMdd";
        DateTimeFormatter formatter;
        if (System.getenv("DATEFORMAT_OUTPUT") != null) {
            dateFormat = System.getenv("DATEFORMAT_OUTPUT");
        }
        try {
            formatter = DateTimeFormatter.ofPattern(dateFormat);
        }
        catch (Exception e) {
            formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        }
        return creationDate.format(formatter);
    }

    public String getCreationTimeString() {
        String timeFormat = "HHmmss";
        DateTimeFormatter formatter;
        if (System.getenv("TIMEFORMAT_OUTPUT") != null) {
            timeFormat = System.getenv("TIMEFORMAT_OUTPUT");
        }
        try {
            formatter = DateTimeFormatter.ofPattern(timeFormat);
        }
        catch (Exception e) {
            formatter = DateTimeFormatter.ofPattern("HHmmss");
        }
        return creationDate.format(formatter);
    }
}
