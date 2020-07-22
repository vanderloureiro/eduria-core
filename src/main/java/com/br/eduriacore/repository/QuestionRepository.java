package com.br.eduriacore.repository;

import java.util.List;

import com.br.eduriacore.model.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long>{

    @Query("SELECT q FROM Question q WHERE q.questionLevel = ?1 AND q.contentOrder = ?2 AND q.course.courseId = ?3")
    List<Question> findAllByFilters(int level, int contentOrder, Long courseId);

}