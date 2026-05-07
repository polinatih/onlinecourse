package com.coursePlatform.patterns.behavioral.chain;

import java.util.ArrayList;
import java.util.List;

public class CheckResult {
    private final boolean allowed;
    private final String  message;
    private final List<String> passedSteps;

    private CheckResult(boolean allowed, String message, List<String> passedSteps) {
        this.allowed     = allowed;
        this.message     = message;
        this.passedSteps = passedSteps;
    }

    public static CheckResult success(String message) {
        return new CheckResult(true, message, new ArrayList<>());
    }

    public static CheckResult failure(String message) {
        return new CheckResult(false, message, new ArrayList<>());
    }

    public boolean isAllowed()          { return allowed; }
    public String getMessage()          { return message; }
    public List<String> getPassedSteps(){ return passedSteps; }

    public void addPassedStep(String step) {
        passedSteps.add(step);
    }
}
