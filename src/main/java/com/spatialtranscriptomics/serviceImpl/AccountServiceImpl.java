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
//import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestOperations;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.HttpStatus;
//import com.spatialtranscriptomics.exceptions.GenericException;
import com.spatialtranscriptomics.model.Account;
import com.spatialtranscriptomics.service.AccountService;

/**
 * This class implements the store/retrieve logic to the ST API for the data model class "Account".
 * The connection to the ST API is handled in a RestTemplate object, which is configured in mvc-dispatcher-servlet.xml
 */

@Service
public class AccountServiceImpl implements AccountService {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(AccountServiceImpl.class);

	@Autowired
	RestOperations secureRestTemplate;

	@Autowired
	Properties appConfig;

        @Override
	public Account find(String id) {
		String url = appConfig.getProperty("url.account");
		url += id;
		Account acc = secureRestTemplate.getForObject(url, Account.class);
		return acc;
	}

	@Override
	public List<Account> list() {
		String url = appConfig.getProperty("url.account");
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
		secureRestTemplate.delete(url + id);
	}

        @Override
	public List<Account> findForDataset(String datasetId) {
		String url = appConfig.getProperty("url.account")  + "?dataset=" + datasetId;
		Account[] accountArray = secureRestTemplate.getForObject(url, Account[].class);
		List<Account> accountList = Arrays.asList(accountArray);
		return accountList;
	}

}
