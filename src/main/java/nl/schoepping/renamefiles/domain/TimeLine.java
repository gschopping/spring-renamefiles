package nl.schoepping.renamefiles.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeLine {
    @Getter
    private LocalDateTime startDate;
    @Getter
    @Setter
    private LocalDateTime endDate;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String description;
    @Getter
    private String countryCode;
    @Getter
    private String country;
    @Getter
    @Setter
    private String province;
    @Getter
    @Setter
    private String city;
    @Getter
    @Setter
    private String location;
    @Getter
    @Setter
    private String author;
    @Getter
    @Setter
    private String website;
    @Getter
    @Setter
    private String copyRight;
    @Getter
    @Setter
    private String comment;
    @Getter
    @Setter
    private String keys;
    @Getter
    @Setter
    private String instructions;
    @Getter
    @Setter
    private Boolean overrideTitle;
    @Getter
    @Setter
    private Boolean overrideLocation;

    public void setCountryCode(String countryCode) {
        Locale locale = new Locale.Builder().setRegion(countryCode).build();
        try {
            String ISO3 = locale.getISO3Country();
            this.countryCode = countryCode;
            this.country = locale.getDisplayCountry();
        }
        catch (Exception e) {
            throw new IllegalStateException(String.format("countryCode: %s is not valid", countryCode));
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
            throw new IllegalStateException( String.format("startDate: %s is not valid", startDate));
        }
    }

    @Override
    public String toString() {
        if (startDate != null) {
            return startDate.toString();
        }
        else {
            return null;
        }

    }


}
