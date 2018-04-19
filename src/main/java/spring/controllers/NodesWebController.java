package spring.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(value = "/dist", method = RequestMethod.GET)
    String getNodesInRange(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon,
                           @RequestParam("dist") Double dist, @RequestParam(name = "lim", defaultValue = "100") Integer lim){
        String result = "";
        try {
            result = mapper.writeValueAsString(restLocalNodeRepository.findNodesInRange(lat, lon, dist, lim));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
