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

    /**
     * Retrieves a task.
     * @param id the task ID.
     * @return the task.
     */
    public Task find(String id);

    /**
     * Retrieves all tasks.
     * @return the tasks.
     */
    public List<Task> list();

    /**
     * Retirieves all tasks for an account.
     * @param accountId the account ID.
     * @return the tasks.
     */
    public List<Task> findForAccount(String accountId);

    /**
     * Adds a task.
     * @param task the task.
     * @return the task with ID assigned.
     */
    public Task add(Task task);

    /**
     * Updates a task.
     * @param task the task.
     */
    public void update(Task task);

    /**
     * Deletes a task.
     * @param id the task ID.
     */
    public void delete(String id);
}
