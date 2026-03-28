package nl.schoepping.renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TimeLines {

    private boolean enabled = true;
    private TimeLine standard;
    private List<TimeLine> timeLines;

    public TimeLines() {
        this.timeLines = new ArrayList<>();
    }

    public void addTimeLine(TimeLine timeLine) {
        this.timeLines.add(timeLine);
    }

}
