import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Enemy {
    private int positionX;
    private int positionY;
    private int movingSpeed;
    private boolean mustRotate;
    private GamePanel gamePanel;
    Direction bulletDirection;
    BufferedImage enemyImage;
    Rectangle rectangle;
    private Bullet bullet;

    public Enemy(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        movingSpeed = 2;
        setRectangleValues();
        setImage();
        bullet = new Bullet(gamePanel);
    }

    public boolean isMustRotate() {
        return mustRotate;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getMovingSpeed() {
        return movingSpeed;
    }

    private void setImage() {
        try {
            enemyImage = ImageIO.read(new FileInputStream("resources/enemy.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setRectangleValues() {
        rectangle = new Rectangle();
        rectangle.x = 0;
        rectangle.y = 0;
        rectangle.width = gamePanel.getTileSize();
        rectangle.height = gamePanel.getTileSize();
    }

    public void setEnemy(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void update(){
        if(gamePanel.getCollision().checkEnemyCollisionWithPlayer(this, gamePanel.player)){
            gamePanel.setGameOver(true);
            return;
        }

        if(!mustRotate){
            if(!gamePanel.getCollision().checkEnemyCollisionWithTile(this))
                moveLeft();
            else mustRotate = true;
        }else {
            if(!gamePanel.getCollision().checkEnemyCollisionWithTile(this))
                moveRight();
            else mustRotate = false;
        }

        if(!bullet.isActive())
            checkIfIseeThePlayer();
    }

    private void moveRight() {
        positionX += movingSpeed;
    }

    private void moveLeft() {
        positionX -= movingSpeed;
    }

    private void checkIfIseeThePlayer() {
        if(gamePanel.getPlayer().getPositionX() / gamePanel.getTileSize() == positionX / gamePanel.getTileSize()){
            if(gamePanel.getPlayer().getPositionY() > positionY) {
                bulletDirection = Direction.DOWN;
            }
            else{
                bulletDirection = Direction.UP;
            }
            bullet.setBullet(positionX, positionY, bulletDirection, true, false);
            gamePanel.bullets.add(bullet);
        }
        if(gamePanel.getPlayer().getPositionY() / gamePanel.getTileSize() == positionY / gamePanel.getTileSize()){
            if(gamePanel.getPlayer().getPositionX() > positionX) {
                bulletDirection = Direction.RIGHT;
            }
            else{
                bulletDirection = Direction.LEFT;
            }
            bullet.setBullet(positionX, positionY, bulletDirection, true, false);
            gamePanel.bullets.add(bullet);
        }
    }

    public void paint(Graphics g){
        g.drawImage(enemyImage, positionX, positionY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}
