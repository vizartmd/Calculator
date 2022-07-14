package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MainWindowController {

    @FXML private Pane titlePane;
    @FXML private ImageView btnMinimize, btnMaximize, btnClose;
    @FXML private Label lblInput;
    @FXML private Label lblInput1;
    @FXML private ListView<String> myListView;

    private double x, y;
    private double num1 = 0;
    private double num2;
    private String operator = "+";
    private String inputProcess = "";
    private final List<String> resultQueue = new LinkedList<>();


    public void init(Stage stage) {
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.getScene().getWindow().setX(mouseEvent.getSceneX()-x);
            stage.getScene().getWindow().setY(mouseEvent.getSceneY()-y);
        });

        btnClose.setOnMouseClicked(mouseEvent -> stage.close());
        btnMinimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));
        btnMaximize.setOnMouseClicked(mouseEvent -> {
            stage.setFullScreen(!stage.isFullScreen());
        });
        myListView.setEditable(false);
        lblInput1.setText(inputProcess);
    }

    @FXML
    void onNumericClicked(MouseEvent event) {
        int value = Integer.parseInt(((Button)event.getSource()).getId().replace("btn", ""));
        lblInput.setText(inputProcess.equals("")?String.valueOf(value):String.valueOf(Math.round(Integer.parseInt(lblInput.getText())*10+value)));
        inputProcess += value;
        lblInput1.setText(inputProcess);
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {
        String symbol = ((Button)event.getSource()).getId().replace("btn", "");
        if (symbol.equals("Equals")) {
            num2 = Integer.parseInt(lblInput.getText());
            switch (operator) {
                case "+" -> {
                    resultQueue.add((num1+num2) + "");
                    lblInput.setText(String.valueOf(num1+num2));
                    if (needToRound((num1+num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)(num1+num2)) + "");
                        lblInput1.setText(String.valueOf(((int)(num1+num2))));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + (num1+num2) + "");
                        lblInput1.setText(String.valueOf(num1+num2));
                    }
                    resetResult();
                }
                case "-" -> {
                    resultQueue.add((num1-num2) + "");
                    lblInput.setText(String.valueOf((num1-num2)));
                    if (needToRound((num1-num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)(num1-num2)) + "");
                        lblInput1.setText(String.valueOf(((int)(num1-num2))));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + (num1-num2) + "");
                        lblInput1.setText(String.valueOf(num1-num2));
                    }
                    resetResult();
                }
                case "*" -> {
                    resultQueue.add((num1*num2) + "");
                    lblInput.setText(String.valueOf(num1*num2));
                    if (needToRound((num1*num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)(num1*num2)) + "");
                        lblInput1.setText(String.valueOf(((int)(num1*num2))));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + (num1*num2) + "");
                        lblInput1.setText(String.valueOf(num1*num2));
                    }
                    resetResult();
                }
                case "/" -> {
                    resultQueue.add((num1/num2) + "");
                    lblInput.setText(String.valueOf(num1/num2));
                    if (needToRound((num1/num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)(num1/num2)) + "");
                        lblInput1.setText(String.valueOf(((int)(num1/num2))));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + (num1/num2) + "");
                        lblInput1.setText(String.valueOf(num1/num2));
                    }
                    resetResult();
                }
            }
        }
        else if (symbol.equals("Clear")) {
            reset();
        }
        else {
            switch (symbol) {
                case "Plus" -> {
                    operator = "+";
                    inputProcess += (" " + operator + " ");
                }
                case "Minus" -> {
                    operator = "-";
                    inputProcess += (" " + operator + " ");
                }
                case "Multiply" -> {
                    operator = "*";
                    inputProcess += (" " + operator + " ");
                }
                case "Divide" -> {
                    operator = "/";
                    inputProcess += (" " + operator + " ");
                }
            }
            num1 = Integer.parseInt(lblInput.getText());
            lblInput.setText(String.valueOf(0));
            lblInput1.setText(inputProcess);
        }
    }

    private boolean needToRound(double num) {
        String numToString = String.valueOf(num);
        int decimalIndex = numToString.indexOf(".");
        numToString = numToString.substring(decimalIndex+1);
        char[] arr = numToString.toCharArray();
        boolean need = true;
        for (char c : arr) {
            if (c != '0') {
                need = false;
                break;
            }
        }
        return need;
    }

    private void resetResult() {
        lblInput.setText(String.valueOf(0));
        operator = ".";
        inputProcess = "";
    }

    private void reset() {
        lblInput1.setText(String.valueOf(0));
        operator = ".";
        inputProcess = "";
    }

    @FXML
    private void key (KeyEvent evt) {
        System.out.println("evt.getCode() = " + evt.getCode());
    }
}
