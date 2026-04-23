package com.coursePlatform.repository;

import com.coursePlatform.model.test.TestAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestAttemptRepository extends JpaRepository<TestAttempt, Long> {
    List<TestAttempt> findByUserId(Long userId);
    List<TestAttempt> findByUserIdOrderByAttemptedAtDesc(Long userId);
}