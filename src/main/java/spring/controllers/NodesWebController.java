package spring.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private RestLocalNodeRepository restLocalNodeRepository;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/{nodeId}",method = RequestMethod.GET)
    String getNode(@PathVariable BigInteger nodeId){
        String result = null;
        try {
            result = mapper.writeValueAsString(restLocalNodeRepository.findOne(nodeId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
