package io.github.scarger.placeholders.util.transformer;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.service.placeholder.PlaceholderProvider;
import io.github.scarger.placeholders.service.placeholder.RandomProvider;
import io.github.scarger.placeholders.service.placeholder.RoleProvider;

import java.util.HashMap;
import java.util.Objects;

public class TitleTransformer {

    private final HashMap<String, PlaceholderProvider> providers;

    public TitleTransformer(CoreService context) {
        providers = new HashMap<>();

        //todo remove temporary test providers
        providers.put("role", new RoleProvider(context));
        providers.put("random", new RandomProvider(context));
    }

    public String transformTitle(String userId, String title) {
        StringBuilder formattedTitleBuilder = new StringBuilder();
        for(String part : title.split(" ")) {
            if(part.startsWith("{") && part.endsWith("}")) {
                String placeholder = part.substring(1, part.length() - 1);
                String value = providers.get(placeholder).value(userId);
                formattedTitleBuilder.append(Objects.requireNonNullElse(value, part));
            } else {
                formattedTitleBuilder.append(part);
            }
            formattedTitleBuilder.append(" ");
        }
        return formattedTitleBuilder.toString().trim();
    }
}
