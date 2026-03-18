package com.logavera.core.model;

import jakarta.annotation.Nonnull;

import java.time.Instant;

/**
 * Represents a version of a document with metadata to uniquely identify it,
 * associate it with a specific document, and track its effective timestamp.
 */
public interface DocumentVersion {

    /**
     * Retrieves the unique identifier of this document version.
     *
     * @return the unique identifier of the document version.
     */
    @Nonnull
    Object getId();

    /**
     * Retrieves the unique identifier of the associated document
     * to which this version belongs.
     *
     * @return the unique identifier of the associated document.
     */
    @Nonnull
    Object getDocumentId();

    /**
     * Retrieves the timestamp indicating when this version of the document
     * became effective.
     *
     * @return the effective timestamp of this document version.
     */
    @Nonnull
    Instant getEffectiveSince();
}
