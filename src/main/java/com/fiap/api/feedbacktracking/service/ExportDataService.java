package com.fiap.api.feedbacktracking.service;

import com.fiap.api.feedbacktracking.model.dto.ExportableDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface ExportDataService {
    void export(HttpServletResponse response, List<? extends ExportableDTO> dtoList) throws IOException;
}
