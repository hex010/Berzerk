import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Character {
    private final KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;
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
            canMove = gamePanel.getCollision().checkCharacterCollisionWithTile(this);
            if(gamePanel.getCollision().checkCharacterCollisionWithCharacters(this, gamePanel.enemies)){
                gamePanel.setGameOver(true);
                return;
            }
            if(canMove)  moveUp();
        } else if(keyHandler.isDownPressed()){
            direction = Direction.DOWN;
            canMove = gamePanel.getCollision().checkCharacterCollisionWithTile(this);
            if(gamePanel.getCollision().checkCharacterCollisionWithCharacters(this, gamePanel.enemies)){
                gamePanel.setGameOver(true);
                return;
            }
            if(canMove)  moveDown();
        } else if(keyHandler.isLeftPressed()){
            direction = Direction.LEFT;
            canMove = gamePanel.getCollision().checkCharacterCollisionWithTile(this);
            if(gamePanel.getCollision().checkCharacterCollisionWithCharacters(this, gamePanel.enemies)){
                gamePanel.setGameOver(true);
                return;
            }
            if(canMove)  moveLeft();
        } else if(keyHandler.isRightPressed()){
            direction = Direction.RIGHT;
            canMove = gamePanel.getCollision().checkCharacterCollisionWithTile(this);
            if(gamePanel.getCollision().checkCharacterCollisionWithCharacters(this, gamePanel.enemies)){
                gamePanel.setGameOver(true);
                return;
            }
            if(canMove)  moveRight();
        } else if(keyHandler.isShootPressed() && !bullet.canMove){
            bullet.setBullet(positionX, positionY, direction, true, true);
            gamePanel.bullets.add(bullet);
        }
    }

    @Override
    public void paint(Graphics g){
        g.drawImage(bufferedImage, positionX, positionY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}
