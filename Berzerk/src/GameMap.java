import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GameMap {
    GamePanel gamePanel;
    Tile[][] tiles;

    public GameMap(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[gamePanel.getMaxScreenRow()][gamePanel.getMaxScreenColumn()];
        readMap();
    }

    private void readMap() {
        try {
            int x = 0;
            int y = 0;
            Scanner input = new Scanner(new File("resources/map.txt"));
            for(int i = 0; i < gamePanel.getMaxScreenRow(); i++){
                for(int j = 0; j < gamePanel.getMaxScreenColumn(); j++){
                    if(input.hasNextInt()){
                        int tileType = input.nextInt();
                        addTile(x, y, i, j, tileType);
                        x += gamePanel.getTileSize();
                    }
                }
                y += gamePanel.getTileSize();
                x = 0;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addTile(int x, int y, int i, int j, int tileType) throws IOException {
        if (tileType == 1) {
            BufferedImage tileImage = ImageIO.read(new FileInputStream("resources/wall.bmp"));
            tiles[i][j] = new Tile(x, y, tileImage, true);
        } else {
            tiles[i][j] = new Tile(x, y, null, false);
        }
    }


    public void paint(Graphics g) {
        for (int i = 0; i < gamePanel.getMaxScreenRow(); i++) {
            for (int j = 0; j < gamePanel.getMaxScreenColumn(); j++) {
                g.drawImage(tiles[i][j].getBufferedImage(), tiles[i][j].getX(), tiles[i][j].getY(), gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            }
        }
    }
}
