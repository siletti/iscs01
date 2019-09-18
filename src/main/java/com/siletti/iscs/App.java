package com.siletti.iscs;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
//import java.io.IOException;


public class App extends Application {

    private Text actionResult;
    private Stage savedStage;
    private static final String titleTxt = "SILETTI - ISCS demo";

    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle(titleTxt);

        // Window label
        Label label = new Label("Select a weather.dat file ");
        label.setTextFill(Color.DARKBLUE);
        label.setFont(Font.font("Calibri", FontWeight.BOLD, 28));
        HBox labelHb = new HBox();
        labelHb.setAlignment(Pos.CENTER);
        labelHb.getChildren().add(label);

        // Buttons
        Button btn1 = new Button("Choose a file...");
        btn1.setOnAction(new ButtonListener());
        HBox buttonHb1 = new HBox(10);
        buttonHb1.setAlignment(Pos.CENTER);
        buttonHb1.getChildren().addAll(btn1);

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

        savedStage = primaryStage;
    }

    private class ButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Optional<File> myFile = showFileChooser();
            if (myFile.isPresent()) {

//                MyFixedWidthParser myFixedWidthParser = new MyFixedWidthParser();
                MyFixedWidthParser myFixedWidthParser = new MyFixedWidthParserFootball();

                List<String[]> resultList = myFixedWidthParser.getTheList(myFile.get());

                /*
                resultList.forEach(strings -> {
                    System.out.println(Arrays.toString(strings).toString());
                });
                */
////////////////////////new code
                Map<String, Integer> myMap = myFixedWidthParser.stringIntegerMap(resultList);

                String result = myFixedWidthParser.getKeyWithMinIntVal(myMap);

///////////////////////////////////

//                String result = dayMinTempRange(resultList);
                actionResult.setText("Day of the month with minimum temp range was : " + result);

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
            return null;
        }
    }

    private String dayMinTempRange(List<String[]> list) {

        Map<String, Integer> myMap = list.stream()
                .filter(arr -> {
                    if (tryParseInt(arr[0]) > 0) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .collect(Collectors.toMap(strings -> strings[0], strings -> Math.abs(tryParseInt(strings[1]) - tryParseInt(strings[2]))));

        myMap.forEach((key, value) -> {
            System.out.println("Key : " + key + " Value : " + value);
        });

        Optional<Map.Entry<String, Integer>> myMinEntry = myMap.entrySet()
                .stream()
                .min(Comparator.comparingInt(Map.Entry::getValue));

        return myMinEntry.get().getKey();

    }

    private static int tryParseInt(String value) {

        String last3Digits;     //substring that will contain last 3 characters

        if (value.length() > 3) {
            last3Digits = value.substring(value.length() - 3);
        } else {
            last3Digits = value;
        }

        try {
            return Integer.parseInt(last3Digits.trim());
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }
}
