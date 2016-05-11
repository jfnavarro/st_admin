package com.st.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import com.st.model.Account;
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
        String url = appConfig.getProperty("url.account") + "all/";
        url += id;
        Account acc = secureRestTemplate.getForObject(url, Account.class);
        return acc;
    }

    @Override
    public List<Account> list() {
        String url = appConfig.getProperty("url.account") + "all/";
        Account[] accountArray = secureRestTemplate.getForObject(url,
                Account[].class);
        List<Account> accountList = Arrays.asList(accountArray);
        return accountList;
    }

    @Override
    public Account add(Account acc) {
        String url = appConfig.getProperty("url.account");
        Account accResponse = secureRestTemplate.postForObject(url, acc,
                Account.class);
        return accResponse;
    }

    @Override
    public void update(Account acc) {
        String url = appConfig.getProperty("url.account");
        String id = acc.getId();
        secureRestTemplate.put(url + id, acc);
    }

    @Override
    public void delete(String id) {
        String url = appConfig.getProperty("url.account");
        secureRestTemplate.delete(url + id + "?cascade=true");
    }

    @Override
    public List<Account> findForDataset(String datasetId) {
        String url = appConfig.getProperty("url.account") + "all/?dataset=" + datasetId;
        Account[] accountArray = secureRestTemplate.getForObject(url, Account[].class);
        List<Account> accountList = Arrays.asList(accountArray);
        return accountList;
    }

}
