package com.otterdesk.pdfWorkerService.model;

import java.util.Date;

public class PdfEvent {

    public String blueprintId;
    public String currentProcessingPhase;
    public String fileLocation;
    public Date createTime;

    public PdfEvent() {}

    public PdfEvent(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public PdfEvent(String blueprintId, String currentProcessingPhase, String fileLocation, Date createTime) {
        this.blueprintId = blueprintId;
        this.currentProcessingPhase = currentProcessingPhase;
        this.fileLocation = fileLocation;
        this.createTime = createTime;
    }

    public String getBlueprintId() {
        return blueprintId;
    }

    public void setBlueprintId(String blueprintId) {
        this.blueprintId = blueprintId;
    }

    public String getCurrentProcessingPhase() {
        return currentProcessingPhase;
    }

    public void setCurrentProcessingPhase(String currentProcessingPhase) {
        this.currentProcessingPhase = currentProcessingPhase;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
