import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Timer {
    private AccessSafe accessSafe;
    private static final Integer keyPress = 5;
    private static final Integer lockReverse = 10;
    private static final Integer lockTimeout = 15;
    private final Timeline timeline;

    private final static IntegerProperty keyPressTimer = new SimpleIntegerProperty(keyPress);
    private final static IntegerProperty lockReverseTimer = new SimpleIntegerProperty(keyPress);
    private final static IntegerProperty lockTimeoutTimer = new SimpleIntegerProperty(keyPress);



    public Timer(AccessSafe accessSafe) {
        timeline = new Timeline();
        this.accessSafe = accessSafe;
    }

    public void keyPressTimer() {
        System.out.println("keypress time out!");
        keyPressTimer.set(keyPress);
        timeline.getKeyFrames().removeAll();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(keyPress), new KeyValue(keyPressTimer,0)));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                accessSafe.getDisplay().timeout();
                accessSafe.getPIN().setText("");
                accessSafe.getPasscode().resetVariables();
            }
        });
        timeline.playFromStart();
    }

    public void lockTimer() {
        System.out.println("lock time out");
        lockReverseTimer.set(lockReverse);
        timeline.getKeyFrames().removeAll();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(lockReverse), new KeyValue(lockReverseTimer,0)));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                accessSafe.getDisplay().safeLock();
                accessSafe.getDoor().getLockStatus().lockedStatus();
                accessSafe.getPIN().setText("");
                accessSafe.getPasscode().resetVariables();
            }
        });
        timeline.playFromStart();
    }

    public void lockout() {
        System.out.println("Lockout!!!");
        lockTimeoutTimer.set(lockTimeout);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(lockTimeout), new KeyValue(lockTimeoutTimer,0)));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                accessSafe.getDisplay().enterPin();
                accessSafe.getPIN().setText("");
                accessSafe.getPasscode().resetVariables();
                accessSafe.setLockout(false);
            }
        });
        timeline.playFromStart();
    }

    public Timeline getTimeline() {
        return timeline;
    }
}

