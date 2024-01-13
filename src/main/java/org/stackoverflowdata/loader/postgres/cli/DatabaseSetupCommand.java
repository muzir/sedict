package org.stackoverflowdata.loader.postgres.cli;

import com.github.rvesse.airline.HelpOption;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;
import com.github.rvesse.airline.annotations.restrictions.AllowedRawValues;
import com.github.rvesse.airline.annotations.restrictions.Required;
import com.github.rvesse.airline.annotations.restrictions.RequiredOnlyIf;
import com.github.rvesse.airline.parser.errors.ParseException;
import org.stackoverflowdata.loader.postgres.PostgresDataSource;
import org.stackoverflowdata.loader.postgres.config.DatabaseConnectionRepository;
import org.stackoverflowdata.loader.postgres.xml.FileStreamReader;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Command(name = "setup-db", description = "Setup your database")
public class DatabaseSetupCommand implements Runnable {
    private HelpOption<DatabaseSetupCommand> help;

    public DatabaseSetupCommand() {
        this.help = new HelpOption<>();
    }

    @Required
    @Option(type = OptionType.COMMAND,
            name = {"--rdbms:url", "--url"},
            description = "Url to use for connection to RDBMS.",
            title = "RDBMS url")
    protected String rdbmsUrl = "";

    @RequiredOnlyIf(names = {"--rdbms:url", "--url"})
    @Option(type = OptionType.COMMAND,
            name = {"-t", "--type"},
            description = "Type of RDBMS.",
            title = "RDBMS type: postgres")
    @AllowedRawValues(allowedValues = {"postgres"})
    protected String rdbmsType = "postgres";

    @RequiredOnlyIf(names = {"--rdbms:url", "--url"})
    @Option(type = OptionType.COMMAND,
            name = {"-d", "--database"},
            description = "Database name.",
            title = "Database name")
    protected String databaseName = "";

    @RequiredOnlyIf(names = {"--rdbms:url", "--url"})
    @Option(type = OptionType.COMMAND,
            name = {"--rdbms:user", "-u", "--user"},
            description = "User for login to RDBMS.",
            title = "RDBMS user")
    protected String rdbmsUser;

    @RequiredOnlyIf(names = {"--rdbms:url", "--url"})
    @Option(type = OptionType.COMMAND,
            name = {"--rdbms:password", "-p", "--password"},
            description = "Password for login to RDBMS.",
            title = "RDBMS password")
    protected String rdbmsPassword;

    @RequiredOnlyIf(names = {"--rdbms:url", "--url"})
    @Option(type = OptionType.COMMAND,
            name = {"-f", "--files"},
            description = "Load xml files to RDBMS tables.",
            title = "XML files")
    @AllowedRawValues(allowedValues = {"Posts.xml", "Tags.xml"})
    protected List<String> files = new ArrayList<>();

    @Override
    public void run() {
        if (help.showHelpIfRequested()) {
            return;
        }
        var connectionUrlPrefix = getConnectionUrlPrefix();
        var databaseUrlBuilder = new StringBuilder(connectionUrlPrefix);
        databaseUrlBuilder.append(rdbmsUrl).append("/").append(databaseName);
        System.out.println("Connecting to database: " + databaseUrlBuilder);
        System.out.println("Credentials: " + rdbmsUser + " / " + rdbmsPassword);
        var postgresDataSource =
                new PostgresDataSource(databaseUrlBuilder.toString(), rdbmsUser, rdbmsPassword);
        var databaseConnectionRepository =
                new DatabaseConnectionRepository(postgresDataSource.getDataSource());
        try {
            databaseConnectionRepository.connect();
            System.out.println("Connection tested successfully");
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
        } catch (SQLException e) {
            throw new RuntimeException(String.format(
                    "Failed to connect to database, check your database url %s and credentials, user: %, password:%s",
                    databaseUrlBuilder, rdbmsUser, rdbmsPassword));
        }
    }

    private String getConnectionUrlPrefix() {
        if (rdbmsType.equals("postgres"))
            return "jdbc:postgresql://";
        throw new RuntimeException("Unsupported database type: " + rdbmsType);
    }
}