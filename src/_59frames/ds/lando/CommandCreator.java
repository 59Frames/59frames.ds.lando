package _59frames.ds.lando;

import _59frames.ds.lando.model.Command;
import _59frames.ds.lando.model.Event;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * {@link CommandCreator}
 *
 * @author Daniel Seifert
 * @version 1.0
 * @since 1.0
 */
public class CommandCreator {

    private String key;
    private Event event;
    private List<String> requiredArgs;
    private List<String> optionalArgs;

    public CommandCreator() {
        this.key = null;
        this.event = null;
        this.requiredArgs = new ArrayList<>();
        this.optionalArgs = new ArrayList<>();
    }

    @Contract("-> new")
    @NotNull
    public static synchronized CommandCreator creator() {
        return new CommandCreator();
    }

    public CommandCreator key(String key) {
        this.key = key;
        return this;
    }

    public CommandCreator event(@NotNull final Event event) {
        this.event = event;
        return this;
    }

    public CommandCreator addRequiredArg(@NotNull final String key) {
        this.requiredArgs.add(key);
        return this;
    }

    public CommandCreator addOptionalArg(@NotNull final String key) {
        this.optionalArgs.add(key);
        return this;
    }

    public CommandCreator addAllRequiredArgs(@NotNull final String... requiredArgs) {
        if (requiredArgs.length == 0)
            return this;

        return this.addAllOptionalArgs(Arrays.asList(requiredArgs));
    }

    public CommandCreator addAllOptionalArgs(@NotNull final String... optionalArgs) {
        if (optionalArgs.length == 0)
            return this;

        return this.addAllOptionalArgs(Arrays.asList(optionalArgs));
    }

    public CommandCreator addAllRequiredArgs(@NotNull final List<String> requiredArgs) {
        this.requiredArgs.addAll(clear(requiredArgs));
        return this;
    }

    public CommandCreator addAllOptionalArgs(@NotNull final List<String> optionalArgs) {
        this.optionalArgs.addAll(clear(optionalArgs));
        return this;
    }

    public Command create() {
        if (this.key == null || (this.key.isEmpty() || this.key.isBlank()))
            throw new IllegalArgumentException("Key is null or empty");

        if (this.event == null)
            throw new IllegalArgumentException("Event is null");

        return new Command(key, event, requiredArgs, optionalArgs);
    }

    @NotNull
    @Contract("_ -> new")
    private List<? extends String> clear(@NotNull List<String> list) {

        var linkedList = new LinkedList<>(list);

        linkedList.removeIf(String::isBlank);
        linkedList.removeIf(String::isEmpty);

        return linkedList;
    }
}
