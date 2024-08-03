package testing;

import safe.*;

import java.io.File;
import java.util.Scanner;

public class text_interface {


    public static void main(String[] args) throws Exception {
        System.out.println("Home Safe - Text Interface Version");
        System.out.println("Type 'help' to see list of commands");

        File storage = new File(args[0]);
        File battery = new File(args[1]);

//      notifications used to set any notifications from LED's to display screen
        Notifications notifications = new Notifications();
//      Door_manager used for the state of the lock and door
        Door door_manager = new Door();
//      Keypad used for user input of keypad
        Keypad keypad = new Keypad();
//      PIN_Manager used to store saved pin and reset the pin
        PIN_Manager pin_manager = new PIN_Manager(storage);
//      Timer used to time the pin entry
        Timer timer = new Timer();

        Thread battery_thread = new Thread(){
            public void run(){
                try {
                    Battery_Manager battery_manager =
                            new Battery_Manager(battery, notifications);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        battery_thread.start();

        Scanner scanner = new Scanner(System.in);
        String input;

        while(!(input = scanner.next()).equals("easter egg")){
            switch(input){
                case "help":
                    System.out.println("Default: ");
                    System.out.println("xxxxxx - Attempt to unlock the safe");
                    System.out.println("exit - Exit the program\n");
                    System.out.println("With the safe unlocked: ");
                    System.out.println("reset - return the pin to the default value");
                    System.out.println("set - enter a new 6 digit combo for your pin");
                    System.out.println("lock - close and lock your safe\n");
                    System.out.println("Type 'help' to see this list again");
                    break;
                case "exit":
                    System.out.println("Goodbye");
                    System.exit(1);
                    break;
                case "reset":
                    if(door_manager.isLocked()){
                        System.out.println("You cannot access that button when the safe is locked");
                    }else{
                        pin_manager.reset();
                    }
                    break;
                case "set":
                    System.out.println("Please enter your old pin number: ");
                    input = scanner.next();
                    if(pin_manager.comparePIN(Integer.parseInt(input))){
                        System.out.println("Please enter your new pin: ");
                        input = scanner.next();
                        pin_manager.set(Integer.parseInt(input));
                        System.out.println("Pin successfully set");
                    }else{
                        System.out.println("Incorrect Pin. Start Over");
                    }
                    break;
                case "lock":
                    if(door_manager.isLocked()){
                        System.out.println("Safe already locked.");
                    }else{
                        door_manager.setLocked(true);
                        door_manager.setDoorOpen(false);
                        System.out.println("Your safe is now locked");
                    }
                    break;
                default:
                    if(input.length() != 6) System.out.println("Unrecognized command or wrong number of digits. Try Again");
                    // If they input a 6 char long input, just assume it is a pin number attempt
                    else{
                        if(pin_manager.comparePIN(Integer.parseInt(input))){
                            System.out.println("Safe unlocked");
                            door_manager.setDoorOpen(true);
                            door_manager.setLocked(false);
                        }else{
                            System.out.println("Incorrect PIN");
                        }
                    }
            }
        }


    }
}
