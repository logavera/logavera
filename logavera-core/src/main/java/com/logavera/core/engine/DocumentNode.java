package com.logavera.core.engine;

import com.logavera.core.model.Document;
import jakarta.annotation.Nonnull;

import java.util.List;

public record DocumentNode<T>(@Nonnull Document<T> document,
                              @Nonnull List<DocumentNode<T>> children,
                              @Nonnull T id) {
}