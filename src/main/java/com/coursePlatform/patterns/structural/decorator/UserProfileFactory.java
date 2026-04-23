package com.coursePlatform.patterns.structural.decorator;

import com.coursePlatform.model.user.User;
import org.springframework.stereotype.Component;

/**
 * ПАТТЕРН DECORATOR — фабрика для сборки профиля с декораторами.
 * Строит цепочку декораторов динамически по переданным параметрам.
 */
@Component
public class UserProfileFactory {

    public UserProfile buildProfile(User user, String avatarUrl,
                                    String theme, String... badges) {
        UserProfile profile = new BasicUserProfile(user);

        if (avatarUrl != null && !avatarUrl.isBlank()) {
            profile = new AvatarDecorator(profile, avatarUrl);
        }

        if (theme != null && !theme.isBlank()) {
            profile = new ThemeDecorator(profile, theme);
        }

        for (String badge : badges) {
            profile = new AchievementBadgeDecorator(profile, badge, "🏅");
        }

        System.out.printf("[ProfileDecorator] Профиль собран для %s: %.0f%% заполнен%n",
                user.getUsername(), profile.getProfileCompleteness() * 100);
        return profile;
    }
}