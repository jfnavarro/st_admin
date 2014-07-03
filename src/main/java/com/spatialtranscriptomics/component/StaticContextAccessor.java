/*
*Copyright © 2014 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/


package com.spatialtranscriptomics.component;

import com.spatialtranscriptomics.model.Account;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Enables static access to beans (which typically have been autowired as single instances).
 */
@Component
public class StaticContextAccessor {

    private static StaticContextAccessor instance;

    private static Account currentUser;
    
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void registerInstance() {
        instance = this;
    }

    /**
     * Enables static access of application-context beans.
     * @param <T> bean class type.
     * @param clazz class name.
     * @return the bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return instance.applicationContext.getBean(clazz);
    }
    
    public static void setCurrentUser(Account user) {
        currentUser = user;
    }
    
    public static Account getCurrentUser() {
        return currentUser;
    }

}
