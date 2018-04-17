package spring.controllers;

import org.openstreetmap.osm._0.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import spring.SpringMain;
import spring.entities.LocalNode;
import spring.repository.LocalNodeRepository;
import xmlutils.XMLFileReader;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class XmlImportController {
    private static Logger logger = LoggerFactory.getLogger(SpringMain.class);


    @Value("${readcount}")
    private int arraySize;
    @Value("${xmlfilename}")
    private String inFileName;
    @Bean
    @Transactional
    public CommandLineRunner nodeProceed(LocalNodeRepository localNodeRepository) {
        return (String... args) ->{
            // открываем файл с данными
            long totalCnt = 0;

            logger.info("Clear data, wait ...");
            localNodeRepository.deleteAllInBatch();

            LocalDateTime startDateTime = LocalDateTime.now();
            logger.info("Start ..." + startDateTime.toString());

            try(XMLFileReader xmlFileReader = new XMLFileReader(inFileName)) {
                long start_nanotime = System.nanoTime();
                // бежим по файлу и создаем объекты
                while (xmlFileReader.hasNext()) {
                    logger.info("Nodes read started...");

                    List<Node> nodeList = xmlFileReader.readNodesFromStream(arraySize);

                    List<LocalNode> localNodeList = nodeList.stream().map(LocalNode::new).collect(Collectors.toList());

//                    logger.info("Nodes read complete. Nodes readed:" + nodeList.size());
                    totalCnt = totalCnt + localNodeList.size();

                    // сохраняем их в БД
                    localNodeRepository.save(localNodeList);

                    localNodeList.clear();

                    long end_nanotime = System.nanoTime();

                    long duration = ((end_nanotime - start_nanotime)/1000000000);
                    long diff = 0;
                    if(duration > 0){
                        diff = totalCnt / duration;
                    }

                    logger.info(String.format("Records inserted: %d; Avg. speed: %d records/sec", totalCnt, diff));
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
