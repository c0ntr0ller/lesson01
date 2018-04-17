package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import spring.entities.LocalNode;

import java.math.BigInteger;

//@Transactional
public interface NodeRepository extends JpaRepository<LocalNode, BigInteger> {
//    @Transactional
//    void deleteAllNodes();
}
