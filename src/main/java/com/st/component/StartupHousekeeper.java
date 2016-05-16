package com.st.component;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Household class for making one-time chores related to the Admin application
 * startup.
 */
@Component
public class StartupHousekeeper implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = Logger
            .getLogger(StartupHousekeeper.class);
    
    /**
     * This method should be invoked more or less at Admin application startup.
     *
     * @param event refresh event.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Setting the default time zone to UTC.");
        DateTimeZone.setDefault(DateTimeZone.UTC);
    }

}
