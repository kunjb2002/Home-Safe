
import java.io.*;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Controller extends Application{


    private AccessSafe accessSafe;

    @Override
    public void start(Stage stage) throws IOException {
        this.accessSafe = new AccessSafe(new VBox(75),this);
        stage.setTitle("Home Safe");
        stage.setScene(accessSafe);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello welcome to your home safe.");
        launch();

        File storage = new File(args[0]);
        File battery = new File(args[1]);

//      notifications used to set any notifications from LED's to display screen
        //Notifications notifications = new Notifications();
//      Door_manager used for the state of the lock and door
        //Door door = new Door();
//      Keypad used for user input of keypad
        //Keypad keypad = new Keypad();
//      PIN_Manager used to store saved pin and reset the pin
        PIN_Manager pin_manager = new PIN_Manager(storage);
//      Timer used to time the pin entry
        //Timer timer = new Timer();

        //Safe_Controller controller = new Safe_Controller(notifications,
        //       door, keypad, pin_manager, timer);

//        Thread battery_thread = new Thread(){
//            public void run(){
//                try {
//                    Battery_Manager battery_manager =
//                            new Battery_Manager(battery, notifications);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        battery_thread.start();
    }





}
