import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void playerShouldMoveUp(){
        GamePanel gamePanel = new GamePanel();
        Player player = new Player(gamePanel);

        player.moveUp();

        Assert.assertEquals(150, player.positionX);
        Assert.assertEquals(147, player.positionY);
    }
    @Test
    public void playerShouldMoveDown(){
        GamePanel gamePanel = new GamePanel();
        Player player = new Player(gamePanel);

        player.moveDown();

        Assert.assertEquals(150, player.positionX);
        Assert.assertEquals(153, player.positionY);
    }
    @Test
    public void playerShouldMoveLeft(){
        GamePanel gamePanel = new GamePanel();
        Player player = new Player(gamePanel);

        player.moveLeft();

        Assert.assertEquals(147, player.positionX);
        Assert.assertEquals(150, player.positionY);
    }
    @Test
    public void playerShouldMoveRight(){
        GamePanel gamePanel = new GamePanel();
        Player player = new Player(gamePanel);

        player.moveRight();

        Assert.assertEquals(153, player.positionX);
        Assert.assertEquals(150, player.positionY);
    }
}
