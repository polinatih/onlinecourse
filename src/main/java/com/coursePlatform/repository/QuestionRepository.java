package com.coursePlatform.repository;

import com.coursePlatform.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByDifficultyScoreLessThanEqual(Integer maxDifficulty);
}
