package com.fiap.api.feedbacktracking.service;

import com.fiap.api.feedbacktracking.exception.ExportExceptionHandler;
import com.fiap.api.feedbacktracking.model.dto.ExportableDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExportCsvService implements ExportDataService {

    @Override
    public void export(HttpServletResponse response, List<? extends ExportableDTO> dtoList) {
        try {
            if (dtoList == null || dtoList.isEmpty()) {
                throw new IllegalArgumentException("Data list cannot be null or empty");
            }

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"export-data.csv\"");

            try (PrintWriter writer = response.getWriter()) {
                ExportableDTO dtoToGetHeaderList = dtoList.get(0);
                List<String> headers = dtoToGetHeaderList.getHeaders();
                writer.println(String.join(",", headers));

                for (ExportableDTO dto : dtoList) {
                    List<String> data = dto.getData();
                    writer.println(data.stream()
                            .map(attributeValue -> attributeValue != null ? attributeValue : "")
                            .collect(Collectors.joining(",")));
                }

                writer.flush();
            }
        } catch (IllegalArgumentException e) {
            ExportExceptionHandler.handleException(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid data: " + e.getMessage());
        } catch (IOException e) {
            ExportExceptionHandler.handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "IO error: " + e.getMessage());
        } catch (Exception e) {
            ExportExceptionHandler.handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error: " + e.getMessage());
        }
    }
}
