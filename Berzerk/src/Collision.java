import java.util.ArrayList;

public class Collision {
    private GamePanel gamePanel;

    public Collision(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean checkPlayerCollisionWithTile(Player player){
        int leftPlayerX = player.getPositionX();
        int rightPlayerX = player.getPositionX() + gamePanel.getTileSize(); // x pos + width
        int topPlayerY = player.getPositionY();
        int bottomPlayerY = player.getPositionY() + gamePanel.getTileSize(); //y pos + height

        int leftPlayerCol = leftPlayerX / gamePanel.getTileSize();
        int rightPlayerCol = rightPlayerX / gamePanel.getTileSize();
        int topPlayerRow = topPlayerY / gamePanel.getTileSize();
        int bottomPlayerRow = bottomPlayerY / gamePanel.getTileSize();

        switch (player.getDirection()){
            case UP: {
                topPlayerRow = (topPlayerY - player.getMovingSpeed()) / gamePanel.getTileSize();

                if(!gamePanel.getGameMap().tiles[topPlayerRow][leftPlayerCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[topPlayerRow][leftPlayerCol].isHasCollision())
                    return true;
                else
                    return false;
            }
            case DOWN: {
                bottomPlayerRow = (bottomPlayerY + player.getMovingSpeed()) / gamePanel.getTileSize();

                if(!gamePanel.getGameMap().tiles[bottomPlayerRow][leftPlayerCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomPlayerRow][rightPlayerCol].isHasCollision())
                    return true;
                else
                    return false;
            }
            case LEFT: {
                leftPlayerCol = (leftPlayerX - player.getMovingSpeed()) / gamePanel.getTileSize();

                if(!gamePanel.getGameMap().tiles[topPlayerRow][leftPlayerCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomPlayerRow][leftPlayerCol].isHasCollision())
                    return true;
                else
                    return  false;
            }
            case RIGHT: {
                rightPlayerCol = (rightPlayerX + player.getMovingSpeed()) /  gamePanel.getTileSize();

                if(!gamePanel.getGameMap().tiles[topPlayerRow][rightPlayerCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomPlayerRow][rightPlayerCol].isHasCollision())
                    return true;
                else
                    return false;
            }
        }

        return false;
    }

    public boolean checkBulletCollisionWithTile(Bullet bullet){
        int leftBulletX = bullet.getPositionX();
        int rightBulletX = bullet.getPositionX() + gamePanel.getTileSize(); // x pos + width
        int topBulletY = bullet.getPositionY();
        int bottomBulletY = bullet.getPositionY() + gamePanel.getTileSize(); //y pos + height

        int leftBulletCol = leftBulletX / gamePanel.getTileSize();
        int rightBulletCol = rightBulletX / gamePanel.getTileSize();
        int topBulletRow = topBulletY / gamePanel.getTileSize();
        int bottomBulletRow = bottomBulletY / gamePanel.getTileSize();

        switch (bullet.getDirection()){
            case UP: {
                topBulletRow = (topBulletY - bullet.getMovingSpeed()) / gamePanel.getTileSize();

                if(!gamePanel.getGameMap().tiles[topBulletRow][leftBulletCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[topBulletRow][rightBulletCol].isHasCollision())
                    return false;
                else
                    return  true;
            }
            case DOWN: {
                bottomBulletRow = (bottomBulletY + bullet.getMovingSpeed()) / gamePanel.getTileSize();

                if(!gamePanel.getGameMap().tiles[bottomBulletRow][leftBulletCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomBulletRow][rightBulletCol].isHasCollision())
                    return false;
                else
                    return  true;
            }
            case LEFT: {
                leftBulletCol = (leftBulletX - bullet.getMovingSpeed()) / gamePanel.getTileSize();

                if(!gamePanel.getGameMap().tiles[topBulletRow][leftBulletCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomBulletRow][leftBulletCol].isHasCollision())
                    return false;
                else
                    return  true;
            }
            case RIGHT: {
                rightBulletCol = (rightBulletX + bullet.getMovingSpeed()) / gamePanel.getTileSize();

                if(!gamePanel.getGameMap().tiles[topBulletRow][rightBulletCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomBulletRow][rightBulletCol].isHasCollision())
                    return false;
                else
                    return  true;
            }
        }

        return false;
    }

    public boolean checkEnemyCollisionWithTile(Enemy enemy) {
        int leftEnemy1X = enemy.getPositionX();
        int rightEnemy1X = enemy.getPositionX() + gamePanel.getTileSize(); // x pos + width
        int topEnemy1Y = enemy.getPositionY();
        int bottomEnemy1Y = enemy.getPositionY() + gamePanel.getTileSize(); //y pos + height

        int leftEnemy1Col = leftEnemy1X / gamePanel.getTileSize();
        int rightEnemy1Col = rightEnemy1X / gamePanel.getTileSize();
        int topEnemy1Row = topEnemy1Y / gamePanel.getTileSize();
        int bottomEnemy1Row = bottomEnemy1Y / gamePanel.getTileSize();

        if(!enemy.isMustRotate()){
            leftEnemy1Col = (leftEnemy1X - enemy.getMovingSpeed()) / gamePanel.getTileSize();

            if (!gamePanel.getGameMap().tiles[topEnemy1Row][leftEnemy1Col].isHasCollision() &&
                    !gamePanel.getGameMap().tiles[bottomEnemy1Row][leftEnemy1Col].isHasCollision())
                return false;
            else
                return  true;
        }else{
            rightEnemy1Col = (rightEnemy1X + enemy.getMovingSpeed()) / gamePanel.getTileSize();

            if (!gamePanel.getGameMap().tiles[topEnemy1Row][rightEnemy1Col].isHasCollision() &&
                    !gamePanel.getGameMap().tiles[bottomEnemy1Row][rightEnemy1Col].isHasCollision())
                return false;
            else
                return  true;
        }
    }

    public boolean checkPlayerCollisionWithEnemy(Player player, ArrayList<Enemy> enemies) {
        for(Enemy enemy : enemies){
            if(enemy != null) {
                enemy.rectangle.x += enemy.getPositionX();
                enemy.rectangle.y += enemy.getPositionY();

                player.rectangle.x += player.getPositionX();
                player.rectangle.y += player.getPositionY();

                switch (player.getDirection()){
                    case UP -> {
                        player.rectangle.y -= player.getMovingSpeed();
                        if (player.rectangle.intersects(enemy.rectangle)){
                            return true;
                        }
                    }
                    case DOWN -> {
                        player.rectangle.y += player.getMovingSpeed();
                        if (player.rectangle.intersects(enemy.rectangle)) {
                            return true;
                        }
                    }
                    case LEFT -> {
                        player.rectangle.x -= player.getMovingSpeed();
                        if (player.rectangle.intersects(enemy.rectangle)) {
                            return true;
                        }
                    }
                    case RIGHT -> {
                        player.rectangle.x += player.getMovingSpeed();
                        if (player.rectangle.intersects(enemy.rectangle)) {
                            return true;
                        }
                    }
                }
                setDefaultPlayerAndEnemyRectangle(player, enemy);
            }
        }
        return false;
    }

    public boolean checkEnemyCollisionWithPlayer(Enemy enemy, Player player) {
        if(player != null) {
            enemy.rectangle.x += enemy.getPositionX();
            enemy.rectangle.y += enemy.getPositionY();

            player.rectangle.x += player.getPositionX();
            player.rectangle.y += player.getPositionY();

            switch (player.getDirection()){
                case UP -> {
                    enemy.rectangle.y -= enemy.getMovingSpeed();
                    if (enemy.rectangle.intersects(player.rectangle)){
                        return true;
                    }
                }
                case DOWN -> {
                    enemy.rectangle.y += enemy.getMovingSpeed();
                    if (enemy.rectangle.intersects(player.rectangle)) {
                        return true;
                    }
                }
                case LEFT -> {
                    enemy.rectangle.x -= enemy.getMovingSpeed();
                    if (enemy.rectangle.intersects(player.rectangle)) {
                        return true;
                    }
                }
                case RIGHT -> {
                    enemy.rectangle.x += enemy.getMovingSpeed();
                    if (enemy.rectangle.intersects(player.rectangle)) {
                        return true;
                    }
                }
            }
            setDefaultPlayerAndEnemyRectangle(player, enemy);
        }
        return false;
    }

    private void setDefaultPlayerAndEnemyRectangle(Player player, Enemy enemy) {
        player.rectangle.x = 0;
        player.rectangle.y = 0;

        enemy.rectangle.x = 0;
        enemy.rectangle.y = 0;
    }
}
