package com.logavera.core.model;

import jakarta.annotation.Nonnull;

public interface Document<T> {

    @Nonnull
    T getId();

    T getParentId();

    void setParentId(T parentId);
}
