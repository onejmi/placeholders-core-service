package io.github.scarger.placeholders.model.collection;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public abstract class DataCollection<T> {

    protected MongoCollection<T> collection;

    public DataCollection(MongoDatabase database, String name, Class<T> documentClass) {
        collection = database.getCollection(name, documentClass);
    }

    public MongoCollection<T> getCol() {
        return collection;
    }

}
