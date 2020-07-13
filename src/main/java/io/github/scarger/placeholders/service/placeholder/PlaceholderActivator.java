package io.github.scarger.placeholders.service.placeholder;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.collection.document.account.UserAccount;
import io.github.scarger.placeholders.service.placeholder.provider.PlaceholderProvider;
import io.github.scarger.placeholders.service.placeholder.provider.RandomProvider;
import io.github.scarger.placeholders.service.placeholder.provider.RoleProvider;

import java.util.HashMap;

public class PlaceholderActivator {

    private final CoreService context;
    private final HashMap<String, PlaceholderProvider> providers;

    public PlaceholderActivator(CoreService context) {
        this.context = context;
        providers = new HashMap<>();
        registerPlaceholders();
    }

    private void registerPlaceholders() {
        //todo remove temporary test providers
        add(new RoleProvider(context));
        add(new RandomProvider(context));
    }

    private void add(PlaceholderProvider provider) {
        providers.put(provider.getPlaceholderMeta().getPlaceholder(), provider);
    }

    public void setState(String userId, String placeholderName, boolean enabled) {
        UserAccount account = context.getUserManager().getUsers().getUser(userId);
        boolean isPlaceholderPresent = account.getCurrentPlaceholders().contains(placeholderName);
        if(enabled && !isPlaceholderPresent) {
            context.getUserManager().getUsers().addPlaceholder(userId, placeholderName);
        } else if(!enabled && isPlaceholderPresent) {
            context.getUserManager().getUsers().removePlaceholder(userId, placeholderName);
        }
    }

    public HashMap<String, PlaceholderProvider> getProviders() {
        return providers;
    }
}
