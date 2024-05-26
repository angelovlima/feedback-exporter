package com.fiap.api.feedbacktracking.model.entity;

import com.fiap.api.feedbacktracking.model.dto.FeedbackCategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback-category")
public class FeedbackCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public FeedbackCategoryDTO toDTO() {
        return new FeedbackCategoryDTO(id, name);
    }
}
