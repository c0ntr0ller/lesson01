package spring.entities;

import org.openstreetmap.osm._0.Node;
import org.openstreetmap.osm._0.Tag;
import spring.NodeConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class LocalNode {
    @Convert(converter = NodeConverter.class)
    private Map<String, String> tagsMap;
    private BigInteger id;
    private Double lat;
    private Double lon;
    private String user;
    private BigInteger uid;
    private Boolean visible;
    private BigInteger version;
    private BigInteger changeset;
    private XMLGregorianCalendar timestamp;

    public LocalNode(Node node){
        this.tagsMap = new HashMap<>();
        for (Tag oldTag : node.getTag()) {
            tagsMap.put(oldTag.getK(),oldTag.getV());
        }
        this.id = node.getId();
        this.lat = node.getLat();
        this.lon = node.getLon();
        this.user = node.getUser();
        this.uid = node.getUid();
        this.visible = node.isVisible();
        this.version = node.getVersion();
        this.changeset = node.getChangeset();
        this.timestamp = getTimestamp();
    }

    public LocalNode(Object e) {
    }

    public Map<String, String> getTagsMap() {
        return tagsMap;
    }

    public void setTagsMap(List<Tag> tagsMap) {
        this.tagsMap.clear();
        for (Tag oldTag : tagsMap) {
            Tag newTag = new Tag();
            newTag.setK(oldTag.getK());
            newTag.setV(oldTag.getV());
        }
    }

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

    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(XMLGregorianCalendar timestamp) {
        this.timestamp = timestamp;
    }
}
