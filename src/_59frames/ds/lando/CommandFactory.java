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
 * {@link CommandFactory}
 *
 * @author Daniel Seifert
 * @version 1.0
 * @since 1.0
 */
public class CommandFactory {

    private String key;
    private Event event;
    private List<ArgumentConstraint> constraints;

    public CommandFactory() {
        this.key = null;
        this.event = null;
        this.constraints = new ArrayList<>();
    }

    @Contract("-> new")
    @NotNull
    public static synchronized CommandFactory factory() {
        return new CommandFactory();
    }

    public CommandFactory key(String key) {
        this.key = key;
        return this;
    }

    public CommandFactory event(@NotNull final Event event) {
        this.event = event;
        return this;
    }

    public CommandFactory addRequiredArgument(@NotNull final String key) {
        return this.addConstraint(new ArgumentConstraint(key, true));
    }

    public CommandFactory addRequiredArgument(@NotNull final String key, @NotNull final Predicate<String> predicate) {
        return this.addConstraint(new ArgumentConstraint(key, true, predicate));
    }

    public CommandFactory addOptionalArgument(@NotNull final String key) {
        return this.addConstraint(new ArgumentConstraint(key, false));
    }

    public CommandFactory addOptionalArgument(@NotNull final String key, @NotNull final Predicate<String> predicate) {
        return this.addConstraint(new ArgumentConstraint(key, false, predicate));
    }

    public CommandFactory addConstraint(@NotNull final String key, final boolean required, @NotNull final Predicate<String> predicate) {
        return this.addConstraint(new ArgumentConstraint(key, required, predicate));
    }

    public CommandFactory addConstraint(ArgumentConstraint constraint) {
        this.constraints.add(constraint);
        return this;
    }

    public Command build() {
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
