package io.github.scarger.placeholders.util.transformer;

import io.github.scarger.placeholders.util.CoreUtil;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    private final CoreUtil utilContext;

    public JsonTransformer(CoreUtil utilContext) {
        this.utilContext = utilContext;
    }

    @Override
    public String render(Object model)
    {
        return utilContext.gson().toJson(model);
    }
}
