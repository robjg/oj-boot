package org.oddjob.boot.config;

import org.oddjob.Oddjob;
import org.oddjob.arooa.convert.ArooaConversionException;
import org.oddjob.rest.model.OddjobTracker;
import org.oddjob.rest.model.OddjobTrackerLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
public class ControllerConfig {

    private static Logger logger = LoggerFactory.getLogger(ControllerConfig.class);

    @Value("${oj.config:oddjob.xml}")
    String config;

    @Bean
    public ApiControl createWebRoot() throws ArooaConversionException {

        File configFile = new File(config);

        if (!configFile.exists()) {
            throw new IllegalArgumentException("No file " + config);
        }

        logger.info("Loading Odjob from " + config);

        Oddjob oddjob = new Oddjob();

        oddjob.setFile(configFile);

        oddjob.load();

        OddjobTrackerLocal oddjobTracker = new OddjobTrackerLocal(
                oddjob.provideBeanDirectory(), oddjob);
        oddjobTracker.track(oddjob);

        AtomicBoolean ready = new AtomicBoolean();

        new Thread(() -> {
            oddjob.run();
            ready.set(true);
        });

        return new ApiControl() {

            @Override
            public OddjobTracker getOddjobTracker() {
                return oddjobTracker;
            }

            @Override
            public File getUploadDirectory() {
                return new File(".");
            }

            @Override
            public boolean getReady() {
                return ready.get();
            }

            @Override
            public void destroy() throws Exception {
                oddjob.destroy();
            }
        };
    }

}
