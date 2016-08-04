package com.st.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import com.st.model.Account;
import com.st.model.AccountId;
import com.st.service.AccountService;

/**
 * This class implements the store/retrieve logic to the ST API for the data
 * model class "Account". The connection to the ST API is handled in a
 * RestTemplate object, which is configured in mvc-dispatcher-servlet.xml
 */
@Service
public class AccountServiceImpl implements AccountService {

    // Note: General service URI logging is performed in CustomOAuth2RestTemplate.
    @SuppressWarnings("unused")
    private static final Logger logger = Logger
            .getLogger(AccountServiceImpl.class);

    @Autowired
    RestOperations secureRestTemplate;

    @Autowired
    Properties appConfig;

    @Override
    public Account find(String id) {
        // onlyEnabled=false to find also among disabled acccounts
        String url = appConfig.getProperty("url.account") + id + "?onlyEnabled=false";
        Account acc = secureRestTemplate.getForObject(url, Account.class);
        return acc;
    }
    
    @Override
    public Account current() {
        String url = appConfig.getProperty("url.account") + "current/user";
        Account acc = secureRestTemplate.getForObject(url, Account.class);
        return acc;
    }

    @Override
    public List<Account> list() {
        // list() returns also disabled accounts by default
        String url = appConfig.getProperty("url.account");
        Account[] accountArray = secureRestTemplate.getForObject(url, Account[].class);
        if (accountArray == null) {
            return null;
        }
        List<Account> accountList = Arrays.asList(accountArray);
        return accountList;
    }
    
    @Override
    public List<AccountId> listIds() {
        // get a list of account ids (id,name)
        String url = appConfig.getProperty("url.account") + "ids/";
        AccountId[] accountArray = secureRestTemplate.getForObject(url, AccountId[].class);
        if (accountArray == null) {
            return null;
        }
        List<AccountId> accountList = Arrays.asList(accountArray);
        return accountList;
    }

    @Override
    public Account add(Account acc) {
        String url = appConfig.getProperty("url.account");
        Account accResponse = secureRestTemplate.postForObject(url, acc, Account.class);
        return accResponse;
    }

    @Override
    public void update(Account acc) {
        String url = appConfig.getProperty("url.account") + acc.getId();
        secureRestTemplate.put(url, acc);
    }

    @Override
    public void delete(String id) {
        // cascade=true to remove also the account's datasets
        String url = appConfig.getProperty("url.account") + id + "?cascade=true";
        secureRestTemplate.delete(url);
    }

    @Override
    public List<AccountId> findForDataset(String datasetId) {
        // returns a list of account ids (id,name)
        String url = appConfig.getProperty("url.account") + "ids/"
                + "?dataset=" + datasetId;
        AccountId[] accountArray = secureRestTemplate.getForObject(url, AccountId[].class);
        if (accountArray == null) {
            return null;
        }
        List<AccountId> accountList = Arrays.asList(accountArray);
        return accountList;
    }

}
