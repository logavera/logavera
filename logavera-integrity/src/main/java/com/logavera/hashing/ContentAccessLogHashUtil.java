package com.logavera.hashing;

import java.time.Instant;

/**
 * Utility class designed to generate secure, signed hashes for content access logs.
 * The class provides a mechanism to combine critical attributes of an access event
 * and generate a tamper-resistant identifier using a secret key.
 */
public class ContentAccessLogHashUtil {

    /**
     * Default constructor.
     */
    public ContentAccessLogHashUtil() {

    }
    /**
     * Creates a signed hash by concatenating the provided content access details
     * and signing the resulting string with a specified secret key.
     *
     * @param prevHash            The hash of the preceding content access log, ensuring chronological linking.
     * @param userAgentData       Information about the accessing user's agent (e.g., browser or device details).
     * @param createdAt           The timestamp indicating when the access event occurred.
     * @param documentVersionHash A unique hash representing the document version accessed.
     * @param documentVersionId   The identifier of the specific document version.
     * @param signedBy            The entity responsible for signing the access log.
     * @param secretKey           The secret key used for securely signing the hash.
     * @return A signed hash string uniquely identifying the content access log.
     */
    public static String getSignedHash(final String prevHash,
                                       final String userAgentData,
                                       final Instant createdAt,
                                       final String documentVersionHash,
                                       final Object documentVersionId,
                                       final String signedBy,
                                       final String secretKey) {
        return HashUtil.signedHash(prevHash +
                        userAgentData +
                        createdAt +
                        documentVersionHash +
                        documentVersionId +
                        signedBy,
                secretKey);
    }
}