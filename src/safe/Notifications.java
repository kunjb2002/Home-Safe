import java.awt.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;



public class Notifications extends TextField {

    private AccessSafe accessSafe;

    public Notifications(AccessSafe accessSafe) {
        this.accessSafe = accessSafe;
        setupDisplay(this);
    }

    private void setupDisplay(Notifications display) {
        this.setMaxWidth(400);
        this.setMaxHeight(100);
        this.setMinWidth(400);
        this.setMinHeight(100);
        display.setEditable(false);
        display.setStyle("-fx-background-color: grey;" +
                "-fx-font-text: 50px;" +
                "-fx-text-fill: darkgreen;" +
                "-fx-alignment: center-left");
        display.setFocusTraversable(false);
        display.appendText("Enter Pin");
        display.setFont(Font.font("",FontWeight.BOLD, 50));

    }

    public void accessGranted() {
        System.out.println("accessGranted!!!");
        this.accessSafe.getDoor().getLockStatus().unlockedStatus();
        this.setFont(Font.font("", FontWeight.BOLD,25));
        this.appendText("Access Granted!");
    }
    public void accessDenied() {
        System.out.println("accessDenied!!!");
        this.setFont(Font.font("", FontWeight.BOLD,25));
        this.appendText("Access Denied!");
        this.accessSafe.getPasscode().accessCount();

    }
    void setBatteryLED(boolean batteryCharged, boolean batteryLEDON){
        if(!batteryCharged){
            if(batteryLEDON){
                System.out.println("Notification: Battery is low LED is on.");
            }else {
                System.out.println("Notification: Battery is low LED turned " +
                        "off to conserve remaining battery.");
            }
        } else {
            System.out.println("Notification: Battery is low LED turned " +
                    "off due to battery charge level going above 20%.");
        }
    }

    public void unlocked(){
        this.clear();
        this.setFont(Font.font("", FontWeight.BOLD,25));
        this.appendText("Unlocked!");
        //this.setFont(Font.font("Helvetica", FontWeight.BOLD,30));
    }

    public void resetCompleted(String PINDisplay) {
        this.clear();
        this.setFont(Font.font("", FontWeight.BOLD,25));
        this.appendText("Reset Completed!");
        this.accessSafe.getPWD().setText(PINDisplay);
    }
    public void newPassword() {
        this.clear();
        this.setFont(Font.font("", FontWeight.BOLD, 25));
        this.appendText("Enter new password:");
    }

    public void confirmPassword() {
        this.clear();
        this.setFont(Font.font("", FontWeight.BOLD,25));
        this.appendText("Confirm password:");
    }

    public void doorIsClosed(){
        this.clear();
        this.setFont(Font.font("", FontWeight.BOLD,25));
        this.appendText("Unlock door first!");
    }
    public void timeout(){
        this.accessSafe.getTimer().getTimeline().stop();
        this.clear();
        this.setFont(Font.font("", FontWeight.BOLD,25));
        this.appendText("Timeout!");
    }
    public void safeLock(){
        this.accessSafe.getTimer().getTimeline().stop();
        this.clear();
        this.setFont(Font.font("", FontWeight.BOLD,25));
        this.appendText("Lock set!");
    }

    public void enterPin(){
        this.clear();
        this.setFont(Font.font("", FontWeight.BOLD,25));
        this.appendText("enter pin:");
    }

    public void lockOut(){
        this.clear();
        this.setFont(Font.font("", FontWeight.BOLD,25));
        this.appendText("Lock out 15s!");
    }


}
