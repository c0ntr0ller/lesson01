package spring.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.openstreetmap.osm._0.Node;
import org.openstreetmap.osm._0.Tag;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.*;

//@NamedNativeQueries({
//        @NamedNativeQuery(name = "LocalNode.deleteAllNodes",
//                query = "DELETE FROM nodes")
//})

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "nodes", schema = "public")
@Data
public class LocalNode implements  Persistable{
    @Id
    @Setter @Getter @JsonProperty
    private BigInteger id;
    @Getter @Setter @JsonProperty
    private Double lat;
    @Getter @Setter @JsonProperty
    private Double lon;
    @Column(name = "\"user\"")
    @Getter @Setter @JsonProperty
    private String user;
    @Getter @Setter @JsonProperty
    private BigInteger uid;
    @Getter @Setter @JsonProperty
    private Boolean visible;
    @Getter @Setter @JsonProperty
    private BigInteger version;
    @Getter @Setter @JsonProperty
    private BigInteger changeset;

    @Column(name = "timestamp", columnDefinition = "timestamp WITHOUT time zone")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter @JsonProperty
    private Date timestamp;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "node", cascade = CascadeType.ALL)
    @Getter @Setter @JsonProperty
    private List<LocalTag> tagList = new ArrayList<>();

    @Convert(converter = NodeConverter.class)
    @Column(name = "tagsmap")
    @Getter @Setter
    private Map<String, String> tagsMap = new HashMap<>();

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
            tagsMap.put(xmlTag.getK(), xmlTag.getV());
        }
    }

    public LocalNode(BigInteger id) {
        this.id = id;
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
