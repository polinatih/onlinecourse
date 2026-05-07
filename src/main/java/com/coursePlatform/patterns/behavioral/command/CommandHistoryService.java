package com.coursePlatform.patterns.behavioral.command;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Сервис истории команд (Invoker).
 * Хранит историю действий каждого пользователя.
 */
@Service
public class CommandHistoryService {

    // userId -> стек команд
    private final Map<Long, Deque<CourseCommand>> history = new HashMap<>();
    // userId -> лог для отображения в UI
    private final Map<Long, List<String>> actionLog = new HashMap<>();

    public void execute(Long userId, CourseCommand command) {
        command.execute();
        history.computeIfAbsent(userId, k -> new ArrayDeque<>()).push(command);
        actionLog.computeIfAbsent(userId, k -> new ArrayList<>())
                 .add("✅ Выполнено: " + command.getDescription());
    }

    public String undoLast(Long userId) {
        Deque<CourseCommand> stack = history.get(userId);
        if (stack == null || stack.isEmpty()) {
            return "Нет действий для отмены";
        }
        CourseCommand last = stack.pop();
        last.undo();
        String msg = "↩️ Отменено: " + last.getDescription();
        actionLog.computeIfAbsent(userId, k -> new ArrayList<>()).add(msg);
        return msg;
    }

    public List<String> getLog(Long userId) {
        return actionLog.getOrDefault(userId, List.of());
    }

    public boolean hasHistory(Long userId) {
        Deque<CourseCommand> stack = history.get(userId);
        return stack != null && !stack.isEmpty();
    }
}
