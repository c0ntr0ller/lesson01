package spring.entities;

import org.hibernate.annotations.Type;
import org.openstreetmap.osm._0.Node;
import org.openstreetmap.osm._0.Tag;
import spring.NodeConverter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.*;

@Entity
@Table(name = "nodes")
public class LocalNode {
//    @Convert(converter = NodeConverter.class)
//    @Column(name = "tagsmap")
//    private Map<String, String> tagsMap = new HashMap<>();
    @Id
    private BigInteger id;
    private Double lat;
    private Double lon;
    @Column(name = "\"user\"")
    private String user;
    private BigInteger uid;
    private Boolean visible;
    private BigInteger version;
    private BigInteger changeset;
    @Column(name = "timestamp", columnDefinition = "timestamp WITHOUT time zone")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

//    @OneToMany
//    private List<LocalTag> tagList = new ArrayList<>();
//
    public LocalNode(Node node){

//        for (Tag xmlTag : node.getTag()) {
////            tagsMap.put(xmlTag.getK(),xmlTag.getV());
//            tagList.add(new LocalTag(xmlTag, node.getId()));
//        }

        this.id = node.getId();
        this.lat = node.getLat();
        this.lon = node.getLon();
        this.user = node.getUser();
        this.uid = node.getUid();
        this.visible = node.isVisible();
        this.version = node.getVersion();
        this.changeset = node.getChangeset();
        this.timestamp = new Date(node.getTimestamp().toGregorianCalendar().getTimeInMillis());
    }

    public LocalNode() {
    }

//    public Map<String, String> getTagsMap() {
//        return tagsMap;
//    }

//
//    public List<LocalTag> getTagList() {
//        return tagList;
//    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BigInteger getUid() {
        return uid;
    }

    public void setUid(BigInteger uid) {
        this.uid = uid;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getChangeset() {
        return changeset;
    }

    public void setChangeset(BigInteger changeset) {
        this.changeset = changeset;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
