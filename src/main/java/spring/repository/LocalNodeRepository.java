package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entities.LocalNode;

import java.math.BigInteger;

public interface LocalNodeRepository extends JpaRepository<LocalNode, BigInteger> {
}
