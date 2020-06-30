package io.github.scarger.placeholders.service.data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.github.scarger.placeholders.CoreService;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.net.UnknownHostException;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DatabaseManager implements Disposable {

    private CoreService context;
    private MongoClient mongoClient;
    private MongoDatabase database;

    private final String mongoURI = "mongodb://localhost:27017";
    private final String databaseID = "coreDB";

    public DatabaseManager(CoreService context) {
        this.context = context;
    }

    public void init() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(new ConnectionString(mongoURI))
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase(databaseID);
    }

    public MongoDatabase getDB() {
        return database;
    }

    @Override
    public void dispose() {
        mongoClient.close();
    }
}
