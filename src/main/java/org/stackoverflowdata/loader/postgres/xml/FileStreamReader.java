package org.stackoverflowdata.loader.postgres.xml;

import org.stackoverflowdata.loader.postgres.FilePath;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileStreamReader {

    private final FileInputStream fileInputStream;

    public FileStreamReader(FilePath filePath) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filePath.getFileName()).getFile());
        this.fileInputStream = new FileInputStream(file);
    }

    public FileStreamReader(String fileLocation) throws FileNotFoundException {
        this.fileInputStream = new FileInputStream(fileLocation);
    }


    public XMLStreamReader getXmlStreamReader() throws XMLStreamException {
        return XMLInputFactory.newInstance().createXMLStreamReader(
                fileInputStream);
    }
}
