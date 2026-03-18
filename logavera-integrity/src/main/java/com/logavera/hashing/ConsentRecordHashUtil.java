package com.logavera.hashing;

import java.time.Instant;

/**
 * Utility class for generating signed hashes for consent records.
 * This class helps in creating a secure and unique hash for consent data.
 */
public class ConsentRecordHashUtil {

    /**
     * Default constructor.
     */
    public ConsentRecordHashUtil() {

    }

    /**
     * Generates a signed hash based on various attributes of the consent record.
     *
     * @param prevHash            The hash of the previous record for chain integrity.
     * @param userAgentData       Information about the user's device and browser.
     * @param createdAt           The timestamp when the consent record was created.
     * @param documentVersionHash The unique hash of the document version.
     * @param isConsented         Indicates whether consent was given or not.
     * @param consenteeId         The unique identifier of the individual giving consent.
     * @param signedBy            The entity that signed the consent record.
     * @param secretKey           A secret key used for signing the hash.
     * @return A secure string representing the signed hash of the consent record.
     */
    public static String getSignedHash(final String prevHash,
                                       final String userAgentData,
                                       final Instant createdAt,
                                       final String documentVersionHash,
                                       final boolean isConsented,
                                       final String consenteeId,
                                       final String signedBy,
                                       final String secretKey) {
        return HashUtil.signedHash(prevHash +
                        userAgentData +
                        createdAt +
                        documentVersionHash +
                        isConsented +
                        consenteeId +
                        signedBy,
                secretKey);
    }
}
