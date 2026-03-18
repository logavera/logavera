package com.logavera.core.model;

import jakarta.annotation.Nonnull;

import java.time.Instant;

/**
 * A database record representing either acceptance or rejection of a certain Document Version at a certain point in time.
 */
public interface ConsentRecord {

    /**
     * Retrieves the unique identifier of the document version associated with this consent record.
     *
     * @return the unique identifier of the document version.
     */
    @Nonnull
    Object getDocumentVersionId();

    /**
     * Determines whether consent has been given for the associated record.
     *
     * @return true if consent has been given, false otherwise.
     */
    boolean isConsented();

    /**
     * Retrieves the timestamp at which this consent record was created.
     *
     * @return the timestamp indicating creation time.
     */
    @Nonnull
    Instant getCreatedAt();

}
