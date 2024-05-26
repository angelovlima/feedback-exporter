package com.fiap.api.feedbacktracking.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feedback_category_id")
    private FeedbackCategory feedbackCategory;

    private String title;
    private String content;

    //Vai mudar para objeto Usuario quando chegar na matéria de Security
    private String author;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    private Boolean viewed;

    @Column(name = "viewed_on")
    private LocalDateTime viewedOn;

    @Column(name = "admin_comment")
    private String adminComment;
}
