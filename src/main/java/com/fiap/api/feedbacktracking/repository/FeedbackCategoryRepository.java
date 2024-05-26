package com.fiap.api.feedbacktracking.repository;

import com.fiap.api.feedbacktracking.model.entity.FeedbackCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackCategoryRepository extends JpaRepository<FeedbackCategory, Long> {
}
