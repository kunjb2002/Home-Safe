import javafx.scene.control.Label;

public class LockStatus extends Label {
    private Door door;
    private boolean isLocked;

    public LockStatus(Door door) {
        super("Locked.");
        this.isLocked = true;
        this.door =door;
        this.setMinHeight(50);
        this.setMinWidth(125);
        this.setStyle("-fx-background-color: red;" +
                "-fx-font-size: 20px;" +
                "-fx-text-fill: black;");
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public void lockedStatus() {
        //this.door.getSafe().getTimer().getTimeline().stop();
        this.isLocked = true;
        this.setText("Locked.");
        this.setStyle("-fx-background-color: red;" +
                "-fx-font-size: 20px;" +
                "-fx-text-fill: black;");
    }

    public void unlockedStatus() {
        this.door.getAccessSafe().getTimer().lockTimer();
        this.isLocked = false;
        this.setText("Unlocked.");
        this.setStyle("-fx-background-color: green;" +
                "-fx-font-size: 20px" +
                "-fx-text-fill: black");
    }

}
