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
    BufferedImage bulletImage;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    int playerX = 150;
    int playerY = 150;
    int playerSpeed = 3; //reiksme pikseliais

    int bulletX = 0;
    int bulletY = 0;
    int bulletSpeed = 10;


    int[][] map = new int[maxScreenRow][maxScreenColumn];

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        try {
             berzerkPlayerImage = ImageIO.read(new FileInputStream("resources/berzerkPlayer.png"));
             wallImage = ImageIO.read(new FileInputStream("resources/wall.bmp"));
            bulletImage = ImageIO.read(new FileInputStream("resources/bullet.png"));
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
        int leftPlayerX = playerX;
        int rightPlayerX = playerX + tileSize; // x pos + width
        int topPlayerY = playerY;
        int bottomPlayerY = playerY + tileSize; //y pos + height

        int leftPlayerCol = leftPlayerX / tileSize;
        int rightPlayerCol = rightPlayerX / tileSize;
        int topPlayerRow = topPlayerY / tileSize;
        int bottomPlayerRow = bottomPlayerY / tileSize;

        int leftBulletX = bulletX;
        int rightBulletX = bulletX + tileSize; // x pos + width
        int topBulletY = bulletY;
        int bottomBulletY = bulletY + tileSize; //y pos + height

        int leftBulletCol = leftBulletX / tileSize;
        int rightBulletCol = rightBulletX / tileSize;
        int topBulletRow = topBulletY / tileSize;
        int bottomBulletRow = bottomBulletY / tileSize;

        if(keyHandler.upPressed){
            topPlayerRow = (topPlayerY - playerSpeed) / tileSize;

            if(map[topPlayerRow][leftPlayerCol] != 1 && map[topPlayerRow][rightPlayerCol] != 1)
                playerY -= playerSpeed;
        } else if(keyHandler.downPressed){
            bottomPlayerRow = (bottomPlayerY + playerSpeed) / tileSize;

            if(map[bottomPlayerRow][leftPlayerCol] != 1 && map[bottomPlayerRow][rightPlayerCol] != 1)
                playerY += playerSpeed;
        } else if(keyHandler.leftPressed){
            leftPlayerCol = (leftPlayerX - playerSpeed) / tileSize;

            if(map[topPlayerRow][leftPlayerCol] != 1 && map[bottomPlayerRow][leftPlayerCol] != 1)
                playerX -= playerSpeed;
        } else if(keyHandler.rightPressed){
            rightPlayerCol = (rightPlayerX + playerSpeed) / tileSize;

            if(map[topPlayerRow][rightPlayerCol] != 1 && map[bottomPlayerRow][rightPlayerCol] != 1)
                playerX += playerSpeed;
        }else if(keyHandler.shootPressed && ! keyHandler.bulletActive){
            bulletX = playerX;
            bulletY = playerY;
            keyHandler.bulletActive = true;
        }

        if(keyHandler.bulletActive){
            if(keyHandler.bulletDirectionUp){
                topBulletRow = (topBulletY - bulletSpeed) / tileSize;

                if(map[topBulletRow][leftBulletCol] != 1 && map[topBulletRow][rightBulletCol] != 1)
                    bulletY -= bulletSpeed;
                else{
                    keyHandler.bulletActive = false;
                }
            }else if(keyHandler.bulletDirectionDown){
                bottomBulletRow = (bottomBulletY + bulletSpeed) / tileSize;

                if(map[bottomBulletRow][leftBulletCol] != 1 && map[bottomBulletRow][rightBulletCol] != 1)
                    bulletY += bulletSpeed;
                else{
                    keyHandler.bulletActive = false;
                }
            }else if(keyHandler.bulletDirectionLeft){
                leftBulletCol = (leftBulletX - bulletSpeed) / tileSize;

                if(map[topBulletRow][leftBulletCol] != 1 && map[bottomBulletRow][leftBulletCol] != 1)
                    bulletX -= bulletSpeed;
                else{
                    keyHandler.bulletActive = false;
                }
            } else if(keyHandler.bulletDirectionRight){
                rightBulletCol = (rightBulletX + bulletSpeed) / tileSize;

                if(map[topBulletRow][rightBulletCol] != 1 && map[bottomBulletRow][rightBulletCol] != 1)
                    bulletX += bulletSpeed;
                else{
                    keyHandler.bulletActive = false;
                }
            } else{
                rightBulletCol = (rightBulletX + bulletSpeed) / tileSize;

                if(map[topBulletRow][rightBulletCol] != 1 && map[bottomBulletRow][rightBulletCol] != 1)
                    bulletX += bulletSpeed;
                else{
                    keyHandler.bulletActive = false;
                }
            }
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

        if(keyHandler.bulletActive){
            g.drawImage(bulletImage, bulletX, bulletY, tileSize, tileSize, null);
        }

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

}
