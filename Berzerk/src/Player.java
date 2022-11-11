import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Player {
    private int positionX;
    private int positionY;
    private int movingSpeed;
    private GamePanel gamePanel;
    private KeyHandler keyHandler;
    private Bullet bullet;
    private BufferedImage bufferedImage;
    private Rectangle rectangle;

    private Direction direction;

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getMovingSpeed() {
        return movingSpeed;
    }

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        setDefaultValues();
        setImage();
        setRectangleValues();
    }

    private void setRectangleValues() {
        rectangle = new Rectangle();
        rectangle.x = 0;
        rectangle.y = 0;
        rectangle.width = gamePanel.getTileSize();
        rectangle.height = gamePanel.getTileSize();
    }

    private void setDefaultValues() {
        positionX = 150;
        positionY = 150;
        movingSpeed = 3;
        bullet = new Bullet(gamePanel);
    }

    private void setImage() {
        try {
            bufferedImage = ImageIO.read(new FileInputStream("resources/berzerkPlayer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyHandler.upPressed){
            direction = Direction.UP;

            if(!gamePanel.getCollision().checkPlayerCollisionWithTile(this))
                positionY -= movingSpeed;
        } else if(keyHandler.downPressed){
            direction = Direction.DOWN;

            if(!gamePanel.getCollision().checkPlayerCollisionWithTile(this))
                positionY += movingSpeed;
        } else if(keyHandler.leftPressed){
            direction = Direction.LEFT;

            if(!gamePanel.getCollision().checkPlayerCollisionWithTile(this))
                positionX -= movingSpeed;
        } else if(keyHandler.rightPressed){
            direction = Direction.RIGHT;

            if(!gamePanel.getCollision().checkPlayerCollisionWithTile(this))
                positionX += movingSpeed;
        } else if(keyHandler.shootPressed && !bullet.isActive()){
            bullet.setBullet(positionX, positionY, direction, true);
            gamePanel.bullets.add(bullet);
        }

    }

    public void paint(Graphics g){
        g.drawImage(bufferedImage, positionX, positionY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
