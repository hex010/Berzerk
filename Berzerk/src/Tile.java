import java.awt.image.BufferedImage;

public class Tile {
    private int x;
    private int y;
    private int type;
    private BufferedImage bufferedImage;
    private boolean hasCollision;

    public Tile(int x, int y, int type, BufferedImage bufferedImage, boolean hasCollision) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.bufferedImage = bufferedImage;
        this.hasCollision = hasCollision;
    }

    public int getX() {
        return x;
    }

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
