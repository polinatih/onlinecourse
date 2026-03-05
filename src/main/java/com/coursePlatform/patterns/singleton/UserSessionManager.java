package com.coursePlatform.patterns.singleton;

import com.coursePlatform.model.user.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class UserSessionManager {

    private static volatile UserSessionManager instance;
    private final Map<String, User> activeSessions = new ConcurrentHashMap<>();

    private UserSessionManager() {}

    public static UserSessionManager getInstance() {
        if (instance == null) {
            synchronized (UserSessionManager.class) {
                if (instance == null) {
                    instance = new UserSessionManager();
                }
            }
        }
        return instance;
    }

    public void openSession(String sessionId, User user) {
        activeSessions.put(sessionId, user);
        System.out.printf("[SessionManager] Сессия открыта: %s → %s%n",
                sessionId, user.getUsername());
    }

    public void closeSession(String sessionId) {
        activeSessions.remove(sessionId);
        System.out.printf("[SessionManager] Сессия закрыта: %s%n", sessionId);
    }

    public Optional<User> getUserBySession(String sessionId) {
        return Optional.ofNullable(activeSessions.get(sessionId));
    }

    public int getActiveSessionCount() {
        return activeSessions.size();
    }

    public boolean isSessionActive(String sessionId) {
        return activeSessions.containsKey(sessionId);
    }
}
