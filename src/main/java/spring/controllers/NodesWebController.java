package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.repository.RestLocalNodeRepository;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "/nodes")
public class NodesWebController {
    @Autowired
    RestLocalNodeRepository restLocalNodeRepository;

    @RequestMapping(value = "/{nodeId}",method = RequestMethod.GET)
    String getNode(@PathVariable BigInteger nodeId){
        return restLocalNodeRepository.findOne(nodeId).toString();
    }
}
