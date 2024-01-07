package org.stackoverflowdata.loader.postgres;

import com.github.rvesse.airline.parser.errors.ParseRestrictionViolatedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandLineTest {
    @Test
    void shouldThrowParseRestrictionViolatedException_whenDatabaseNameIsMissing() {
        String[] args = new String[]{"setup-db", "-t", "postgres"};
        assertThrows(ParseRestrictionViolatedException.class, () ->
                CommandLine.runCommand(args));
    }

    @Test
    void shouldThrowParseRestrictionViolatedException_whenDatabaseTypeIsNotSupported2() {
        String[] args = new String[]{"setup-db", "-t", "mysql"};
        assertThrows(ParseRestrictionViolatedException.class, () ->
                CommandLine.runCommand(args));
    }

    @Test
    void shouldThrowParseRestrictionViolatedException_whenLoadDbFilesAreNotSupported() {
        String[] args = new String[]{"load-db", "-f", "post.xml"};
        assertThrows(ParseRestrictionViolatedException.class, () ->
                CommandLine.runCommand(args));
    }

    @Test
    void shouldNotThrowParseRestrictionViolatedException_whenLoadDbFilesAreSupported() {
        String[] args = new String[]{"load-db", "-f", "posts.xml", "-f", "tags.xml"};
        assertDoesNotThrow(() ->
                CommandLine.runCommand(args));
    }
}