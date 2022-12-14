import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class Character {
    protected GamePanel gamePanel;
    protected int positionX;
    protected int positionY;
    protected int movingSpeed;
    protected boolean canMove;
    protected Bullet bullet;
    protected BufferedImage bufferedImage;
    protected Rectangle myRectangle;
    protected Direction direction;


    public Character(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setRectangleValues();
    }

    private void setRectangleValues() {
        myRectangle = new Rectangle();
        myRectangle.x = 0;
        myRectangle.y = 0;
        myRectangle.width = gamePanel.getTileSize();
        myRectangle.height = gamePanel.getTileSize();
    }

    protected void moveRight() {
        positionX += movingSpeed;
    }

    protected void moveLeft() {
        positionX -= movingSpeed;
    }

    protected void moveDown() {
        positionY += movingSpeed;
    }

    protected void moveUp() {
        positionY -= movingSpeed;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getMovingSpeed() {
        return movingSpeed;
    }

    public Rectangle getMyRectangle() {
        return myRectangle;
    }

    public Direction getDirection() {
        return direction;
    }


    public void update() {

    }

    public void paint(Graphics g) {
    }
}
