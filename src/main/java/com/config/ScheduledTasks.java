package com.config;

/**
 * Created by simon on 6/8/2019.
 */
import com.service.DataFillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@EnableScheduling
@Component
public class ScheduledTasks {
    @Inject
    private DataFillService dataFillService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    private static boolean needToRunStartupMethod = true;

    @Scheduled(fixedRate = 3600000)
    public void keepAlive() {
        //log "alive" every hour for sanity checks
        LOGGER.debug("Create R oot user");
        dataFillService.usuarioRoot("root@gmail.com");
    }

    public void runOnceOnlyOnStartup() {
        LOGGER.debug("running startup job");
    }
}
