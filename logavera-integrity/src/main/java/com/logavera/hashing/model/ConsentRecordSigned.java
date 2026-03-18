package com.logavera.hashing.model;

import com.logavera.core.model.ConsentRecord;

/**
 * Represents a signed consent record, extending the ConsentRecord interface
 * to include cryptographic hash and additional metadata for signature validation.
 */
public interface ConsentRecordSigned extends ConsentRecord {

    /**
     * Retrieves the hash value of the previous record in the chain.
     *
     * @return the hash value of the previous record as a String.
     */
    String getPrevHash();

    /**
     * Retrieves the hash value of the current record.
     *
     * @return the hash value of the current record as a String.
     */
    String getHash();

    /**
     * Retrieves the unique identifier of the consentee.
     *
     * @return the identifier of the consentee as a String.
     */
    String getConsenteeId();

    /**
     * Retrieves the data associated with the user agent (e.g., browser details).
     *
     * @return the user agent data as a String.
     */
    String getUserAgentData();

    /**
     * Retrieves the identifier of the entity who signed the record.
     *
     * @return the signer’s identifier as a String.
     */
    String getSignedBy();
}
