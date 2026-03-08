package nl.schoepping.spring_renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExifInfo {
    private LocalDateTime creationDate;
    private Double latitude;
    private Double longitude;
}
