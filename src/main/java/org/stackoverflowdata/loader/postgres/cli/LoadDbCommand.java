package org.stackoverflowdata.loader.postgres.cli;

import com.github.rvesse.airline.HelpOption;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;
import com.github.rvesse.airline.annotations.restrictions.AllowedRawValues;
import com.github.rvesse.airline.parser.errors.ParseException;
import org.stackoverflowdata.loader.postgres.xml.FileStreamReader;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
    @AllowedRawValues(allowedValues = {"posts.xml", "Tags.xml"})
    protected List<String> files = new ArrayList<>();

    @Override
    public void run() {
        if (!help.showHelpIfRequested())
            files.forEach(file -> {
                System.out.println("Loading file: " + file);
                try {
                    FileStreamReader fileStreamReader = new FileStreamReader(file);
                    fileStreamReader.processFileContent();
                } catch (XMLStreamException e) {
                    throw new ParseException(e.getMessage());
                } catch (FileNotFoundException e) {
                    throw new ParseException(e.getMessage());
                }
            });
    }
}
