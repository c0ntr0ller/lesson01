package spring.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.openstreetmap.osm._0.Tag;
import javax.persistence.*;
import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="tags", schema = "public")
@Data
public class LocalTag {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private BigInteger id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "node_id", referencedColumnName = "id")
    @Getter @Setter
    private LocalNode node;

    @Getter @Setter @JsonProperty
    private String k;

    @Getter @Setter @JsonProperty
    private String v;

    public LocalTag() {
    }

    public LocalTag(Tag xmlTag, LocalNode node) {
        this.k = xmlTag.getK();
        this.v = xmlTag.getV();
        this.node = node;
    }
}
