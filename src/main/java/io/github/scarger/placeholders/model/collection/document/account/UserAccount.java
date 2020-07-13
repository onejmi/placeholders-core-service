package io.github.scarger.placeholders.model.collection.document.account;

import com.mongodb.BasicDBObject;

import java.util.HashSet;
import java.util.Set;

public class UserAccount {
    private String id;
    private AccountRole role;
    private BasicDBObject titles;
    private Set<String> currentPlaceholders;

    public UserAccount() {}

    public UserAccount(String userId) {
        id = userId;
        role = AccountRole.BASIC;
        titles = new BasicDBObject();
        currentPlaceholders = new HashSet<>();
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

    public Set<String> getCurrentPlaceholders() {
        return currentPlaceholders;
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

    public void setCurrentPlaceholders(Set<String> currentPlaceholders) {
        this.currentPlaceholders = currentPlaceholders;
    }
}
