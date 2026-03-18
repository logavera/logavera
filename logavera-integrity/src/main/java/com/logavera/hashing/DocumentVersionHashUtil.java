package com.logavera.hashing;

import java.time.Instant;

public class DocumentVersionHashUtil {

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
