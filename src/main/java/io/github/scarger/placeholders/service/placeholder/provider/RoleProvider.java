package io.github.scarger.placeholders.service.placeholder.provider;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.service.placeholder.PlaceholderMeta;

public class RoleProvider extends PlaceholderProvider {

    public RoleProvider(CoreService context) {
        super(context,
                new PlaceholderMeta(
                        "role",
                        "Role Placeholder",
                        "Shows your current subscription role.",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fb/Heraldic_eastern_crown.svg/" +
                                "1200px-Heraldic_eastern_crown.svg.png"
                )
        );
    }

    @Override
    public String value(String userId, String videoId) {
        return context.getUserManager().getUsers().getUser(userId).getRole().toString();
    }
}
