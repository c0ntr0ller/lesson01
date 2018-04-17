package spring.entities;

import com.github.thealchemist.pg_hibernate.HstoreType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.openstreetmap.osm._0.Node;
import org.openstreetmap.osm._0.Tag;
import org.springframework.data.domain.Persistable;
import spring.NodeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

//@NamedNativeQueries({
//        @NamedNativeQuery(name = "LocalNode.deleteAllNodes",
//                query = "DELETE FROM nodes")
//})
@Entity
@Table(name = "nodes", schema = "public")
//@TypeDef(name = "hstore", typeClass = HstoreType.class)
public class LocalNode implements  Persistable{
    @Id
    @Setter @Getter
    private BigInteger id;
    @Getter @Setter
    private Double lat;
    @Getter @Setter
    private Double lon;
    @Column(name = "\"user\"")
    @Getter @Setter
    private String user;
    @Getter @Setter
    private BigInteger uid;
    @Getter @Setter
    private Boolean visible;
    @Getter @Setter
    private BigInteger version;
    @Getter @Setter
    private BigInteger changeset;

    @Column(name = "timestamp", columnDefinition = "timestamp WITHOUT time zone")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date timestamp;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "node", cascade = CascadeType.ALL)
    @Getter @Setter
    private List<LocalTag> tagList = new ArrayList<>();

//    @Convert(converter = NodeConverter.class)
//    @Type(type = "hstore")
//    @Column(name = "tagsmap", columnDefinition = "hstore")
//    @Getter @Setter
//    private Map<String, String> tagsMap = new HashMap<>();

    public LocalNode(Node node){
        this.id = node.getId();
        this.lat = node.getLat();
        this.lon = node.getLon();
        this.user = node.getUser();
        this.uid = node.getUid();
        this.visible = node.isVisible();
        this.version = node.getVersion();
        this.changeset = node.getChangeset();
        this.timestamp = new Date(node.getTimestamp().toGregorianCalendar().getTimeInMillis());
        for (Tag xmlTag : node.getTag()) {
            tagList.add(new LocalTag(xmlTag, this));
//            tagsMap.put(xmlTag.getK(), xmlTag.getV());
        }
    }

    @Override
    public boolean isNew() {
        return true; //null == this.getId();
    }

    public LocalNode() {
    }

    @Override
    public BigInteger getId() {
        return id;
    }
}
