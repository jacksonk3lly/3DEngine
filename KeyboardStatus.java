import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyboardStatus implements KeyListener {
    private Map<Integer, Boolean> keyStatus;

    public KeyboardStatus() {
        keyStatus = new HashMap<>();
    }

    public boolean isKeyPressed(int keyCode) {
        return keyStatus.getOrDefault(keyCode, false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyStatus.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyStatus.put(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }
}