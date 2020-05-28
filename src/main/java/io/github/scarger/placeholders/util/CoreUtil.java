package io.github.scarger.placeholders.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.scarger.placeholders.util.transformer.JsonTransformer;

public class CoreUtil {

    private Gson gson;

    //transformers
    private final JsonTransformer jsonTransformer;

    public CoreUtil() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        jsonTransformer = new JsonTransformer(this);
    }

    public Gson gson() {
        return gson;
    }

    public JsonTransformer toJson() {
        return jsonTransformer;
    }
}
