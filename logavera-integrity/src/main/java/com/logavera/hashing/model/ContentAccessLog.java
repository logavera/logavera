package com.logavera.hashing.model;

import java.time.Instant;

public interface ContentAccessLog {

    String getPrevHash();

    String getHash();

    String getUserAgentData();

    Instant getCreatedAt();

    String getDocumentVersionHash();

    String getDocumentVersionId();

    String getSignedBy();
}