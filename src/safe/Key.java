import javafx.scene.control.Button;

public class Key extends Button {

    private char keyValue;

    private Keypad keypad;

    public Key(char keyValue, Keypad keypad) {
        this.keyValue = keyValue;
        this.keypad = keypad;
        this.setText(Character.toString(this.getKeyValue()));
        this.setStyle("-fx-background-color: white;" +
                "-fx-font-size: 20px;" +
                "-fx-text-fill: black");
        this.setOnAction(actionEvent ->  keyPress());
    }

    private void keyPress() {
        this.keypad.pressKey(this);
        System.out.println(getKeyValue());
    }

    public char getKeyValue() {
        return keyValue;
    }
}
