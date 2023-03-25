package com.example.demo1;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;


public class Application extends javafx.application.Application{


    public Application() throws IOException {

    }

    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        VBox VBox = new VBox();
        Controller controller=new Controller();
        HBox hBox = new HBox();
        hBox.setSpacing(50);
        hBox.getChildren().addAll(controller);
        Scene scene = new Scene(hBox,1350, 700);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
    }

}