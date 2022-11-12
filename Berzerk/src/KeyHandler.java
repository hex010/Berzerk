import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean shootPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_A){
            leftPressed = true;
        }else if(keyCode == KeyEvent.VK_D){
            rightPressed = true;
        }else if(keyCode == KeyEvent.VK_W){
            upPressed = true;
        }else if(keyCode == KeyEvent.VK_S){
            downPressed = true;
        }else if(keyCode == KeyEvent.VK_SPACE){
            shootPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_A){
            leftPressed = false;
        }else if(keyCode == KeyEvent.VK_D){
            rightPressed = false;
        }else if(keyCode == KeyEvent.VK_W){
            upPressed = false;
        }else if(keyCode == KeyEvent.VK_S){
            downPressed = false;
        }else if(keyCode == KeyEvent.VK_SPACE){
            shootPressed = false;
        }
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
}
