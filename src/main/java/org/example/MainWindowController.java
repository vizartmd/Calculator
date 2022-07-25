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
    @FXML
    private Button btnEquals;

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
    void onBtnEqualsPressed(MouseEvent event) {
        btnEquals.getStyleClass().add("btnEqualsPressed");
        btnEquals.getStyleClass().remove("btnEquals");
        PauseTransition pause = new PauseTransition(Duration.millis(100));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                btnEquals.getStyleClass().remove("btnEqualsPressed");
                btnEquals.getStyleClass().add("btnEquals");
            }
        });
        pause.play();
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
        } else {
            popOver.hide();
        }
    }

    @FXML
    private void onKeyPressed (KeyEvent evt) {
        switch (evt.getCode()) {
            case DECIMAL:
                addingTheDecimal();
                break;
            case ADD:
                addAddOperation();
                break;
            case SUBTRACT:
                addSubtractOperation();
                break;
            case MULTIPLY:
                addMultiplyOperation();
                break;
            case DIVIDE:
                addDivideOperation();
                break;
            case NUMPAD1:
                addingTheOneDigit();
                break;
            case NUMPAD2:
                addingTheTwoDigit();
                break;
            case NUMPAD3:
                addingTheThreeDigit();
                break;
            case NUMPAD4:
                addingTheFourDigit();
                break;
            case NUMPAD5:
                addingTheFiveDigit();
                break;
            case NUMPAD6:
                addingTheSixDigit();
                break;
            case NUMPAD7:
                addingTheSevenDigit();
                break;
            case NUMPAD8:
                addingTheEightDigit();
                break;
            case NUMPAD9:
                addingTheNineDigit();
                break;
            case NUMPAD0:
                addingTheZeroDigit();
                break;
            case LEFT_PARENTHESIS:
                insertLeftParenthesis();
                break;
            case RIGHT_PARENTHESIS:
                insertRightParenthesis();
                break;
            case BACK_SPACE:
                removeLastCharacter();
                break;
            case ESCAPE:
                reset();
                break;
            case ENTER:
                if (inputProcess.equals("")) {
                    return;
                }
                checkExpResult();
                break;
        }
    }

    private void addingTheDecimal() {
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
    }

    private void addingTheOneDigit() {
        btnValue = "1";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addingTheTwoDigit() {
        btnValue = "2";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addingTheThreeDigit() {
        btnValue = "3";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addingTheFourDigit() {
        btnValue = "4";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addingTheFiveDigit() {
        btnValue = "5";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addingTheSixDigit() {
        btnValue = "6";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addingTheSevenDigit() {
        btnValue = "7";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addingTheEightDigit() {
        btnValue = "8";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addingTheNineDigit() {
        btnValue = "9";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addingTheZeroDigit() {
        btnValue = "0";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addAddOperation() {
        setOperatorToAdd();
    }

    private void setOperatorToAdd() {
        if (inputProcess.equals("") || inputProcess.equals("-")  || inputProcess.endsWith("(")) {
            return;
        }
        operator = "+";
        if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
            removeOperator();
        }
        addOperation();
    }

    private void addSubtractOperation() {
        setOperatorToMinus();
    }

    private void addMultiplyOperation() {
        setOperatorToMultiply();
    }

    private void addDivideOperation() {
        setOperatorToDivide();
    }

    private void insertLeftParenthesis() {
        if (!inputProcess.endsWith("+") || !inputProcess.endsWith("-") || !inputProcess.endsWith("*") || !inputProcess.endsWith("/")) {
            return;
        }
        btnValue = "(";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        operator = "";
    }

    private void insertRightParenthesis() {
        if (inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
        return;
    }
        btnValue = ")";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        operator = "";
    }

    private void removeLastCharacter() {
        if (inputProcess.equals("")) {
            return;
        }
        inputProcess = inputProcess.substring(0, inputProcess.length() - 1);
        lblInput.setText(inputProcess);
    }

    private void checkExpResult() {
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName("Nashorn");
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
            operands.remove(operands.size() - 1);
        }
    }

    @FXML
    void onMousePressed(MouseEvent event) {
        String symbol = ((Button) event.getSource()).getId().replace("btn", "");
        switch (symbol) {
            case "Dot":
                addDecimalOnMousePressed();
                break;
            case "Plus":
                addPlusOnMousePressed();
                break;
            case "Minus":
                addMinusOnMousePressed();
                break;
            case "Multiply":
                addMultiplyOnMousePressed();
                break;
            case "Divide":
                addDivideOnMousePressed();
                break;
            case "1":
                addDigitOneOnMousePressed();
                break;
            case "2":
                addDigitTwoOnMousePressed();
                break;
            case "3":
                addDigitThreeOnMousePressed();
                break;
            case "4":
                addDigitFourOnMousePressed();
                break;
            case "5":
                addDigitFiveOnMousePressed();
                break;
            case "6":
                addDigitSixOnMousePressed();
                break;
            case "7":
                addDigitSevenOnMousePressed();
                break;
            case "8":
                addDigitEightOnMousePressed();
                break;
            case "9":
                addDigitNineOnMousePressed();
                break;
            case "0":
                addDigitZeroOnMousePressed();
                break;
            case "Pow":
                getTheResultOfTheNumberSquared();
                break;
            case "Radic":
                extractRadical();
                break;
            case "Procent":
                getRemainderAfterDivision();
                break;
            case "Clear":
                reset();
                break;
            case "Erase":
                eraseLastSymbol();
                break;
            case "LeftParenthesis":
                addLeftParenthesis();
                break;
            case "RightParenthesis":
                addRightParenthesis();
                break;
            case "Equals":
                evaluateTheResult();
                break;
            default:
        }
    }

    private void evaluateTheResult() {
        if (inputProcess.equals("")) {
            return;
        }
        checkExpResult();
    }

    private void addRightParenthesis() {
        inputProcess += ")";
        lblInput.setText(inputProcess);
    }

    private void addLeftParenthesis() {
        inputProcess += "(";
        lblInput.setText(inputProcess);
    }

    private void eraseLastSymbol() {
        if (inputProcess.equals("")) {
            return;
        }
        inputProcess = inputProcess.substring(0, inputProcess.length() - 1);
        lblInput.setText(inputProcess);
    }

    private void getTheResultOfTheNumberSquared() {
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
    }

    private void addDigitZeroOnMousePressed() {
        btnValue = "0";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addDigitNineOnMousePressed() {
        btnValue = "9";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addDigitEightOnMousePressed() {
        btnValue = "8";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addDigitSevenOnMousePressed() {
        btnValue = "7";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addDigitSixOnMousePressed() {
        btnValue = "6";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addDigitFiveOnMousePressed() {
        btnValue = "5";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addDigitFourOnMousePressed() {
        btnValue = "4";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addDigitThreeOnMousePressed() {
        btnValue = "3";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addDigitTwoOnMousePressed() {
        btnValue = "2";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addDigitOneOnMousePressed() {
        btnValue = "1";
        inputProcess += btnValue;
        lblInput.setText(inputProcess);
        currentOperand += btnValue;
        operator = "";
    }

    private void addDivideOnMousePressed() {
        setOperatorToDivide();
    }

    private void setOperatorToDivide() {
        if (inputProcess.equals("") || inputProcess.equals("-") || inputProcess.endsWith("(")) {
            return;
        }
        operator = "/";
        if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
            removeOperator();
        }
        divideOperations();
    }

    private void addMultiplyOnMousePressed() {
        setOperatorToMultiply();
    }

    private void setOperatorToMultiply() {
        if (inputProcess.equals("") || inputProcess.equals("-") || inputProcess.endsWith("(")) {
            return;
        }
        operator = "*";
        if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
            removeOperator();
        }
        multiplyOperation();
    }

    private void addMinusOnMousePressed() {
        setOperatorToMinus();
        if (inputProcess.equals("-") || inputProcess.endsWith("(")) {
            return;
        } else if (inputProcess.endsWith(".") || inputProcess.endsWith("+") || inputProcess.endsWith("-") || inputProcess.endsWith("*") || inputProcess.endsWith("/")) {
            removeOperator();
        }
        subtractOperation();
    }

    private void setOperatorToMinus() {
        operator = "-";
    }

    private void addPlusOnMousePressed() {
        setOperatorToAdd();
    }

    private void addDecimalOnMousePressed() {
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
    }

    private void getRemainderAfterDivision() {
        if (inputProcess.equals("")) {
            return;
        }
        inputProcess += "%";
        lblInput.setText(inputProcess);
    }

    private void extractRadical() {
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