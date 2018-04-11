package spring.repository;

import org.springframework.data.repository.CrudRepository;
import spring.entities.LocalNode;

import java.util.List;

public interface NodeRepository extends CrudRepository {
    public void saveAll(List<LocalNode> localNodes);
}
