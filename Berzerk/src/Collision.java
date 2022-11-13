import java.awt.*;
import java.util.ArrayList;

public class Collision {
    private final GamePanel gamePanel;

    public Collision(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean checkCharacterCollisionWithTile(Character character){
        int leftCharacterX = character.getPositionX();
        int rightCharacterX = character.getPositionX() + gamePanel.getTileSize(); // x pos + width
        int topCharacterY = character.getPositionY();
        int bottomCharacterY = character.getPositionY() + gamePanel.getTileSize(); //y pos + height

        int leftCharacterCol = leftCharacterX / gamePanel.getTileSize();
        int rightCharacterCol = rightCharacterX / gamePanel.getTileSize();
        int topCharacterRow = topCharacterY / gamePanel.getTileSize();
        int bottomCharacterRow = bottomCharacterY / gamePanel.getTileSize();

        switch (character.getDirection()) {
            case UP -> {
                topCharacterRow = (topCharacterY - character.getMovingSpeed()) / gamePanel.getTileSize();

                return !gamePanel.getGameMap().tiles[topCharacterRow][leftCharacterCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[topCharacterRow][rightCharacterCol].isHasCollision();
            }
            case DOWN -> {
                bottomCharacterRow = (bottomCharacterY + character.getMovingSpeed()) / gamePanel.getTileSize();

                return !gamePanel.getGameMap().tiles[bottomCharacterRow][leftCharacterCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomCharacterRow][rightCharacterCol].isHasCollision();
            }
            case LEFT -> {
                leftCharacterCol = (leftCharacterX - character.getMovingSpeed()) / gamePanel.getTileSize();

                return !gamePanel.getGameMap().tiles[topCharacterRow][leftCharacterCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomCharacterRow][leftCharacterCol].isHasCollision();
            }
            case RIGHT -> {
                rightCharacterCol = (rightCharacterX + character.getMovingSpeed()) / gamePanel.getTileSize();

                return !gamePanel.getGameMap().tiles[topCharacterRow][rightCharacterCol].isHasCollision() &&
                        !gamePanel.getGameMap().tiles[bottomCharacterRow][rightCharacterCol].isHasCollision();
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

    public int getObjectIndexByCheckingCollisionBetweenCharacterAndCharacters(Character character, ArrayList<Character> targets) {
        int index = -1;
        for(Character target : targets){
            index++;
            if(target != null) {
                Rectangle characterRectangle = character.getMyRectangle();
                Rectangle characterTargetRectangle = target.getMyRectangle();

                characterRectangle.x += character.getPositionX();
                characterRectangle.y += character.getPositionY();

                characterTargetRectangle.x += target.getPositionX();
                characterTargetRectangle.y += target.getPositionY();


                switch (character.getDirection()){
                    case UP -> {
                        characterRectangle.y -= character.getMovingSpeed();
                        if (characterRectangle.intersects(characterTargetRectangle)){
                            return index;
                        }
                    }
                    case DOWN -> {
                        characterRectangle.y += character.getMovingSpeed();
                        if (characterRectangle.intersects(characterTargetRectangle)) {
                            return index;
                        }
                    }
                    case LEFT -> {
                        characterRectangle.x -= character.getMovingSpeed();
                        if (characterRectangle.intersects(characterTargetRectangle)) {
                            return index;
                        }
                    }
                    case RIGHT -> {
                        characterRectangle.x += character.getMovingSpeed();
                        if (characterRectangle.intersects(characterTargetRectangle)) {
                            return index;
                        }
                    }
                }
                setDefaultCharactersRectangles(characterRectangle, characterTargetRectangle);
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
}
