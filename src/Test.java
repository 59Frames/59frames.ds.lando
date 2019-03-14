import _59frames.ds.lando.CommandCreator;
import _59frames.ds.lando.CommandListener;
import _59frames.ds.lando.model.Event;
import _59frames.ds.lando.util.ParameterParser;

public class Test {
    public static void main(String[] args) {

        var listener = CommandListener.builder()
                .paramChar(ParameterParser.DEFAULT_PARAM_CHAR)
                .hasDefaultHelpCommand(true)
                .hasDefaultExitCommand(true)
                .startWithBuild(true)
                .build();

        Event printEvent = arguments -> {
            if (arguments.hasArgument("place")) {
                var place = arguments.getArgument("place").getValue();
                System.out.println(String.format("My name is %s, I am %d years old and I grew up in %s.", arguments.getArgument("name"), arguments.getArgument("age").toInteger(), place));
            } else {
                System.out.println(String.format("My name is %s, I am %d years old.", arguments.getArgument("name"), arguments.getArgument("age").toInteger()));
            }
        };

        var printPerson = CommandCreator.creator()
                .key("print")
                .event(printEvent)
                .addRequiredArg("name")
                .addRequiredArg("age")
                .addOptionalArg("place")
                .create();

        listener.add(printPerson);
    }
}
