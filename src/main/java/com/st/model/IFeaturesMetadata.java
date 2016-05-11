package com.st.model;

import org.joda.time.DateTime;

/**
 * This interface defines the FeaturesMetadata model.
 */
public interface IFeaturesMetadata {

    public String getDatasetId();

    public void setDatasetId(String id);

    public String getFilename();

    public void setFilename(String filename);

    public DateTime getLastModified();

    public DateTime getCreated();

    public void setLastModified(DateTime d);

    public void setCreated(DateTime d);

    public long getSize();

    public void setSize(long size);

    public String getReadableSize();

}
