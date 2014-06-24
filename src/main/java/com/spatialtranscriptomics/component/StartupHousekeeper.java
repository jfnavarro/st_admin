/*
*Copyright © 2014 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.component;

import org.joda.time.DateTimeZone;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Household class for making one-time chores related to the app startup.
 */
@Component
public class StartupHousekeeper implements ApplicationListener<ContextRefreshedEvent> {

        /**
         * This method should be invoked more or less at app startup.
         * @param event .
         */
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//System.out.println("Setting UTC!");
		DateTimeZone.setDefault(DateTimeZone.UTC);
	}

  
}