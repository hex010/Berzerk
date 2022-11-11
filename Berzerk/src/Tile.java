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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public boolean isHasCollision() {
        return hasCollision;
    }

    public void setHasCollision(boolean hasCollision) {
        this.hasCollision = hasCollision;
    }
}
