package io.github.scarger.placeholders.service.placeholder.provider;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.service.placeholder.PlaceholderMeta;

public abstract class PlaceholderProvider {

    protected CoreService context;
    private PlaceholderMeta placeholderMeta;

    public PlaceholderProvider(CoreService context, PlaceholderMeta placeholderMeta) {
        this.placeholderMeta = placeholderMeta;
        this.context = context;
    }

    public PlaceholderMeta getPlaceholderMeta() {
        return placeholderMeta;
    }

    public abstract String value(String userId, String videoId);
}
