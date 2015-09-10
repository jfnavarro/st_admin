/*
 * Copyright © 2012 Spatial Transcriptomics AB
 * Read LICENSE for more information about licensing terms
 * Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
 * 
 */

package com.spatialtranscriptomics.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import com.spatialtranscriptomics.model.Account;
import com.spatialtranscriptomics.service.AccountService;

/**
 * This class implements the store/retrieve logic to the ST API for the data
 * model class "Account". The connection to the ST API is handled in a
 * RestTemplate object, which is configured in mvc-dispatcher-servlet.xml
 */
@Service
public class AccountServiceImpl implements AccountService {

    // Note: General service URI logging is performed in CustomOAuth2RestTemplate.
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);

    @Autowired
    RestOperations secureRestTemplate;

    @Autowired
    Properties appConfig;

    @Override
    public Account find(String id) {
        String url = appConfig.getProperty("url.account") + "/" + id;
        return secureRestTemplate.getForObject(url, Account.class);
    }

    @Override
    public List<Account> list() {
        String url = appConfig.getProperty("url.account");
        return Arrays.asList(secureRestTemplate.getForObject(url, Account[].class));
    }

    @Override
    public Account add(Account acc) {
        String url = appConfig.getProperty("url.account");
        return secureRestTemplate.postForObject(url, acc, Account.class);
    }

    @Override
    public void update(Account acc) {
        String url = appConfig.getProperty("url.account") + "/" + acc.getId();
        secureRestTemplate.put(url, acc);
    }

    @Override
    public void delete(String id) {
        String url = appConfig.getProperty("url.account");
        secureRestTemplate.delete(url + id + "?cascade=true");
    }

    @Override
    public List<Account> findForDataset(String datasetId) {
        String url = appConfig.getProperty("url.account") + "/?dataset=" + datasetId;
        return Arrays.asList(secureRestTemplate.getForObject(url, Account[].class));
    }

}
