import java.awt.image.BufferedImage;

public class Tile {
    private final int x;
    private final int y;
    private final BufferedImage bufferedImage;
    private final boolean hasCollision;

    public Tile(int x, int y, BufferedImage bufferedImage, boolean hasCollision) {
        this.x = x;
        this.y = y;
        this.bufferedImage = bufferedImage;
        this.hasCollision = hasCollision;
    }

    public int getX() { return x; }
    public int getY() {
        return y;
    }
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
    public boolean isHasCollision() {
        return hasCollision;
    }
}
