package org.stackoverflowdata.loader.postgres.cli.examples;

import com.github.rvesse.airline.HelpOption;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;

@Command(name = "setup-log", description = "Setup our log")
public class LoggingCommand implements Runnable {

    public LoggingCommand() {
        this.help = new HelpOption<>();
    }

    private HelpOption<LoggingCommand> help;

    @Option(name = {"-v", "--verbose"},
            description = "Set log verbosity on/off")
    private boolean verbose = false;

    @Override
    public void run() {
        if (!help.showHelpIfRequested())
            System.out.println("Verbosity: " + verbose);
    }
}
