import org.junit.Assert;
import org.junit.Test;

public class BulletTest {

    @Test
    public void bulletShouldMoveUp(){
        GamePanel gamePanel = new GamePanel();
        Bullet bullet = new Bullet(gamePanel);
        bullet.setBullet(150, 150, Direction.UP, true, true);

        bullet.moveUp();

        Assert.assertEquals(150, bullet.positionX);
        Assert.assertEquals(143, bullet.positionY);
    }
    @Test
    public void bulletShouldMoveDown(){
        GamePanel gamePanel = new GamePanel();
        Bullet bullet = new Bullet(gamePanel);
        bullet.setBullet(150, 150, Direction.DOWN, true, true);

        bullet.moveDown();

        Assert.assertEquals(150, bullet.positionX);
        Assert.assertEquals(157, bullet.positionY);
    }
    @Test
    public void bulletShouldMoveLeft(){
        GamePanel gamePanel = new GamePanel();
        Bullet bullet = new Bullet(gamePanel);
        bullet.setBullet(150, 150, Direction.LEFT, true, true);

        bullet.moveLeft();

        Assert.assertEquals(143, bullet.positionX);
        Assert.assertEquals(150, bullet.positionY);
    }
    @Test
    public void bulletShouldMoveRight(){
        GamePanel gamePanel = new GamePanel();
        Bullet bullet = new Bullet(gamePanel);
        bullet.setBullet(150, 150, Direction.RIGHT, true, true);

        bullet.moveRight();

        Assert.assertEquals(157, bullet.positionX);
        Assert.assertEquals(150, bullet.positionY);
    }
}
