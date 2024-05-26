package com.fiap.api.feedbacktracking.model.dto;

import java.util.List;

public interface ExportableDTO {
    List<String> getHeaders();

    List<String> getData();
}
