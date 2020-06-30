package io.github.scarger.placeholders.service.data.google;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import io.github.scarger.placeholders.CoreService;

import java.io.IOException;

public class CredentialStoreFactory implements DataStoreFactory {

    private CredentialStore store;

    private CoreService context;

    public CredentialStoreFactory(CoreService context) {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataStore<StoredCredential> getDataStore(String id) throws IOException {
        if(store == null) store = new CredentialStore(context, this);
        return store;
    }
}
