package io.github.scarger.placeholders.service.data.user;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.collection.UserCollection;

public class UserManager {

    private final CoreService context;
    private final UserCollection userCollection;

    public UserManager(CoreService context) {
        this.context = context;
        userCollection = new UserCollection(context.getDatabaseManager().getDB());
    }

    public UserCollection getUsers() {
        return userCollection;
    }

}
