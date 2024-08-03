import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Keypad extends GridPane {

    private AccessSafe accessSafe;

    private Key[][] keyLayout;

    public Keypad(AccessSafe accessSafe) {
        this.accessSafe = accessSafe;
        this.setVgap(15);
        this.setHgap(15);
        this.keyLayout = new Key[4][3];

        char keyValue = '1';
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                Key key = new Key(keyValue,this);
                this.getKeyLayout()[j][i] = key;
                this.add(key,j,i);
                keyValue++;
            }
        }
        this.add(new Key('0',this),1,4);
        this.add(new Key('R',this),0,4);
        this.add(new Key('#',this),2,4);
        this.setMinHeight(600);
        this.setMaxWidth(400);
        this.setStyle("-fx-background-color: lightblue;");
    }

    public Key[][] getKeyLayout() {
        return keyLayout;
    }

    public void pressKey(Key key) {
        this.accessSafe.pressKey(key);
    }


}
