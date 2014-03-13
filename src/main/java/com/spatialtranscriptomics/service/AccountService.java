/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import java.util.List;

import com.spatialtranscriptomics.model.Account;


public interface AccountService {

	public Account find(String id);

	public List<Account> list();
	
	public List<Account> findForDataset(String datasetId);

	public Account add(Account acc);

	public void update(Account acc);

	public void delete(String id);
}
