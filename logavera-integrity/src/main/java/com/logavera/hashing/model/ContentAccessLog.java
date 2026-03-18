package com.logavera.hashing.model;

import java.time.Instant;

/**
 * Represents a log entry for accessing content within the system.
 * Provides methods to retrieve metadata associated with each access instance.
 */
public interface ContentAccessLog {

    /**
     * Retrieves the hash of the previous content access log entry.
     *
     * @return the hash of the previous log entry.
     */
    String getPrevHash();

    /**
     * Retrieves the unique hash of the current content access log entry.
     *
     * @return the unique hash of this log entry.
     */
    String getHash();

    /**
     * Retrieves the user agent data of the client that accessed the content.
     *
     * @return the user agent information as a string.
     */
    String getUserAgentData();

    /**
     * Retrieves the timestamp indicating when this log entry was created.
     *
     * @return the creation timestamp.
     */
    Instant getCreatedAt();

    /**
     * Retrieves the hash of the associated document version for this log entry.
     *
     * @return the hash of the document version.
     */
    String getDocumentVersionHash();

    /**
     * Retrieves the unique identifier of the document version linked to this log entry.
     *
     * @return the unique identifier of the document version.
     */
    String getDocumentVersionId();

    /**
     * Retrieves the identifier of the user or system responsible for signing this log entry.
     *
     * @return the identifier of the signer.
     */
    String getSignedBy();
}