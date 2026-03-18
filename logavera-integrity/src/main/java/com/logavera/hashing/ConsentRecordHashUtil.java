package com.logavera.hashing;

import java.time.Instant;

public class ConsentRecordHashUtil {

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
