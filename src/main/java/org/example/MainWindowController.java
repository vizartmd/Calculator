package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainWindowController {

    @FXML
    private Pane titlePane;
    @FXML
    private ImageView btnMinimize, btnMaximize, btnClose;
    @FXML
    private Label lblInput;
    @FXML
    private Label lblInput1;
    @FXML
    private ListView<String> myListView;

    private double x, y;
    private double num1;
    private double num2;
    private String operator = "+";
    private String inputProcess = "";
    private boolean isFinishedOperation = true;

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
        lblInput.setText(inputProcess);
    }

    @FXML
    void onNumericClicked(MouseEvent event) {
        if (isFinishedOperation) {
            reset();
            isFinishedOperation = false;
        }
        String buttonId = ((Button)event.getSource()).getId();
        String btnValue = buttonId.substring(3);
        if (inputProcess.equals("√ ")) {
            inputProcess += btnValue;
            lblInput.setText(inputProcess);
            lblInput1.setText(btnValue);
            return;
        }
        if (!inputProcess.equals("")) {
            if (inputProcess.substring(inputProcess.length() -1).equals("√") && inputProcess.length() > 2) {
                return;
            }
        }
        if (btnValue.equals("Dot")) {
            if (lblInput1.getText().contains(".")) {
                return;
            }
            if (lblInput1.getText().equals("")) {
                inputProcess += ("0");
                lblInput.setText(inputProcess);
            }
            btnValue = ".";
        }
        inputProcess += btnValue;
        lblInput1.setText(lblInput1.getText().concat(btnValue));
        lblInput.setText(inputProcess);
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {
        String symbol = ((Button)event.getSource()).getId().replace("btn", "");
        if (symbol.equals("Equals")) {
            if (inputProcess.equals("√ ")) {
                return;
            }
            if (inputProcess.contains("√")) {
                double num = Double.parseDouble(String.valueOf(lblInput1.getText()));
                extractSqrt(num);
                return;
            }
            num2 = Double.parseDouble(lblInput1.getText());
            switch (operator) {
                case "+":
                    if (needToRound((num1+num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)Math.nextUp(num1+num2)) + "");
                        lblInput.setText(String.valueOf(Math.nextUp(num1+num2)));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + Math.nextUp(num1+num2) + "");
                        lblInput.setText(String.valueOf(Math.nextUp(num1+num2)));
                    }
                    lblInput1.setText(String.valueOf(num1+num2));
                    break;
                case "-":
                    if (needToRound((num1-num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)(num1-num2)) + "");
                        lblInput.setText(String.valueOf((int)(num1-num2)));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + (num1-num2) + "");
                        lblInput.setText(String.valueOf(num1-num2));
                    }
                    lblInput1.setText(String.valueOf(num1-num2));
                    break;
                case "*":
                    if (needToRound((num1*num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)(num1*num2)) + "");
                        lblInput.setText(String.valueOf((int)(num1*num2)));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + (num1*num2) + "");
                        lblInput.setText(String.valueOf(num1*num2));
                    }
                    lblInput1.setText(String.valueOf(num1*num2));
                    break;
                case "/":
                    if (needToRound((num1/num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)(num1/num2)) + "");
                        lblInput.setText(String.valueOf((int)(num1/num2)));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + (num1/num2) + "");
                        lblInput.setText(String.valueOf(num1/num2));
                    }
                    lblInput1.setText(String.valueOf(num1/num2));
                    break;
            }
            isFinishedOperation = true;
            inputProcess = "";
        }
        else if (symbol.equals("Clear")) {
            reset();
        }
        else if (symbol.equals("Radic")) {
            if (!lblInput.getText().equals("")) {
                if(lblInput.getText().substring(0, 1).equals("-")){
                    reset();
                    inputProcess = "";
                    return;
                }
                    lblInput.setText(lblInput1.getText() + " √");
                    inputProcess = lblInput.getText();
            } else {
                lblInput.setText("√ ");
                inputProcess += lblInput.getText();
                isFinishedOperation = false;
            }
        }
        else {
            if ((lblInput.getText().contains("+") || lblInput.getText().contains("-")
                        || lblInput.getText().contains("*") || lblInput.getText().contains("/")
                        || lblInput.getText().contains("√")) &&  !lblInput.getText().substring(0, 1).equals("-")) {
                    return;
            }
            isFinishedOperation = false;
            num1 = Double.parseDouble(lblInput1.getText());
            if (needToRound(num1)) {
                inputProcess = String.valueOf(Math.round(num1));
            } else {
                inputProcess = String.valueOf(num1);
            }
            switch (symbol) {
                case "Plus":
                    operator = "+";
                    inputProcess += (" " + operator + " ");
                    break;
                case "Minus":
                    operator = "-";
                    inputProcess += (" " + operator + " ");
                    break;
                case "Multiply":
                    operator = "*";
                    inputProcess += (" " + operator + " ");
                    break;
                case "Divide":
                    operator = "/";
                    inputProcess += (" " + operator + " ");
                    break;
                case "Dot":
                    operator = ".";
                    if (lblInput1.getText().equals("") || lblInput1.getText().equals(".")) {
                        return;
                    } else {
                        inputProcess += (" " + operator + " ");
                    }
                    break;
            }
            lblInput1.setText("");
            lblInput.setText(inputProcess);
        }
    }

    private void extractSqrt(double num) {
        if (needToRound(num)) {
            myListView.getItems().add(0, inputProcess + " = " + Math.sqrt(num) + "");
            lblInput.setText(String.valueOf(Math.sqrt(num)));
        } else {
            myListView.getItems().add(0, inputProcess + " = " + Math.sqrt(num) + "");
            lblInput.setText(String.valueOf(Math.sqrt(num)));
        }
        lblInput1.setText(String.valueOf(Math.sqrt(num)));
        inputProcess = "";
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

    private void reset() {
        lblInput1.setText("");
        lblInput.setText("");
        inputProcess = "";
    }

    @FXML
    private void key (KeyEvent evt) {
        System.out.println("evt.getCode() = " + evt.getCode());
    }
}
