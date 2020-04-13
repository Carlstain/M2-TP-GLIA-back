package m2tp.serietemp;

import m2tp.serietemp.models.Event;
import m2tp.serietemp.models.Serie;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RestRepositoryConfigurator implements  RepositoryRestConfigurer{

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Serie.class);
        config.exposeIdsFor(Event.class);
    }
}