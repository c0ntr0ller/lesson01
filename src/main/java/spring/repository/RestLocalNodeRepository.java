package spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import spring.entities.LocalNode;

import java.math.BigInteger;
import java.util.List;

public interface RestLocalNodeRepository extends PagingAndSortingRepository<LocalNode, BigInteger> {
    @Query(value = "SELECT * FROM nodes WHERE earth_distance(ll_to_earth(:lat, :lon), ll_to_earth(lat, lon)) < :dist limit(:lim)",
            nativeQuery = true)
    List<LocalNode> findNodesInRange(@Param("lat") Double lat, @Param("lon") Double lon, @Param("dist") Double dist, @Param("lim") Integer lim);
}
