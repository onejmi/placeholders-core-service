package io.github.scarger.placeholders.model.collection.document.account;

import com.mongodb.BasicDBObject;

public class UserAccount {
    private String id;
    private AccountRole role;
    private BasicDBObject titles;

    public UserAccount() {}

    public UserAccount(String userId) {
        this.id = userId;
        this.role = AccountRole.BASIC;
        this.titles = new BasicDBObject();
    }

    public String getId() {
        return id;
    }

    public AccountRole getRole() {
        return role;
    }

    public BasicDBObject getTitles() {
        return titles;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitles(BasicDBObject titles) {
        this.titles = titles;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }
}
