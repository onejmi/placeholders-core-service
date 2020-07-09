package io.github.scarger.placeholders.service.placeholder;

import io.github.scarger.placeholders.CoreService;

public class RandomProvider extends PlaceholderProvider {

    public RandomProvider(CoreService context) {
        super(context);
    }

    @Override
    public String value(String userId) {
        return String.valueOf((int) (Math.ceil(Math.random() * 99) + 1));
    }
}
