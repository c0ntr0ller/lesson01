package spring.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import spring.entities.LocalNode;

import java.math.BigInteger;

public interface RestLocalNodeRepository extends PagingAndSortingRepository<LocalNode, BigInteger> {
}
