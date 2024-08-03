import java.util.ArrayList;

public class Passcode {
    private AccessSafe accessSafe;
    private String password;
    private String  PIN;
    private String PINDisplay;
    private String keyPress;
    private boolean PINEntry;
    private int accessCount;
    private ArrayList<Boolean> resetPWD;

    public Passcode(String password, AccessSafe accessSafe) {
        this.accessSafe = accessSafe;
        this.password = password;
        this.PIN = "";
        this.PINDisplay = "";
        this.keyPress = "";
        this.PINEntry = true;
        this.accessCount = 0;
        this.resetPWD = new ArrayList<>(2);
        this.resetPWD.add(false);
        this.resetPWD.add(false);
    }

    public void normalEntry(char keyEntry) {
        this.PIN += keyEntry;
        this.PINDisplay += " " + "*";
        this.keyPress += " " + keyEntry;
        this.PINEntry = true;
        this.accessSafe.getDisplay().clear();
        this.accessSafe.getDisplay().appendText(PINDisplay);
        this.accessSafe.getKeyPressMonitor().setText(keyPress);

        if(PIN.length() == 4) {
            System.out.println("Entry pin = " + PIN);
            System.out.println("Current Password = " + password);
            this.accessSafe.getTimer().getTimeline().stop();
            this.accessSafe.getDisplay().clear();
            if(this.accessSafe.getPasscode().test(PIN)) {
                this.accessSafe.getDisplay().accessGranted();
            } else {
                this.accessSafe.getDisplay().accessDenied();
            }
            this.PIN = "";
            this.PINDisplay = "";
            this.keyPress =  "";
        }
    }

    public void resetPassword(char keyEntry) {
        this.PIN += keyEntry;
        this.PINDisplay += " " +'*';
        this.keyPress += " " + keyEntry;
        accessSafe.getDisplay().clear();
        accessSafe.getDisplay().appendText(PINDisplay);
        accessSafe.getKeyPressMonitor().setText(keyPress);

        if(PIN.length() == 4) {
            this.accessSafe.getTimer().getTimeline().stop();
            if(resetPWD.get(0) && resetPWD.get(1)) {
                accessSafe.getDisplay().clear();
                if(accessSafe.getPasscode().test(PIN)) {
                    resetPWD.set(0,false);
                    resetPWD.set(1,false);
                    this.accessSafe.getPasscode().setPasscode(PIN);
                    this.accessSafe.getDisplay().resetCompleted(keyPress);
                } else {
                    resetPWD.set(1,false);
                    accessSafe.getDisplay().newPassword();
                }
                this.PIN = "";
                this.PINDisplay = "";
                this.keyPress =  "";
            } else if (resetPWD.get(0)) {
                accessSafe.getDisplay().clear();
                accessSafe.getPasscode().setPasscode(PIN);
                resetPWD.set(1,true);
                accessSafe.getDisplay().confirmPassword();
                this.PIN = "";
                this.PINDisplay = "";
                this.keyPress =  "";
            }

        }
    }

    public void accessCount(){
        this.accessCount++;
        if(accessCount > 5){
            this.accessSafe.setLockout(true);
            this.accessSafe.getDisplay().lockOut();
            this.accessSafe.getTimer().lockout();
            this.accessCount = 0;
            this.resetVariables();
        }
    }

    public boolean test(String PIN) {
        return PIN.equals(this.password);
    }

    public String getPin() {
        return this.PIN;
    }

    public void setPasscode(String PIN) {
        this.password = PIN;
    }

    public ArrayList<Boolean> getResetPWD() {
        return this.resetPWD;
    }

    public void setPwd(String pwd){
        this.password = pwd;
    }
    public void resetVariables(){
        this.PIN ="";
        this.PINDisplay ="";
        this.keyPress="";
    }
}
