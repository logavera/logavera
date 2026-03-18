package com.logavera.core.engine;

import com.logavera.core.model.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentTreeTest {

    @Mock
    private Document<String> documentA1;
    @Mock
    private Document<String> documentA2;
    @Mock
    private Document<String> documentB1;
    @Mock
    private Document<String> documentB2;
    @Mock
    private Document<String> documentB3;
    @Mock
    private Document<String> documentC1;

    private final DocumentTree documentTree = new DocumentTree();

    @Test
    void buildFullTree_emptyList() {
        final DocumentNode<String> result = documentTree.build(Collections.emptyList(), null);

        assertEquals(0, result.children().size());
    }

    @Test
    void buildFullTree_nonEmptyList() {
        when(documentA1.getId()).thenReturn("A1");
        when(documentA2.getId()).thenReturn("A2");
        when(documentB1.getId()).thenReturn("B1");
        when(documentB2.getId()).thenReturn("B2");
        when(documentB3.getId()).thenReturn("B3");
        when(documentC1.getId()).thenReturn("C1");

        when(documentB1.getParentId()).thenReturn("A1");
        when(documentB2.getParentId()).thenReturn("A1");
        when(documentB3.getParentId()).thenReturn("A1");
        when(documentC1.getParentId()).thenReturn("B1");

        final DocumentNode<String> result = documentTree.build(List.of(documentA1, documentA2, documentB1, documentB2, documentB3, documentC1), null);
        assertTrue(result.children().stream().anyMatch(it -> it.document().getId().equals("A2")));

        final Optional<DocumentNode<String>> a1 = result.children().stream().filter(it -> it.document().getId().equals("A1")).findFirst();
        assertTrue(a1.isPresent());
        assertTrue(a1.get().children().stream().anyMatch(it -> it.document().getId().equals("B2")));
        assertTrue(a1.get().children().stream().anyMatch(it -> it.document().getId().equals("B3")));

        final Optional<DocumentNode<String>> b1 = a1.get().children().stream().filter(it -> it.document().getId().equals("B1")).findFirst();
        assertTrue(b1.isPresent());
        assertTrue(b1.get().children().stream().anyMatch(it -> it.document().getId().equals("C1")));
    }

    @Test
    void buildFromRoot() {
        when(documentA1.getId()).thenReturn("A1");
        when(documentB1.getId()).thenReturn("B1");
        when(documentB2.getId()).thenReturn("B2");
        when(documentB3.getId()).thenReturn("B3");
        when(documentC1.getId()).thenReturn("C1");

        when(documentB1.getParentId()).thenReturn("A1");
        when(documentB2.getParentId()).thenReturn("A1");
        when(documentB3.getParentId()).thenReturn("A1");
        when(documentC1.getParentId()).thenReturn("B1");

        final DocumentNode<String> result = documentTree.build(List.of(documentA1, documentA2, documentB1, documentB2, documentB3, documentC1), documentA1);

        assertTrue(result.children().stream().anyMatch(it -> it.document().getId().equals("B2")));
        assertTrue(result.children().stream().anyMatch(it -> it.document().getId().equals("B3")));

        final Optional<DocumentNode<String>> b1 = result.children().stream().filter(it -> it.document().getId().equals("B1")).findFirst();
        assertTrue(b1.isPresent());
        assertTrue(b1.get().children().stream().anyMatch(it -> it.document().getId().equals("C1")));
    }

    @Test
    void getNodes() {
        when(documentA1.getId()).thenReturn("A1");
        when(documentB1.getId()).thenReturn("B1");
        when(documentB2.getId()).thenReturn("B2");
        when(documentB3.getId()).thenReturn("B3");
        when(documentC1.getId()).thenReturn("C1");

        when(documentB1.getParentId()).thenReturn("A1");
        when(documentB2.getParentId()).thenReturn("A1");
        when(documentB3.getParentId()).thenReturn("A1");
        when(documentC1.getParentId()).thenReturn("B1");

        final DocumentNode<String> rootNode = documentTree.build(List.of(documentA1, documentA2, documentB1, documentB2, documentB3, documentC1), documentA1);
        final List<DocumentNode<String>> result = documentTree.getNodes(rootNode);

        assertEquals(5, result.size());
    }
}