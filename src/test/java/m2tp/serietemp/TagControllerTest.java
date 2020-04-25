package m2tp.serietemp;

import com.fasterxml.jackson.databind.ObjectMapper;
import m2tp.serietemp.models.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TagControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

    public static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findTagScope () throws Exception {
        MvcResult result = this.mockMvc.perform(get("/tags")).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(200, 204);
        MvcResult result2 = this.mockMvc.perform(get("/tags/all/1")).andReturn();
        assertThat(result2.getResponse().getStatus()).isIn(200, 204);
        MvcResult result3 = this.mockMvc.perform(get("/tags/1")).andReturn();
        assertThat(result3.getResponse().getStatus()).isIn(200, 204);
    }

    @Test
    public void deleteTagScope() throws  Exception {
        MvcResult result = this.mockMvc.perform(delete("/tags/1")).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(200, 404);
        MvcResult result2 = this.mockMvc.perform(delete("/tags/all/1")).andReturn();
        assertThat(result2.getResponse().getStatus()).isIn(200, 404);
    }
}
