import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListenerC extends KeyAdapter {
    boolean[] keys = new boolean[255];
    public Player player;

    public void keyPressed(KeyEvent evt) {
        keys[evt.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent evt){
        keys[evt.getKeyCode()] = false;
    }
}