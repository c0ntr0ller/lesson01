package DAO;

import org.openstreetmap.osm._0.Node;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class NodeDAOExecute implements NodeDAO {
    private static final String INSERT_QUERY_FIRST_PART =
            "insert into nodes_execute(id, lat, lon, \"user\", uid, visible, \"version\", changeset, \"timestamp\")values(%d, %.12g, %.12g, '%s', %d, %b, %d, %d, '%s')";

    public void insertNodeArray(List<Node> nodes, Connection connection) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try{
            Statement statement = connection.createStatement();
            for (Node node:nodes) {
                String sql_string = String.format(Locale.ROOT,
                        INSERT_QUERY_FIRST_PART,
                        node.getId(),
                        node.getLat(),
                        node.getLon(),
                        node.getUser(),
                        node.getUid(),
                        node.isVisible(),
                        node.getVersion(),
                        node.getChangeset(),
                        new Timestamp(node.getTimestamp().toGregorianCalendar().getTimeInMillis()).toLocalDateTime().format(dateFormatter));
                statement.execute(sql_string);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }

    }
}


