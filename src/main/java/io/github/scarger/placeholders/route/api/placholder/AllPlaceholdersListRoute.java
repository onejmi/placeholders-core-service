package io.github.scarger.placeholders.route.api.placholder;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.service.placeholder.provider.PlaceholderProvider;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.stream.Collectors;

public class AllPlaceholdersListRoute implements Route {

    private final CoreService context;

    public AllPlaceholdersListRoute(CoreService context) {
        this.context = context;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return context.getPlaceholderActivator()
                .getProviders()
                .values()
                .stream()
                .map(PlaceholderProvider::getPlaceholderMeta)
                .collect(Collectors.toSet());
    }
}
