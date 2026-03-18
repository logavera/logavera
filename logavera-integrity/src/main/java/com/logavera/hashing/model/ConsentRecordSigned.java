package com.logavera.hashing.model;

import com.logavera.core.model.ConsentRecord;

public interface ConsentRecordSigned extends ConsentRecord {

    String getPrevHash();

    String getHash();

    String getConsenteeId();

    String getUserAgentData();

    String getSignedBy();
}
