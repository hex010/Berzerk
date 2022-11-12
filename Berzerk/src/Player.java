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
    Rectangle rectangle;

    private Direction direction;
    boolean canMove;

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

    public void update(){

        if(keyHandler.upPressed){
            direction = Direction.UP;
            canMove = gamePanel.getCollision().checkPlayerCollisionWithTile(this);
            if(gamePanel.getCollision().checkPlayerCollisionWithEnemy(this, gamePanel.enemies)){
                gamePanel.setGameOver(true);
                return;
            }
            if(canMove)  makeMove();
        } else if(keyHandler.downPressed){
            direction = Direction.DOWN;
            canMove = gamePanel.getCollision().checkPlayerCollisionWithTile(this);
            if(gamePanel.getCollision().checkPlayerCollisionWithEnemy(this, gamePanel.enemies)){
                gamePanel.setGameOver(true);
                return;
            }
            if(canMove)  makeMove();
        } else if(keyHandler.leftPressed){
            direction = Direction.LEFT;
            canMove = gamePanel.getCollision().checkPlayerCollisionWithTile(this);
            if(gamePanel.getCollision().checkPlayerCollisionWithEnemy(this, gamePanel.enemies)){
                gamePanel.setGameOver(true);
                return;
            }
            if(canMove)  makeMove();
        } else if(keyHandler.rightPressed){
            direction = Direction.RIGHT;
            canMove = gamePanel.getCollision().checkPlayerCollisionWithTile(this);
            if(gamePanel.getCollision().checkPlayerCollisionWithEnemy(this, gamePanel.enemies)){
                gamePanel.setGameOver(true);
                return;
            }
            if(canMove)  makeMove();
        } else if(keyHandler.shootPressed && !bullet.isActive()){
            bullet.setBullet(positionX, positionY, direction, true, true);
            gamePanel.bullets.add(bullet);
        }
    }


    private void makeMove() {
        switch (direction) {
            case UP -> {
                moveUp();
            }
            case DOWN -> {
                moveDown();
            }
            case LEFT -> {
                moveLeft();
            }
            case RIGHT -> {
                moveRight();
            }
        }
    }

    private void moveRight() {
        positionX += movingSpeed;
    }

    private void moveLeft() {
        positionX -= movingSpeed;
    }

    private void moveDown() {
        positionY += movingSpeed;
    }

    private void moveUp() {
        positionY -= movingSpeed;
    }

    public void paint(Graphics g){
        g.drawImage(bufferedImage, positionX, positionY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
