package org.stackoverflowdata.loader.postgres;

import com.github.rvesse.airline.annotations.Cli;
import com.github.rvesse.airline.help.Help;
import com.github.rvesse.airline.parser.errors.ParseException;
import org.stackoverflowdata.loader.postgres.cli.examples.DatabaseSetupCommand;
import org.stackoverflowdata.loader.postgres.cli.examples.LoggingCommand;
import org.stackoverflowdata.loader.postgres.xml.FileStreamParser;
import org.stackoverflowdata.loader.postgres.xml.FileStreamReader;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

@Cli(name = "sedict",
        description = "Stack exchange database import command line tool",
        defaultCommand = Help.class,
        commands = {DatabaseSetupCommand.class, LoggingCommand.class, Help.class})
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

    private static void processFileContent(String fileName) throws XMLStreamException, FileNotFoundException {
        // https://docs.oracle.com/javase/tutorial/jaxp/limits/limits.html, totalEntitySizeLimit, jdk.xml.totalEntitySizeLimit
        // A positive integer. A value less than or equal to 0 indicates no limit. If the value is not an integer, a NumericFormatException is thrown.
        System.setProperty("jdk.xml.totalEntitySizeLimit", "0");
        var xmlStreamReader = new FileStreamReader(fileName).getXmlStreamReader();
        new FileStreamParser(xmlStreamReader).parseXmlData();
    }
}