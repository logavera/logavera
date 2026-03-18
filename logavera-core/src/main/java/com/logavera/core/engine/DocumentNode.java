package com.logavera.core.engine;

import com.logavera.core.model.Document;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * Represents a node in a document tree structure.
 * Each node contains a document, its children nodes, and a unique identifier.
 *
 * @param <T>      the type of the identifier for the node and its document.
 * @param document The document associated with this node.
 * @param children The list of child nodes associated with this document node.
 * @param id       The unique identifier for this document node.
 */
public record DocumentNode<T>(

        @Nonnull Document<T> document,

        @Nonnull List<DocumentNode<T>> children,

        @Nonnull T id) {
}