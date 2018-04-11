package oldstyle.DAO;

import org.openstreetmap.osm._0.Node;

import java.sql.Connection;
import java.util.List;

public interface NodeDAO {
    public void insertNodeArray(List<Node> nodes, Connection connection);
    public default long insertNode(Node node){ return 0;}
    public default long updateNode(Node node){return 0;}
    public default void deleteNode(Node node){};
}
