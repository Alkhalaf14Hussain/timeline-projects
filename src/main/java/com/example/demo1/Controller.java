package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class Controller extends Pane  {
    TextField textID= new TextField();
    Label label= new Label("project ID");

    Label durationLabel = new Label();
    Label projectLabel = new Label();
    Label projectInfoLabel= new Label();
    TableView<Project> tableView = new TableView<>();
    TableColumn<Project,String> index = new TableColumn("project #");
    TableColumn<Project,String> tbProjectID = new TableColumn("project Id");
    TableColumn<Project,Integer> tbStage = new TableColumn("stage");
    TableColumn <Project, String> tbDate = new TableColumn("date");


    public Node createTableView(){
        return tableView;
    }
    TimeLine timeLine;
    ReworkStage reworkStage;
    HBox hBox = new HBox();
    public Controller(){
        hBox.getChildren().addAll(label,textID);
        hBox.setSpacing(10);
        hBox.setTranslateX(500);
        this.getChildren().add(hBox);
        ArrayList<Project> list = null;
        try {
            list = (ArrayList<Project>) FileReader.getProjects("C:\\Users\\Dell\\Desktop\\Projects.xls", "C:\\Users\\Dell\\Desktop\\Stages.xls", "C:\\Users\\Dell\\Desktop\\Stages_Detailed.xls");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.projects = list;
        for (int i = 0; i < projects.size(); ++i)
            projects.get(i).setProjectNum(String.valueOf(i + 1));
        tableView.getColumns().addAll(index,tbProjectID,tbStage);
        index.setCellValueFactory(new PropertyValueFactory<>("projectNum"));
        tbProjectID.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        tbStage.setCellValueFactory(new PropertyValueFactory<>("stageCount"));

        Project selected = tableView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            timeLine = new TimeLine(selected);
            reworkStage = new ReworkStage(selected.getStages().get(Integer.parseInt(selected.projectNum)).getDocumentNum()
                    ,selected);
            textID.setText(selected.projectId);

        }
        else {
            textID.setText(projects.get(0).projectId);
            timeLine = new TimeLine(projects.get(0));
            reworkStage = new ReworkStage(projects.get(0).getStages().get(0).getDocumentNum(),projects.get(0));
        }
        ObservableList<Project> projectList = FXCollections.observableArrayList(this.projects);
        tableView.setItems(projectList);
        tableView.setOnKeyPressed(e -> selectedProject());
        tableView.setOnMouseClicked(this::selectedProject);
        tableView.getSelectionModel().selectFirst();

        VBox vBox = new VBox();
        vBox.setSpacing(100);
        HBox box = new HBox();
        vBox.getChildren().addAll(reworkStage.pane,timeLine);
        box.getChildren().addAll(tableView,vBox);
        box.setSpacing(50);
        this.getChildren().add(box);
    }
    ArrayList<Project> projects;

    private void selectedProject(MouseEvent mouseEvent) {
        Project project =tableView.getSelectionModel().getSelectedItem();
        if (project != null) {
            showProjectInfo(project);
            showReworkInfo(project);
            textID.setText(project.projectId);
        }
    }
    private void selectedProject() {
        Project project = tableView.getSelectionModel().getSelectedItem();
        if (project != null) {
            showProjectInfo(project);
            showReworkInfo(project);
        }
    }
    private void showProjectInfo(Project project) {
        this.timeLine.clearTimeLine();
        this.timeLine.drawTimeLine(project);
        projectLabel.setText(project.getProjectId());
        int duration = new DateFormatter().getDuration(project);
        if (duration != 0)
            if (duration == 1)
                durationLabel.setText(duration + " Day");
            else
                durationLabel.setText(duration + " Days");
        projectInfoLabel.setText("Start Date: " + project.getStartDate() + " End Date: " + project.getEndDate() + "\nCreated On: " + project.getCreatedOn() + " Last Change: " + project.getChangedOn());
    }
    private void showReworkInfo(Project project){
        this.reworkStage.clearTimeLine();

        this.reworkStage.reworkCalculation(project);
    }
}