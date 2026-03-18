package com.logavera.hashing.model;

import com.logavera.core.model.DocumentVersion;

public interface DocumentVersionSigned extends DocumentVersion {

    String getPrevHash();

    String getHash();

    String getContentHash();

    String getSignedBy();

}
