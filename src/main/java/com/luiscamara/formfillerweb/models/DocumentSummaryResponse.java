package com.luiscamara.formfillerweb.models;

import java.time.LocalDateTime;

public class DocumentSummaryResponse {
    private String id;
    private String filename;
    private LocalDateTime createdAt;

    public DocumentSummaryResponse(String id, String filename, LocalDateTime createdAt) {
        this.id = id;
        this.filename = filename;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
