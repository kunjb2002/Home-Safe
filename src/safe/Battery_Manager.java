import java.io.*;
import java.util.Scanner;

public class Battery_Manager{

    public Battery_Manager(File battery, Notifications notification)
            throws Exception {
        boolean batteryCharged = true;
        boolean batteryLEDOn = false;

        while (true){
            Scanner sc = new Scanner(battery);
            while (sc.hasNext()){
                String battery_level = sc.nextLine();
//                System.out.println(battery_level);
                if(Integer.parseInt(battery_level) < 20 && batteryCharged){
                    batteryCharged = false;
                    batteryLEDOn = true;
                    notification.setBatteryLED(false, true);
                } else if(Integer.parseInt(battery_level) > 20 &&
                        !batteryCharged){
                    batteryCharged = true;
                    batteryLEDOn = false;
                    notification.setBatteryLED(true, false);
                } else if(Integer.parseInt(battery_level) < 20 &&
                        batteryLEDOn) {
                    batteryLEDOn = false;
                    notification.setBatteryLED(false, false);
                }
            }
            Thread.sleep(5000);
        }
    }
}
