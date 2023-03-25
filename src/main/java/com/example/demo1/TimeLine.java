package com.example.demo1;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TimeLine extends Pane  {
    private DateFormatter dateFormatter = new DateFormatter();

    public TimeLine( Project project){
        drawTimeLine(project);
        this.setId("Time/Line");
    }

    public void drawTimeLine(Project project) {
         drawTimeLine(10, project);

    }
    private void drawTimeLine(int offset, Project project) {

        VBox vBox = new VBox();
        int axisLength = 1010 - (2 * offset); // this will always be 780
        int Days;
        try {
            Days = dateFormatter.dateDifference(project) + 1;
        } catch (Exception e) {
        Days=0;
        }
        double distanceBetweenDays = (double) axisLength / (double) Days ;
        axisLength = (int) ((offset) + (Days * distanceBetweenDays)); // To Extend the line if needed.
        // draw the main line from left to right


        Text duration = new Text("Duration: "+dateFormatter.getDuration(project));
        duration.setTranslateX(300);
        duration.setTranslateY(-80);
        duration.setFill(Color.RED);
        this.getChildren().add(duration);
        Line horizontalAxis = new Line(offset, offset * 3, axisLength, offset * 3);


        horizontalAxis.setId("main-line");
        horizontalAxis.setStrokeWidth(2);
        this.getChildren().add(horizontalAxis);

        // We will show all days only if the difference between each 2 days is less than 1. Otherwise, the timeline will be too crowded with lines.
        // So to make the distance between days at least one, we should show only some days. The next variable determines how many days to show.
        int everyIthDays = (int) Math.ceil((double) Days / 180); // I found 180 a suitable value :)
        // The next does the same thing but for months.
        int possibleMonths = (int) Math.ceil((double) Days / 30);
        int everyIthMonths = (int) Math.ceil((double) possibleMonths / 6); // Cap the number of shown months to 6.
        int monthCounter = 0; // will be used to determine if ith month should be shown.
        // Stages
        ArrayList<Stages> stages = project.getStages();
        int numOfStages = stages.size();
        int stageIndex = 0;

        // Draw first and last dates
        LocalDate startDate = dateFormatter.getStartDate(project);
        LocalDate endDate = dateFormatter.getEndDate(project);
        drawStartDate(offset, offset, startDate);
        drawEndDate(axisLength, offset, endDate);
        boolean downLineDirection = true;
        for (int i = 0; i < Days; i++) {
            LocalDate currentTempDay = startDate.plusDays(i);

            boolean startOfMonth = isStartOfMonth(currentTempDay);
            double dayLineX = (offset) + (i * distanceBetweenDays);
            Line dayLine = new Line(dayLineX, offset * 2, dayLineX, offset * 4);
            dayLine.getStyleClass().add("dayLine");
            dayLine.setStrokeWidth(1);
            if (i % everyIthDays == 0)
            {
                this.getChildren().add(dayLine);
            }
            // check if month should be shown. If so, then show Month with this format: MMM year.
            if (startOfMonth) {
                if (everyIthMonths != 0)  // TODO handle this issue better
                    if (monthCounter++ % everyIthMonths == 0) {
                        drawMajorDate(dayLineX, offset, dayLine, currentTempDay);
                    }
            }
            int temp = stageIndex;
            stageIndex = drawStages(offset, project, stages, numOfStages, stageIndex, currentTempDay, dayLineX, downLineDirection);
            if (stageIndex != temp)
                downLineDirection = !downLineDirection;
        }
    }
    public void clearTimeLine() {
        this.getChildren().clear();
    }
        private void drawMajorDate(double dayLineX, int offset, Line dayLine, LocalDate currentTempDay) {
            String DateLabel = currentTempDay.getMonth().toString().substring(0, 3) + " " + currentTempDay.getYear();
            Label yearLabel = new Label(DateLabel);
            yearLabel.setLayoutX(dayLineX - offset);
            yearLabel.setLayoutY(offset * 5);
            yearLabel.setStyle("-fx-font-size: 11px; -fx-font-style: italic;");
            dayLine.setStrokeWidth(3);
            if (!this.getChildren().contains(dayLine))
                this.getChildren().add(dayLine);
            this.getChildren().add(yearLabel);
        }
        private void drawStartDate(double dayLineX, int offset, LocalDate date) {
            drawMajorLine(dayLineX, offset, true, date);
        }
        private void drawEndDate(double dayLineX, int offset, LocalDate date) {
            drawMajorLine(dayLineX, offset, false, date);
        }
        private void drawMajorLine(double dayLineX, int offset, boolean leftLabel, LocalDate date) {
            Line dayLine = new Line(dayLineX, offset * 2, dayLineX, offset * 4);
            dayLine.getStyleClass().add("MajorLine");
            dayLine.setStrokeWidth(3);
            if (!this.getChildren().contains(dayLine))
                this.getChildren().add(dayLine);

            int notationLabelWidth = (offset * 9);

            Label notationLabel = new Label(date.toString());
            notationLabel.setLayoutX(leftLabel? -40: dayLineX);
            notationLabel.setLayoutY(offset - 20);
            notationLabel.setPrefWidth(notationLabelWidth);
            notationLabel.setPrefHeight(40);
            notationLabel.setStyle("-fx-font-size: 10px; -fx-text-alignment: left;");

            this.getChildren().add(notationLabel);
        }

    private int drawStages(int offset, Project project, ArrayList<Stages> stages, int numOfStages, int stageIndex, LocalDate currentTempDay, double dayLineX, boolean direction) {
        int extend = 0;
        int yPos;
        int endY;

        boolean isForward;
        while (stageIndex < numOfStages && currentTempDay.equals(stages.get(stageIndex).getDate())) {
            Stages currentStage = stages.get(stageIndex);
            Label stageDateLabel=new Label(String.valueOf(currentStage.getDate()));
            Label boxLabel = new Label(".");
            isForward= currentStage.isForward(stages,stageIndex);

            boxLabel.setBackground(new Background(new BackgroundFill(isForward? Color.GREEN: Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            // Stage Label
            String DateLabel = " "+ String.valueOf(currentStage.getNewValue());
            Label stageLabel = new Label(DateLabel);
            stageLabel.setTextFill(isForward ? Color.GREEN: Color.RED);
//
            yPos = offset * -1 - (extend += 20);
            endY = offset * -2 - extend;
            stageLabel.setLayoutX(dayLineX);
            stageLabel.setLayoutY(yPos);
            stageLabel.setStyle("-fx-font-size: 12px;");
            boxLabel.setLayoutX(dayLineX);
            boxLabel.setLayoutY(yPos);
            stageDateLabel.setTranslateX(dayLineX-30);
            stageDateLabel.setTranslateY(offset*3+60);
            stageDateLabel.setRotate(90);
            Line notationLine = new Line(dayLineX-5, offset * 3, dayLineX-5, endY);


            notationLine.getStyleClass().add("notationLine");
            notationLine.setStrokeWidth(1);
            notationLine.setStroke(Color.YELLOW);
            this.getChildren().add(stageDateLabel);
            this.getChildren().add(notationLine);
            this.getChildren().add(boxLabel);
            this.getChildren().add(stageLabel);
            stageIndex++;
        }

        return stageIndex;
    }
    private boolean isStartOfMonth(LocalDate date) {
        return date.getDayOfMonth() == 1;
    }


}
