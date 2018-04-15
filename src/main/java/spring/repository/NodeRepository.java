package spring.repository;

import org.springframework.data.repository.CrudRepository;
import spring.entities.LocalNode;

import java.math.BigInteger;

public interface NodeRepository extends CrudRepository<LocalNode, BigInteger> {
//    public void saveAll(List<LocalNode> localNodes);
}
