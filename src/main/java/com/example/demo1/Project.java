package com.example.demo1;
import java.time.LocalDate;
import java.util.ArrayList;
public class Project {

    public Project(
            String projectNum){
        this.projectNum = projectNum;
    }
    String projectNum;
    String projectId;
    int stageCount;
    String date;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate createdOn;
    private LocalDate changedOn;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getChangedOn() {
        return changedOn;
    }

    public void setChangedOn(LocalDate changedOn) {
        this.changedOn = changedOn;
    }

    private ArrayList<Stages> stages = new ArrayList<>();

    public ArrayList<Stages> getStages(){
        return stages;
    }

    public void addStage(Stages stage) {
        stages.add(stage);
    }
    public Project(){}
    public Project(String v1, String v2, Integer v3, String v4) {
        this.projectNum =v1;
        this.projectId=v2;
        this.stageCount =v3;
        this.date =v4;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getStageCount() {
        return stageCount;
    }

    public void setStageCount(Integer stageCount) {
        this.stageCount = stageCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
