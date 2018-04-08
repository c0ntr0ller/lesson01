package DAO;

import dbservices.DBService;
import org.openstreetmap.osm._0.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class NodeDAOPrepared implements NodeDAO {
    private static final String INSERT_QUERY = "insert into nodes_prepared(id, lat, lon, \"user\", uid, visible, \"version\", changeset, \"timestamp\")values(?,?,?,?,?,?,?,?,?)";
    @Override
    public void insertNodeArray(List<Node> nodes) {
        try(Connection connection = DBService.instance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);

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

                statement.execute();
            }
        } catch (IllegalAccessException | SQLException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
