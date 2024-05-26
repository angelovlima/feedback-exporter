package com.fiap.api.feedbacktracking.service;

import com.fiap.api.feedbacktracking.exception.FeedbackCategoryNotFoundException;
import com.fiap.api.feedbacktracking.exception.FeedbackNotFoundException;
import com.fiap.api.feedbacktracking.model.dto.FeedbackDTO;
import com.fiap.api.feedbacktracking.model.entity.FeedbackCategory;
import com.fiap.api.feedbacktracking.model.entity.Feedback;
import com.fiap.api.feedbacktracking.repository.FeedbackCategoryRepository;
import com.fiap.api.feedbacktracking.repository.FeedbackRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackCategoryRepository feedbackCategoryRepository;
    private final ExportCsvService exportCsvService;
    private final ExportPdfService exportPdfService;

    @Autowired
    public FeedbackService(
            FeedbackRepository feedbackRepository,
            FeedbackCategoryRepository feedbackCategoryRepository,
            ExportCsvService exportCsvService,
            ExportPdfService exportPdfService) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackCategoryRepository = feedbackCategoryRepository;
        this.exportCsvService = exportCsvService;
        this.exportPdfService = exportPdfService;
    }

    public Feedback toEntity(FeedbackDTO feedbackDTO) {
        FeedbackCategory feedbackCategory = feedbackCategoryRepository.findById(feedbackDTO.category().id())
                .orElseThrow(
                        () -> new FeedbackCategoryNotFoundException(
                                "FeedbackCategory not found with id: " + feedbackDTO.category().id()
                        ));
        return new Feedback(
                feedbackDTO.id(),
                feedbackCategory,
                feedbackDTO.title(),
                feedbackDTO.content(),
                feedbackDTO.author(),
                feedbackDTO.createdOn(),
                feedbackDTO.viewed(),
                feedbackDTO.viewedOn(),
                feedbackDTO.adminComment()
        );
    }

    public FeedbackDTO toDTO(Feedback feedback) {
        FeedbackCategory feedbackCategory = feedbackCategoryRepository.findById(feedback.getFeedbackCategory().getId())
                .orElseThrow(
                        () -> new FeedbackCategoryNotFoundException(
                                "FeedbackCategory not found with id: " + feedback.getFeedbackCategory().getId()
                        ));
        return new FeedbackDTO(
                feedback.getId(),
                feedbackCategory.toDTO(),
                feedback.getTitle(),
                feedback.getContent(),
                feedback.getAuthor(),
                feedback.getCreatedOn(),
                feedback.getViewed(),
                feedback.getViewedOn(),
                feedback.getAdminComment()
        );
    }

    public FeedbackDTO findById(Long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new FeedbackNotFoundException("Feedback not found with id: " + id));
        return toDTO(feedback);
    }

    public Page<FeedbackDTO> findAllPageable(Pageable pageable) {
        Page<Feedback> feedbackPage = feedbackRepository.findAll(pageable);
        return feedbackPage.map(this::toDTO);
    }

    public List<FeedbackDTO> findAll() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        return feedbackList.stream().map(this::toDTO).toList();
    }

    public FeedbackDTO save(FeedbackDTO feedbackDTO) {
        Feedback feedbackToSave = toEntity(feedbackDTO);
        return toDTO(feedbackRepository.save(feedbackToSave));
    }

    public FeedbackDTO update(FeedbackDTO feedbackDTO) {
        FeedbackCategory feedbackCategory = feedbackCategoryRepository.findById(feedbackDTO.category().id())
                .orElseThrow(
                        () -> new FeedbackCategoryNotFoundException(
                                "FeedbackCategory not found with id: " + feedbackDTO.category().id()
                        ));
        Feedback feedbackToUpdate = feedbackRepository.findById(feedbackDTO.id())
                .orElseThrow(
                        () -> new FeedbackNotFoundException(
                                "Feedback not found with id: " + feedbackDTO.id()
                        ));
        feedbackToUpdate.setTitle(feedbackDTO.title());
        feedbackToUpdate.setContent(feedbackDTO.content());
        feedbackToUpdate.setAuthor(feedbackDTO.author());
        feedbackToUpdate.setFeedbackCategory(feedbackCategory);
        return toDTO(feedbackRepository.save(feedbackToUpdate));
    }

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

    public void exportCSV(HttpServletResponse httpServletResponse) {
        List<FeedbackDTO> feedbackDTOList = findAll();
        exportCsvService.export(httpServletResponse, feedbackDTOList);
    }

    public void exportPDF(HttpServletResponse httpServletResponse) {
        List<FeedbackDTO> feedbackDTOList = findAll();
        exportPdfService.export(httpServletResponse, feedbackDTOList);
    }
}
