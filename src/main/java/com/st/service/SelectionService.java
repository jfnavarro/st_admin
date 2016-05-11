package com.st.service;

import java.util.List;
import com.st.model.Selection;

/**
 * Interface for the selection service.
 */
public interface SelectionService {

    /**
     * Retrieves a selection.
     * @param id the selection ID.
     * @return the selection.
     */
    public Selection find(String id);

    /**
     * Retrieves all selections.
     * @return the selections.
     */
    public List<Selection> list();

    /**
     * Retrieves all selections for an account.
     * @param accountId  the account ID.
     * @return the selections.
     */
    public List<Selection> findForAccount(String accountId);

    /**
     * Retrieves all selections for a dataset.
     * @param datasetId the dataset ID.
     * @return the selections.
     */
    public List<Selection> findForDataset(String datasetId);

    /**
     * Retrieves all selections for a task.
     * @param taskId  the task ID.
     * @return the selections.
     */
    public List<Selection> findForTask(String taskId);

    /**
     * Adds a selection.
     * @param sel the selection.
     * @return the selection with ID assigned.
     */
    public Selection add(Selection sel);

    /**
     * Updates a selection.
     * @param sel the selection.
     */
    public void update(Selection sel);

    /**
     * Deletes a selection.
     * @param id the selection ID.
     */
    public void delete(String id);

}
