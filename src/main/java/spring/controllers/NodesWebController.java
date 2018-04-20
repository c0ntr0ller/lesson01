package spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<?> getNode(@PathVariable BigInteger nodeId){
        return ResponseEntity.ok(restLocalNodeRepository.findOne(nodeId));
    }

    @RequestMapping(value = "/dist", method = RequestMethod.GET)
    ResponseEntity<?> getNodesInRange(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon,
                           @RequestParam("dist") Double dist, @RequestParam(name = "lim", defaultValue = "100") Integer lim){
        return ResponseEntity.ok(restLocalNodeRepository.findNodesInRange(lat, lon, dist, lim));
    }

    public void setRepo(RestLocalNodeRepository repo){
        this.restLocalNodeRepository = repo;
    }
}
