package oldstyle;

import oldstyle.DAO.NodeDAO;
import oldstyle.DAO.NodeDAOBatch;
import oldstyle.dbservices.DBService;
import org.openstreetmap.osm._0.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xmlutils.XMLFileReader;

import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class MainClass {
    private static Logger logger = LoggerFactory.getLogger(MainClass.class);

    public static void main(String[] args) throws Exception {
        long totalCnt = 0;
        final int arraySize = 10000;
        LocalDateTime startDateTime = LocalDateTime.now();

        logger.info("Start ..." + startDateTime.toString());

        String fileName = args.length > 0?args[0]:null;

        try(XMLFileReader xmlFileReader = new XMLFileReader(fileName);
            Connection connection = DBService.instance().getConnection()){

            while (xmlFileReader.hasNext()) {
                logger.info("Nodes read started...");

                List<Node> nodeList = xmlFileReader.readNodesFromStream(arraySize);

                logger.info("Nodes read complete. Nodes readed:" + nodeList.size());
                totalCnt = totalCnt + nodeList.size();

                long start_time3 = System.nanoTime();
                NodeDAO nodeDAO3 = new NodeDAOBatch();

                nodeDAO3.insertNodeArray(nodeList, connection);

                long end_time3 = System.nanoTime();
                long diff3 = (end_time3 - start_time3);
                logger.info("diff in ms:" + diff3);
            }
        }

        LocalDateTime finishDateTime = LocalDateTime.now();

        logger.info("Finished..." + finishDateTime.toString());

        long s = Duration.between(startDateTime, finishDateTime).getSeconds();
        logger.info(String.format("Duration: %d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60)));

        logger.info("Records inserted:" + totalCnt);
    }
}
