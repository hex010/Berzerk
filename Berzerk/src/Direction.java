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
    }, DOWN{
        @Override
        public void move(Character character){
            character.moveDown();
        }

        @Override
        public Direction opposite() {
            return UP;
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
    }, LEFT{
        @Override
        public void move(Character character){
            character.moveLeft();
        }

        @Override
        public Direction opposite() {
            return RIGHT;
        }
    };

    public abstract void move(Character character);
    public abstract Direction opposite();
}
