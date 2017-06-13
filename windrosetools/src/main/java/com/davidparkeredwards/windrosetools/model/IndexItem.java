package com.davidparkeredwards.windrosetools.model;

import com.google.firebase.database.Exclude;

/**
 * Created by davidedwards on 6/13/17.
 */

public class IndexItem {

    public String description;
    public String userId;
    public String companyId;

    public IndexItem() {
    }

    public IndexItem(String description, String userId, String companyId) {
        this.description = description;
        this.userId = userId;
        this.companyId = companyId;
    }

    @Exclude
    public String getDescription() {
        return description;
    }
    @Exclude
    public void setDescription(String description) {
        this.description = description;
    }
    @Exclude
    public String getUserId() {
        return userId;
    }
    @Exclude
    public void setUserId(String userId) {
        this.userId = userId;
    }
    @Exclude
    public String getCompanyId() {
        return companyId;
    }
    @Exclude
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
