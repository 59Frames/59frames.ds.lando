package _59frames.ds.lando.model;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class ArgumentConstraint {
    public static final Predicate<String> STRING = val -> true;
    public static final Predicate<String> NUMBER = val -> val.matches("^-?\\d*\\.?\\d+$");
    public static final Predicate<String> BOOLEAN = val -> (val.equalsIgnoreCase("true") || val.equalsIgnoreCase("false"));

    private final String key;
    private final boolean isRequired;
    private final Predicate<String> predicate;

    public ArgumentConstraint(@NotNull final String key) {
        this(key, false);
    }

    public ArgumentConstraint(@NotNull final String key, final boolean isRequired) {
        this(key, isRequired, STRING);
    }

    public ArgumentConstraint(@NotNull final String key, @NotNull final Predicate<String> predicate) {
        this(key, false, predicate);
    }

    public ArgumentConstraint(@NotNull final String key, final boolean isRequired, @NotNull final Predicate<String> predicate) {
        this.key = key;
        this.isRequired = isRequired;
        this.predicate = predicate;
    }

    public boolean validate(String key, String str) {
        return this.key.equalsIgnoreCase(key) && predicate.test(str);
    }

    public boolean isRequired() {
        return this.isRequired;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public String toString() {
        return this.key;
    }
}
