package _59frames.ds.lando.model;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ArgumentConstraints {
    private final HashMap<String, ArgumentConstraint> constraints;

    public ArgumentConstraints(@NotNull List<ArgumentConstraint> list) {
        this.constraints = new HashMap<>();
        for (var con : list)
            constraints.put(con.getKey(), con);
    }

    public ArgumentConstraints(HashMap<String, ArgumentConstraint> constraints) {
        this.constraints = constraints;
    }

    public ArgumentConstraints() {
        this(new HashMap<>());
    }

    public void add(String key, ArgumentConstraint constraint) {
        this.constraints.put(key, constraint);
    }

    public boolean hasConstraint(String key) {
        return this.constraints.containsKey(key);
    }

    public ArgumentConstraint get(String key) {
        return this.constraints.get(key);
    }

    public int size() {
        return this.constraints.size();
    }

    public boolean hasRequiredArgs() {
        if (this.constraints == null)
            return false;

        return this.constraints.values().stream().anyMatch(ArgumentConstraint::isRequired);
    }

    public boolean hasOptionalArgs() {
        if (this.constraints == null)
            return false;

        return this.constraints.values().stream().anyMatch(constraint -> !constraint.isRequired());
    }

    public List<ArgumentConstraint> getOptionalArgs() {
        return this.constraints.values().stream().filter(constraint -> !constraint.isRequired()).collect(Collectors.toList());
    }

    public List<ArgumentConstraint> getRequiredArgs() {
        return this.constraints.values().stream().filter(ArgumentConstraint::isRequired).collect(Collectors.toList());
    }

    public void forEach(BiConsumer<String, ArgumentConstraint> action) {
        this.constraints.forEach(action);
    }
}
