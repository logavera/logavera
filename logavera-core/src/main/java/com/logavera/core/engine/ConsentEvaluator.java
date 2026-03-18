package com.logavera.core.engine;

import com.logavera.core.model.ConsentRecord;
import com.logavera.core.model.Document;
import com.logavera.core.model.DocumentVersion;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

final class ConsentEvaluator {

    private final ContextBuilder contextBuilder;

    ConsentEvaluator(final ContextBuilder contextBuilder) {
        this.contextBuilder = contextBuilder;
    }

    <T> List<Document<T>> evaluate(final List<? extends Document<T>> documents,
                                   final List<? extends DocumentVersion> documentVersions,
                                   final List<? extends ConsentRecord> consentRecords,
                                   final Document<T> rootDocument,
                                   final Instant evaluationTime) {
        final ConsentDecisionContext<T> context = contextBuilder.buildContext(documents,
                documentVersions,
                consentRecords,
                rootDocument,
                evaluationTime != null ? evaluationTime : Instant.now());

        final List<Document<T>> result = new ArrayList<>();

        isConsented(context.rootDocumentNode(), context, result);

        return result;
    }

    private <T> boolean isConsented(final DocumentNode<T> documentNode,
                                    final ConsentDecisionContext<T> context,
                                    final List<? super Document<T>> result) {
        final DocumentNode<T> rootDocument = context.documentNodeById().get(documentNode.id());
        final List<DocumentVersion> documentVersions = context.documentVersionsByDocument().getOrDefault(documentNode.id(), Collections.emptyList());
        final List<DocumentVersion> effectiveDocumentVersions = getEffectiveDocumentVersions(context, documentVersions);
        final boolean rootConsented = isRootConsented(context, effectiveDocumentVersions);
        final boolean allDescendantDocumentsConsented = getDescendantsConsents(context, result, rootDocument).stream().allMatch(it -> it);

        final boolean consented = rootConsented && allDescendantDocumentsConsented;

        if (consented) {
            result.add(rootDocument.document());
        }

        return consented;
    }

    private <T> List<DocumentVersion> getEffectiveDocumentVersions(final ConsentDecisionContext<T> context,
                                                                   final List<DocumentVersion> documentVersions) {
        return documentVersions.stream()
                .filter(documentVersion -> isEffective(documentVersion, context)).toList();
    }

    private <T> boolean isRootConsented(final ConsentDecisionContext<T> context,
                                        final List<DocumentVersion> documentVersions) {
        if (documentVersions.isEmpty()) {
            return true;
        }

        return documentVersions.stream()
                .max(Comparator.comparing(DocumentVersion::getEffectiveSince))
                .map(documentVersion -> isConsented(documentVersion, context))
                .orElseThrow();
    }

    private <T> List<Boolean> getDescendantsConsents(final ConsentDecisionContext<T> context,
                                                     final List<? super Document<T>> result,
                                                     final DocumentNode<T> rootDocument) {
        return rootDocument.children().stream()
                .map(childNode -> isConsented(childNode, context, result))
                .toList();
    }

    private <T> boolean isEffective(final DocumentVersion documentVersion,
                                    final ConsentDecisionContext<T> context) {
        return !documentVersion.getEffectiveSince().truncatedTo(ChronoUnit.DAYS).isAfter(context.evaluationTime());
    }

    private <T> boolean isConsented(final DocumentVersion documentVersion,
                                    final ConsentDecisionContext<T> context) {
        return context.consentRecordsByDocumentVersion().getOrDefault(documentVersion.getId(), Collections.emptyList()).stream()
                .max(Comparator.comparing(ConsentRecord::getCreatedAt))
                .map(ConsentRecord::isConsented)
                .orElse(false);
    }
}
