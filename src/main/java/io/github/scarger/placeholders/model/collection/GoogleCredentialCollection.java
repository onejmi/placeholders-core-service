package io.github.scarger.placeholders.model.collection;

import com.mongodb.client.MongoDatabase;
import io.github.scarger.placeholders.model.collection.document.GoogleCredentialDocument;

public class GoogleCredentialCollection extends DataCollection<GoogleCredentialDocument> {

    public GoogleCredentialCollection(MongoDatabase database) {
        super(database, "googleCreds", GoogleCredentialDocument.class);
    }

}
