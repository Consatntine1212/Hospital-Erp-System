package com.HospitalErp.Request_Models;

import java.sql.Timestamp;

public class MessageRequest {

    private Long senderId;

    private String emailBody;

    private String emailSubject;

    private Timestamp timeStamp;

    private Boolean isDraft;

    private String recipientEmail;
    // Constructors, getters, and setters

    public MessageRequest(Long senderId, String emailBody, String emailSubject, Timestamp timeStamp, Boolean isDraft, String recipientEmail) {
        this.senderId = senderId;
        this.emailBody = emailBody;
        this.emailSubject = emailSubject;
        this.timeStamp = timeStamp;
        this.isDraft = isDraft;
        this.recipientEmail = recipientEmail;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }
}
