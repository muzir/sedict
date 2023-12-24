package org.stackoverflowdata.loader.postgres;

import org.stackoverflowdata.loader.postgres.xml.FileStreamParser;
import org.stackoverflowdata.loader.postgres.xml.FileStreamReader;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        CommandLineHandler commandLineHandler = new CommandLineHandler(args);

        // Access the values based on the flags
        String filePath = commandLineHandler.getFilePath();

        if (filePath != null) {
            System.out.println("File Path: " + filePath);
            processFileContent(filePath);
        } else {
            System.out.println("Usage: java -jar sedict-0.1.jar --file filePath");
        }
    }

    private static void processFileContent(String fileName) throws XMLStreamException, FileNotFoundException {
        // https://docs.oracle.com/javase/tutorial/jaxp/limits/limits.html, totalEntitySizeLimit, jdk.xml.totalEntitySizeLimit
        // A positive integer. A value less than or equal to 0 indicates no limit. If the value is not an integer, a NumericFormatException is thrown.
        System.setProperty("jdk.xml.totalEntitySizeLimit", "0");
        var xmlStreamReader = new FileStreamReader(fileName).getXmlStreamReader();
        new FileStreamParser(xmlStreamReader).parseXmlData();
    }
}