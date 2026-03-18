package com.logavera.core.model;

import jakarta.annotation.Nonnull;

import java.time.Instant;

public interface DocumentVersion {

    @Nonnull
    Object getId();

    @Nonnull
    Object getDocumentId();

    @Nonnull
    Instant getEffectiveSince();
}
