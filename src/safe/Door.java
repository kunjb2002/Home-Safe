import javafx.scene.control.Label;

import java.util.concurrent.locks.Lock;

public class Door extends Label {

    private boolean doorStatus;
    private AccessSafe accessSafe;
    private LockStatus lockStatus;


    public Door(AccessSafe accessSafe){
        super("Closed");
        this.accessSafe = accessSafe;
        this.lockStatus = new LockStatus(this);
        this.setMinHeight(50);
        this.setMinWidth(125);
        this.setStyle("-fx-background-color: red;" +
                "-fx-font-size: 20px;" +
                "-fx-text-fill: black;");
        this.doorStatus = false;
    }

    public void locked(){
        this.accessSafe.getTimer().lockTimer();
        this.doorStatus = false;
        this.setText("Closed.");
        this.setStyle("-fx-background-color: RED;" +
                "-fx-font-size: 20px;");

    }
    public void unlocked(){
        //this.accessSafe.getTime().lockTimer();
        this.doorStatus = true;
        this.setText("Open.");
        this.setStyle("-fx-background-color: Green;" +
                "-fx-font-size: 20px;");
    }

    public boolean isOpenDoor(){
        return this.doorStatus;
    }

    public LockStatus getLockStatus() {
        return this.lockStatus;
    }

    public AccessSafe getAccessSafe() {
        return this.accessSafe;
    }




}
