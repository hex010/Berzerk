import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class Enemy extends Character {
    public Enemy(GamePanel gamePanel) {
        super(gamePanel);
        setDefaultValues();
        setImage();
    }

    private void setDefaultValues() {
        movingSpeed = 2;
        direction = Direction.RIGHT;
        bullet = new Bullet(gamePanel);
    }

    private void setImage() {
        try {
            bufferedImage = ImageIO.read(new FileInputStream("resources/enemy.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEnemy(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    public void update(){
        if(gamePanel.getCollision().checkCharacterCollisionWithCharacters(this, gamePanel.players)){
            gamePanel.setGameOver(true);
            return;
        }
        canMove = gamePanel.getCollision().checkCharacterCollisionWithTile(this);
        makeMove();

        if(!bullet.isActive())
            checkIfIseeThePlayer();
    }

    private void makeMove() {
        if(canMove){
            if(direction == Direction.LEFT)
                moveLeft();
            else
                moveRight();
        }
        else {
            if(direction == Direction.LEFT)
                direction = Direction.RIGHT;
            else
                direction = Direction.LEFT;
        }
    }

    private void checkIfIseeThePlayer() {
        if(gamePanel.getPlayer().getPositionX() / gamePanel.getTileSize() == positionX / gamePanel.getTileSize()){
            if(gamePanel.getPlayer().getPositionY() > positionY)
                shootTowardsThePlayer(Direction.DOWN);
            else
                shootTowardsThePlayer(Direction.UP);
        }
        if(gamePanel.getPlayer().getPositionY() / gamePanel.getTileSize() == positionY / gamePanel.getTileSize()){
            if(gamePanel.getPlayer().getPositionX() > positionX)
                shootTowardsThePlayer(Direction.RIGHT);
            else
                shootTowardsThePlayer(Direction.LEFT);
        }
    }

    private void shootTowardsThePlayer(Direction shootDirection) {
        bullet.setBullet(positionX, positionY, shootDirection, true, false);
        gamePanel.bullets.add(bullet);
    }

    @Override
    public void paint(Graphics g){
        g.drawImage(bufferedImage, positionX, positionY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}
