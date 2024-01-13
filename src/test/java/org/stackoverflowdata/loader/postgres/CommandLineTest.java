package org.stackoverflowdata.loader.postgres;

import com.github.rvesse.airline.parser.errors.ParseRestrictionViolatedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandLineTest {
    @Test
    void shouldThrowParseRestrictionViolatedException_whenDatabaseNameIsMissing() {
        String[] args = new String[]{"setup-db", "--url", "localhost:5432", "-t", "postgres"};
        assertThrows(ParseRestrictionViolatedException.class, () ->
                CommandLine.runCommand(args));
    }

    @Test
    void shouldThrowParseRestrictionViolatedException_whenDatabaseTypeIsNotSupported2() {
        String[] args = new String[]{"setup-db", "--url", "localhost:5432", "-t", "mysql"};
        assertThrows(ParseRestrictionViolatedException.class, () ->
                CommandLine.runCommand(args));
    }

    @Test
    void shouldThrowParseRestrictionViolatedException_whenLoadDbFilesAreNotSupported() {
        String[] args = new String[]{"setup-db", "--url", "localhost:5432", "-f", "post.xml"};
        assertThrows(ParseRestrictionViolatedException.class, () ->
                CommandLine.runCommand(args));
    }
}