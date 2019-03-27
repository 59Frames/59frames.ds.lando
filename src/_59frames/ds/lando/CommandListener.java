package _59frames.ds.lando;

import _59frames.ds.lando.model.Constraint;
import _59frames.ds.lando.model.Command;
import _59frames.ds.lando.util.ArgumentParser;
import _59frames.ds.lando.util.ArgumentValidator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.InvalidParameterException;
import java.util.Scanner;
import java.util.TreeMap;

public class CommandListener {
    private final PrintStream output;
    private final Scanner scanner;
    private final ArgumentParser parser;

    private TreeMap<String, Command> commands = new TreeMap<>();

    private boolean running = false;

    private CommandListener(@NotNull final InputStream inputStream, @NotNull final OutputStream outputStream, @NotNull final ArgumentParser parser) {
        this.output = new PrintStream(outputStream);
        this.scanner = new Scanner(inputStream);
        this.parser = parser;
    }

    @NotNull
    @Contract(" -> new")
    public static Builder builder() {
        return new Builder();
    }

    public void start() {
        if (isRunning()) return;

        running = true;
        new Thread(() -> {
            while (running) {
                listen();
            }
        }).start();
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void add(Command command) {
        this.commands.put(command.getKey(), command);
    }

    private void listen() {
        try {
            String s = scanner.nextLine();
            if (s != null && !(s.isEmpty() || s.isBlank()))
                interpret(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void interpret(@NotNull String s) {
        final var input = clean(s).split("\\s");

        if (input.length < 1)
            return;

        final var key = input[0].toLowerCase();
        if (commands.containsKey(key)) {
            var c = commands.get(key);

            var argStrings = new String[input.length - 1];
            System.arraycopy(input, 1, argStrings, 0, argStrings.length);

            var args = parser.parse(argStrings, c);

            if (c.hasRequiredArgs()) {
                for (var name : c.getRequiredArgs()) {
                    if (!args.has(name.getKey())) {
                        missingArgument(name.getKey());
                    }
                }
            }

            if (c.hasOptionalArgs()) {
                for (var name : args.keys()) {
                    if (!c.hasConstraint(name)) {
                        unknownArgument(name);
                    }
                }
            }

            if (ArgumentValidator.validate(args, c.getConstraints())) {
                c.execute(args);
            }
            else {
                ArgumentValidator.getInvalidArguments().forEach(this::invalidArgument);
            }
        } else {
            unknownCommand(key);
        }
    }

    @NotNull
    private String clean(@NotNull String string) {
        return string.trim().strip().replaceAll("\\s{2,}", " ");
    }

    private void unknownCommand(String command) {
        output.println(String.format("Unknown command { %s }", command));
    }

    private void missingArgument(String argument) {
        output.println(String.format("Missing argument { %s }", argument));
    }

    private void unknownArgument(String argument) {
        output.println(String.format("Unknown argument { %s }", argument));
    }

    private void invalidArgument(String argument) {
        output.println(String.format("invalid argument value thrown by { %s }", argument));
    }

    public static class Builder {
        private char argumentSeparatorChar;
        private InputStream inputStream;
        private OutputStream outputStream;
        private boolean startWithBuild;
        private boolean hasNamedArguments;
        private boolean hasHelpCommand;
        private boolean hasExitCommand;

        public Builder() {
            this.argumentSeparatorChar = ArgumentParser.DEFAULT_PARAM_CHAR;
            this.outputStream = System.err;
            this.inputStream = System.in;
            this.hasNamedArguments = true;
            this.hasHelpCommand = true;
            this.hasExitCommand = true;
            this.startWithBuild = false;
        }

        public Builder argumentSeparatorChar(final char pChar) {
            if (Character.isSpaceChar(pChar) || Character.isLetterOrDigit(pChar) || Character.isWhitespace(pChar))
                throw new InvalidParameterException("Parameter char can't be space, letter or digit");
            this.argumentSeparatorChar = pChar;
            return this;
        }

        public Builder input(@NotNull InputStream input) {
            this.inputStream = input;
            return this;
        }

        public Builder errorOutput(@NotNull OutputStream output) {
            this.outputStream = output;
            return this;
        }

        public Builder hasNamedArguments(final boolean val) {
            this.hasNamedArguments = val;
            return this;
        }

        public Builder hasDefaultHelpCommand(final boolean val) {
            this.hasHelpCommand = val;
            return this;
        }

        public Builder hasDefaultExitCommand(final boolean val) {
            this.hasExitCommand = val;
            return this;
        }

        public Builder startWithBuild() {
            return startWithBuild(true);
        }

        public Builder startWithBuild(final boolean val) {
            this.startWithBuild = val;
            return this;
        }

        public CommandListener build() {
            final var instance = new CommandListener(inputStream, outputStream, new ArgumentParser(argumentSeparatorChar, hasNamedArguments));

            if (hasHelpCommand) {
                instance.add(new Command("help", args -> {
                    //instance.sortCommands();

                    final var leftAlignFormat = "| %-16.16s | %-48.48s | %-48.48s |%n";

                    instance.output.format("+------------------+--------------------------------------------------+--------------------------------------------------+%n");
                    instance.output.format("| Command key      | Required Arguments                               | Optional Arguments                               |%n");
                    instance.output.format("+------------------+--------------------------------------------------+--------------------------------------------------+%n");

                    instance.commands.forEach((key, command) -> instance.output.format(leftAlignFormat, key, command.getRequiredArgs(), command.getOptionalArgs()));

                    instance.output.format("+------------------+--------------------------------------------------+--------------------------------------------------+%n");
                }));
            }

            if (hasExitCommand) {
                instance.add(CommandFactory.factory()
                        .key("exit")
                        .event(args -> {
                            instance.stop();
                            if (args.has("kill")) {
                                if (args.get("kill").toBool()) {
                                    System.gc();
                                    System.exit(0);
                                }
                            }
                        })
                        .addOptionalArgument("kill", Constraint.BOOLEAN)
                        .build());
            }

            if (startWithBuild)
                instance.start();

            return instance;
        }
    }
}