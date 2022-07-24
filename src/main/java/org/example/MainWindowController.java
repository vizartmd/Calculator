package org.example;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import static org.example.PopOver.ArrowLocation.TOP_CENTER;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
    private Label wrongExpression;
    @FXML
    private ListView<String> myListView;
    @FXML
    private Button modeBtn;

    private double x, y;
    private String operator = "+";
    private String inputProcess = "";
    private String btnValue = "";
    private String currentOperand = "";
    private List<String> operands = new ArrayList<>();
    private List<String> operations = new ArrayList<>();
    private ScriptEngineManager scriptEngineManager;
    private ScriptEngine scriptEngine;
    private Object expResult;

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
    void onMousePressedModeButton(MouseEvent event) {
        Label basicMode = new Label("Basic Mode");
        basicMode.paddingProperty().set(new Insets(3));
        RadioButton radioButton = new RadioButton();
        radioButton.paddingProperty().set(new Insets(3));
        RadioButton radioButton1 = new RadioButton();
        radioButton1.paddingProperty().set(new Insets(3));
        RadioButton radioButton2 = new RadioButton();
        radioButton2.paddingProperty().set(new Insets(3));
        RadioButton radioButton3 = new RadioButton();
        radioButton3.paddingProperty().set(new Insets(3));
        RadioButton radioButton4 = new RadioButton();
        radioButton4.paddingProperty().set(new Insets(3));
        radioButton1.paddingProperty().set(new Insets(3));
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton.setToggleGroup(toggleGroup);
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);
        radioButton4.setToggleGroup(toggleGroup);
        Label advancedMode = new Label("Advanced Mode");
        advancedMode.paddingProperty().set(new Insets(3));
        Label financialMode = new Label("Financial Mode");
        financialMode.paddingProperty().set(new Insets(3));
        Label programmingMode = new Label("Programming Mode");
        programmingMode.paddingProperty().set(new Insets(3));
        Label keyBoardMode = new Label("Keyboard Mode");
        programmingMode.paddingProperty().set(new Insets(3));

        GridPane gridPane = new GridPane();
        gridPane.addColumn(0, basicMode);
        gridPane.addColumn(0, advancedMode);
        gridPane.addColumn(0, financialMode);
        gridPane.addColumn(0, programmingMode);
        gridPane.addColumn(0, keyBoardMode);
        gridPane.addRow(0, radioButton);
        gridPane.addRow(1, radioButton1);
        gridPane.addRow(2, radioButton2);
        gridPane.addRow(3, radioButton3);
        gridPane.addRow(4, radioButton4);
        VBox vBox = new VBox(gridPane);
        //Create PopOver and add look and feel
        PopOver popOver = new PopOver(vBox);
        popOver.setFadeInDuration(new Duration(100));
        popOver.setArrowLocation(TOP_CENTER);
        popOver.setArrowSize(12);

        if (!popOver.isShowing()) {
            popOver.show(modeBtn);
//            modeBtn.styleProperty().setValue("-fx-background-color: black");
        } else {
            popOver.hide();
        }
    }

    @FXML
    private void onKeyPressed (KeyEvent evt) {
        System.out.println(evt.getCode());
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
                if (inputProcess.equals("")) {
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
    void onMousePressed(MouseEvent event) {
        String symbol = ((Button) event.getSource()).getId().replace("btn", "");
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
                inputProcess += "(";
                lblInput.setText(inputProcess);
                break;
            case "RightParenthesis":
                inputProcess += ")";
                lblInput.setText(inputProcess);
                break;
            case "Equals":
                if (inputProcess.equals("")) {
                    return;
                }
                scriptEngineManager = new ScriptEngineManager();
                scriptEngine = scriptEngineManager.getEngineByName("Nashorn");
                checkExpResult();
                break;
        }
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