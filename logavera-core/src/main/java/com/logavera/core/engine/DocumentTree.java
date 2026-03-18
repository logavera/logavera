package com.logavera.core.engine;

import com.logavera.core.model.Document;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A utility class that provides methods for constructing and navigating a tree structure of documents.
 * <p>
 * The class includes functionalities to:
 * - Build a document tree from a list of documents.
 * - Retrieve a flattened list of all nodes from any given root node.
 * - Build a full tree or a tree starting from a specific root document.
 * <p>
 * The class relies on the {@link Document} and {@link DocumentNode} classes to handle individual nodes and
 * document relationships.
 */
public class DocumentTree {

    /**
    * Default constructor.
     */
    public DocumentTree() {
        
    }
    
    /**
     * Builds a tree structure from a list of documents.
     * @param <T>          documentId type
     * @param documents    List of documents
     * @param rootDocument If provided, documents above the hierarchy will be ignored
     * @return Root node of the built tree
     */
    @Nonnull
    public <T> DocumentNode<T> build(@Nonnull final List<? extends Document<T>> documents, final Document<T> rootDocument) {
        if (rootDocument == null) {
            return buildFullTree(documents);
        }

        return buildFromRoot(documents, rootDocument);
    }

    /**
     * Retrieves a list of all nodes in the tree starting from the provided root node.
     * @param <T>      documentId type
     * @param rootNode Root node
     * @return Flattened list of the root and its descendants
     */
    @Nonnull
    public <T> List<DocumentNode<T>> getNodes(@Nonnull final DocumentNode<T> rootNode) {
        final List<DocumentNode<T>> result = new ArrayList<>();
        final List<DocumentNode<T>> childrenNodes = rootNode.children().stream()
                .flatMap(childNode -> getNodes(childNode).stream()).toList();

        result.add(rootNode);
        result.addAll(childrenNodes);

        return result;
    }

    private <T> DocumentNode<T> buildFullTree(final List<? extends Document<T>> documents) {
        final Map<Boolean, ? extends List<? extends Document<T>>> documentsByHasParent = documents.stream()
                .collect(Collectors.partitioningBy(it -> it.getParentId() != null));
        final Document<T> rootDocument = dummyRootDocument();
        final Map<T, ? extends List<? extends Document<T>>> documentsByParentId = documentsByHasParent.get(true).stream()
                .collect(Collectors.groupingBy(Document::getParentId));
        final List<DocumentNode<T>> children = documentsByHasParent.get(false).stream()
                .map(orphanDocument -> toDocumentNode(orphanDocument, documentsByParentId))
                .toList();

        return new DocumentNode<>(rootDocument, children, rootDocument.getId());
    }

    private <T> DocumentNode<T> buildFromRoot(final List<? extends Document<T>> allDocuments, final Document<T> document) {
        final Map<T, ? extends List<? extends Document<T>>> documentsByParentId = allDocuments.stream().filter(doc -> doc.getParentId() != null)
                .collect(Collectors.groupingBy(Document::getParentId));

        return toDocumentNode(document, documentsByParentId);
    }

    private <T> List<DocumentNode<T>> buildChildrenNodes(final List<? extends Document<T>> documents,
                                                         final Map<T, ? extends List<? extends Document<T>>> documentsByParentId) {
        if (documents == null || documents.isEmpty()) {
            return Collections.emptyList();
        }

        return documents.stream()
                .map(document -> toDocumentNode(document, documentsByParentId))
                .toList();
    }

    private <T> DocumentNode<T> toDocumentNode(final Document<T> document, Map<T, ? extends List<? extends Document<T>>> documentsByParentId) {
        final List<? extends Document<T>> childDocuments = documentsByParentId.get(document.getId());
        final List<DocumentNode<T>> childrenNodes = buildChildrenNodes(childDocuments, documentsByParentId);

        return new DocumentNode<>(document, childrenNodes, document.getId());
    }

    @SuppressWarnings("unchecked")
    private <T> Document<T> dummyRootDocument() {
        return (Document<T>) new Document<Long>() {

            @Nonnull
            @Override
            public Long getId() {
                return -1L;
            }

            @Override
            public Long getParentId() {
                return null;
            }

            @Override
            public void setParentId(Long parentId) {
                //NO-OP
            }
        };
    }
}
