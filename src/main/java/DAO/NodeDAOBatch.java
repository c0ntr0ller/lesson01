package DAO;

import dbservices.DBService;
import org.openstreetmap.osm._0.Node;
import org.openstreetmap.osm._0.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class NodeDAOBatch implements NodeDAO {
    private static final String NODE_INSERT_QUERY = "insert into nodes(id, lat, lon, \"user\", uid, visible, \"version\", changeset, \"timestamp\")values(?,?,?,?,?,?,?,?,?)";
    private static final String TAG_INSERT_QUERY = "insert into tags(nodeid, k, v)values(?,?,?)";

    private static final int BATCH_SIZE = 1000;

    @Override
    public void insertNodeArray(List<Node> nodes) {
        int count = 0;
        try(Connection connection = DBService.instance().getConnection()) {

            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(NODE_INSERT_QUERY);
            PreparedStatement tag_statement = connection.prepareStatement(TAG_INSERT_QUERY);

            for (Node node:nodes) {
                statement.setLong(1, node.getId().longValue());
                statement.setDouble(2, node.getLat());
                statement.setDouble(3, node.getLon());
                statement.setString(4, node.getUser());
                if(node.getUid() != null){
                    statement.setLong(5, node.getUid().longValue());
                }else statement.setObject(5, null);

                if(node.isVisible() != null) {
                    statement.setBoolean(6, node.isVisible());
                }else statement.setObject(6, null);

                if(node.getVersion() != null) {
                    statement.setLong(7, node.getVersion().longValue());
                }else statement.setObject(7, null);

                if(node.getChangeset() != null) {
                    statement.setLong(8, node.getChangeset().longValue());
                }else statement.setObject(8, null);

                if(node.getTimestamp() != null){
                    statement.setTimestamp(9, new Timestamp(node.getTimestamp().toGregorianCalendar().getTimeInMillis()));
                }else statement.setObject(9, null);
                statement.addBatch();

                if(node.getTag() != null){
                    for(Tag tag: node.getTag()){

                        tag_statement.setLong(1, node.getId().longValue());
                        if(tag.getK() != null){
                            tag_statement.setString(2, tag.getK());
                        }else tag_statement.setObject(2, null);
                        if(tag.getV() != null){
                            tag_statement.setString(3, tag.getV());
                        }else tag_statement.setObject(3, null);
                        tag_statement.addBatch();
                    }
                }

                count++;

                if (count%BATCH_SIZE == 0) {
                    statement.executeBatch();
                    tag_statement.executeBatch();
                    connection.commit();
                    count = 0;
                }
            }

            if(count > 0){
                statement.executeBatch();
                tag_statement.executeBatch();
                connection.commit();
            }
        } catch (IllegalAccessException | SQLException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
