package org.stackoverflowdata.loader.postgres.xml;

import org.stackoverflowdata.loader.postgres.PostgresDataSource;
import org.stackoverflowdata.loader.postgres.stackoverflow.StackoverflowTableFactory;

import javax.sql.DataSource;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class FileStreamParser {

    private static final String ROW_ELEMENT = "row";
    public static final long BATCH_SIZE = 50_000l;
    private final XMLStreamReader xmlStreamReader;
    private final DataSource dataSource;

    public FileStreamParser(XMLStreamReader xmlStreamReader, DataSource dataSource) {
        this.xmlStreamReader = xmlStreamReader;
        this.dataSource = dataSource;
    }

    public FileStreamParser(XMLStreamReader xmlStreamReader) {
        this.xmlStreamReader = xmlStreamReader;
        var pgDataSource = new PostgresDataSource();
        this.dataSource = pgDataSource.getDataSource();
    }

    public void parseXmlData() throws XMLStreamException {
        // reading the data
        while (xmlStreamReader.hasNext()) {

            int eventCode = xmlStreamReader.next();

            // this triggers _users records_ logic
            //
            if (isStartElement(eventCode)) {
                String localName = xmlStreamReader.getLocalName();
                var stackoverflowTable = new StackoverflowTableFactory(localName, dataSource).getStackoverflowTable();

                // read and parse the user data rows
                //
                long batchCounter = 0;
                while (xmlStreamReader.hasNext()) {

                    eventCode = xmlStreamReader.next();

                    // this breaks _users record_ reading logic
                    //
                    if (isEndElement(eventCode)
                            &&
                            xmlStreamReader.getLocalName().equalsIgnoreCase(stackoverflowTable.getXmlElementName())) {
                        break;
                    } else {

                        if ((XMLStreamConstants.START_ELEMENT == eventCode)
                                && xmlStreamReader.getLocalName().equalsIgnoreCase(ROW_ELEMENT)) {
                            batchCounter++;
                            stackoverflowTable.addRow(xmlStreamReader);
                        }
                    }

                    if ((batchCounter % BATCH_SIZE) == 0l) {
                        System.out.println("Saving batchCounter:" + batchCounter);
                        stackoverflowTable.saveBatch();
                        System.out.println("BatchCounter:" + batchCounter + " saved");
                    }
                }
                stackoverflowTable.saveBatch();
            }
        }
    }

    private static boolean isEndElement(int eventCode) {
        return XMLStreamConstants.END_ELEMENT == eventCode;
    }

    private static boolean isStartElement(int eventCode) {
        return XMLStreamConstants.START_ELEMENT == eventCode;
    }
}
