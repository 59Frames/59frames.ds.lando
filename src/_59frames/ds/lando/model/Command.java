package _59frames.ds.lando.model;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {
    private final String key;
    private final Event event;
    private final List<String> requiredArgs;
    private final List<String> optionalArgs;

    public Command(@NotNull final String key, @NotNull final Event event) {
        this(key, event, new ArrayList<>(), new ArrayList<>());
    }

    public Command(@NotNull final String key, @NotNull final Event event, @NotNull final String[] requiredArgs) {
        this(key, event, Arrays.asList(requiredArgs), new ArrayList<>());
    }

    public Command(@NotNull final String key, @NotNull final Event event, @NotNull final String[] requiredArgs, @NotNull final String[] optionalArgs) {
        this(key, event, Arrays.asList(requiredArgs), Arrays.asList(optionalArgs));
    }

    public Command(@NotNull final String key, @NotNull final Event event, @NotNull final List<String> requiredArgs, @NotNull final List<String> optionalArgs) {
        if (key.split("\\s").length != 1)
            throw new MalformedParametersException("Command key can only be one word");

        this.key = key;
        this.event = event;
        this.requiredArgs = requiredArgs;
        this.optionalArgs = optionalArgs;
    }

    public void execute(Arguments args) {
        event.onEvent(args);
    }

    public boolean hasRequiredArgs() {
        return this.requiredArgs != null && !this.requiredArgs.isEmpty();
    }

    public boolean hasOptionalArgs() {
        return this.optionalArgs != null && !this.optionalArgs.isEmpty();
    }

    public List<String> getOptionalArgs() {
        return this.optionalArgs;
    }

    public List<String> getRequiredArgs() {
        return this.requiredArgs;
    }

    public String getKey() {
        return this.key;
    }
}
