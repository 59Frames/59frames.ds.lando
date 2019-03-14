import _59frames.ds.lando.CommandListener;
import _59frames.ds.lando.model.Command;
import _59frames.ds.lando.util.ParameterParser;

public class Test {
    public static void main(String[] args) {

        var listener = CommandListener.builder()
                .paramChar(ParameterParser.DEFAULT_PARAM_CHAR)
                .hasDefaultHelpCommand(true)
                .hasDefaultExitCommand(true)
                .startWithBuild(true)
                .build();

        listener.add(
                new Command("print",
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
