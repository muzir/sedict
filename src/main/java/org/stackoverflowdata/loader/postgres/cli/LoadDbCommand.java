package org.stackoverflowdata.loader.postgres.cli;

import com.github.rvesse.airline.HelpOption;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;
import com.github.rvesse.airline.annotations.restrictions.AllowedRawValues;

import java.util.Arrays;

@Command(name = "load-db", description = "Loading database")
public class LoadDbCommand implements Runnable {

    public LoadDbCommand() {
        this.help = new HelpOption<>();
    }

    private HelpOption<LoadDbCommand> help;

    @Option(type = OptionType.COMMAND,
            name = {"-f", "--files"},
            description = "Load xml files to RDBMS tables.",
            title = "XML files")
    @AllowedRawValues(allowedValues = {"posts.xml", "tags.xml"})
    private String files;

    @Override
    public void run() {

        if (!help.showHelpIfRequested())
            System.out.println("Files: " + files);
    }
}
