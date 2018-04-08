import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

public class XsiTypeReader extends StreamReaderDelegate {

    public XsiTypeReader(XMLStreamReader reader) {
        super(reader);
    }

    @Override
    public String getNamespaceURI() {
        return "http://openstreetmap.org/osm/0.6";
    }
}