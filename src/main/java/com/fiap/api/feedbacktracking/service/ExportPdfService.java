package com.fiap.api.feedbacktracking.service;

import com.fiap.api.feedbacktracking.exception.ExportIOException;
import com.fiap.api.feedbacktracking.exception.ExportIllegalArgumentException;
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
    public void export(HttpServletResponse response, List<? extends ExportableDTO> dtoList) throws ExportIOException {
        if (dtoList == null || dtoList.isEmpty()) {
            throw new ExportIllegalArgumentException("A lista de dados não pode ser nula ou vazia.");
        }
        try {
            String htmlContent = generateHtmlContent(dtoList);

            ByteArrayOutputStream pdfOutputStream = generatePdfFromHtml(htmlContent);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"export.pdf\"");
            response.setContentLength(pdfOutputStream.size());

            response.getOutputStream().write(pdfOutputStream.toByteArray());
        } catch (IllegalArgumentException e) {
            throw new ExportIllegalArgumentException("Argumento ilegal ou inapropriado fornecido para o método de exportação de feedback em PDF.", e);
        } catch (IOException e) {
            throw new ExportIOException("Erro durante a manipulação dos arquivos PDF.", e);
        }
    }

    private String generateHtmlContent(List<? extends ExportableDTO> dtoList) {
        Context context = new Context();
        context.setVariable("dtoList", dtoList);
        return templateEngine.process("exportPdfTemplate", context);
    }

    private ByteArrayOutputStream generatePdfFromHtml(String htmlContent) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);

            return outputStream;
        } catch (IOException e) {
            throw new ExportIOException("Erro ao gerar PDF a partir do HTML.", e);
        }
    }
}
