package m2tp.serietemp;

import com.fasterxml.jackson.databind.ObjectMapper;
import m2tp.serietemp.models.Serie;
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
public class SerieControllerTest {
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
    public void getSeriesScope() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/series")).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(200, 204);
        result = this.mockMvc.perform(get("/series/1")).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(200, 404);
    }

    @Test
    public void getSeriesMeScope() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/series/me")).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(200, 204, 401);
    }

    @Test
    public void getSeriesSharedScope() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/series/shared")).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(200, 204, 401);
    }

    @Test
    public void addSerieScope() throws Exception {
        Serie serie = new Serie((long) 1,"title","description", (long) 1);
        MvcResult result = this.mockMvc.perform(
                post("/series")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(serie))).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(201, 401, 400);
    }

    @Test
    public void deleteSerieScope() throws Exception {
        MvcResult result = this.mockMvc.perform(
                delete("/series/1")).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(200, 401, 403);
    }

    @Test
    public void editSerieScopte() throws Exception {
        Serie serie = new Serie((long) 1,"newtitle","newdescription", (long) 1);
        MvcResult result = this.mockMvc.perform(
                put("/series/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(serie))).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(200, 401, 403);
    }
}
