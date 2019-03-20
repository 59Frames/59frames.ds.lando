package _59frames.ds.lando.model;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {
    private final String key;
    private final Event event;
    private final Constraints constraints;

    public Command(@NotNull final String key, @NotNull final Event event) {
        this(key, event, new ArrayList<>());
    }

    public Command(@NotNull final String key, @NotNull final Event event, @NotNull final Constraint... constraints) {
        this(key, event, Arrays.asList(constraints));
    }

    public Command(@NotNull final String key, @NotNull final Event event, @NotNull final List<Constraint> constraints) {
        if (key.split("\\s").length != 1)
            throw new MalformedParametersException("Command key can only be a single word");

        this.key = key;
        this.event = event;
        this.constraints = new Constraints(constraints);
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

    public List<Constraint> getOptionalArgs() {
        return this.constraints.getOptionalArgs();
    }

    public List<Constraint> getRequiredArgs() {
        return this.constraints.getRequiredArgs();
    }

    public Constraints getConstraints() {
        return this.constraints;
    }

    public Constraint getConstraint(String key) {
        return this.constraints.get(key);
    }

    public String getKey() {
        return this.key;
    }

    public boolean hasConstraint(String name) {
        return this.constraints.has(name);
    }
}
