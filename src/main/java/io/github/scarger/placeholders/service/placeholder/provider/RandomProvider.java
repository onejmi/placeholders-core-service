package io.github.scarger.placeholders.service.placeholder.provider;

import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.service.placeholder.PlaceholderMeta;

public class RandomProvider extends PlaceholderProvider {

    public RandomProvider(CoreService context) {
        super(context,
                new PlaceholderMeta(
                        "random",
                        "Random Placeholder",
                        "Placeholder which generates a random number from 1-100",
                        "https://cerberus-laboratories.com/images/blog/random_numbers.jpg"
                )
        );
    }

    @Override
    public String value(String userId, String videoId) {
        return String.valueOf((int) (Math.ceil(Math.random() * 99) + 1));
    }
}
