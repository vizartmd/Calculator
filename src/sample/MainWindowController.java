package sample;

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

    @FXML private Pane titlePane;
    @FXML private ImageView btnMinimize, btnMaximize, btnClose;
    @FXML private Label lblInput;
    @FXML private Label lblInput1;
    @FXML private ListView<String> myListView;

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
        if (btnValue.equals("Dot")) {
            if (lblInput1.getText().contains(".")) {
                return;
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
            num2 = Double.parseDouble(lblInput1.getText());
            switch (operator) {
                case "+" -> {
                    if (needToRound((num1+num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)(num1+num2)) + "");
                        lblInput.setText(String.valueOf((int)(num1+num2)));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + (num1+num2) + "");
                        lblInput.setText(String.valueOf(num1+num2));
                    }
                    lblInput1.setText(String.valueOf(num1+num2));
                }
                case "-" -> {
                    if (needToRound((num1-num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)(num1-num2)) + "");
                        lblInput.setText(String.valueOf((int)(num1-num2)));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + (num1-num2) + "");
                        lblInput.setText(String.valueOf(num1-num2));
                    }
                    lblInput1.setText(String.valueOf(num1-num2));
                }
                case "*" -> {
                    if (needToRound((num1*num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)(num1*num2)) + "");
                        lblInput.setText(String.valueOf((int)(num1*num2)));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + (num1*num2) + "");
                        lblInput.setText(String.valueOf(num1*num2));
                    }
                    lblInput1.setText(String.valueOf(num1*num2));
                }
                case "/" -> {
                    if (needToRound((num1/num2))) {
                        myListView.getItems().add(0, inputProcess + " = " + ((int)(num1/num2)) + "");
                        lblInput.setText(String.valueOf((int)(num1/num2)));
                    } else {
                        myListView.getItems().add(0, inputProcess + " = " + (num1/num2) + "");
                        lblInput.setText(String.valueOf(num1/num2));
                    }
                    lblInput1.setText(String.valueOf(num1/num2));
                }
            }
            isFinishedOperation = true;
            inputProcess = "";
        }
        else if (symbol.equals("Clear")) {
            reset();
        }
        else {
            isFinishedOperation = false;
            num1 = Double.parseDouble(lblInput1.getText());
            inputProcess = String.valueOf(num1);
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
                case "Dot" -> {
                    operator = ".";
                    inputProcess += (" " + operator + " ");
                }
            }
            lblInput1.setText("");
            lblInput.setText(inputProcess);
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
        lblInput1.setText("");
        inputProcess = "";
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
