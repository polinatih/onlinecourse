package com.coursePlatform.patterns.behavioral.command;

/**
 * Паттерн Command (Команда)
 * Используется для записи/отмены записи на курс с возможностью отмены действий (undo).
 */
public interface CourseCommand {
    void execute();
    void undo();
    String getDescription();
}
