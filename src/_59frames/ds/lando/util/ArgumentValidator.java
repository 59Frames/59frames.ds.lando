package _59frames.ds.lando.util;

import _59frames.ds.lando.model.Argument;
import _59frames.ds.lando.model.Constraint;
import _59frames.ds.lando.model.Constraints;
import _59frames.ds.lando.model.Arguments;
import org.jetbrains.annotations.NotNull;

public class ArgumentValidator {
    public static boolean validate(@NotNull final Argument argument, @NotNull final Constraint constraint) {
        return constraint.validate(argument.getKey(), argument.getValue());
    }

    public static boolean validate(@NotNull final Arguments arguments, @NotNull final Constraints constraints) {
        var iterator = arguments.iterator();
        var isValid = true;
        while (iterator.hasNext()) {
            var arg = iterator.next();
            if (!constraints.has(arg.getKey()))
                return false;

            isValid = validate(arg, constraints.get(arg.getKey()));

            if (!isValid)
                break;
        }

        return isValid;
    }
}
