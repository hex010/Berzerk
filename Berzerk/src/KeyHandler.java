import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean shootPressed;

    public boolean playerDirectionUp;
    public boolean playerDirectionDown;
    public boolean playerDirectionLeft;
    public boolean playerDirectionRight;

    public boolean bulletDirectionUp;
    public boolean bulletDirectionDown;
    public boolean bulletDirectionLeft;
    public boolean bulletDirectionRight;

    public Boolean bulletActive = false;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_A){
            leftPressed = true;

            playerDirectionUp = false;
            playerDirectionDown = false;
            playerDirectionLeft = true;
            playerDirectionRight = false;
        }else if(keyCode == KeyEvent.VK_D){
            rightPressed = true;

            playerDirectionUp = false;
            playerDirectionDown = false;
            playerDirectionLeft = false;
            playerDirectionRight = true;
        }else if(keyCode == KeyEvent.VK_W){
            upPressed = true;

            playerDirectionUp = true;
            playerDirectionDown = false;
            playerDirectionLeft = false;
            playerDirectionRight = false;
        }else if(keyCode == KeyEvent.VK_S){
            downPressed = true;

            playerDirectionUp = false;
            playerDirectionDown = true;
            playerDirectionLeft = false;
            playerDirectionRight = false;
        }else if(keyCode == KeyEvent.VK_SPACE){
            shootPressed = true;

            if(!bulletActive) {
                bulletDirectionDown = playerDirectionDown;
                bulletDirectionUp = playerDirectionUp;
                bulletDirectionRight = playerDirectionRight;
                bulletDirectionLeft = playerDirectionLeft;
            }
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
}
