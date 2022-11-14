import java.awt.*;

public enum Direction {
    UP{
        @Override
        public void move(Character character){
            character.moveUp();
        }

        @Override
        public Direction opposite() {
            return DOWN;
        }

        @Override
        public boolean collisionBetweenCharacterAndTile(Collision collision) {
            collision.topCharacterRow = (collision.topCharacterY - collision.characterMovingSpeed) / collision.getGamePanel().getTileSize();

            return !collision.getGamePanel().getGameMap().tiles[collision.topCharacterRow][collision.leftCharacterCol].isHasCollision() &&
                    !collision.getGamePanel().getGameMap().tiles[collision.topCharacterRow][collision.rightCharacterCol].isHasCollision();
        }

        @Override
        public boolean collisionBetweenTwoRectangles(Collision collision, Rectangle characterRectangle, Rectangle characterTargetRectangle) {
            characterRectangle.y -= collision.characterMovingSpeed;
            return characterRectangle.intersects(characterTargetRectangle);
        }
    }, DOWN{
        @Override
        public void move(Character character){
            character.moveDown();
        }

        @Override
        public Direction opposite() {
            return UP;
        }

        @Override
        public boolean collisionBetweenCharacterAndTile(Collision collision) {
            collision.bottomCharacterRow = (collision.bottomCharacterY + collision.characterMovingSpeed) / collision.getGamePanel().getTileSize();

            return !collision.getGamePanel().getGameMap().tiles[collision.bottomCharacterRow][collision.leftCharacterCol].isHasCollision() &&
                    !collision.getGamePanel().getGameMap().tiles[collision.bottomCharacterRow][collision.rightCharacterCol].isHasCollision();
        }

        @Override
        public boolean collisionBetweenTwoRectangles(Collision collision, Rectangle characterRectangle, Rectangle characterTargetRectangle) {
            characterRectangle.y += collision.characterMovingSpeed;
            return characterRectangle.intersects(characterTargetRectangle);
        }
    }, RIGHT{
        @Override
        public void move(Character character){
            character.moveRight();
        }

        @Override
        public Direction opposite() {
            return LEFT;
        }

        @Override
        public boolean collisionBetweenCharacterAndTile(Collision collision) {
            collision.rightCharacterCol = (collision.rightCharacterX + collision.characterMovingSpeed) / collision.getGamePanel().getTileSize();

            return !collision.getGamePanel().getGameMap().tiles[collision.topCharacterRow][collision.rightCharacterCol].isHasCollision() &&
                    !collision.getGamePanel().getGameMap().tiles[collision.bottomCharacterRow][collision.rightCharacterCol].isHasCollision();
        }

        @Override
        public boolean collisionBetweenTwoRectangles(Collision collision, Rectangle characterRectangle, Rectangle characterTargetRectangle) {
            characterRectangle.x += collision.characterMovingSpeed;
            return characterRectangle.intersects(characterTargetRectangle);
        }
    }, LEFT{
        @Override
        public void move(Character character){
            character.moveLeft();
        }

        @Override
        public Direction opposite() {
            return RIGHT;
        }

        @Override
        public boolean collisionBetweenCharacterAndTile(Collision collision) {
            collision.leftCharacterCol = (collision.leftCharacterX - collision.characterMovingSpeed) / collision.getGamePanel().getTileSize();

            return !collision.getGamePanel().getGameMap().tiles[collision.topCharacterRow][collision.leftCharacterCol].isHasCollision() &&
                    !collision.getGamePanel().getGameMap().tiles[collision.bottomCharacterRow][collision.leftCharacterCol].isHasCollision();
        }

        @Override
        public boolean collisionBetweenTwoRectangles(Collision collision, Rectangle characterRectangle, Rectangle characterTargetRectangle) {
            characterRectangle.x -= collision.characterMovingSpeed;
            return characterRectangle.intersects(characterTargetRectangle);
        }
    };

    public abstract void move(Character character);
    public abstract Direction opposite();

    public abstract boolean collisionBetweenCharacterAndTile(Collision collision);
    public abstract boolean collisionBetweenTwoRectangles(Collision collision, Rectangle characterRectangle, Rectangle characterTargetRectangle);
}
