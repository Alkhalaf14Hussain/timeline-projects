package com.example.demo1;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import java.util.ArrayList;

public class ReworkStage extends Stages {

    Pane pane = new Pane();
    public ReworkStage(int documentNumber){
        super(documentNumber);

    }
    public ReworkStage(int documentNum,Project project) {
        super(documentNum);
        pane=reworkCalculation(project);

    }

    ArrayList<Stages> s;
    int beforeAward=0;
    int afterAward=0;
    public Pane reworkCalculation(Project projectStages){
        s = projectStages.getStages();
        boolean awarded=false;

        for (int i=0; i<s.size();i++) {
            if (s.size()>i)
            {if (s.get(i).getNewValue()==5)
                awarded=true;
            }

            if (s.size()>=5 && s.size()> i+1) {
                if (!awarded){

                    if (s.get(i).getOldValue() > s.get(i).getNewValue()) {
                        beforeAward++;
                    }
            }else {

                    if (s.get(i).getOldValue() > s.get(i).getNewValue())
                        afterAward++;
                }
            }
        }



        Label reworkLable= new Label("REWORK         ");
        reworkLable.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        reworkLable.setScaleX(2);
        reworkLable.setTranslateX(30);
        reworkLable.setTranslateY(0);

        Text beforeText =new Text(String.valueOf(beforeAward));
        beforeText.setFill(Color.RED);
        beforeText.setFont(new Font("Cambria", 25));
        Text afterText = new Text(String.valueOf(afterAward));
        afterText.setFill(Color.GREEN);
        afterText.setFont(new Font("Cambria", 25));

        Label beforeLable= new Label("before Award    After Award");

        beforeLable.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
        beforeLable.setScaleX(1);

        VBox vBox = new VBox();
        HBox hBox = new HBox();
        hBox.setSpacing(60);
        hBox.setTranslateX(40);
        HBox hBox1 =new HBox();
        hBox1.setSpacing(10);

        hBox.getChildren().addAll(beforeText,afterText);
        hBox1.getChildren().addAll(beforeLable);
        vBox.getChildren().addAll(reworkLable,hBox,hBox1);
        vBox.setSpacing(3);
        pane.getChildren().add(vBox);
        return pane;

    }

    public void clearTimeLine() {
        pane.getChildren().clear();
        beforeAward=0;
        afterAward=0;
    }
}
