package com.logavera.core.engine;

import com.logavera.core.model.ConsentRecord;
import com.logavera.core.model.Document;
import com.logavera.core.model.DocumentVersion;
import jakarta.annotation.Nonnull;

import java.time.Instant;
import java.util.List;


/**
 * The ConsentEngine class is responsible for evaluating consent information
 * to determine which documents are consented by analyzing consent records
 * and document hierarchies.
 * This class acts as the core logic for handling
 * consent evaluation operations.
 */
public class ConsentEngine {

    private final ConsentEvaluator consentEvaluator;

    /**
     * Default constructor for ConsentEngine.
     * Initializes documentTree, contextBuilder, and consentEvaluator instances.
     */
    public ConsentEngine() {
        final DocumentTree documentTree = new DocumentTree();
        final ContextBuilder contextBuilder = new ContextBuilder(documentTree);
        consentEvaluator = new ConsentEvaluator(contextBuilder);
    }

    /**
     * Evaluates provided sources to compute the list of consented documents
     *
     * @param <T>              documentId type
     * @param documents        List of documents
     * @param documentVersions List of document versions
     * @param consentRecords   List of consent records
     * @param rootDocument     If provided, documents above the hierarchy will be ignored during evaluation
     * @param evaluationTime   Return results at a particular moment. Default: Instant.now()
     * @return List of consented documents. Consented document means:
     * - the document does not have non-consented currently effective versions
     * - the document does not have non-consented descendants
     */
    @Nonnull
    public <T> List<Document<T>> evaluate(@Nonnull final List<? extends Document<T>> documents,
                                          @Nonnull final List<? extends DocumentVersion> documentVersions,
                                          @Nonnull final List<? extends ConsentRecord> consentRecords,
                                          final Document<T> rootDocument,
                                          final Instant evaluationTime) {
        return consentEvaluator.evaluate(documents, documentVersions, consentRecords, rootDocument, evaluationTime);
    }
}
