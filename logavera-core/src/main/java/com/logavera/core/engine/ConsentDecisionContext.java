package com.logavera.core.engine;

import com.logavera.core.model.ConsentRecord;
import com.logavera.core.model.DocumentVersion;

import java.time.Instant;
import java.util.List;
import java.util.Map;

record ConsentDecisionContext<T>(Map<T, DocumentNode<T>> documentNodeById,
                                 Map<Object, List<DocumentVersion>> documentVersionsByDocument,
                                 Map<Object, List<ConsentRecord>> consentRecordsByDocumentVersion,
                                 DocumentNode<T> rootDocumentNode,
                                 Instant evaluationTime) {
}
