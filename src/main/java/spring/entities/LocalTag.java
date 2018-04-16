package spring.entities;

import org.openstreetmap.osm._0.Tag;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="tags", schema = "public")
public class LocalTag {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private BigInteger id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "node_id", referencedColumnName = "id")
    private LocalNode node;

    private String k;
    private String v;

    public LocalTag() {
    }

    public LocalNode getNode() {
        return node;
    }

    public void setNode(LocalNode node) {
        this.node = node;
    }

    public LocalTag(Tag xmlTag, LocalNode node) {
        this.k = xmlTag.getK();
        this.v = xmlTag.getV();
        this.node = node;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
