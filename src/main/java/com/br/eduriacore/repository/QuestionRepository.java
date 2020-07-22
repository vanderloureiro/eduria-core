package com.br.eduriacore.repository;

import java.util.List;

import com.br.eduriacore.model.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long>{

    @Query("SELECT q FROM Question q WHERE u.questionLevel = ?1 AND u.contentOder = ?2")
    List<Question> findAllByLevelAndContentOrder(int level, int contentOrder);

}