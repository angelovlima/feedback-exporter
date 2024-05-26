package com.fiap.api.feedbacktracking.model.dto;

import com.fiap.api.feedbacktracking.model.entity.FeedbackCategory;

public record FeedbackCategoryDTO(
        Long id,
        String name
) {
    public FeedbackCategory toEntity() {
        return new FeedbackCategory(this.id, this.name);
    }
}
