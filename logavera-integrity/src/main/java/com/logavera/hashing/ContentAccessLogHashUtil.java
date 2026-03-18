package com.logavera.hashing;

import java.time.Instant;

public class ContentAccessLogHashUtil {

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