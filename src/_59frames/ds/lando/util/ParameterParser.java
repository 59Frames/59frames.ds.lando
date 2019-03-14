package _59frames.ds.lando.util;

import _59frames.ds.lando.model.Argument;
import _59frames.ds.lando.model.Arguments;
import _59frames.ds.lando.model.Command;

import java.util.List;

public class ParameterParser {
    private final char parameterChar;
    private final boolean isNamed;

    public ParameterParser(final char parameterChar, final boolean isNamed) {
        this.parameterChar = parameterChar;
        this.isNamed = isNamed;
    }

    public Arguments parse(String[] args, Command command) {
        var arguments = new Arguments();

        if (command.countTotalArguments() < 1)
            return arguments;

        if (isNamed) {
            String[] argPair;
            for (var argString : args) {
                if (!argString.contains(String.format("%c", this.parameterChar))) continue;

                argPair = argString.split(String.format("%c", this.parameterChar));

                if (argPair.length != 2) continue;

                arguments.add(new Argument(argPair[0], argPair[1]));
            }
        } else {
            var counter = 0;
            for (var argString : args) {
                if (argString.equalsIgnoreCase(String.format("%c", this.parameterChar))) continue;

                if (counter < command.countTotalArguments()) {
                    if (counter >= command.getRequiredArgs().size()) {
                        arguments.add(new Argument(command.getOptionalArgs().get(counter-command.getRequiredArgs().size()), argString));
                    } else {
                        arguments.add(new Argument(command.getRequiredArgs().get(counter), argString));
                    }
                    counter++;
                }
            }

        }
        return arguments;
    }
}
