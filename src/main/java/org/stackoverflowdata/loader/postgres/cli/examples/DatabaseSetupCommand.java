package org.stackoverflowdata.loader.postgres.cli.examples;

import com.github.rvesse.airline.HelpOption;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;
import com.github.rvesse.airline.annotations.restrictions.AllowedRawValues;
import com.github.rvesse.airline.annotations.restrictions.Required;
import com.github.rvesse.airline.annotations.restrictions.RequiredOnlyIf;
import org.stackoverflowdata.loader.postgres.PostgresDataSource;
import org.stackoverflowdata.loader.postgres.config.DatabaseConnectionRepository;

import java.sql.SQLException;

@Command(name = "setup-db", description = "Setup your database")
public class DatabaseSetupCommand implements Runnable {
    private HelpOption<DatabaseSetupCommand> help;

    public DatabaseSetupCommand() {
        this.help = new HelpOption<>();
    }

    @Required
    @Option(type = OptionType.COMMAND,
            name = {"--rdbms:host", "--host"},
            description = "Host to use for connection to RDBMS.",
            title = "RDBMS host")
    protected String rdbmsHost = "";

    @RequiredOnlyIf(names = {"--rdbms:host", "--host"})
    @Option(type = OptionType.COMMAND,
            name = {"-t", "--type"},
            description = "Type of RDBMS.",
            title = "RDBMS type: postgres")
    @AllowedRawValues(allowedValues = {"postgres"})
    protected String rdbmsType = "postgres";

    @RequiredOnlyIf(names = {"--rdbms:host", "--host"})
    @Option(type = OptionType.COMMAND,
            name = {"-d", "--database"},
            description = "Database name.",
            title = "Database name")
    protected String databaseName = "";

    @RequiredOnlyIf(names = {"--rdbms:host", "--host"})
    @Option(type = OptionType.COMMAND,
            name = {"--rdbms:user", "-u", "--user"},
            description = "User for login to RDBMS.",
            title = "RDBMS user")
    protected String rdbmsUser;

    @RequiredOnlyIf(names = {"--rdbms:host", "--host"})
    @Option(type = OptionType.COMMAND,
            name = {"--rdbms:password", "--password"},
            description = "Password for login to RDBMS.",
            title = "RDBMS password")
    protected String rdbmsPassword;

    @RequiredOnlyIf(names = {"--rdbms:host", "--host"})
    @Option(type = OptionType.COMMAND,
            name = {"--rdbms:port", "--port"},
            description = "Port for login to RDBMS.",
            title = "RDBMS port")
    protected String rdbmsPort;

    @Override
    public void run() {
        String databaseUrl = "jdbc:postgresql://" + rdbmsHost + ":" + rdbmsPort + "/" + databaseName;
        System.out.println("Connecting to database: " + databaseUrl);
        System.out.println("Credentials: " + rdbmsUser + " / " + rdbmsPassword);
        PostgresDataSource postgresDataSource = new PostgresDataSource(databaseUrl, rdbmsUser, rdbmsPassword);
        DatabaseConnectionRepository databaseConnectionRepository =
                new DatabaseConnectionRepository(postgresDataSource.getDataSource());
        try {
            databaseConnectionRepository.connect();
        } catch (SQLException e) {
            throw new RuntimeException(String.format(
                    "Failed to connect to database, check your database url %s and credentials, user: %, password:%s",
                    databaseUrl, rdbmsUser, rdbmsPassword));
        }
        System.out.println("Connection established: " + postgresDataSource.getDataSource().toString());
    }
}