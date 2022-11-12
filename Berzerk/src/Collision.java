import java.awt.*;
import java.util.ArrayList;

public class Collision {
    private GamePanel gamePanel;

    public Collision(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean checkCharacterCollisionWithTile(Character character){
        int leftPlayerX = character.getPositionX();
        int rightPlayerX = character.getPositionX() + gamePanel.getTileSize(); // x pos + width
        int topPlayerY = character.getPositionY();
        int bottomPlayerY = character.getPositionY() + gamePanel.getTileSize(); //y pos + height

        int leftPlayerCol = leftPlayerX / gamePanel.getTileSize();
        int rightPlayerCol = rightPlayerX / gamePanel.getTileSize();
        int topPlayerRow = topPlayerY / gamePanel.getTileSize();
        int bottomPlayerRow = bottomPlayerY / gamePanel.getTileSize();

        switch (character.getDirection()) {
            case UP -> {
                topPlayerRow = (topPlayerY - character.getMovingSpeed()) / gamePanel.getTileSize();

                return !gamePanel.getGameMap().tiles[topPlayerRow][leftPlayerCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[topPlayerRow][leftPlayerCol].isHasCollision();
            }
            case DOWN -> {
                bottomPlayerRow = (bottomPlayerY + character.getMovingSpeed()) / gamePanel.getTileSize();

                return !gamePanel.getGameMap().tiles[bottomPlayerRow][leftPlayerCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomPlayerRow][rightPlayerCol].isHasCollision();
            }
            case LEFT -> {
                leftPlayerCol = (leftPlayerX - character.getMovingSpeed()) / gamePanel.getTileSize();

                return !gamePanel.getGameMap().tiles[topPlayerRow][leftPlayerCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomPlayerRow][leftPlayerCol].isHasCollision();
            }
            case RIGHT -> {
                rightPlayerCol = (rightPlayerX + character.getMovingSpeed()) / gamePanel.getTileSize();

                return !gamePanel.getGameMap().tiles[topPlayerRow][rightPlayerCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomPlayerRow][rightPlayerCol].isHasCollision();
            }
        }

        return false;
    }

    public boolean checkCharacterCollisionWithCharacters(Character character, ArrayList<Character> targets) {
        for (Character target : targets) {
            if (target != null) {

                Rectangle characterRectangle = character.getMyRectangle();
                Rectangle characterTargetRectangle = target.getMyRectangle();

                characterRectangle.x += character.getPositionX();
                characterRectangle.y += character.getPositionY();

                characterTargetRectangle.x += target.getPositionX();
                characterTargetRectangle.y += target.getPositionY();

                switch (character.getDirection()) {
                    case UP -> {
                        characterRectangle.y -= character.getMovingSpeed();
                        if (characterRectangle.intersects(characterTargetRectangle)) {
                            return true;
                        }
                    }
                    case DOWN -> {
                        characterRectangle.y += character.getMovingSpeed();
                        if (characterRectangle.intersects(characterTargetRectangle)) {
                            return true;
                        }
                    }
                    case LEFT -> {
                        characterRectangle.x -= character.getMovingSpeed();
                        if (characterRectangle.intersects(characterTargetRectangle)) {
                            return true;
                        }
                    }
                    case RIGHT -> {
                        characterRectangle.x += character.getMovingSpeed();
                        if (characterRectangle.intersects(characterTargetRectangle)) {
                            return true;
                        }
                    }
                }
                setDefaultCharactersRectangles(characterRectangle, characterTargetRectangle);
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

        switch (bullet.getDirection()) {
            case UP -> {
                topBulletRow = (topBulletY - bullet.getMovingSpeed()) / gamePanel.getTileSize();

                return gamePanel.getGameMap().tiles[topBulletRow][leftBulletCol].isHasCollision() ||
                        gamePanel.getGameMap().tiles[topBulletRow][rightBulletCol].isHasCollision();
            }
            case DOWN -> {
                bottomBulletRow = (bottomBulletY + bullet.getMovingSpeed()) / gamePanel.getTileSize();

                return gamePanel.getGameMap().tiles[bottomBulletRow][leftBulletCol].isHasCollision() ||
                        gamePanel.getGameMap().tiles[bottomBulletRow][rightBulletCol].isHasCollision();
            }
            case LEFT -> {
                leftBulletCol = (leftBulletX - bullet.getMovingSpeed()) / gamePanel.getTileSize();

                return gamePanel.getGameMap().tiles[topBulletRow][leftBulletCol].isHasCollision() ||
                        gamePanel.getGameMap().tiles[bottomBulletRow][leftBulletCol].isHasCollision();
            }
            case RIGHT -> {
                rightBulletCol = (rightBulletX + bullet.getMovingSpeed()) / gamePanel.getTileSize();

                return gamePanel.getGameMap().tiles[topBulletRow][rightBulletCol].isHasCollision() ||
                        gamePanel.getGameMap().tiles[bottomBulletRow][rightBulletCol].isHasCollision();
            }
        }

        return false;
    }

    public int checkBulletWithCharacters(Bullet bullet, ArrayList<Character> targets) {
        int index = -1;
        for(Character target : targets){
            index++;
            if(target != null) {
                Rectangle bulletRectangle = bullet.getRectangle();
                Rectangle characterTargetRectangle = target.getMyRectangle();

                bulletRectangle.x += bullet.getPositionX();
                bulletRectangle.y += bullet.getPositionY();

                characterTargetRectangle.x += target.getPositionX();
                characterTargetRectangle.y += target.getPositionY();


                switch (bullet.getDirection()){
                    case UP -> {
                        bulletRectangle.y -= bullet.getMovingSpeed();
                        if (bulletRectangle.intersects(characterTargetRectangle)){
                            return index;
                        }
                    }
                    case DOWN -> {
                        bulletRectangle.y += bullet.getMovingSpeed();
                        if (bulletRectangle.intersects(characterTargetRectangle)) {
                            return index;
                        }
                    }
                    case LEFT -> {
                        bulletRectangle.x -= bullet.getMovingSpeed();
                        if (bulletRectangle.intersects(characterTargetRectangle)) {
                            return index;
                        }
                    }
                    case RIGHT -> {
                        bulletRectangle.x += bullet.getMovingSpeed();
                        if (bulletRectangle.intersects(characterTargetRectangle)) {
                            return index;
                        }
                    }
                }
                setDefaultBulletAndCharacterRectangle(bulletRectangle, characterTargetRectangle);
            }
        }
        return -1;
    }

    private void setDefaultCharactersRectangles(Rectangle characterRectangle, Rectangle characterTargetRectangle) {
        characterRectangle.x = 0;
        characterRectangle.y = 0;

        characterTargetRectangle.x = 0;
        characterTargetRectangle.y = 0;
    }

    private void setDefaultBulletAndCharacterRectangle(Rectangle bulletRectangle, Rectangle characterTargetRectangle) {
        bulletRectangle.x = 0;
        bulletRectangle.y = 0;

        characterTargetRectangle.x = 0;
        characterTargetRectangle.y = 0;
    }
}
