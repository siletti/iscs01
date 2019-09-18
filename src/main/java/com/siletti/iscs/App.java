package com.siletti.iscs;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
//import java.io.IOException;


public class App extends Application {

    private Text actionResult;
    private static final String titleTxt = " - ISCS demo";

    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle(titleTxt);

        // Window label
        Label label = new Label("Select a file ");
        label.setTextFill(Color.DARKBLUE);
        label.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        HBox labelHb = new HBox();
        labelHb.setAlignment(Pos.CENTER);
        labelHb.getChildren().add(label);

        // Buttons
        Button btn1 = new Button("Weather file ");
        Button btn2 = new Button("Football file ");
        btn1.setOnAction(new ButtonListener1());
        btn2.setOnAction(new ButtonListener2());
        HBox buttonHb1 = new HBox(10);
        buttonHb1.setAlignment(Pos.CENTER);
        buttonHb1.getChildren().addAll(btn1, btn2);

        // Status message text
        actionResult = new Text();
        actionResult.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
        actionResult.setFill(Color.FIREBRICK);

        // Vbox
        VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(25, 10, 10, 10));
        vbox.getChildren().addAll(labelHb, buttonHb1, actionResult);

        // Scene
        Scene scene = new Scene(vbox, 600, 400); // w x h
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class ButtonListener1 implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            actionResult.setText("");
            Optional<File> myFile = showFileChooser();
            if (myFile.isPresent()) {
                MyFixedWidthParser myFixedWidthParser = null;
                try {
                    myFixedWidthParser = new MyFixedWidthParserWeather(myFile.get());
                } catch (IOException ex) {
                    actionResult.setText("Sorry cannot read the file");
                    ex.printStackTrace();
                }
                String result = myFixedWidthParser.getKeyWithMinIntVal();
                actionResult.setText("Day of the month with minimum temp range was : " + result);
            }
        }
    }

    private class ButtonListener2 implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            actionResult.setText("");
            Optional<File> myFile = showFileChooser();
            if (myFile.isPresent()) {
                MyFixedWidthParser myFixedWidthParser = null;
                try {
                    myFixedWidthParser = new MyFixedWidthParserFootball(myFile.get());
                } catch (IOException ex) {
                    actionResult.setText("Sorry cannot read the file");
                }
                String result = myFixedWidthParser.getKeyWithMinIntVal();
                actionResult.setText("Team with minimum goals difference was : " + result);
            }
        }
    }

    private Optional<File> showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            return Optional.of(selectedFile);
        } else {
            return Optional.empty();
        }
    }

}
