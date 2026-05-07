package com.coursePlatform.patterns.behavioral.observer;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Субъект паттерна Observer.
 * Хранит подписчиков на курс и рассылает им события.
 */
@Service
public class CourseNotificationService {

    // courseId -> список подписчиков
    private final Map<Long, List<CourseEventListener>> subscribers = new HashMap<>();

    // courseId -> лог событий для отображения в UI
    private final Map<Long, List<String>> eventLog = new LinkedHashMap<>();

    public void subscribe(Long courseId, CourseEventListener listener) {
        subscribers.computeIfAbsent(courseId, k -> new ArrayList<>()).add(listener);
        log(courseId, "🔔 " + listener.getSubscriberName() + " подписался на обновления");
    }

    public void unsubscribe(Long courseId, String subscriberName) {
        List<CourseEventListener> list = subscribers.get(courseId);
        if (list != null) {
            list.removeIf(l -> l.getSubscriberName().equals(subscriberName));
            log(courseId, "🔕 " + subscriberName + " отписался");
        }
    }

    public void notifySubscribers(Long courseId, String eventType, String message) {
        log(courseId, "📢 [" + eventType + "] " + message);
        List<CourseEventListener> list = subscribers.getOrDefault(courseId, List.of());
        for (CourseEventListener l : list) {
            l.onEvent(eventType, message);
        }
    }

    public int getSubscriberCount(Long courseId) {
        return subscribers.getOrDefault(courseId, List.of()).size();
    }

    public List<String> getEventLog(Long courseId) {
        return eventLog.getOrDefault(courseId, List.of());
    }

    public boolean isSubscribed(Long courseId, String name) {
        return subscribers.getOrDefault(courseId, List.of())
                .stream().anyMatch(l -> l.getSubscriberName().equals(name));
    }

    private void log(Long courseId, String entry) {
        eventLog.computeIfAbsent(courseId, k -> new ArrayList<>()).add(entry);
    }
}
