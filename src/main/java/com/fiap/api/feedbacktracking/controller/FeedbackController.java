package com.fiap.api.feedbacktracking.controller;

import com.fiap.api.feedbacktracking.exception.ExportIOException;
import com.fiap.api.feedbacktracking.model.dto.FeedbackDTO;
import com.fiap.api.feedbacktracking.service.FeedbackService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackDTO> save(@RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO savedFeedbackDTO = feedbackService.save(feedbackDTO);
        return new ResponseEntity<>(savedFeedbackDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<FeedbackDTO> update(@RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO updatedFeedbackDTO = feedbackService.update(feedbackDTO);
        return ResponseEntity.ok(updatedFeedbackDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        feedbackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> findById(@PathVariable Long id) {
        FeedbackDTO feedbackDTO = feedbackService.findById(id);
        return ResponseEntity.ok(feedbackDTO);
    }

    @GetMapping
    public ResponseEntity<Page<FeedbackDTO>> findAllPageable(
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable
    ) {
        Page<FeedbackDTO> pageFeedbackDTO = feedbackService.findAllPageable(pageable);
        return ResponseEntity.ok(pageFeedbackDTO);
    }

    @GetMapping("/export/csv")
    @ResponseStatus(HttpStatus.OK)
    public void exportCSV(HttpServletResponse httpServletResponse) throws ExportIOException {
        feedbackService.exportCSV(httpServletResponse);
    }

    @GetMapping("/export/pdf")
    @ResponseStatus(HttpStatus.OK)
    public void exportPDF(HttpServletResponse httpServletResponse) throws ExportIOException {
        feedbackService.exportPDF(httpServletResponse);
    }
}
