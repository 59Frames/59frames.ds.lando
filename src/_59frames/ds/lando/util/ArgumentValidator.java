package _59frames.ds.lando.util;

import _59frames.ds.lando.model.Argument;
import _59frames.ds.lando.model.Constraint;
import _59frames.ds.lando.model.Constraints;
import _59frames.ds.lando.model.Arguments;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ArgumentValidator {
    private static final LinkedHashMap<String, Argument> invalidArguments = new LinkedHashMap<>();

    public static boolean isInvalid(@NotNull final Argument argument, @NotNull final Constraint constraint) {
        return !constraint.validate(argument.getKey(), argument.getValue());
    }

    public static boolean validate(@NotNull final Arguments arguments, @NotNull final Constraints constraints) {
        invalidArguments.clear();
        fillInvalidArguments(arguments, constraints);
        return invalidArguments.isEmpty();
    }

    @Contract(pure = true)
    public static LinkedHashMap<String, Argument> getInvalidArguments() {
        return invalidArguments;
    }

    private static void fillInvalidArguments(@NotNull final Arguments arguments, @NotNull final Constraints constraints) {
        var iterator = arguments.iterator();
        while (iterator.hasNext()) {
            var arg = iterator.next();
            if (!constraints.has(arg.getKey()) || isInvalid(arg, constraints.get(arg.getKey()))) {
                invalidArguments.put(arg.getKey(), arg);
            }
        }
    }
}
