package com.logavera.hashing;

import java.time.Instant;

/**
 * Utility class for generating signed hashes of document versions.
 * This class provides methods to compute signed hashes based on document content,
 * metadata, and a secret key for ensuring integrity and traceability.
 */
public class DocumentVersionHashUtil {

    /**
     * Default constructor.
     */
    public DocumentVersionHashUtil() {

    }

    /**
     * Generates a signed hash for a document version by combining the previous hash,
     * current content hash, creation time, signer information, and a secret key.
     *
     * @param prevHash    the hash of the previous document version.
     * @param contentHash the hash of the current document content.
     * @param createdAt   the timestamp when the document version was created.
     * @param signedBy    the identifier of the entity that signed this version.
     * @param secretKey   the secret key used for signing the hash.
     * @return a signed hash representing the document version's integrity.
     */
    public static String getSignedHash(final String prevHash,
                                       final String contentHash,
                                       final Instant createdAt,
                                       final String signedBy,
                                       final String secretKey) {
        return HashUtil.signedHash(prevHash +
                        contentHash +
                        createdAt +
                        signedBy,
                secretKey);
    }
}
