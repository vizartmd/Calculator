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

import java.text.DecimalFormat;

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
    private String btnValue = "";
    private boolean isFinishedOperation = true;
    DecimalFormat df = new DecimalFormat("###.########");

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
    private void onKeyPressed (KeyEvent evt) {
        System.out.println(evt.getCode());
        switch (evt.getCode()) {
            case DECIMAL:
                btnValue = ".";
//                operator = ".";
                inputProcess += operator;
                lblInput.setText(inputProcess);
                break;
            case ADD:
                operator = "+";
                inputProcess += operator;
                lblInput.setText(inputProcess);
                break;
            case SUBTRACT:
                operator = "-";
                inputProcess += operator;
                lblInput.setText(inputProcess);
                break;
            case MULTIPLY:
                operator = "*";
                inputProcess += operator;
                lblInput.setText(inputProcess);
                break;
            case DIVIDE:
                operator = "/";
                inputProcess += operator;
                lblInput.setText(inputProcess);
                break;
            case NUMPAD1:
                btnValue = "1";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case NUMPAD2:
                btnValue = "2";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case NUMPAD3:
                btnValue = "3";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case NUMPAD4:
                btnValue = "4";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case NUMPAD5:
                btnValue = "5";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case NUMPAD6:
                btnValue = "6";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case NUMPAD7:
                btnValue = "7";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case NUMPAD8:
                btnValue = "8";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case NUMPAD9:
                btnValue = "9";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case NUMPAD0:
                btnValue = "0";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case EQUALS:
                if (isFinishedOperation) return;
                if (getPower()) return;
                if (getRadical()) return;
                num2 = Double.parseDouble(lblInput1.getText());
                checkOperator(df);
                isFinishedOperation = true;
                inputProcess = "";
                break;
        }
    }

    private void checkOperator(DecimalFormat df) {
        switch (operator) {
            case "+":
                if (needToRound(Double.parseDouble(df.format(num1 + num2)))) {
                    myListView.getItems().add(0, inputProcess + " = " + ((int) Math.nextUp(num1 + num2)) + "");
                    lblInput.setText(String.valueOf((int) Math.nextUp(num1 + num2)));
                } else {
                    myListView.getItems().add(0, inputProcess + " = " + Double.parseDouble(df.format(num1 + num2)));
                    lblInput.setText(String.valueOf(Double.parseDouble(df.format(num1 + num2))));
                }
                lblInput1.setText(String.valueOf(num1 + num2));
                break;
            case "-":
                if (needToRound(Double.parseDouble(df.format(num1 - num2)))) {
                    myListView.getItems().add(0, inputProcess + " = " + ((int) Math.nextUp(num1 - num2)) + "");
                    lblInput.setText(String.valueOf((int) Math.nextUp(num1 - num2)));
                } else {
                    myListView.getItems().add(0, inputProcess + " = " + Double.parseDouble(df.format(num1 - num2)));
                    lblInput.setText(String.valueOf(Double.parseDouble(df.format(num1 - num2))));
                }
                lblInput1.setText(String.valueOf(num1 - num2));
                break;
            case "*":
                if (needToRound(Double.parseDouble(df.format(num1 * num2)))) {
                    myListView.getItems().add(0, inputProcess + " = " + ((int) Math.nextUp(num1 * num2)) + "");
                    lblInput.setText(String.valueOf((int) Math.nextUp(num1 * num2)));
                } else {
                    myListView.getItems().add(0, inputProcess + " = " + Double.parseDouble(df.format(num1 * num2)));
                    lblInput.setText(String.valueOf(Double.parseDouble(df.format(num1 * num2))));
                }
                lblInput1.setText(String.valueOf(num1 * num2));
                break;
            case "/":
                if (needToRound(Double.parseDouble(df.format(num1 / num2)))) {
                    myListView.getItems().add(0, inputProcess + " = " + ((int) Math.nextUp(num1 / num2)) + "");
                    lblInput.setText(String.valueOf((int) Math.nextUp(num1 / num2)));
                } else {
                    myListView.getItems().add(0, inputProcess + " = " + Double.parseDouble(df.format(num1 / num2)));
                    lblInput.setText(String.valueOf(Double.parseDouble(df.format(num1 / num2))));
                }
                lblInput1.setText(String.valueOf(num1 / num2));
                break;
        }
    }

    private boolean getRadical() {
        if (inputProcess.contains("√")) {
            double num = Double.parseDouble(lblInput1.getText());
            String s;
            if (needToRound(Math.sqrt(num))) {
                s = String.valueOf(Math.round(Math.sqrt(num)));
            } else {
                s = String.valueOf(Math.sqrt(num));
            }
            myListView.getItems().add(0, inputProcess + " = " + s);
            lblInput.setText(s);
            lblInput1.setText(s);
            inputProcess = "";
            isFinishedOperation = true;
            return true;
        }
        return false;
    }

    private boolean getPower() {
        if (inputProcess.contains("²")) {
            double num = Double.parseDouble(lblInput1.getText());
            String s;
            if (needToRound(num * num)) {
                s = String.valueOf(Math.round(num * num));
            } else {
                s = String.valueOf(num * num);
            }
            myListView.getItems().add(0, inputProcess + " = " + s);
            num1 = num * num;
            lblInput.setText(s);
            lblInput1.setText(s);
            inputProcess = "";
            isFinishedOperation = true;
            return true;
        }
        return false;
    }

    @FXML
    void onNumericClicked(MouseEvent event) {
        if (isFinishedOperation) {
            reset();
            isFinishedOperation = false;
        }
        String buttonId = ((Button)event.getSource()).getId();
        btnValue = buttonId.substring(3);
        if (inputProcess.equals("√")) {
            inputProcess += btnValue;
            lblInput.setText(inputProcess);
            lblInput1.setText(btnValue);
            return;
        }
        if (inputProcess.contains("√") && inputProcess.length() >= 3 && !String.valueOf(inputProcess.charAt(0)).equals("√")) {
            return;
        }
        if (lblInput.getText().contains("²")) {
            return;
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
        DecimalFormat df = new DecimalFormat("###.########");
        switch (symbol) {
            case "Equals":
                if (isFinishedOperation) return;
                if (getPower()) return;
                if (getRadical()) return;
                num2 = Double.parseDouble(lblInput1.getText());
                checkOperator(df);
                isFinishedOperation = true;
                inputProcess = "";
                break;
            case "Clear":
                reset();
                break;
            case "Pow":
                if (lblInput.getText().equals("") || lblInput.getText().contains(" ") || lblInput.getText().contains("+") || lblInput.getText().contains("√")
                        || lblInput.getText().contains("-") || lblInput.getText().contains("*")
                        || lblInput.getText().contains("/") || lblInput.getText().contains("²")) {
                    return;
                }
                inputProcess = String.valueOf(lblInput.getText());
                lblInput1.setText(inputProcess);
                lblInput.setText(inputProcess + "²");
                inputProcess += "²";
                isFinishedOperation = false;
                break;
            case "Radic":
                if (lblInput.getText().contains("²") || lblInput.getText().contains("√")) {
                    return;
                }
                if (!lblInput.getText().equals("")) {
                    if (lblInput.getText().substring(0, 1).equals("-")) {
                        return;
                    }
                    lblInput1.setText(lblInput.getText());
                    lblInput.setText("√" + lblInput.getText());
                } else {
                    lblInput.setText("√");
                }
                inputProcess = lblInput.getText();
                isFinishedOperation = false;
                break;
            default:
                if ((lblInput.getText().contains("+") || lblInput.getText().contains("-")
                        || lblInput.getText().contains("*") || lblInput.getText().contains("/") || lblInput.getText().contains("²")
                        || lblInput.getText().contains("√")) && !lblInput.getText().substring(0, 1).equals("-")) {
                    return;
                }
                isFinishedOperation = false;
                num1 = Double.parseDouble(lblInput1.getText());
                inputProcess = df.format(num1);
                switch (symbol) {
                    case "Plus":
                        operator = "+";
                        inputProcess += operator;
                        btnValue = "";
                        break;
                    case "Minus":
                        operator = "-";
                        inputProcess += operator;
                        btnValue = "";
                        break;
                    case "Multiply":
                        operator = "*";
                        inputProcess += operator;
                        btnValue = "";
                        break;
                    case "Divide":
                        operator = "/";
                        inputProcess += operator;
                        btnValue = "";
                        break;
                    case "Dot":
                        operator = ".";
                        if (lblInput1.getText().equals("") || lblInput1.getText().equals(".")) {
                            return;
                        } else {
                            inputProcess += operator;
                        }
                        break;
                }
                lblInput1.setText("");
                lblInput.setText(inputProcess);
                break;
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

    private void reset() {
        lblInput1.setText("");
        lblInput.setText("");
        inputProcess = "";
    }
}