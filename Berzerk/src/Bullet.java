import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Bullet {
    private int positionX;
    private int positionY;
    private Direction direction;
    private boolean active;
    private int movingSpeed;
    BufferedImage bulletImage;
    GamePanel gamePanel;

    public Bullet(GamePanel gamePanel) {
        movingSpeed = 5;
        setImage();
        this.gamePanel = gamePanel;
    }
    private void setImage() {
        try {
            bulletImage = ImageIO.read(new FileInputStream("resources/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBullet(int locationX, int locationY, Direction direction, boolean active) {
        this.positionX = locationX;
        this.positionY = locationY;
        this.direction = direction;
        this.active = active;
    }

    public void update(){
        switch (direction){
            case UP: {
                if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
                    positionY -= movingSpeed;
                else active = false;
                break;
            }
            case DOWN: {
                if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
                    positionY += movingSpeed;
                else active = false;
                break;
            }
            case LEFT: {
                if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
                    positionX -= movingSpeed;
                else active = false;
                break;
            }
            case RIGHT: {
                if(!gamePanel.getCollision().checkBulletCollisionWithTile(this))
                    positionX += movingSpeed;
                else active = false;
                break;
            }
        }
    }

    public void paint(Graphics g){
        g.drawImage(bulletImage, positionX, positionY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isActive() {
        return active;
    }


    public int getMovingSpeed() {
        return movingSpeed;
    }

}
