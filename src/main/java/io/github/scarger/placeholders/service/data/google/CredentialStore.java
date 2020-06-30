package io.github.scarger.placeholders.service.data.google;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import com.mongodb.Block;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.collection.GoogleCredentialCollection;
import io.github.scarger.placeholders.model.collection.document.GoogleCredentialDocument;
import org.bson.Document;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.mongodb.client.model.Filters.eq;


public class CredentialStore implements DataStore<StoredCredential> {

    private CredentialStoreFactory storeFactory;
    private GoogleCredentialCollection mongoCreds;

    private CoreService context;

    public CredentialStore(CoreService context, CredentialStoreFactory storeFactory) {
        this.context = context;
        this.storeFactory = storeFactory;
        mongoCreds = new GoogleCredentialCollection(context.getDatabaseManager().getDB());
    }

    @Override
    public DataStoreFactory getDataStoreFactory() {
        return storeFactory;
    }

    @Override
    public String getId() {
        return "credential-store";
    }

    @Override
    public int size() throws IOException {
        return (int) mongoCreds.getCol().countDocuments();
    }

    @Override
    public boolean isEmpty() throws IOException {
        return mongoCreds.getCol().countDocuments() > 0;
    }

    @Override
    public boolean containsKey(String key) throws IOException {
        return mongoCreds.getCol().countDocuments(eq("userId", key)) > 0;
    }

    @Override
    public boolean containsValue(StoredCredential value) throws IOException {
        return mongoCreds.getCol().countDocuments(eq("storedCredential.accessToken", value.getAccessToken())) > 0;
    }

    @Override
    public Set<String> keySet() throws IOException {
        Set<String> keys = new HashSet<>();
        mongoCreds.getCol().find().forEach((Block<GoogleCredentialDocument>) cred -> keys.add(cred.getUserId()));
        return keys;
    }

    @Override
    public Collection<StoredCredential> values() throws IOException {
        Set<StoredCredential> allCreds = new HashSet<>();
        mongoCreds.getCol().find().forEach((Block<GoogleCredentialDocument>) cred -> allCreds.add(cred.getStoredCredential()));
        return allCreds;
    }

    @Override
    public StoredCredential get(String key) throws IOException {
        GoogleCredentialDocument doc = mongoCreds.getCol().find(eq("userId", key)).first();
        return doc == null ? null : doc.getStoredCredential();
    }

    @Override
    public DataStore<StoredCredential> set(String key, StoredCredential value) throws IOException {
        UpdateOptions updateOptions = new UpdateOptions();
        updateOptions.upsert(true);
        mongoCreds.getCol().updateOne(eq("userId", key), Updates.set("storedCredential", value), updateOptions);
        return this;
    }

    @Override
    public DataStore<StoredCredential> clear() throws IOException {
        mongoCreds.getCol().deleteMany(new Document());
        return this;
    }

    @Override
    public DataStore<StoredCredential> delete(String key) throws IOException {
        mongoCreds.getCol().deleteOne(eq("userId", key));
        return this;
    }
}
