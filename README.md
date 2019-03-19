# Lando

A simple terminal util to create your own java console commands.

## Getting Started

Currently, lando is running on Java 11 but I will add downward compatibility within a few weeks.

### Installing

1. Download the Jar file from our [releases](https://github.com/59Frames/59frames.ds.lando/releases)
2. Add the jar file as a library to your project
3. You are good to go!

Here's a short demo. You will find more information down below.

![](assets/Lando_Home.gif)

```java
public class Test {
    public static void main(String[] args){
        CommandListener listener = CommandListener.builder()
                .build();
        
        Command command = CommandCreator.creator()
                .key("hello")
                .event(arguments -> System.out.println("Hello World!"))
                .create();
        
        listener.add(command);
        
        listener.start();
        
        // Now if you type "hello" into your java terminal, you should simply see
        // "Hello World!"
        // Just without the quotes
        // then I type "exit" to stop the listener from listening.
    }
}
```

## Usage

So before we start creating Commands, let's check out the features of our CommandListener
```java
public class Test {
    public static void main(String[] args){
        CommandListener simpleListener = CommandListener.builder().build(); 
        // Above is a very basic listener.
        // It uses System.in as input,
        // System.err as errorOutput
        // "exit" and "help" commands are integrated
        // will not start with build.
        // the default parameter char is '='
        // it has named arguments
        
        // below is a customized one
        CommandListener customListener = CommandListener.builder()
                .input(System.in)
                .errorOutput(System.err)
                .paramChar(':')
                .startWithBuild(true)
                .hasDefaultExitCommand(true)
                .hasDefaultHelpCommand(true)
                .hasNamedArguments(true) // we recommend to make the arguments named
                .build();
    }
}
```

Moving on to basic commands

````java
public class Test {
    public static void main(String[] args){
        // So even tho this here is a perfectly good option
        Command command = new Command(
                        "say",
                        arguments -> {
                            // The event
                            // ...
                            String str = String.format("Saying: %s", arguments.getArgument("first"));
        
                            if (arguments.hasArgument("second"))
                                str = String.format("%s %s", str, arguments.getArgument("second"));
        
                            System.out.println(str);
                        },
                        new String[]{"first"}, // Required Arguments
                        new String[]{"second"}); // Optional Arguments
                        
        // I do not really support it since it does look kind of ugly ...
        // Overview = null
        // So I came up with a CommandCreator
        
        command = CommandCreator.creator()
                .key("say")
                .event(arguments -> {
                    // The event
                    // ...
                    String str = String.format("Saying: %s", arguments.getArgument("first"));
        
                    if (arguments.hasArgument("second"))
                        str = String.format("%s %s", str, arguments.getArgument("second"));
                    System.out.println(str);
                })
                .addRequiredArg("first")
                .addOptionalArg("second")
                .create();
        
        // Only thing is that if you don't specify the key and or event,
        // It will throw an exception
    }
}
````

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/59Frames/59frames.ds.lando/tags). 

## Authors

* **Daniel Seifert** - *Initial work* - [59Frames](https://github.com/59Frames)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Inspired by [Marcel Schmutz](https://github.com/schmarcel02/ConsoleUtil)
