package nl.schoepping.spring_renamefiles.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeLine {
    @Setter(AccessLevel.NONE)
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    private String description;
    @Setter(AccessLevel.NONE)
    private String countryCode;
    @Setter(AccessLevel.NONE)
    private String country;
    private String province;
    private String city;
    private String location;
    private String author;
    private String website;
    private String copyRight;
    private String comment;
    private String keys;
    private String instructions;
    private Boolean overrideTitle;
    private Boolean overrideLocation;

    public void setCountryCode(String countryCode) {
        Locale locale = new Locale.Builder().setRegion(countryCode).build();
        try {
            String ISO3 = locale.getISO3Country();
            this.countryCode = countryCode;
            this.country = locale.getDisplayCountry();
        }
        catch (Exception e) {
            throw new IllegalStateException(String.format("countrycode: %s is not valid", countryCode));
        }
    }

    public void setStartDate(String startDate) {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        if (System.getenv("DATEFORMAT_YAML") != null) {
            dateFormat = System.getenv("DATEFORMAT_YAML");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            this.startDate = LocalDateTime.parse(startDate, formatter);
        }
        catch (Exception e) {
            throw new IllegalStateException( String.format("startdate: %s is not valid", startDate));
        }
    }

    @Override
    public String toString() {
        return startDate.toString();
    }


}
