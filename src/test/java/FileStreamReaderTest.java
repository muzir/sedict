import org.junit.jupiter.api.Test;
import org.stackoverflowdata.loader.postgres.FilePath;
import org.stackoverflowdata.loader.postgres.xml.FileStreamReader;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileStreamReaderTest {

    private FileStreamReader fileStreamReader;
    private final static String fileLocation = "Tags-test.xml";

    @Test
    void shouldGetFileStreamFromFileLocation() throws FileNotFoundException, XMLStreamException {
        fileStreamReader = new FileStreamReader(new FilePath(fileLocation));
        var xmlStreamReader = fileStreamReader.getXmlStreamReader();
        assertNotNull(xmlStreamReader);
    }
}
