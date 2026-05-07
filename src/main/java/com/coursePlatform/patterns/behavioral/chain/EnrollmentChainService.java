package com.coursePlatform.patterns.behavioral.chain;

import com.coursePlatform.model.user.User;
import org.springframework.stereotype.Service;

/**
 * Сервис цепочки ответственности.
 * Собирает цепочку и запускает проверку.
 */
@Service
public class EnrollmentChainService {

    private final AgeCheckHandler          ageCheck;
    private final PaymentCheckHandler      paymentCheck;
    private final PrerequisiteCheckHandler prerequisiteCheck;

    public EnrollmentChainService(AgeCheckHandler ageCheck,
                                  PaymentCheckHandler paymentCheck,
                                  PrerequisiteCheckHandler prerequisiteCheck) {
        this.ageCheck          = ageCheck;
        this.paymentCheck      = paymentCheck;
        this.prerequisiteCheck = prerequisiteCheck;

        ageCheck.setNext(paymentCheck).setNext(prerequisiteCheck);
    }

    public CheckResult check(User user) {
        boolean hasPaid = !user.getEnrolledCourses().isEmpty();
        int completed   = user.getEnrolledCourses().size();

        CheckRequest request = new CheckRequest(
                user.getUsername(),
                20,
                hasPaid,
                completed
        );
        return ageCheck.handle(request);
    }
}