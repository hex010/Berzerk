import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class Bullet extends Character{
    private boolean shotByPlayer;

    public Bullet(GamePanel gamePanel) {
        super(gamePanel);
        movingSpeed = 7;
        setImage();
    }

    private void setImage() {
        try {
            bufferedImage = ImageIO.read(new FileInputStream("resources/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBullet(int locationX, int locationY, Direction direction, boolean active, boolean shotByPlayer) {
        this.positionX = locationX;
        this.positionY = locationY;
        this.direction = direction;
        this.canMove = active;
        this.shotByPlayer = shotByPlayer;
    }

    @Override
    public void update(){
        if (shotByPlayerHitTheEnemy()) return;
        if (shotByEnemyHitThePlayer()) return;

        canMove = gamePanel.getCollision().checkCharacterCollisionWithTile(this);

        direction.move(this);
    }

    private boolean shotByEnemyHitThePlayer() {
        if(shotByPlayer) return false;

        int playerIndex = gamePanel.getCollision().getObjectIndexByCheckingCollisionBetweenCharacterAndCharacters(this, gamePanel.players);
        if (playerIndex != -1) {
            gamePanel.setGameOver(true);
            return true;
        }

        return false;
    }

    private boolean shotByPlayerHitTheEnemy() {
        if(!shotByPlayer) return false;

        int enemyIndex = gamePanel.getCollision().getObjectIndexByCheckingCollisionBetweenCharacterAndCharacters(this, gamePanel.enemies);
        if (enemyIndex != -1) {
            gamePanel.setScore(gamePanel.getScore() + 1);
            gamePanel.enemies.remove(enemyIndex);
            gamePanel.addNewEnemy();
            canMove = false;
            return true;
        }

        return false;
    }

    @Override
    public void paint(Graphics g){
        g.drawImage(bufferedImage, positionX, positionY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}
