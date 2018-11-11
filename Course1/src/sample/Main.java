package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.*;
import javafx.scene.control.Button;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Image image = new Image("file:image/home.jpg");
        ImageView imag1 = new ImageView(image);
        imag1.setFitHeight(450);
        imag1.setFitWidth(500);

        BorderPane pane = new BorderPane();
        Student student = new Student();
        Teacher teacher = new Teacher();
        Manager manager = new Manager();

        Button bt1 = new Button("管理员");
        Button bt2 = new Button("学生");
        Button bt3 = new Button("教师");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15,5,5,5));
        vBox.getChildren().add(bt1);
        vBox.getChildren().add(bt2);
        vBox.getChildren().add(bt3);

        pane.setTop(imag1);
        pane.setCenter(vBox);

        bt1.setOnAction(e2 -> {
            pane.getChildren().clear();
            pane.setTop(manager);
        });
        bt2.setOnAction(e -> {
            pane.getChildren().clear();
            pane.setTop(student);
        });

        bt3.setOnAction(e1 -> {
            pane.getChildren().clear();
            pane.setTop(teacher);
        });

        Scene scene = new Scene(pane,500,600);
        primaryStage.setTitle("选课系统");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
