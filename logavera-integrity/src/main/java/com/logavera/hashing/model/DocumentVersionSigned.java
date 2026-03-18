package com.logavera.hashing.model;

import com.logavera.core.model.DocumentVersion;

/**
 * The DocumentVersionSigned interface represents a version of a document
 * that has integrity verification properties such as hash and signing information.
 * It extends the base DocumentVersion interface.
 */
public interface DocumentVersionSigned extends DocumentVersion {

    /**
     * Retrieves the hash of the previous document version in the chain.
     *
     * @return the hash of the previous document version.
     */
    String getPrevHash();

    /**
     * Retrieves the hash of the current document version.
     *
     * @return the hash of the current document version.
     */
    String getHash();

    /**
     * Retrieves the hash of the document content associated
     * with this version.
     *
     * @return the hash of the document content.
     */
    String getContentHash();

    /**
     * Retrieves the identifier of the entity who signed this document version.
     *
     * @return the identifier of the signer.
     */
    String getSignedBy();

}
