package com.logavera.core.model;

import jakarta.annotation.Nonnull;

import java.time.Instant;

public interface ConsentRecord {

    @Nonnull
    Object getDocumentVersionId();

    boolean isConsented();

    @Nonnull
    Instant getCreatedAt();

}
