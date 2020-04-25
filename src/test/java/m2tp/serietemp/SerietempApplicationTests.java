package m2tp.serietemp;

import m2tp.serietemp.controller.EventsController;
import m2tp.serietemp.controller.SeriesController;
import m2tp.serietemp.controller.TagController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SerietempApplicationTests {

    @Autowired
    private EventsController eventsController;
    @Autowired
    private SeriesController seriesController;
    @Autowired
    private TagController tagController;
    @Test
    void contextLoads() {
        assertThat(eventsController).isNotNull();
        assertThat(seriesController).isNotNull();
        assertThat(tagController).isNotNull();
    }

}
