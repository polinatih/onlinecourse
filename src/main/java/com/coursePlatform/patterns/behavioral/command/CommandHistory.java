package com.coursePlatform.patterns.behavioral.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Инициатор (Invoker) — история команд с поддержкой undo
 */
public class CommandHistory {

    private Deque<CourseCommand> history = new ArrayDeque<>();

    public void executeCommand(CourseCommand command) {
        command.execute();
        history.push(command);
    }

    public void undoLastCommand() {
        if (!history.isEmpty()) {
            CourseCommand last = history.pop();
            last.undo();
        } else {
            System.out.println("[CommandHistory] Нет команд для отмены");
        }
    }
}
