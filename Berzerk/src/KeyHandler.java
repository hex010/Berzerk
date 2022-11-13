import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyHandler extends KeyAdapter {
    static KeyHandler instance;
    private final Map<Integer, KeyHandlerCommand> commands = new HashMap<>();

    private KeyHandler() {
        setCommandsMap();
    }

    static KeyHandler getInstance(){
        if(instance == null){
            instance = new KeyHandler();
        }
        return instance;
    }

    private void setCommandsMap() {
        commands.put(KeyEvent.VK_A, new LeftKeyCommand(this));
        commands.put(KeyEvent.VK_D, new RightKeyCommand(this));
        commands.put(KeyEvent.VK_S, new DownKeyCommand(this));
        commands.put(KeyEvent.VK_W, new UpKeyCommand(this));
        commands.put(KeyEvent.VK_SPACE, new ShootKeyCommand(this));
    }

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean shootPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        commands.get(e.getKeyCode()).executeKeyPressed();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        commands.get(e.getKeyCode()).executeKeyReleased();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isShootPressed() {
        return shootPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void setShootPressed(boolean shootPressed) {
        this.shootPressed = shootPressed;
    }
}
