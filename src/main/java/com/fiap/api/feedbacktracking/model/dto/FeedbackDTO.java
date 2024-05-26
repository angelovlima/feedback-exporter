package com.fiap.api.feedbacktracking.model.dto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public record FeedbackDTO(
        Long id,
        FeedbackCategoryDTO category,
        String title,
        String content,
        String author,
        LocalDateTime createdOn,
        Boolean viewed,
        LocalDateTime viewedOn,
        String adminComment
) implements ExportableDTO {

    @Override
    public List<String> getHeaders() {
        return Arrays.asList("id", "FeedbackCategory", "Title", "Content", "Author", "Created On");
    }

    @Override
    public List<String> getData() {
        return Arrays.asList(id.toString(), category.name(), title, content, author, createdOn.toString());
    }
}
