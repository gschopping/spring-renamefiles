package nl.schoepping.spring_renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeLine {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    private String description;
    private String countryCode;
    private String province;
    private String city;
    private String location;
    private String author;
    private String website;
    private String copyright;
    private String comment;
    private String keys;
    private String instructions;
    private Boolean override;

    public static class TimeLineBuilder {
        public TimeLineBuilder startDate(String startDate) {
            String dateFormat = "yyyy-MM-dd HH:mm:ss";
            if (System.getenv("DATEFORMAT_YAML") != null) {
                dateFormat = System.getenv("DATEFORMAT_YAML");
            }
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                this.startDate = LocalDateTime.parse(startDate, formatter);
            }
            catch (Exception e) {
                this.startDate = LocalDateTime.now();
            }
            return this;
        }

        public TimeLineBuilder endDate(String endDate) {
            String dateFormat = "yyyy-MM-dd HH:mm:ss";
            if (System.getenv("DATEFORMAT_YAML") != null) {
                dateFormat = System.getenv("DATEFORMAT_YAML");
            }
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                this.endDate = LocalDateTime.parse(endDate, formatter);
            }
            catch (Exception e) {
                this.endDate = LocalDateTime.now();
            }
            return this;
        }

    }


}
