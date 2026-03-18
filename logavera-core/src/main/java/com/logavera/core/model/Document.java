package com.logavera.core.model;

import jakarta.annotation.Nonnull;

/**
 * Represents a generic document structure that can have a unique identifier
 * and an optional parent-child hierarchy relationship.
 *
 * @param <T> the type of the identifier used for this document.
 */
public interface Document<T> {

    /**
     * Retrieves the unique identifier of the document.
     *
     * @return the unique identifier of type {@code T}.
     */
    @Nonnull
    T getId();

    /**
     * Retrieves the identifier of the parent document, if one exists.
     *
     * @return the parent document's identifier of type {@code T},
     * or {@code null} if no parent exists.
     */
    T getParentId();

    /**
     * Sets the identifier of the parent document.
     *
     * @param parentId the identifier of the parent document of type {@code T}.
     */
    void setParentId(T parentId);
}
