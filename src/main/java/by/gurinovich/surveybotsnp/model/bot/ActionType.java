package by.gurinovich.surveybotsnp.model.bot;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum ActionType {
    START("/start"),
    SURVEY("/form"),
    REPORT("/report");

    private final String path;

    ActionType(String path) {
        this.path = path;
    }

    public static Optional<ActionType> getByPath(final String path) {
        return Arrays.stream(ActionType.values())
                .filter(type -> type.path.equals(path))
                .findFirst();
    }
}
