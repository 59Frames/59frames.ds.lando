package _59frames.ds.lando.util;

import _59frames.ds.lando.model.Argument;
import _59frames.ds.lando.model.ArgumentConstraint;
import _59frames.ds.lando.model.ArgumentConstraints;
import _59frames.ds.lando.model.Arguments;
import org.jetbrains.annotations.NotNull;

public class ArgumentValidator {
    public static boolean validate(@NotNull final Argument argument, @NotNull final ArgumentConstraint constraint) {
        return constraint.validate(argument.getKey(), argument.getValue());
    }

    public static boolean validate(@NotNull final Arguments arguments, @NotNull final ArgumentConstraints constraints) {
        var iterator = arguments.iterator();
        var isValid = true;
        while (iterator.hasNext()) {
            var arg = iterator.next();
            if (!constraints.hasConstraint(arg.getKey()))
                return false;

            isValid = validate(arg, constraints.get(arg.getKey()));

            if (!isValid)
                break;
        }

        return isValid;
    }
}
