public class Safe_Controller {

    public Safe_Controller(Notifications notification,
                           Door door_manager, Keypad keypad,
                           PIN_Manager pin_manager, Timer timer){
//      The assumption is made here that when starting the program that the
//      safe is already in the state of having the door closed which under our
//      design means that the safe is locked, this based on that when using
//      the safe the user will leave it in the locked state
        System.out.println("Door to safe is currently closed and locked.");
        System.out.println("Please enter 6 digit pin to unlock the safe.");




    }

}
