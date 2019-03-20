package _59frames.ds.lando;

import _59frames.ds.lando.model.ArgumentConstraint;
import _59frames.ds.lando.model.Command;
import _59frames.ds.lando.model.Event;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    private List<ArgumentConstraint> constraints;

    public CommandCreator() {
        this.key = null;
        this.event = null;
        this.constraints = new ArrayList<>();
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
        return this.addConstraint(new ArgumentConstraint(key, true));
    }

    public CommandCreator addRequiredArg(@NotNull final String key, @NotNull final Predicate<String> predicate) {
        return this.addConstraint(new ArgumentConstraint(key, true, predicate));
    }

    public CommandCreator addOptionalArg(@NotNull final String key) {
        return this.addConstraint(new ArgumentConstraint(key, false));
    }

    public CommandCreator addOptionalArg(@NotNull final String key, @NotNull final Predicate<String> predicate) {
        return this.addConstraint(new ArgumentConstraint(key, false, predicate));
    }

    public CommandCreator addAllRequiredArgs(@NotNull final String... requiredArgs) {
        if (requiredArgs.length == 0)
            return this;

        return this.addAllOptionalArgs(Arrays.asList(requiredArgs));
    }

    public CommandCreator addConstraint(@NotNull final String key, final boolean required, @NotNull final Predicate<String> predicate) {
        this.constraints.add(new ArgumentConstraint(key, required, predicate));
        return this;
    }

    public CommandCreator addConstraint(ArgumentConstraint constraint) {
        this.constraints.add(constraint);
        return this;
    }

    public CommandCreator addAllOptionalArgs(@NotNull final String... optionalArgs) {
        if (optionalArgs.length == 0)
            return this;

        return this.addAllOptionalArgs(Arrays.asList(optionalArgs));
    }

    public CommandCreator addAllRequiredArgs(@NotNull final List<String> requiredArgs) {
        this.constraints.addAll(clear(requiredArgs).stream().map(val -> new ArgumentConstraint(val, true)).collect(Collectors.toList()));
        return this;
    }

    public CommandCreator addAllOptionalArgs(@NotNull final List<String> optionalArgs) {
        this.constraints.addAll(clear(optionalArgs).stream().map(val -> new ArgumentConstraint(val, false)).collect(Collectors.toList()));
        return this;
    }

    public Command create() {
        if (this.key == null || (this.key.isEmpty() || this.key.isBlank()))
            throw new IllegalArgumentException("Key is null or empty");

        if (this.event == null)
            throw new IllegalArgumentException("Event is null");

        return new Command(key, event, constraints);
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
