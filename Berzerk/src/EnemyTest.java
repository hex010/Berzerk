import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnemyTest {
    @Test
    public void enemyDirectionShouldBeChangedToOpposite() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        GamePanel gamePanel = new GamePanel();
        Enemy enemy = new Enemy(gamePanel);
        enemy.direction = Direction.LEFT;

        Method method = Enemy.class.getDeclaredMethod("changeDirection");
        method.setAccessible(true);
        method.invoke(enemy);

        Assert.assertEquals(Direction.RIGHT, enemy.getDirection());
    }

    @Test
    public void playerShouldBeToMyRight() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        GamePanel gamePanel = new GamePanel();
        gamePanel.getPlayer().positionX = 22;
        Enemy enemy = new Enemy(gamePanel);
        enemy.positionX = 20;

        Method method = Enemy.class.getDeclaredMethod("ThePlayerIsToMyRight");
        method.setAccessible(true);
        boolean methodResult = (boolean) method.invoke(enemy);

        Assert.assertEquals(true,methodResult);
    }

    @Test
    public void PlayerAndIShouldBeOnTheSameXAxis() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        GamePanel gamePanel = new GamePanel();
        gamePanel.getPlayer().positionY = 128; //128-159 tinka
        Enemy enemy = new Enemy(gamePanel);
        enemy.positionY = 150;

        Method method = Enemy.class.getDeclaredMethod("IAndPlayerOnTheSameXAxis");
        method.setAccessible(true);
        boolean methodResult = (boolean) method.invoke(enemy);

        Assert.assertEquals(true,methodResult);
    }
}
