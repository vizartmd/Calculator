package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Queue;

public class MainWindowController {

    @FXML private Pane titlePane;
    @FXML private ImageView btnMinimize, btnMaximize, btnClose;
    @FXML private Label lblResult;
    @FXML private Label lblInput;

    private double x, y;
    private double num1 = 0;
    private double num2;
    private String operator = "+";
    private Queue<String> resultQueue;

    public void init(Stage stage) {
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getSceneX()+x);
            stage.setY(mouseEvent.getSceneY()+y);
        });

        btnClose.setOnMouseClicked(mouseEvent -> stage.close());
        btnMinimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));
        btnMaximize.setOnMouseClicked(mouseEvent -> {
            stage.setFullScreen(!stage.isFullScreen());
        });
    }

    @FXML
    void onNumericClicked(MouseEvent event) {
        int value = Integer.parseInt(((Button)event.getSource()).getId().replace("btn", ""));
        lblInput.setText(String.valueOf(Math.round(Float.parseFloat(Double.parseDouble(lblInput.getText())==0?String.valueOf((double)value):String.valueOf(Double.parseDouble(lblInput.getText())*10+value)))));
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {
        String symbol = ((Button)event.getSource()).getId().replace("btn", "");
        if (symbol.equals("Equals")) {
            num2 = Double.parseDouble(lblInput.getText());
            switch (operator) {
                case "+" -> lblResult.setText((num1+num2) + "");
                case "-" -> lblResult.setText((num1-num2) + "");
                case "*" -> lblResult.setText((num1*num2) + "");
                case "/" -> lblResult.setText((num1/num2) + "");
            }
            resetResult();
        }
        else if (symbol.equals("Clear")) {
            resetResult();
        }
        else {
            switch (symbol) {
                case "Plus" -> operator = "+";
                case "Minus" -> operator = "-";
                case "Multiply" -> operator = "*";
                case "Divide" -> operator = "/";
            }
            num1 = Double.parseDouble(lblInput.getText());
            lblInput.setText(String.valueOf(0));
        }
    }

    private void resetResult() {
        lblInput.setText(String.valueOf(0));
        operator = ".";
    }
}
