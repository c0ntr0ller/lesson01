package xmlutils;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.openstreetmap.osm._0.Node;
import org.springframework.beans.factory.annotation.Autowired;
import spring.ConfigClass;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class XMLFileReader implements AutoCloseable {

    @Autowired
    private ConfigClass configClass;

    private BufferedInputStream bis;
    private XMLStreamReader xmlStreamReader;
    private Unmarshaller jaxbUnmarshaller;

    public XMLFileReader() throws IOException, XMLStreamException, JAXBException {
//        if(configClass.getFileName() == null || inFileName.isEmpty()) inFileName = "data/RU-NVS.osm.bz2";
        String inFileName = configClass.getFileName();
        File inFile = new File(inFileName);

        if (!Files.exists(inFile.toPath())) {
            throw new IOException(String.format("File not found: %s", inFileName));
        }

        bis = new BufferedInputStream(new FileInputStream(inFile));
        // подготовка к чтению файла
        InputStream iStream = new BZip2CompressorInputStream(bis);

        XMLInputFactory factory = XMLInputFactory.newInstance();

        xmlStreamReader = factory.createXMLStreamReader(iStream);

        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();

    }

    public XMLFileReader(String fileName) throws IOException, XMLStreamException, JAXBException {
        String inFileName = (fileName == null || fileName.isEmpty()) ? "data/RU-NVS.osm.bz2" : fileName;
        File inFile = new File(inFileName);

        if (!Files.exists(inFile.toPath())) {
            throw new IOException(String.format("File not found: %s", inFileName));
        }

        bis = new BufferedInputStream(new FileInputStream(inFile));
        // подготовка к чтению файла
        InputStream iStream = new BZip2CompressorInputStream(bis);

        XMLInputFactory factory = XMLInputFactory.newInstance();

        xmlStreamReader = factory.createXMLStreamReader(iStream);

        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();

    }

    public List<Node> readNodesFromStream(int arraySize) throws XMLStreamException, JAXBException {
        if(xmlStreamReader == null || !xmlStreamReader.hasNext()){
            return null;
        }
        XsiTypeReader xtr = new XsiTypeReader(xmlStreamReader);
        // лист для результата
        List<Node> nodeList = new ArrayList<>();
        // указатель на текущую ноду
        Node node = null;
        // счетчик чтений
        int count = 0;

        // если в ридере еще есть что читать
        while (xmlStreamReader.hasNext()) {
            try {
                // читаем следующий
                xmlStreamReader.next();
                // если элемент стартовый и его имя node
                if (xmlStreamReader.isStartElement() &&
                        xmlStreamReader.getLocalName().equalsIgnoreCase("node")) {
                    // создаем объект тип LocalNode
                    node = (Node) jaxbUnmarshaller.unmarshal(xtr);
                    // добавляем в лист ноду
                    nodeList.add(node);
                    // инкрементируем счетчик
                    count++;
                    if(count%arraySize == 0){
                        return nodeList;
                    }
                }
                // если достигли установленного размера - возвращаем лист
            } catch (XMLStreamException e1) {
                e1.printStackTrace();
            }
        }
        // закрываем ридер, если больше нечего читать
        xmlStreamReader.close();
        // возвращаем лист с остатком
        return nodeList;
    }

    public boolean hasNext() throws XMLStreamException {
        return xmlStreamReader.hasNext();
    }
    @Override
    public void close() throws Exception {
        bis.close();
        xmlStreamReader.close();
    }
}