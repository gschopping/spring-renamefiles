package nl.schoepping.renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RenameFiles {
    private String path;
    private String action;
    private ConfigPath config;
    public enum Action {
        MOVE,
        COPY,
    }

    public Action getActionAction() {
        if (action.equalsIgnoreCase("MOVE")) {
            return Action.MOVE;
        }
        return Action.COPY;
    }

    public boolean isRoot() {
        if (path.equalsIgnoreCase("root")) {
            return true;
        }
        else return path.equalsIgnoreCase("all");
    }

    public boolean isGPS(String subdirectory) {
        if (path.equalsIgnoreCase("all") && subdirectory.matches(config.getPathForGps())) {
            return true;
        }
        else return path.matches(config.getPathForGps()) && subdirectory.matches(config.getPathForGps()) && path.equals(subdirectory);
    }

    public boolean isTimeLaps(String subdirectory) {
        if (path.equalsIgnoreCase("all") && subdirectory.matches(config.getPathForTimeLaps())) {
            return true;
        }
        else return path.matches(config.getPathForTimeLaps()) && subdirectory.matches(config.getPathForTimeLaps()) && path.equals(subdirectory);
    }
}
