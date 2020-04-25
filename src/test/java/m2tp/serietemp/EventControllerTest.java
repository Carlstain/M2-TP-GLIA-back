package m2tp.serietemp;

import com.fasterxml.jackson.databind.ObjectMapper;
import m2tp.serietemp.models.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EventControllerTest {
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
    public void findEventScope() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/events")).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(200, 204);
        MvcResult result2 = this.mockMvc.perform(get("/events/1")).andReturn();
        assertThat(result2.getResponse().getStatus()).isIn(200, 404);
        MvcResult result3 = this.mockMvc.perform(get("/series/1/events")).andReturn();
        assertThat(result3.getResponse().getStatus()).isIn(200, 204);
    }

    @Test
    public void addEventScope() throws Exception {
        Event event = new Event();
        event.setSerieId((long) 1);
        event.setId((long) 0);
        event.setMoment(new Date());
        event.setRecord((double) 0);
        MvcResult result = this.mockMvc.perform(
                post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(event))).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(201, 400, 401, 403);
    }

    @Test
    public void deleteEventScope() throws Exception {
        MvcResult result = this.mockMvc.perform(
                delete("/events/id")).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(201, 400, 401, 403);
    }

    @Test
    public void editEventScope() throws Exception {
        Event event = new Event();
        event.setRecord((double) 1);
        MvcResult result = this.mockMvc.perform(
                put("/events/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(event))).andReturn();
        assertThat(result.getResponse().getStatus()).isIn(200, 401, 403, 400);
    }

}
