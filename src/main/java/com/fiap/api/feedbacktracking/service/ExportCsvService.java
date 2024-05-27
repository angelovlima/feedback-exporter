package com.fiap.api.feedbacktracking.service;

import com.fiap.api.feedbacktracking.exception.ExportIOException;
import com.fiap.api.feedbacktracking.exception.ExportIllegalArgumentException;
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
    public void export(HttpServletResponse response, List<? extends ExportableDTO> dtoList) throws ExportIOException {
        if (dtoList == null || dtoList.isEmpty()) {
            throw new ExportIllegalArgumentException("A lista de dados não pode ser nula ou vazia.");
        }

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-data.csv\"");

        try (PrintWriter writer = response.getWriter()) {
            writeCsvContent(writer, dtoList);
        } catch (IOException e) {
            throw new ExportIOException("Erro durante a manipulação dos arquivos CSV.", e);
        }
    }

    private void writeCsvContent(PrintWriter writer, List<? extends ExportableDTO> dtoList) {
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
}
