package io.github.scarger.placeholders.service.placeholder;

import io.github.scarger.placeholders.CoreService;

public abstract class PlaceholderProvider {

    protected CoreService context;

    public PlaceholderProvider(CoreService context) {
        this.context = context;
    }

    public abstract String value(String userId);
}
