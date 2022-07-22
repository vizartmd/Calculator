package org.example;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainWindowController<ActionListener> {

    @FXML
    private Pane titlePane;
    @FXML
    private ImageView btnMinimize, btnMaximize, btnClose;
    @FXML
    private Label lblInput;
    @FXML
    private Label lblInput1;
    @FXML
    private Label wrongExpression;
    @FXML
    private ListView<String> myListView;

    private double x, y;
    private String operator = "+";
    private String inputProcess = "";
    private String btnValue = "";
    private String currentOperand = "";
    private List<String> operands = new ArrayList<>();
    private List<String> operations = new ArrayList<>();
    ScriptEngineManager scriptEngineManager;
    ScriptEngine scriptEngine;
    Object expResult;

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
    private void onKeyPressed (KeyEvent evt) throws ScriptException, InterruptedException {
        System.out.println(evt.getCode());
        System.out.println("inputProcess = " + inputProcess);
        switch (evt.getCode()) {
            case DECIMAL:
                if (currentOperand.contains(".") || inputProcess.equals("-")) {
                    return;
                }
                if (currentOperand.equals("")) {
                    currentOperand += "0.";
                    btnValue = "0.";
                    inputProcess += "0.";
                } else {
                    currentOperand += ".";
                    btnValue += ".";
                    inputProcess += ".";
                }
                lblInput.setText(inputProcess);
                break;
            case ADD:
                if (inputProcess.equals("") || inputProcess.equals("-")  || inputProcess.endsWith("(")) {
                    return;
                }
                operator = "+";
                if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
                    removeOperator();
                }
                addOperation();
                break;
            case SUBTRACT:
                operator = "-";
                if (inputProcess.equals("-") || inputProcess.endsWith("(")){
                    return;
                } else if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
                    removeOperator();
                }
                subtractOperation();
                break;
            case MULTIPLY:
                if (inputProcess.equals("") || inputProcess.equals("-") || inputProcess.endsWith("(")){
                    return;
                }
                operator = "*";
                if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
                    removeOperator();
                }
                multiplyOperation();
                break;
            case DIVIDE:
                if (inputProcess.equals("") || inputProcess.equals("-") || inputProcess.endsWith("(")){
                    return;
                }
                operator = "/";
                if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
                    removeOperator();
                }
                divideOperations();
                break;
            case NUMPAD1:
                btnValue = "1";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case NUMPAD2:
                btnValue = "2";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case NUMPAD3:
                btnValue = "3";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case NUMPAD4:
                btnValue = "4";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case NUMPAD5:
                btnValue = "5";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case NUMPAD6:
                btnValue = "6";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case NUMPAD7:
                btnValue = "7";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case NUMPAD8:
                btnValue = "8";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case NUMPAD9:
                btnValue = "9";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case NUMPAD0:
                btnValue = "0";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case LEFT_PARENTHESIS:
                if (!inputProcess.endsWith("+") || !inputProcess.endsWith("-") || !inputProcess.endsWith("*") || !inputProcess.endsWith("/")) {
                    return;
                }
                btnValue = "(";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case RIGHT_PARENTHESIS:
                if (inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
                    return;
                }
                btnValue = ")";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                operator = "";
                break;
            case BACK_SPACE:
                if (inputProcess.equals("")) {
                    return;
                }
                inputProcess = inputProcess.substring(0, inputProcess.length() - 1);
                lblInput.setText(inputProcess);
                break;
            case ESCAPE:
                reset();
                break;
            case ENTER:
                if (inputProcess.equals("") || !areTheBracketsInPairs()) {
                    return;
                }
                scriptEngineManager = new ScriptEngineManager();
                scriptEngine = scriptEngineManager.getEngineByName("Nashorn");
                checkExpResult();
                break;
        }
    }

    private void checkExpResult() {
        try {
            expResult = scriptEngine.eval(inputProcess);
            lblInput.setText(expResult.toString());
            lblInput.setText(expResult.toString());
            myListView.getItems().add(0, inputProcess + " = " + expResult);
            inputProcess = "";
        }
        catch (ScriptException e) {
            System.out.println(e.getMessage());
            wrongExpression.setText("wrong expression!");
            wrongExpression.setTextFill(Color.color(1, 0, 0));
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    wrongExpression.setText(null);
                }
            });
            pause.play();
        }
    }

    private void addOperation() {
        inputProcess += operator;
        lblInput.setText(inputProcess);
        operations.add("ADD");
        operands.add(currentOperand);
        currentOperand = "";
    }

    private void subtractOperation() {
        inputProcess += operator;
        lblInput.setText(inputProcess);
        operations.add("SUBTRACT");
        operands.add(currentOperand);
        currentOperand = "";
    }

    private void multiplyOperation() {
        inputProcess += operator;
        lblInput.setText(inputProcess);
        operations.add("MULTIPLY");
        operands.add(currentOperand);
        currentOperand = "";
    }

    private void divideOperations() {
        inputProcess += operator;
        lblInput.setText(inputProcess);
        operations.add("DIVIDE");
        operands.add(currentOperand);
        currentOperand = "";
    }

    private void removeOperator() {
        if (currentOperand.length() > 0) {
            currentOperand = currentOperand.substring(0, currentOperand.length() - 1);
        }
        inputProcess = inputProcess.substring(0, inputProcess.length() - 1);
        if (btnValue.length() > 0) {
            btnValue = btnValue.substring(0, btnValue.length() - 1) + operator;
        }
        if (operations.size() > 0) {
            operations.remove(operations.size() - 1);
        }
        if (operands.size() > 0) {
            operands.remove(operands.size() - 1);
        }
    }

    @FXML
    void onMousePressed(MouseEvent event) throws ScriptException, InterruptedException {
        String symbol = ((Button) event.getSource()).getId().replace("btn", "");
        System.out.println(symbol);
        switch (symbol) {
            case "Dot":
                if (currentOperand.contains(".") || inputProcess.equals("-")) {
                    return;
                }
                if (currentOperand.equals("") || inputProcess.endsWith("(")) {
                    currentOperand += "0.";
                    btnValue = "0.";
                    inputProcess += "0.";
                } else {
                    currentOperand += ".";
                    btnValue += ".";
                    inputProcess += ".";
                }
                lblInput.setText(inputProcess);
                break;
            case "Plus":
                if (inputProcess.equals("") || inputProcess.equals("-") || inputProcess.endsWith("(")) {
                    return;
                }
                operator = "+";
                if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
                    removeOperator();
                }
                addOperation();
                break;
            case "Minus":
                operator = "-";
                if (inputProcess.equals("-") || inputProcess.endsWith("(")) {
                    return;
                } else if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
                    removeOperator();
                }
                subtractOperation();
                break;
            case "Multiply":
                if (inputProcess.equals("") || inputProcess.equals("-") || inputProcess.endsWith("(")) {
                    return;
                }
                operator = "*";
                if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
                    removeOperator();
                }
                multiplyOperation();
                break;
            case "Divide":
                if (inputProcess.equals("") || inputProcess.equals("-") || inputProcess.endsWith("(")) {
                    return;
                }
                operator = "/";
                if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
                    removeOperator();
                }
                divideOperations();
                break;
            case "1":
                btnValue = "1";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case "2":
                btnValue = "2";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case "3":
                btnValue = "3";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case "4":
                btnValue = "4";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case "5":
                btnValue = "5";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case "6":
                btnValue = "6";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case "7":
                btnValue = "7";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case "8":
                btnValue = "8";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case "9":
                btnValue = "9";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case "0":
                btnValue = "0";
                inputProcess += btnValue;
                lblInput.setText(inputProcess);
                currentOperand += btnValue;
                operator = "";
                break;
            case "Pow":
                if (lblInput.getText().equals("") || lblInput.getText().contains("+")
                        || lblInput.getText().contains("-") || lblInput.getText().contains("*")
                        || lblInput.getText().contains("/")) {
                    return;
                }
                inputProcess = String.valueOf(lblInput.getText());
                lblInput1.setText(inputProcess);
                inputProcess += "²";
                double powResult = Math.pow(Double.parseDouble(lblInput1.getText()), 2);
                myListView.getItems().add(0, inputProcess + " = " + powResult);
                reset();
                lblInput.setText(String.valueOf(powResult));
                break;
            case "Radic":
                if (lblInput.getText().equals("") || lblInput.getText().contains("+")
                        || lblInput.getText().contains("-") || lblInput.getText().contains("*")
                        || lblInput.getText().contains("/")) {
                    return;
                }
                if (!lblInput.getText().equals("")) {
                    if (lblInput.getText().startsWith("-")) {
                        return;
                    }
                    lblInput1.setText(lblInput.getText());
                    lblInput.setText("√" + lblInput.getText());
                } else {
                    lblInput.setText("√");
                }
                Double sqrtResult = Math.sqrt(Double.parseDouble(lblInput1.getText()));
                inputProcess = lblInput.getText();
                myListView.getItems().add(0, inputProcess + " = " + sqrtResult);
                reset();
                lblInput.setText(String.valueOf(sqrtResult));
                break;
            case "Procent":
                if (inputProcess.equals("")) {
                    return;
                }
                inputProcess += "%";
                lblInput.setText(inputProcess);
                break;
            case "Clear":
                reset();
                break;
            case "Erase":
                if (inputProcess.equals("")) {
                    return;
                }
                inputProcess = inputProcess.substring(0, inputProcess.length() - 1);
                lblInput.setText(inputProcess);
                break;
            case "LeftParenthesis":
//                if (!inputProcess.endsWith("+") || !inputProcess.endsWith("-") || !inputProcess.endsWith("*") || !inputProcess.endsWith("/")) {
//                    return;
//                }
                inputProcess += "(";
                lblInput.setText(inputProcess);
                break;
            case "RightParenthesis":
//                if (inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
//                    return;
//                }
                inputProcess += ")";
                lblInput.setText(inputProcess);
                break;
            case "Equals":
                if (inputProcess.equals("") || !areTheBracketsInPairs()) {
                    return;
                }
                scriptEngineManager = new ScriptEngineManager();
                scriptEngine = scriptEngineManager.getEngineByName("Nashorn");
                checkExpResult();
                break;
        }
        System.out.println("inputProcess = " + inputProcess);
    }

    private boolean areTheBracketsInPairs() {
        boolean theBracketsAreInPairs;
        if (!inputProcess.contains("(") || !inputProcess.contains(")")) {
            theBracketsAreInPairs = true;
        } else {
            theBracketsAreInPairs = leftBracketsCountEqualsRightBracketsCount();
        }
        return theBracketsAreInPairs;
    }

    private boolean leftBracketsCountEqualsRightBracketsCount() {
        int leftBracketsCount = 0;
        int rightBracketsCount = 0;
        String leftBracket = "(";
        String rightBracket = ")";
        for (int i = 0; i < inputProcess.length(); i++) {
            if (leftBracket.equals(String.valueOf(inputProcess.charAt(i)))) {
                leftBracketsCount++;
            }
            if (rightBracket.equals(String.valueOf(inputProcess.charAt(i)))) {
                rightBracketsCount++;
            }
        }
        return leftBracketsCount == rightBracketsCount;
    }

    private void reset() {
        lblInput1.setText("");
        lblInput.setText("");
        inputProcess = "";
        currentOperand = "";
        btnValue = "";
        operations.clear();
        operands.clear();
    }
}