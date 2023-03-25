package com.example.demo1;

import java.time.LocalDate;
import java.util.ArrayList;

public class Stages {
    private int documentNum;
    private int newValue;
    private int oldValue;
    private LocalDate date;
    private String language;

    public Stages(int documentNum) {
        this.documentNum = documentNum;
    }

    public int getDocumentNum() {
        return documentNum;
    }

    public void setDocumentNum(int documentNum) {
        this.documentNum = documentNum;
    }

    public int getNewValue() {
        return newValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }

    public int getOldValue() {
        return oldValue;
    }

    public void setOldValue(int oldValue) {
        this.oldValue = oldValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isForward(ArrayList<Stages> stages, int index) {
        Stages currentStage = stages.get(index);
        if (index < stages.size() && index>0) {
            if (currentStage.getNewValue() < stages.get(index-1).getNewValue())
                return false;
        }
        return true;
    }
}

