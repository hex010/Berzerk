import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class GamePanel extends JPanel implements Runnable, ActionListener {
    final int originalTileSize = 16;
    final int scale = 2;
    final int tileSize = originalTileSize * scale;
    final int maxScreenColumn = 28;
    final int maxScreenRow = 22;
    final int screenWidth = tileSize * maxScreenColumn;
    final int screenHeight = tileSize * maxScreenRow;
    final int fpsCount = 60;
    final int oneSecondInNanoTime = 1000000000;

    JButton playAgainButton;
    JButton quitGameButton;
    Boolean gameOver = false;

    BufferedImage berzerkPlayerImage;
    BufferedImage wallImage;
    BufferedImage bulletImage;
    BufferedImage enemyImage;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    Rectangle playerRectangle;
    int defaultPlayerRectangleX;
    int defaultPlayerRectangleY;

    Rectangle enemyRectangle;
    int defaultEnemyRectangleX;
    int defaultEnemyRectangleY;

    Rectangle enemy2Rectangle;
    int defaultEnemy2RectangleX;
    int defaultEnemy2RectangleY;

    int playerX = 150;
    int playerY = 150;
    int playerSpeed = 3; //reiksme pikseliais

    int bulletX = 0;
    int bulletY = 0;
    int bulletSpeed = 10;

    int enemy1X = 3 * tileSize;
    int enemy1Y = 18 * tileSize;

    int enemy2X = 16 * tileSize;
    int enemy2Y = 5 * tileSize;

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
             enemyImage = ImageIO.read(new FileInputStream("resources/enemy.gif"));
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

        playerRectangle = new Rectangle();
        playerRectangle.x = 0;
        playerRectangle.y = 0;
        defaultPlayerRectangleX = playerRectangle.x;
        defaultPlayerRectangleY = playerRectangle.y;
        playerRectangle.width = tileSize;
        playerRectangle.height = tileSize;

        enemyRectangle = new Rectangle();
        enemyRectangle.x = 0;
        enemyRectangle.y = 0;
        defaultEnemyRectangleX = enemyRectangle.x;
        defaultEnemyRectangleY = enemyRectangle.y;
        enemyRectangle.height = tileSize;
        enemyRectangle.width = tileSize;

        enemy2Rectangle = new Rectangle();
        enemy2Rectangle.x = 0;
        enemy2Rectangle.y = 0;
        defaultEnemy2RectangleX = enemyRectangle.x;
        defaultEnemy2RectangleY = enemyRectangle.y;
        enemy2Rectangle.height = tileSize;
        enemy2Rectangle.width = tileSize;

        playAgainButton = new JButton();
        playAgainButton.addActionListener(this);
        playAgainButton.setVisible(false);
        this.add(playAgainButton);

        quitGameButton = new JButton();
        quitGameButton.addActionListener(this);
        quitGameButton.setVisible(false);
        this.add(quitGameButton);
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
        if(gameOver) return;


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

            if(map[topPlayerRow][leftPlayerCol] == 0 && map[topPlayerRow][rightPlayerCol] == 0)
                playerY -= playerSpeed;
        } else if(keyHandler.downPressed){
            bottomPlayerRow = (bottomPlayerY + playerSpeed) / tileSize;

            if(map[bottomPlayerRow][leftPlayerCol] == 0 && map[bottomPlayerRow][rightPlayerCol] == 0)
                playerY += playerSpeed;
        } else if(keyHandler.leftPressed){
            leftPlayerCol = (leftPlayerX - playerSpeed) / tileSize;

            if(map[topPlayerRow][leftPlayerCol] == 0 && map[bottomPlayerRow][leftPlayerCol] == 0)
                playerX -= playerSpeed;
        } else if(keyHandler.rightPressed){
            rightPlayerCol = (rightPlayerX + playerSpeed) / tileSize;

            if(map[topPlayerRow][rightPlayerCol] == 0 && map[bottomPlayerRow][rightPlayerCol] == 0)
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
        playerRectangle.x = playerX + playerRectangle.x;
        playerRectangle.y = playerY + playerRectangle.y;

        enemyRectangle.x = enemy1X + enemyRectangle.x;
        enemyRectangle.y = enemy1Y + enemyRectangle.y;

        enemy2Rectangle.x = enemy2X + enemy2Rectangle.x;
        enemy2Rectangle.y = enemy2Y + enemy2Rectangle.y;

        if(keyHandler.upPressed) {
            playerRectangle.y -= playerSpeed;
            if(playerRectangle.intersects(enemyRectangle)){
                gameOver = true;
                System.out.println("up collision for enemy 1");
            }
            if(playerRectangle.intersects(enemy2Rectangle)){
                gameOver = true;
                System.out.println("up collision for enemy 2");
            }
        }else if(keyHandler.downPressed){
            playerRectangle.y += playerSpeed;
            if(playerRectangle.intersects(enemyRectangle)){
                gameOver = true;
                System.out.println("down collision for enemy 1");
            }
            if(playerRectangle.intersects(enemy2Rectangle)){
                gameOver = true;
                System.out.println("down collision for enemy 2");
            }
        }else if(keyHandler.leftPressed){
            playerRectangle.x -= playerSpeed;
            if(playerRectangle.intersects(enemyRectangle)){
                gameOver = true;
                System.out.println("left collision for enemy 1");
            }
            if(playerRectangle.intersects(enemy2Rectangle)){
                gameOver = true;
                System.out.println("left collision for enemy 2");
            }
        }else if(keyHandler.rightPressed){
            playerRectangle.x += playerSpeed;
            if(playerRectangle.intersects(enemyRectangle)){
                gameOver = true;
                System.out.println("right collision for enemy 1");

            }
            if(playerRectangle.intersects(enemy2Rectangle)){
                gameOver = true;
                System.out.println("right collision for enemy 2");
            }
        }

        playerRectangle.x = defaultPlayerRectangleX;
        playerRectangle.y = defaultPlayerRectangleY;
        enemyRectangle.x = defaultEnemyRectangleX;
        enemyRectangle.y = defaultEnemyRectangleY;
        enemy2Rectangle.x = defaultEnemy2RectangleX;
        enemy2Rectangle.y = defaultEnemy2RectangleY;

    }

    public void paint(Graphics g){
        super.paint(g);
        if(gameOver){
            g.setColor(Color.red);
            g.setFont(new Font("MS Mincho", Font.PLAIN, 50));
            g.drawString("GAME OVER", screenWidth/3, screenHeight/3);

            playAgainButton.setText("Play again");
            playAgainButton.setSize(200, 50);
            playAgainButton.setLocation(screenWidth/3 + 50, screenHeight/3 + 50);
            playAgainButton.setBackground(Color.white);

            quitGameButton.setText("Quit");
            quitGameButton.setSize(100, 50);
            quitGameButton.setLocation(screenWidth/3 + 100, screenHeight/3 + 120);
            quitGameButton.setBackground(Color.white);

            playAgainButton.setVisible(true);
            quitGameButton.setVisible(true);
        }else {
            g.drawImage(berzerkPlayerImage, playerX, playerY, tileSize, tileSize, null);

            int x = 0;
            int y = 0;
            for (int i = 0; i < maxScreenRow; i++) {
                for (int j = 0; j < maxScreenColumn; j++) {
                    if (map[i][j] == 1) {
                        g.drawImage(wallImage, x, y, tileSize, tileSize, null);
                    }
                    x += tileSize;
                }
                y += tileSize;
                x = 0;
            }

            if (keyHandler.bulletActive) {
                g.drawImage(bulletImage, bulletX, bulletY, tileSize, tileSize, null);
            }

            g.drawImage(enemyImage, enemy1X, enemy1Y, tileSize, tileSize, null);
            g.drawImage(enemyImage, enemy2X, enemy2Y, tileSize, tileSize, null);

        }

        g.dispose();

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(e.getActionCommand(), "Play again")){
            playerX = 150;
            playerY = 150;
            playAgainButton.setVisible(false);
            quitGameButton.setVisible(false);
            gameOver = false;
        } else if(Objects.equals(e.getActionCommand(), "Quit")){
            System.exit(0);
        }
    }
}
