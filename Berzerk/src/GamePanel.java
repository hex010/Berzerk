import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 2;
    final int tileSize = originalTileSize * scale;
    final int maxScreenColumn = 28;
    final int maxScreenRow = 22;
    final int screenWidth = tileSize * maxScreenColumn;
    final int screenHeight = tileSize * maxScreenRow;
    final int fpsCount = 60;
    final int oneSecondInNanoTime = 1000000000;

    BufferedImage berzerkPlayerImage;
    BufferedImage wallImage;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    int playerX = 150;
    int playerY = 150;
    int playerSpeed = 3; //reiksme pikseliais

    int map[][] = new int[maxScreenRow][maxScreenColumn];

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        try {
             berzerkPlayerImage = ImageIO.read(new FileInputStream("resources/BerzerkPlayer.png"));
             wallImage = ImageIO.read(new FileInputStream("resources/wall.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Scanner input = new Scanner(new File("resources/map.txt"));
            for(int i = 0; i < maxScreenRow; i++){
                for(int j = 0; j < maxScreenColumn; j++){
                    if(input.hasNextInt()){
                        map[i][j] = input.nextInt();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double drawInterval = oneSecondInNanoTime / (double) fpsCount;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        if(keyHandler.upPressed){
            playerY -= playerSpeed;
        } else if(keyHandler.downPressed){
            playerY += playerSpeed;
        } else if(keyHandler.leftPressed){
            playerX -= playerSpeed;
        } else if(keyHandler.rightPressed){
            playerX += playerSpeed;
        }
    }

    public void paint(Graphics g){
        super.paint(g);

        g.drawImage(berzerkPlayerImage, playerX, playerY, tileSize, tileSize, null);

        int x = 0;
        int y = 0;
        for(int i = 0; i < maxScreenRow; i++){
            for(int j = 0; j < maxScreenColumn; j++) {
                if(map[i][j] == 1){
                    g.drawImage(wallImage, x, y, tileSize, tileSize, null);
                }
                x+=tileSize;
            }
            y+=tileSize;
            x = 0;
        }

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

}
