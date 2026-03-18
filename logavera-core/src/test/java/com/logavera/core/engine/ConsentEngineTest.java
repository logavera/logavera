package com.logavera.core.engine;

import com.logavera.core.model.ConsentRecord;
import com.logavera.core.model.Document;
import com.logavera.core.model.DocumentVersion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsentEngineTest {

    @Mock
    private Document<Object> document1;
    @Mock
    private Document<Object> document2;
    @Mock
    private Document<Object> document3;
    @Mock
    private DocumentVersion documentVersion1;
    @Mock
    private DocumentVersion documentVersion2;
    @Mock
    private ConsentRecord consentRecord1;
    @Mock
    private ConsentRecord consentRecord2;

    private final ConsentEngine consentEngine = new ConsentEngine();

    @Test
    void GIVEN_document_AND_version_AND_consent_THEN_document() {
        final ConsentEngine consentEngine = new ConsentEngine();
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.isConsented()).thenReturn(true);
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.contains(document1));
    }

    @Test
    void GIVEN_document_AND_version_AND_consent_THEN_document2() {
        final ConsentEngine consentEngine = new ConsentEngine();
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of();
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.contains(document1));
    }

    @Test
    void GIVEN_document_AND_version_AND_no_consent_THEN_emptyList() {
        final ConsentEngine consentEngine = new ConsentEngine();
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("999");
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.isEmpty());
    }

    @Test
    void GIVEN_document_AND_version_AND_consent_after_evaluation_time_THEN_emptyList() {
        final ConsentEngine consentEngine = new ConsentEngine();
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now());

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, Instant.now().minus(1, ChronoUnit.HALF_DAYS));

        assertTrue(result.isEmpty());
    }

    @Test
    void GIVEN_document_AND_version_AND_consented_not_THEN_emptyList() {
        final ConsentEngine consentEngine = new ConsentEngine();
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.isConsented()).thenReturn(false);
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.isEmpty());
    }

    @Test
    void GIVEN_document_AND_version_effective_null_AND_consent_THEN_emptyList() {
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.isEmpty());
    }

    @Test
    void GIVEN_document_AND_version_effective_future_AND_consent_THEN_document() {
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().plus(1, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.contains(document1));
    }

    @Test
    void GIVEN_document_AND_version_AND_version_effective_future_AND_consent_earlier_THEN_document() {
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1, documentVersion2);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));
        when(documentVersion2.getDocumentId()).thenReturn("1");
        when(documentVersion2.getEffectiveSince()).thenReturn(Instant.now().plus(1, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.isConsented()).thenReturn(true);
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.contains(document1));
    }

    @Test
    void GIVEN_document_AND_version_AND_version_effective_future_AND_consent_later_THEN_emptyList() {
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1, documentVersion2);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));
        when(documentVersion2.getDocumentId()).thenReturn("1");
        when(documentVersion2.getEffectiveSince()).thenReturn(Instant.now().plus(1, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("2");
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.isEmpty());
    }

    @Test
    void GIVEN_document_AND_version_AND_version_AND_consent_earlier_THEN_emptyList() {
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1, documentVersion2);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));
        when(documentVersion2.getDocumentId()).thenReturn("1");
        when(documentVersion2.getId()).thenReturn("2");
        when(documentVersion2.getEffectiveSince()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.isEmpty());
    }

    @Test
    void GIVEN_document_AND_version_AND_version_AND_consent_later_THEN_document() {
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1, documentVersion2);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));
        when(documentVersion2.getDocumentId()).thenReturn("1");
        when(documentVersion2.getId()).thenReturn("2");
        when(documentVersion2.getEffectiveSince()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("2");
        when(consentRecord1.isConsented()).thenReturn(true);
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.contains(document1));
    }

    @Test
    void GIVEN_document_AND_version_AND_consent_and_no_consent_THEN_emptyList() {
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1, consentRecord2);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));
        when(consentRecord2.getDocumentVersionId()).thenReturn("1");
        when(consentRecord2.isConsented()).thenReturn(false);
        when(consentRecord2.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.isEmpty());
    }

    @Test
    void GIVEN_document_AND_version_AND_consented_not_and_consent_THEN_document() {
        final List<Document<Object>> documents = List.of(document1);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1, consentRecord2);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));
        when(consentRecord2.getDocumentVersionId()).thenReturn("1");
        when(consentRecord2.isConsented()).thenReturn(true);
        when(consentRecord2.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.contains(document1));
    }

    @Test
    void GIVEN_document_with_versions_AND_consented_child_with_version_THEN_child() {
        final List<Document<Object>> documents = List.of(document1, document2);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1, documentVersion2);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));

        when(document2.getId()).thenReturn("2");
        when(document2.getParentId()).thenReturn("1");
        when(documentVersion2.getDocumentId()).thenReturn("2");
        when(documentVersion2.getId()).thenReturn("2");
        when(documentVersion2.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));

        when(consentRecord1.getDocumentVersionId()).thenReturn("2");
        when(consentRecord1.isConsented()).thenReturn(true);
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertFalse(result.contains(document1));
        assertTrue(result.contains(document2));
    }

    @Test
    void GIVEN_document_without_versions_AND_consented_child_with_version_THEN_document_AND_child() {
        final List<Document<Object>> documents = List.of(document1, document2);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");
        when(document2.getId()).thenReturn("2");
        when(document2.getParentId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("2");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));

        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.isConsented()).thenReturn(true);
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.contains(document1));
        assertTrue(result.contains(document2));
    }

    @Test
    void GIVEN_consented_document_with_versions_AND_consented_child_with_version_THEN_all_documents() {
        final List<Document<Object>> documents = List.of(document1, document2);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1, documentVersion2);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1, consentRecord2);

        when(document1.getId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("1");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.isConsented()).thenReturn(true);
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        when(document2.getId()).thenReturn("2");
        when(document2.getParentId()).thenReturn("1");
        when(documentVersion2.getDocumentId()).thenReturn("2");
        when(documentVersion2.getId()).thenReturn("2");
        when(documentVersion2.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));
        when(consentRecord2.getDocumentVersionId()).thenReturn("2");
        when(consentRecord2.isConsented()).thenReturn(true);
        when(consentRecord2.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.contains(document1));
        assertTrue(result.contains(document2));
    }

    @Test
    void GIVEN_document_AND_consented_child_AND_not_consented_child_THEN_emptyList() {
        final List<Document<Object>> documents = List.of(document1, document2, document3);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1, documentVersion2);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1);

        when(document1.getId()).thenReturn("1");

        when(document2.getId()).thenReturn("2");
        when(document2.getParentId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("2");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.isConsented()).thenReturn(true);
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        when(document3.getId()).thenReturn("3");
        when(document3.getParentId()).thenReturn("1");
        when(documentVersion2.getDocumentId()).thenReturn("3");
        when(documentVersion2.getId()).thenReturn("2");
        when(documentVersion2.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertFalse(result.contains(document1));
        assertTrue(result.contains(document2));
        assertFalse(result.contains(document3));
    }

    @Test
    void GIVEN_document_AND_consented_child_AND_consented_child_THEN_all_documents() {
        final List<Document<Object>> documents = List.of(document1, document2, document3);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1, documentVersion2);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1, consentRecord2);

        when(document1.getId()).thenReturn("1");

        when(document2.getId()).thenReturn("2");
        when(document2.getParentId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("2");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.isConsented()).thenReturn(true);
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        when(document3.getId()).thenReturn("3");
        when(document3.getParentId()).thenReturn("1");
        when(documentVersion2.getDocumentId()).thenReturn("3");
        when(documentVersion2.getId()).thenReturn("2");
        when(documentVersion2.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));
        when(consentRecord2.getDocumentVersionId()).thenReturn("2");
        when(consentRecord2.isConsented()).thenReturn(true);
        when(consentRecord2.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, null, null);

        assertTrue(result.contains(document1));
        assertTrue(result.contains(document2));
        assertTrue(result.contains(document3));
    }

    @Test
    void GIVEN_consented_documents_AND_root_document2_THEN_documents_2_and_3() {
        final List<Document<Object>> documents = List.of(document1, document2, document3);
        final List<DocumentVersion> documentVersions = List.of(documentVersion1, documentVersion2);
        final List<ConsentRecord> consentRecords = List.of(consentRecord1, consentRecord2);

        when(document2.getId()).thenReturn("2");
        when(document2.getParentId()).thenReturn("1");
        when(documentVersion1.getDocumentId()).thenReturn("2");
        when(documentVersion1.getId()).thenReturn("1");
        when(documentVersion1.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));
        when(consentRecord1.getDocumentVersionId()).thenReturn("1");
        when(consentRecord1.isConsented()).thenReturn(true);
        when(consentRecord1.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        when(document3.getId()).thenReturn("3");
        when(document3.getParentId()).thenReturn("2");
        when(documentVersion2.getDocumentId()).thenReturn("3");
        when(documentVersion2.getId()).thenReturn("2");
        when(documentVersion2.getEffectiveSince()).thenReturn(Instant.now().minus(2, ChronoUnit.DAYS));
        when(consentRecord2.getDocumentVersionId()).thenReturn("2");
        when(consentRecord2.isConsented()).thenReturn(true);
        when(consentRecord2.getCreatedAt()).thenReturn(Instant.now().minus(1, ChronoUnit.MINUTES));

        final List<Document<Object>> result = consentEngine.evaluate(documents, documentVersions, consentRecords, document2, null);

        assertFalse(result.contains(document1));
        assertTrue(result.contains(document2));
        assertTrue(result.contains(document3));
    }
}