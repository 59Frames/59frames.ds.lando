package _59frames.ds.lando.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Constraints {
    private final LinkedHashMap<String, Constraint> constraints;
    private List<Constraint> required = null;
    private List<Constraint> optional = null;

    public Constraints(@NotNull List<Constraint> list) {
        this.constraints = new LinkedHashMap<>();
        for (var con : list)
            constraints.put(con.getKey(), con);
    }

    public Constraints(@NotNull final LinkedHashMap<String, Constraint> constraints) {
        this.constraints = constraints;
    }

    public Constraints() {
        this(new LinkedHashMap<>());
    }

    public void add(String key, Constraint constraint) {
        this.constraints.put(key, constraint);
    }

    public boolean has(String key) {
        return this.constraints.containsKey(key);
    }

    public Constraint get(String key) {
        return this.constraints.get(key);
    }

    public int size() {
        return this.constraints.size();
    }

    public boolean hasRequiredArgs() {
        return this.constraints != null && this.constraints.values().stream().anyMatch(Constraint::isRequired);
    }

    public boolean hasOptionalArgs() {
        return this.constraints != null && this.constraints.values().stream().anyMatch(constraint -> !constraint.isRequired());
    }

    public List<Constraint> getOptionalArgs() {
        if (this.optional == null)
            this.optional = this.constraints.values().stream().filter(constraint -> !constraint.isRequired()).collect(Collectors.toList());
        return this.optional;
    }

    public List<Constraint> getRequiredArgs() {
        if (this.required == null)
            this.required = this.constraints.values().stream().filter(Constraint::isRequired).collect(Collectors.toList());
        return this.required;
    }

    public void forEach(BiConsumer<String, Constraint> action) {
        this.constraints.forEach(action);
    }
}
