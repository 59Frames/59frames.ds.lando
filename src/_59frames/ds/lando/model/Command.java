package _59frames.ds.lando.model;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Command {
    private final String key;
    private final Event event;
    private final ArgumentConstraints constraints;

    public Command(@NotNull final String key, @NotNull final Event event) {
        this(key, event, new ArrayList<>());
    }

    public Command(@NotNull final String key, @NotNull final Event event, @NotNull final ArgumentConstraint... constraints) {
        this(key, event, Arrays.asList(constraints));
    }

    public Command(@NotNull final String key, @NotNull final Event event, @NotNull final List<ArgumentConstraint> constraints) {
        if (key.split("\\s").length != 1)
            throw new MalformedParametersException("Command key can only be a single word");

        this.key = key;
        this.event = event;
        this.constraints = new ArgumentConstraints(constraints);
    }

    public int countTotalArguments() {
        return constraints.size();
    }

    public void execute(Arguments args) {
        event.onEvent(args);
    }

    public boolean hasRequiredArgs() {
        return this.constraints.hasRequiredArgs();
    }

    public boolean hasOptionalArgs() {
        return this.constraints.hasOptionalArgs();
    }

    public List<ArgumentConstraint> getOptionalArgs() {
        return this.constraints.getOptionalArgs();
    }

    public List<ArgumentConstraint> getRequiredArgs() {
        return this.constraints.getRequiredArgs();
    }

    public ArgumentConstraints getConstraints() {
        return this.constraints;
    }

    public ArgumentConstraint getConstraint(String key) {
        return this.constraints.get(key);
    }

    public String getKey() {
        return this.key;
    }

    public boolean hasConstraint(String name) {
        return this.constraints.hasConstraint(name);
    }
}
