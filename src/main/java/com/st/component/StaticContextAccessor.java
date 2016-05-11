package com.st.component;

import com.st.model.Account;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Enables static access to beans (which typically have been autowired as single instances).
 * It provides access to the current user, among other things.
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
    
    /**
     * Sets the current user, for global access.
     * @param user the user.
     */
    public static void setCurrentUser(Account user) {
        currentUser = user;
    }
    
    /**
     * Returns the current user, for global access.
     * @return 
     */
    public static Account getCurrentUser() {
        return currentUser;
    }

}
