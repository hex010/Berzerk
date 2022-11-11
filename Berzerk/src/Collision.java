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
                    return false;
                else
                    return  true;
            }
            case DOWN: {
                bottomPlayerRow = (bottomPlayerY + player.getMovingSpeed()) / gamePanel.getTileSize();

                if(!gamePanel.getGameMap().tiles[bottomPlayerRow][leftPlayerCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomPlayerRow][rightPlayerCol].isHasCollision())
                    return false;
                else
                    return  true;
            }
            case LEFT: {
                leftPlayerCol = (leftPlayerX - player.getMovingSpeed()) / gamePanel.getTileSize();

                if(!gamePanel.getGameMap().tiles[topPlayerRow][leftPlayerCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomPlayerRow][leftPlayerCol].isHasCollision())
                    return false;
                else
                    return  true;
            }
            case RIGHT: {
                rightPlayerCol = (rightPlayerX + player.getMovingSpeed()) /  gamePanel.getTileSize();

                if(!gamePanel.getGameMap().tiles[topPlayerRow][rightPlayerCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomPlayerRow][rightPlayerCol].isHasCollision())
                    return false;
                else
                    return  true;
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
}
