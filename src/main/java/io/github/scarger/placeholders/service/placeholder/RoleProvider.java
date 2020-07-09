package io.github.scarger.placeholders.service.placeholder;

import io.github.scarger.placeholders.CoreService;

public class RoleProvider extends PlaceholderProvider {

    public RoleProvider(CoreService context) {
        super(context);
    }

    @Override
    public String value(String userId) {
        return context.getUserManager().getUsers().getUser(userId).getRole().toString();
    }
}
