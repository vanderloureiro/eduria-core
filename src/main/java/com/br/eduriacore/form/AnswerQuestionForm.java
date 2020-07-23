package com.br.eduriacore.form;

import lombok.Data;

@Data
public class AnswerQuestionForm {
    
    private Long questionId;
    private Long enrollmentId;
    private int selectedAlternative;

}