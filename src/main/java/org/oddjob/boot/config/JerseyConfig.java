package org.oddjob.boot.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(OddjobController.class);
        register(StaticController.class);
    }

}
