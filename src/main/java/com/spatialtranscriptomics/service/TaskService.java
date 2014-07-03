/*
*Copyright © 2012 Spatial Transcriptomics AB
*Read LICENSE for more information about licensing terms
*Contact: Jose Fernandez Navarro <jose.fernandez.navarro@scilifelab.se>
* 
*/

package com.spatialtranscriptomics.service;

import java.util.List;

import com.spatialtranscriptomics.model.Task;

/**
 * Interface for the task service.
 */
public interface TaskService {

    public Task find(String id);

    public List<Task> list();

    public List<Task> findForAccount(String accountId);

    public Task add(Task task);

    public void update(Task task);

    public void delete(String id);
	
    public void deleteForAccount(String accountId);
}
