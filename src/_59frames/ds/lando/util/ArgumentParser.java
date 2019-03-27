package _59frames.ds.lando.util;

import _59frames.ds.lando.model.Argument;
import _59frames.ds.lando.model.Arguments;
import _59frames.ds.lando.model.Command;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ArgumentParser {
    public static final char DEFAULT_PARAM_CHAR = '=';

    private final char argumentSeparatorChar;
    private final boolean isNamed;

    public ArgumentParser(final char argumentSeparatorChar, final boolean isNamed) {
        this.argumentSeparatorChar = argumentSeparatorChar;
        this.isNamed = isNamed;
    }

    public Arguments parse(String[] args, Command command) {
        var arguments = new Arguments();

        if (command.countTotalArguments() < 1)
            return arguments;

        return isNamed
                ? parseNamed(arguments, args)
                : parseUnnamed(arguments, args, command);
    }

    @Contract("_, _ -> param1")
    private Arguments parseNamed(@NotNull final Arguments args, @NotNull final String[] arr) {
        String[] argPair;
        for (var argString : arr) {
            if (!argString.contains(String.format("%c", this.argumentSeparatorChar))) continue;

            argPair = argString.split(String.format("%c", this.argumentSeparatorChar));

            if (argPair.length != 2) continue;

            args.add(new Argument(argPair[0], argPair[1]));
        }
        return args;
    }

    @Contract("_, _, _ -> param1")
    private Arguments parseUnnamed(@NotNull final Arguments args, @NotNull final String[] arr, @NotNull final Command command) {
        var counter = 0;
        for (var argString : arr) {
            if (argString.equalsIgnoreCase(String.format("%c", this.argumentSeparatorChar))) continue;

            if (counter < command.countTotalArguments()) {
                if (counter >= command.getRequiredArgs().size()) {
                    args.add(new Argument(command.getOptionalArgs().get(counter - command.getRequiredArgs().size()).getKey(), argString));
                } else {
                    args.add(new Argument(command.getRequiredArgs().get(counter).getKey(), argString));
                }
                counter++;
            }
        }
        return args;
    }
}
