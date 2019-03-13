package _59frames.ds.eggsy.model;

import java.util.Arrays;
import java.util.List;

public class Command {
    private Event event;
    private List<String> requiredArgs, optionalArgs;

    public Command(String[] requiredArgs, String[] optionalArgs, Event event) {
        this.event = event;
        this.requiredArgs = Arrays.asList(requiredArgs);
        this.optionalArgs = Arrays.asList(optionalArgs);
    }

    public Command( List<String> requiredArgs, List<String> optionalArgs, Event event) {
        this.event = event;
        this.requiredArgs = requiredArgs;
        this.optionalArgs = optionalArgs;
    }

    public void execute(Arguments args) {
        event.onEvent(args);
    }

    public List<String> getOptionalArgs() {
        return optionalArgs;
    }

    public List<String> getRequiredArgs() {
        return requiredArgs;
    }
}
