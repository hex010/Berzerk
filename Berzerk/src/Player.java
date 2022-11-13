import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Character {
    private final KeyHandler keyHandler;

    public Player(GamePanel gamePanel) {
        super(gamePanel);
        this.keyHandler = KeyHandler.getInstance();
        setDefaultValues();
        setImage();
    }

    private void setDefaultValues() {
        positionX = 150;
        positionY = 150;
        movingSpeed = 3;
        bullet = new Bullet(gamePanel);
        direction = Direction.RIGHT;
        canMove = false;
    }

    private void setImage() {
        try {
            bufferedImage = ImageIO.read(new FileInputStream("resources/berzerkPlayer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(){
        if(keyHandler.isUpPressed()){
            direction = Direction.UP;
            makeMove();
        } else if(keyHandler.isDownPressed()){
            direction = Direction.DOWN;
            makeMove();
        } else if(keyHandler.isLeftPressed()){
            direction = Direction.LEFT;
            makeMove();
        } else if(keyHandler.isRightPressed()){
            direction = Direction.RIGHT;
            makeMove();
        } else if(keyHandler.isShootPressed() && !bullet.canMove){
            shootBullet();
        }
    }

    private void shootBullet() {
        bullet.setBullet(positionX, positionY, direction, true, true);
        gamePanel.bullets.add(bullet);
    }

    private void makeMove() {
        canMove = gamePanel.getCollision().checkCharacterCollisionWithTile(this);
        if(gamePanel.getCollision().checkCharacterCollisionWithCharacters(this, gamePanel.enemies)){
            gamePanel.setGameOver(true);
            return;
        }
        if(canMove)  direction.move(this);
    }

    @Override
    public void paint(Graphics g){
        g.drawImage(bufferedImage, positionX, positionY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}
