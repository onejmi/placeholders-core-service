package io.github.scarger.placeholders.model.collection;

import com.mongodb.client.MongoDatabase;
import io.github.scarger.placeholders.model.collection.document.account.AccountRole;
import io.github.scarger.placeholders.model.collection.document.account.UserAccount;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class UserCollection extends DataCollection<UserAccount>{
    public UserCollection(MongoDatabase database) {
        super(database, "users", UserAccount.class);
    }

    public void addUser(String userId) {
        collection.insertOne(new UserAccount(userId));
    }

    public boolean isUserPresent(String userId) {
        return collection.countDocuments(eq("_id", userId)) > 0;
    }

    public UserAccount getUser(String userId) {
        return collection.find(eq("_id", userId)).first();
    }

    public void setTitle(String userId, String videoId, String unformattedTitle) {
        collection.updateOne(eq("_id", userId), set("titles." + videoId, unformattedTitle));
    }

    public void setRole(String userId, AccountRole role) {
        collection.updateOne(eq("_id", userId), set("role", role));
    }

}
