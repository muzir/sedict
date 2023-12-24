package org.stackoverflowdata.loader.postgres.stackoverflow;

import javax.xml.stream.XMLStreamReader;

public interface StackoverflowTable {
    String getXmlElementName();

    void addRow(XMLStreamReader xmlStreamReader);

    void saveBatch();
}
