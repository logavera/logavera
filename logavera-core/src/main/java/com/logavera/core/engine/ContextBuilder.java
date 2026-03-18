package com.logavera.core.engine;

import com.logavera.core.model.ConsentRecord;
import com.logavera.core.model.Document;
import com.logavera.core.model.DocumentVersion;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class ContextBuilder {

    private final DocumentTree documentTree;

    ContextBuilder(final DocumentTree documentTree) {
        this.documentTree = documentTree;
    }

    <T> ConsentDecisionContext<T> buildContext(final List<? extends Document<T>> documents,
                                               final List<? extends DocumentVersion> documentVersions,
                                               final List<? extends ConsentRecord> consentRecords,
                                               final Document<T> rootDocument,
                                               final Instant evaluationTime) {
        final DocumentNode<T> rootNode = getRootNode(documents, rootDocument);
        final List<DocumentNode<T>> documentNodes = documentTree.getNodes(rootNode);
        final Map<T, DocumentNode<T>> documentNodeById = documentNodes.stream()
                .collect(Collectors.toMap(DocumentNode::id, Function.identity()));

        return new ConsentDecisionContext<>(documentNodeById,
                documentVersions.stream().collect(Collectors.groupingBy(DocumentVersion::getDocumentId)),
                consentRecords.stream()
                        .filter(consentRecord -> consentRecord.getCreatedAt().isBefore(evaluationTime))
                        .collect(Collectors.groupingBy(ConsentRecord::getDocumentVersionId)),
                rootNode,
                evaluationTime != null ? evaluationTime : Instant.now()
        );
    }

    private <T> DocumentNode<T> getRootNode(final List<? extends Document<T>> documents,
                                            final Document<T> rootDocument) {
        return documentTree.build(documents, rootDocument);
    }
}
