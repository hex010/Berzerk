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

        if(!bullet.canMove)
            checkIfISeeThePlayer();
    }

    private void makeMove() {
        if(canMove){
            direction.move(this);
            return;
        }

        changeDirection();
    }

    private void changeDirection() {
        direction = direction.opposite();
    }

    private void checkIfISeeThePlayer() {
        if(IAndPlayerOnTheSameYAxis()){
            if(ThePlayerBelowMe()) {
                shootTowardsThePlayer(Direction.DOWN);
                return;
            }
            shootTowardsThePlayer(Direction.UP);
        }

        if(IAndPlayerOnTheSameXAxis()){
            if(ThePlayerIsToMyRight()) {
                shootTowardsThePlayer(Direction.RIGHT);
                return;
            }
            shootTowardsThePlayer(Direction.LEFT);
        }
    }

    private boolean ThePlayerIsToMyRight() {
        return gamePanel.getPlayer().getPositionX() > positionX;
    }

    private boolean ThePlayerBelowMe() {
        return gamePanel.getPlayer().getPositionY() > positionY;
    }

    private boolean IAndPlayerOnTheSameXAxis() {
        return gamePanel.getPlayer().getPositionY() / gamePanel.getTileSize() == positionY / gamePanel.getTileSize();
    }

    private boolean IAndPlayerOnTheSameYAxis() {
        return gamePanel.getPlayer().getPositionX() / gamePanel.getTileSize() == positionX / gamePanel.getTileSize();
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
