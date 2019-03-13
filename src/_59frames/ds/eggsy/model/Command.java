package _59frames.ds.eggsy.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {
    private final String key;
    private final Event event;
    private List<String> requiredArgs;
    private List<String> optionalArgs = new ArrayList<>();

    public Command(String key, Event event, String... requiredArgs) {
        this(key, event, Arrays.asList(requiredArgs));
    }

    public Command(String key, Event event) {
        this.key = key;
        this.event = event;
    }

    public Command(String key, Event event, List<String> requiredArgs) {
        this.key = key;
        this.event = event;
        this.requiredArgs = requiredArgs;
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
