package io.github.scarger.placeholders.util.transformer;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.collection.document.account.UserAccount;

import java.util.Objects;

public class TitleTransformer {

    private CoreService context;

    public TitleTransformer(CoreService context) {
        this.context = context;
    }

    public String transformTitle(String userId, String videoId, String title) {
        StringBuilder formattedTitleBuilder = new StringBuilder();
        for(String part : title.split(" ")) {
            formattedTitleBuilder.append(" ");
            if(part.startsWith("{") && part.endsWith("}")) {
                String placeholder = part.substring(1, part.length() - 1);
                UserAccount user = context.getUserManager().getUsers().getUser(userId);
                if(user.getCurrentPlaceholders().contains(placeholder)) {
                    String value = context.getPlaceholderActivator()
                            .getProviders()
                            .get(placeholder)
                            .value(userId, videoId);
                    formattedTitleBuilder.append(Objects.requireNonNullElse(value, part));
                    continue;
                }
            }
            formattedTitleBuilder.append(part);
        }
        return formattedTitleBuilder.toString().trim();
    }
}
