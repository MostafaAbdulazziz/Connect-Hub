package com.socialnetwork.connecthub.backend.model;

import java.util.Date;

public class Story extends Content {
    private Date expiryDate;

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
