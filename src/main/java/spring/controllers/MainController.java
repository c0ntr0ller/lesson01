package spring.controllers;

import org.openstreetmap.osm._0.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import spring.ConfigClass;
import spring.SpringMain;
import spring.entities.LocalNode;
import spring.repository.NodeRepository;
import xmlutils.XMLFileReader;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private static Logger logger = LoggerFactory.getLogger(SpringMain.class);


    @Value("${readcount}")
    private int arraySize;
    @Value("${xmlfilename}")
    private String inFileName;
    @Bean
    public CommandLineRunner nodeProceed(NodeRepository nodeRepository) {
        return (String... args) ->{
            // открываем файл с данными
            long totalCnt = 0;

            LocalDateTime startDateTime = LocalDateTime.now();

            logger.info("Start ..." + startDateTime.toString());

            try(XMLFileReader xmlFileReader = new XMLFileReader(inFileName)) {
                // бежим по файлу и создаем объекты
                while (xmlFileReader.hasNext()) {
                    logger.info("Nodes read started...");

                    List<Node> nodeList = xmlFileReader.readNodesFromStream(arraySize);

                    List<LocalNode> localNodeList = nodeList.stream().map(LocalNode::new).collect(Collectors.toList());

                    logger.info("Nodes read complete. Nodes readed:" + nodeList.size());
                    totalCnt = totalCnt + nodeList.size();

                    long start_time3 = System.nanoTime();

                    // сохраняем их в БД
                    nodeRepository.save(localNodeList);

                    long end_time3 = System.nanoTime();
                    long diff3 = (end_time3 - start_time3);
                    logger.info("diff in ms:" + diff3);
                }
            }
        };
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }
}
