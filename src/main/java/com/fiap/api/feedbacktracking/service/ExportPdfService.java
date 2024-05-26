package com.fiap.api.feedbacktracking.service;

import com.fiap.api.feedbacktracking.exception.ExportExceptionHandler;
import com.fiap.api.feedbacktracking.model.dto.ExportableDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportPdfService implements ExportDataService {

    private final TemplateEngine templateEngine;

    public ExportPdfService() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        this.templateEngine = new SpringTemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    public void export(HttpServletResponse response, List<? extends ExportableDTO> dtoList) {
        try {
            if (dtoList == null || dtoList.isEmpty()) {
                throw new IllegalArgumentException("Data list cannot be null or empty");
            }

            String htmlContent = generateHtmlContent(dtoList);

            ByteArrayOutputStream pdfOutputStream = generatePdfFromHtml(htmlContent);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"export.pdf\"");
            response.setContentLength(pdfOutputStream.size());

            response.getOutputStream().write(pdfOutputStream.toByteArray());
        } catch (IllegalArgumentException e) {
            ExportExceptionHandler.handleException(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid data: " + e.getMessage());
        } catch (IOException e) {
            ExportExceptionHandler.handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "IO error: " + e.getMessage());
        } catch (Exception e) {
            ExportExceptionHandler.handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error: " + e.getMessage());
        }
    }

    private String generateHtmlContent(List<? extends ExportableDTO> dtoList) {
        Context context = new Context();
        context.setVariable("dtoList", dtoList);
        return templateEngine.process("exportPdfTemplate", context);
    }

    private ByteArrayOutputStream generatePdfFromHtml(String htmlContent) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);

        return outputStream;
    }
}
