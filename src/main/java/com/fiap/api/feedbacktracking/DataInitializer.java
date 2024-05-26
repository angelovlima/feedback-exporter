package com.fiap.api.feedbacktracking;

import com.fiap.api.feedbacktracking.model.entity.FeedbackCategory;
import com.fiap.api.feedbacktracking.repository.FeedbackCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    //Criado unicamente para manter tipos de categoryFeedback pre-cadastrados antes da criação do banco.
    @Bean
    public CommandLineRunner initializeDatabase(FeedbackCategoryRepository feedbackCategoryRepository) {
        return args -> {
            FeedbackCategory elogioCategory = new FeedbackCategory();
            elogioCategory.setName("Elogio");
            feedbackCategoryRepository.save(elogioCategory);

            FeedbackCategory sugestaoCategory = new FeedbackCategory();
            sugestaoCategory.setName("Sugestão");
            feedbackCategoryRepository.save(sugestaoCategory);

            FeedbackCategory reclamacaoCategory = new FeedbackCategory();
            reclamacaoCategory.setName("Reclamação");
            feedbackCategoryRepository.save(reclamacaoCategory);
        };
    }
}
