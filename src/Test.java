import _59frames.ds.lando.CommandListener;
import _59frames.ds.lando.model.Command;

public class Test {
    public static void main(String[] args) {

        var listener = CommandListener.builder()
                .paramChar('=')
                .hasDefaultHelpCommand(true)
                .hasDefaultExitCommand(true)
                .hasNamedArguments(true)
                .startWithBuild(true)
                .build();

        listener.add(
                new Command("print-person",
                        args1 -> {
                            if (args1.hasArgument("place")) {
                                System.out.println(String.format("My name is %s and I am %d years old and my hometown is %s", args1.getArgument("name"), args1.getArgument("age").getInteger(), args1.getArgument("place")));
                            } else {
                                System.out.println(String.format("My name is %s and I am %d years old", args1.getArgument("name"), args1.getArgument("age").getInteger()));
                            }
                        },
                        new String[]{"name", "age"},
                        new String[]{"place"})
        );
    }
}
