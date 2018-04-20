import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import spring.SpringMain;
import spring.controllers.NodesWebController;
import spring.entities.LocalNode;
import spring.repository.RestLocalNodeRepository;

//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import java.math.BigInteger;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringMain.class})
@WebAppConfiguration
public class TestNodesWebController {
    private MockMvc mock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private RestLocalNodeRepository restLocalNodeRepository;

    @Autowired
    private NodesWebController nodesWebController;

    @Before
    public void setup(){
        this.mock = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        restLocalNodeRepository = Mockito.mock(RestLocalNodeRepository.class);

        when(restLocalNodeRepository.findOne(BigInteger.valueOf(54)))
                .thenReturn(new LocalNode(BigInteger.valueOf(54)));
        when(restLocalNodeRepository.findOne(BigInteger.valueOf(0)))
                .thenReturn(null);
        nodesWebController.setRepo(restLocalNodeRepository);
    }

    @Test
    public void testNodeNotFound() throws Exception {
        mock.perform(get("/nodes/0"))
                .andExpect(content().contentType("APPLICATION_JSON"))
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void testFound54() throws Exception {
        mock.perform(get("/nodes/54"))
                .andExpect(content()
                        .contentType("APPLICATION_JSON"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id","54)"));
    }
}
