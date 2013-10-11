/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.model;

import java.util.List;

/**
 * This interface defines the Account model. Must to the same as the Account model in ST API
 */

public interface IAccount {

	public String getId();

	public void setId(String id);

	public String getUsername();

	public void setUsername(String username);

	public String getPassword();

	public void setPassword(String password);

	public boolean isEnabled();

	public void setEnabled(boolean isEnabled);

	public String getRole();

	public void setRole(String role);

	public List<String> getGrantedDatasets();

	public void setGrantedDatasets(List<String> grantedDatasets);
}
