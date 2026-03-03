package com.coursePlatform.patterns.prototype;

import com.coursePlatform.model.question.Question;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ═══════════════════════════════════════════════════════════
 * ПАТТЕРН: PROTOTYPE
 * ═══════════════════════════════════════════════════════════
 * Реестр прототипов вопросов. clone() создаёт независимую копию.
 * SOLID SRP: отвечает только за хранение и клонирование прототипов.
 */
@Component
public class QuestionPrototypeRegistry {

    private final Map<String, Question> registry = new HashMap<>();

    public void register(String key, Question question) {
        registry.put(key, question);
        System.out.printf("[PrototypeRegistry] Зарегистрирован прототип: '%s'%n", key);
    }

    public Question cloneQuestion(String key) {
        Question prototype = registry.get(key);
        if (prototype == null) {
            throw new IllegalArgumentException("Прототип не найден: " + key);
        }
        Question cloned = prototype.clone();
        System.out.printf("[PrototypeRegistry] Клонирован вопрос '%s'%n", key);
        return cloned;
    }

    public boolean hasPrototype(String key) {
        return registry.containsKey(key);
    }

    public Set<String> getRegisteredKeys() {
        return registry.keySet();
    }
}
