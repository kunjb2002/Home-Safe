import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class AccessSafe extends Scene {

    private Controller controller;

    private Label currentPasswordStatus;

    private Notifications display;

    private Door door;

    private Keypad keypad;

    private  Label keyPressMonitor;

    private Passcode passcode;

    private boolean lockout;

    private AccessSafe accessSafe;
    private Label currentPWD;
    private Timer timer;

    public AccessSafe(Parent parent, Controller controller) {
        super(parent, 1800, 1000);
        this.controller = controller;
        this.passcode = new Passcode("0000",this);
        this.door = new Door(this);
        this.timer = new Timer(this);

        VBox main = (VBox) parent;
        main.setAlignment(Pos.CENTER);
        main.setStyle("-fx-background-color: darkgrey");


        HBox layout = new HBox(400);
        layout.setAlignment(Pos.CENTER);

        VBox API = new VBox();
        API.setAlignment(Pos.CENTER);
        API.setStyle("-fx-background-color: grey");
        API.setMinHeight(650);
        API.setMinWidth(450);
        this.display = new Notifications(this);
        this.display.setAlignment(Pos.CENTER);
        this.keypad = new Keypad(this);
        this.keypad.setAlignment(Pos.CENTER);
        API.getChildren().addAll(display,keypad);



        VBox action = new VBox(20);
        action.setAlignment(Pos.CENTER);
        Button physicalKey = new Button("Key");
        physicalKey.setStyle("-fx-background-color: green; " +
                "-fx-font-size: 20px; " +
                "-fx-text-fill: #000000");
        physicalKey.setOnAction(actionEvent -> usePhysicalKey());
        Button openDoor = new Button("Open Door(action)");
        openDoor.setStyle("-fx-background-color: green;" +
                "-fx-font-size: 20px;" +
                "-fx-text-fill: #000000");
        openDoor.setOnAction(actionEvent -> openDoor());
        Button closeDoor = new Button("Close Door(action)");
        closeDoor.setStyle("-fx-background-color: green;" +
                "-fx-font-size: 20px;" +
                "-fx-text-fill: #000000");
        closeDoor.setOnAction(actionEvent -> closeDoor());
        action.getChildren().addAll(physicalKey,openDoor,closeDoor);


        VBox monitor = new VBox(20);
        monitor.setAlignment(Pos.CENTER);
        HBox entryPassword = new HBox();
        entryPassword.setMinHeight(50);
        entryPassword.setAlignment(Pos.CENTER);
        entryPassword.setStyle("-fx-background-color: lightblue;" +
                "-fx-font-size: 20px;");
        this.keyPressMonitor = new Label("");
        keyPressMonitor.setStyle("-fx-backgourd-color: white;" +
                "-fx-font-size:20;");
        Label keyPressMonitorLabel = new Label("Current entry: ");
        entryPassword.getChildren().addAll(keyPressMonitorLabel,keyPressMonitor);
        HBox currentPassword = new HBox();
        currentPassword.setMinHeight(50);
        currentPassword.setAlignment(Pos.CENTER);
        currentPassword.setStyle("-fx-background-color: lightblue;" +
                "-fx-font-size: 20px;");
        Label currentPasswordLabel = new Label("Current Password: ");
        this.currentPasswordStatus = new Label("0 0 0 0");
        currentPassword.getChildren().addAll(currentPasswordLabel,currentPasswordStatus);
        HBox currentDoorStatus = new HBox();
        currentDoorStatus.setMinHeight(50);
        currentDoorStatus.setAlignment(Pos.CENTER);
        currentDoorStatus.setStyle("-fx-background-color: lightblue;" +
                "-fx-font-size: 20px;");
        Label currentDoorStatusLabel = new Label("Door Status: ");
        currentDoorStatus.getChildren().addAll(currentDoorStatusLabel, this.door);
        HBox currentLockStatus = new HBox();
        currentLockStatus.setMinHeight(50);
        currentLockStatus.setAlignment(Pos.CENTER);
        currentLockStatus.setStyle("-fx-background-color: lightblue;" +
                "-fx-font-size: 20px;");
        Label currentLockStatusLabel = new Label("Lock Status: ");
        currentLockStatus.getChildren().addAll(currentLockStatusLabel,door.getLockStatus());
        HBox currentBatteryStatus = new HBox();
        currentBatteryStatus.setMinHeight(50);
        currentBatteryStatus.setAlignment(Pos.CENTER);
        currentBatteryStatus.setStyle("-fx-background-color: lightblue;" +
                "-fx-font-size: 20px");
        Label currentBatteryStatusLabel = new Label("Battery Status: ");
        currentBatteryStatus.getChildren().add(currentBatteryStatusLabel);
        monitor.getChildren().addAll(entryPassword,currentPassword,currentDoorStatus,currentLockStatus,currentBatteryStatus);



        layout.getChildren().addAll(API, action, monitor);
        main.getChildren().add(layout);

    }

    public void closeDoor() {
        if(this.getDoor().isOpenDoor() && !lockout){
            this.getDoor().locked();
        }
        System.out.println("Close door(action)!!!");
    }

    public void openDoor() {
        if (!this.getDoor().getLockStatus().isLocked() && !this.getDoor().isOpenDoor() && !lockout) {
            this.getDoor().unlocked();
        }
        System.out.println("Open door(action)!!!");
    }

    private void usePhysicalKey() {
        if(this.getDoor().getLockStatus().isLocked() && !lockout){
            this.display.unlocked();
            this.getDoor().getLockStatus().unlockedStatus();
        }
        System.out.println("use key!!!");
    }

    public void pressKey(Key key) {
        Boolean lockout = this.lockout;
        Boolean lockStatus = this.getDoor().getLockStatus().isLocked();
        Boolean doorStatus = this.getDoor().isOpenDoor();

        if(!doorStatus && !lockout) {
            this.getTimer().keyPressTimer();
            char keyPress = key.getKeyValue();
            String PIN = this.getPasscode().getPin();
            ArrayList<Boolean> resetPWD = this.getPasscode().getResetPWD();

            if(keyPress == 'R' && PIN.length() == 0 && !resetPWD.get(0)) {
                if(!this.getDoor().getLockStatus().isLocked()) {
                    this.getPasscode().getResetPWD().set(0,true);
                    this.getDisplay().newPassword();
                } else {
                    this.getDisplay().doorIsClosed();
                }
            } else if (PIN.length() < 4 && resetPWD.get(0)) {
                this.getPasscode().resetPassword(keyPress);
            } else if (PIN.length() < 4 && !resetPWD.get(0) && lockStatus) {
                this.getPasscode().normalEntry(keyPress);
            }
        }
    }

    public Notifications getDisplay() {
        return display;
    }

    public Label getKeyPressMonitor() {
        return this.keyPressMonitor;
    }

    public Passcode getPasscode() {
        return passcode;
    }

    public Label getPWD() {
        return this.currentPasswordStatus;
    }

    public Label getPIN() {
        return this.keyPressMonitor;
    }


    public Door getDoor(){
        return this.door;
    }

    public Timer getTimer() {
        return this.timer;
    }
    public void setLockout(Boolean value){
        this.lockout = value;
    }

}
