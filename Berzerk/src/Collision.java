import java.awt.*;
import java.util.ArrayList;

public class Collision {
    private final GamePanel gamePanel;

    protected int leftCharacterX;
    protected int rightCharacterX;
    protected int topCharacterY;
    protected int bottomCharacterY;

    protected int leftCharacterCol;
    protected int rightCharacterCol;
    protected int topCharacterRow;
    protected int bottomCharacterRow;

    protected int characterMovingSpeed;


    public Collision(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean checkCharacterCollisionWithTile(Character character){
        leftCharacterX = character.getPositionX();
        rightCharacterX = character.getPositionX() + gamePanel.getTileSize(); // x pos + width
        topCharacterY = character.getPositionY();
        bottomCharacterY = character.getPositionY() + gamePanel.getTileSize(); //y pos + height

        leftCharacterCol = leftCharacterX / gamePanel.getTileSize();
        rightCharacterCol = rightCharacterX / gamePanel.getTileSize();
        topCharacterRow = topCharacterY / gamePanel.getTileSize();
        bottomCharacterRow = bottomCharacterY / gamePanel.getTileSize();

        characterMovingSpeed = character.getMovingSpeed();

        return character.getDirection().collisionBetweenCharacterAndTile(this);
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

                if(character.getDirection().collisionBetweenTwoRectangles(this, characterRectangle, characterTargetRectangle)){
                    setDefaultCharactersRectangles(characterRectangle, characterTargetRectangle);
                    return true;
                }

                setDefaultCharactersRectangles(characterRectangle, characterTargetRectangle);
                return false;
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

                if(character.getDirection().collisionBetweenTwoRectangles(this, characterRectangle, characterTargetRectangle)){
                    setDefaultCharactersRectangles(characterRectangle, characterTargetRectangle);
                    return index;
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

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
