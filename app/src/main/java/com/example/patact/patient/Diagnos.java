package com.example.patact.patient;

public class Diagnos {
    private long id;
    private long diagId;
    private String diagCode;
    private String diagText;
    private String text;
    private String date;
    private long typeId;
    private String typeText;
    private Long formId;
    private String formText;

    // Конструкторы
    public Diagnos() {
    }

    public Diagnos(long id, long diagId, String diagCode, String diagText, String text, String date, long typeId, String typeText, Long formId, String formText) {
        this.id = id;
        this.diagId = diagId;
        this.diagCode = diagCode;
        this.diagText = diagText;
        this.text = text;
        this.date = date;
        this.typeId = typeId;
        this.typeText = typeText;
        this.formId = formId;
        this.formText = formText;
    }

    // Геттеры
    public long getId() {
        return id;
    }

    public long getDiagId() {
        return diagId;
    }

    public String getDiagCode() {
        return diagCode;
    }

    public String getDiagText() {
        return diagText;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public long getTypeId() {
        return typeId;
    }

    public String getTypeText() {
        return typeText;
    }

    public Long getFormId() {
        return formId;
    }

    public String getFormText() {
        return formText;
    }
}

