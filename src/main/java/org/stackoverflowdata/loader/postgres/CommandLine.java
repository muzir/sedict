package org.stackoverflowdata.loader.postgres;

import com.github.rvesse.airline.annotations.Cli;
import com.github.rvesse.airline.help.Help;
import com.github.rvesse.airline.parser.errors.ParseException;
import org.stackoverflowdata.loader.postgres.cli.DatabaseSetupCommand;
import org.stackoverflowdata.loader.postgres.cli.LoadDbCommand;
import org.stackoverflowdata.loader.postgres.xml.FileStreamParser;
import org.stackoverflowdata.loader.postgres.xml.FileStreamReader;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

@Cli(name = "sedict",
        description = "Stack exchange database import command line tool",
        defaultCommand = Help.class,
        commands = {DatabaseSetupCommand.class, LoadDbCommand.class, Help.class})
public class CommandLine {

    public static void main(String... args) {
        try {
            runCommand(args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    static void runCommand(String[] args) {
        com.github.rvesse.airline.Cli<Runnable> cli = new com.github.rvesse.airline.Cli<>(
                CommandLine.class);
        Runnable cmd = cli.parse(args);
        cmd.run();
    }
}