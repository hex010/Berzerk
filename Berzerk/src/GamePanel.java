import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import java.util.Random;
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

    Random random = new Random();
    int[] enemyPositionsX = new int[7];
    int[] enemyPositionsY = new int[7];

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

    Rectangle enemy1BulletRectangle;
    Rectangle enemy2BulletRectangle;
    int defaultEnemy1BulletRectangleX;
    int defaultEnemy1BulletRectangleY;
    int defaultEnemy2BulletRectangleX;
    int defaultEnemy2BulletRectangleY;

    Rectangle playerBulletRectangle;
    int defaultplayerBulletRectangleX;
    int defaultplayerBulletRectangleY;

    boolean enemyBulletActive = false;
    boolean enemyBulletDirectionUp = false;
    boolean enemyBulletDirectionDown = false;
    boolean enemyBulletDirectionLeft = false;
    boolean enemyBulletDirectionRight = false;
    int enemyBulletX = 0;
    int enemyBulletY = 0;

    int score = 0;

    int playerX = 150;
    int playerY = 150;
    int playerSpeed = 3; //reiksme pikseliais

    int bulletX = 0;
    int bulletY = 0;
    int bulletSpeed = 10;

    int enemy1X = 3 * tileSize;
    int enemy1Y = 18 * tileSize;
    boolean enemy1MustRotate = false;

    int enemy2X = 15 * tileSize;
    int enemy2Y = 3 * tileSize;
    boolean enemy2MustRotate = false;

    int enemySpeed = 4;
    int enemyMoveCooldown = 0;
    int[][] map = new int[maxScreenRow][maxScreenColumn];

    public GamePanel(){
        setWindowParameters();

        loadResources();
        readGameMap();
        setEnemyPositionsArrayValues();
        setRectangleParameters();
        setButtonParameters();
    }

    private void setButtonParameters() {
        playAgainButton = new JButton();
        playAgainButton.addActionListener(this);
        playAgainButton.setVisible(false);
        this.add(playAgainButton);

        quitGameButton = new JButton();
        quitGameButton.addActionListener(this);
        quitGameButton.setVisible(false);
        this.add(quitGameButton);
    }

    private void setRectangleParameters() {
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
        defaultEnemy2RectangleX = enemy2Rectangle.x;
        defaultEnemy2RectangleY = enemy2Rectangle.y;
        enemy2Rectangle.height = tileSize;
        enemy2Rectangle.width = tileSize;

        enemy1BulletRectangle = new Rectangle();
        enemy1BulletRectangle.x = 0;
        enemy1BulletRectangle.y = 0;
        defaultEnemy1BulletRectangleX = enemy1BulletRectangle.x;
        defaultEnemy1BulletRectangleY = enemy1BulletRectangle.y;
        enemy1BulletRectangle.height = tileSize;
        enemy1BulletRectangle.width = tileSize;

        enemy2BulletRectangle = new Rectangle();
        enemy2BulletRectangle.x = 0;
        enemy2BulletRectangle.y = 0;
        defaultEnemy2BulletRectangleX = enemy2BulletRectangle.x;
        defaultEnemy2BulletRectangleY = enemy2BulletRectangle.y;
        enemy2BulletRectangle.height = tileSize;
        enemy2BulletRectangle.width = tileSize;

        playerBulletRectangle = new Rectangle();
        playerBulletRectangle.x = 0;
        playerBulletRectangle.y = 0;
        defaultplayerBulletRectangleX = playerBulletRectangle.x;
        defaultplayerBulletRectangleY = playerBulletRectangle.y;
        playerBulletRectangle.height = tileSize;
        playerBulletRectangle.width = tileSize;
    }

    private void setWindowParameters() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    private void loadResources() {
        try {
             berzerkPlayerImage = ImageIO.read(new FileInputStream("resources/berzerkPlayer.png"));
             wallImage = ImageIO.read(new FileInputStream("resources/wall.bmp"));
             bulletImage = ImageIO.read(new FileInputStream("resources/bullet.png"));
             enemyImage = ImageIO.read(new FileInputStream("resources/enemy.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readGameMap() {
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

    private void setEnemyPositionsArrayValues() {
        enemyPositionsX[0] = 9 * tileSize;
        enemyPositionsY[0] = 12 * tileSize;

        enemyPositionsX[1] = 19 * tileSize;
        enemyPositionsY[1] = 19 * tileSize;

        enemyPositionsX[2] = 13 * tileSize;
        enemyPositionsY[2] = 19 * tileSize;

        enemyPositionsX[3] = 21 * tileSize;
        enemyPositionsY[3] = 11 * tileSize;

        enemyPositionsX[4] = 9 * tileSize;
        enemyPositionsY[4] = 4 * tileSize;

        enemyPositionsX[5] = 3 * tileSize;
        enemyPositionsY[5] = 18 * tileSize;

        enemyPositionsX[6] = 15 * tileSize;
        enemyPositionsY[6] = 3 * tileSize;
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

        int enemyLeftBulletX = enemyBulletX;
        int enemyRightBulletX = enemyBulletX + tileSize; // x pos + width
        int enemyTopBulletY = enemyBulletY;
        int enemyBottomBulletY = enemyBulletY + tileSize; //y pos + height

        int enemyLeftBulletCol = enemyLeftBulletX / tileSize;
        int enemyRightBulletCol = enemyRightBulletX / tileSize;
        int enemyTopBulletRow = enemyTopBulletY / tileSize;
        int enemyBottomBulletRow = enemyBottomBulletY / tileSize;

        playerRectangle.x = playerX + playerRectangle.x;
        playerRectangle.y = playerY + playerRectangle.y;

        enemyRectangle.x = enemy1X + enemyRectangle.x;
        enemyRectangle.y = enemy1Y + enemyRectangle.y;

        enemy2Rectangle.x = enemy2X + enemy2Rectangle.x;
        enemy2Rectangle.y = enemy2Y + enemy2Rectangle.y;

        playerBulletRectangle.x = bulletX + playerBulletRectangle.x;
        playerBulletRectangle.y = bulletY + playerBulletRectangle.y;

        enemy1BulletRectangle.x = enemyBulletX + enemy1BulletRectangle.x;
        enemy1BulletRectangle.y = enemyBulletY + enemy1BulletRectangle.y;

        enemy2BulletRectangle.x = enemyBulletX + enemy2BulletRectangle.x;
        enemy2BulletRectangle.y = enemyBulletY + enemy2BulletRectangle.y;

        movePlayer(leftPlayerX, rightPlayerX, topPlayerY, bottomPlayerY, leftPlayerCol, rightPlayerCol, topPlayerRow, bottomPlayerRow);

        moveEnemies();

        checkCollisionBetweenPlayerAndEnemy();

        checkCollisionOfPlayerBullet(leftBulletX, rightBulletX, topBulletY, bottomBulletY, leftBulletCol, rightBulletCol, topBulletRow, bottomBulletRow);

        checkCollisionOfEnemyBullet(enemyLeftBulletX, enemyRightBulletX, enemyTopBulletY, enemyBottomBulletY, enemyLeftBulletCol, enemyRightBulletCol, enemyTopBulletRow, enemyBottomBulletRow);

        checkIfEnemySeeThePlayer();

        setDefaultRectangleValues();
    }

    private void setDefaultRectangleValues() {
        playerBulletRectangle.x = defaultplayerBulletRectangleX;
        playerBulletRectangle.y = defaultplayerBulletRectangleY;
        enemy1BulletRectangle.x = defaultEnemy1BulletRectangleX;
        enemy1BulletRectangle.y = defaultEnemy1BulletRectangleY;
        enemy2BulletRectangle.x = defaultEnemy2BulletRectangleX;
        enemy2BulletRectangle.y = defaultEnemy2BulletRectangleY;
        playerRectangle.x = defaultPlayerRectangleX;
        playerRectangle.y = defaultPlayerRectangleY;
        enemyRectangle.x = defaultEnemyRectangleX;
        enemyRectangle.y = defaultEnemyRectangleY;
        enemy2Rectangle.x = defaultEnemy2RectangleX;
        enemy2Rectangle.y = defaultEnemy2RectangleY;
    }

    private void checkIfEnemySeeThePlayer() {
        if(playerX / tileSize == enemy1X / tileSize){
            if(playerY > enemy1Y) {
                enemyBulletDirectionDown = true;
                enemyBulletX = enemy1X;
                enemyBulletY = enemy1Y;
                enemyBulletActive = true;
            }
            else{
                enemyBulletDirectionUp = true;
                enemyBulletX = enemy1X;
                enemyBulletY = enemy1Y;
                enemyBulletActive = true;
            }
        }
        if(playerY / tileSize == enemy1Y / tileSize){
            if(playerX > enemy1X) {
                enemyBulletDirectionRight = true;
                enemyBulletX = enemy1X;
                enemyBulletY = enemy1Y;
                enemyBulletActive = true;
            }
            else{
                enemyBulletDirectionLeft = true;
                enemyBulletX = enemy1X;
                enemyBulletY = enemy1Y;
                enemyBulletActive = true;
            }
        }
        if(playerX / tileSize == enemy2X / tileSize){
            if(playerY > enemy2Y) {
                enemyBulletDirectionDown = true;
                enemyBulletX = enemy2X;
                enemyBulletY = enemy2Y;
                enemyBulletActive = true;
            }
            else{
                enemyBulletDirectionUp = true;
                enemyBulletX = enemy2X;
                enemyBulletY = enemy2Y;
                enemyBulletActive = true;
            }
        }
        if(playerY / tileSize == enemy2Y / tileSize){
            if(playerX > enemy2X) {
                enemyBulletDirectionRight = true;
                enemyBulletX = enemy2X;
                enemyBulletY = enemy2Y;
                enemyBulletActive = true;
            }
            else{
                enemyBulletDirectionLeft = true;
                enemyBulletX = enemy2X;
                enemyBulletY = enemy2Y;
                enemyBulletActive = true;
            }
        }
    }

    private void checkCollisionOfEnemyBullet(int enemyLeftBulletX, int enemyRightBulletX, int enemyTopBulletY, int enemyBottomBulletY, int enemyLeftBulletCol, int enemyRightBulletCol, int enemyTopBulletRow, int enemyBottomBulletRow) {
        if(enemyBulletActive){
            if(enemyBulletDirectionUp){
                enemy1BulletRectangle.y -= bulletSpeed;
                enemy2BulletRectangle.y -= bulletSpeed;

                enemyTopBulletRow = (enemyTopBulletY - bulletSpeed) / tileSize;

                if(enemy1BulletRectangle.intersects(playerRectangle)){
                    gameOver = true;
                }
                if(enemy2BulletRectangle.intersects(playerRectangle)){
                    gameOver = true;
                }

                if(map[enemyTopBulletRow][enemyLeftBulletCol] != 1 && map[enemyTopBulletRow][enemyRightBulletCol] != 1)
                    enemyBulletY -= bulletSpeed;
                else{
                    enemyBulletActive = false;
                    enemyBulletDirectionUp = false;
                }
            }else if(enemyBulletDirectionDown){
                enemy1BulletRectangle.y += bulletSpeed;
                enemy2BulletRectangle.y += bulletSpeed;

                enemyBottomBulletRow = (enemyBottomBulletY + bulletSpeed) / tileSize;

                if(enemy1BulletRectangle.intersects(playerRectangle)){
                    gameOver = true;
                }
                if(enemy2BulletRectangle.intersects(playerRectangle)){
                    gameOver = true;
                }

                if(map[enemyBottomBulletRow][enemyLeftBulletCol] != 1 && map[enemyBottomBulletRow][enemyRightBulletCol] != 1)
                    enemyBulletY += bulletSpeed;
                else{
                    enemyBulletActive = false;
                    enemyBulletDirectionDown = false;
                }
            }else if(enemyBulletDirectionLeft){
                enemy1BulletRectangle.x -= bulletSpeed;
                enemy2BulletRectangle.x -= bulletSpeed;

                enemyLeftBulletCol = (enemyLeftBulletX - bulletSpeed) / tileSize;

                if(enemy1BulletRectangle.intersects(playerRectangle)){
                    gameOver = true;
                }
                if(enemy2BulletRectangle.intersects(playerRectangle)){
                    gameOver = true;
                }

                if(map[enemyTopBulletRow][enemyLeftBulletCol] != 1 && map[enemyBottomBulletRow][enemyLeftBulletCol] != 1)
                    enemyBulletX -= bulletSpeed;
                else{
                    enemyBulletActive = false;
                    enemyBulletDirectionLeft = false;
                }
            } else if(enemyBulletDirectionRight){
                enemy1BulletRectangle.x += bulletSpeed;
                enemy2BulletRectangle.x += bulletSpeed;

                enemyRightBulletCol = (enemyRightBulletX + bulletSpeed) / tileSize;

                if(enemy1BulletRectangle.intersects(playerRectangle)){
                    gameOver = true;
                }
                if(enemy2BulletRectangle.intersects(playerRectangle)){
                    gameOver = true;
                }

                if(map[enemyTopBulletRow][enemyRightBulletCol] != 1 && map[enemyBottomBulletRow][enemyRightBulletCol] != 1)
                    enemyBulletX += bulletSpeed;
                else{
                    enemyBulletActive = false;
                    enemyBulletDirectionRight = false;
                }
            }
        }
    }

    private void checkCollisionOfPlayerBullet(int leftBulletX, int rightBulletX, int topBulletY, int bottomBulletY, int leftBulletCol, int rightBulletCol, int topBulletRow, int bottomBulletRow) {
        if(keyHandler.bulletActive){
            if(keyHandler.bulletDirectionUp){
                playerBulletRectangle.y -= bulletSpeed;

                topBulletRow = (topBulletY - bulletSpeed) / tileSize;

                if(playerBulletRectangle.intersects(enemyRectangle)){
                    int randomPosition = random.nextInt(7);
                    enemy1X = enemyPositionsX[randomPosition];
                    enemy1Y = enemyPositionsY[randomPosition];
                    score++;
                }
                if(playerBulletRectangle.intersects(enemy2Rectangle)){
                    int randomPosition = random.nextInt(7);
                    enemy2X = enemyPositionsX[randomPosition];
                    enemy2Y = enemyPositionsY[randomPosition];
                    score++;
                }

                if(map[topBulletRow][leftBulletCol] != 1 && map[topBulletRow][rightBulletCol] != 1)
                    bulletY -= bulletSpeed;
                else{
                    keyHandler.bulletActive = false;
                }
            }else if(keyHandler.bulletDirectionDown){
                playerBulletRectangle.y += bulletSpeed;

                bottomBulletRow = (bottomBulletY + bulletSpeed) / tileSize;

                if(playerBulletRectangle.intersects(enemyRectangle)){
                    int randomPosition = random.nextInt(7);
                    enemy1X = enemyPositionsX[randomPosition];
                    enemy1Y = enemyPositionsY[randomPosition];
                    score++;
                }
                if(playerBulletRectangle.intersects(enemy2Rectangle)){
                    int randomPosition = random.nextInt(7);
                    enemy2X = enemyPositionsX[randomPosition];
                    enemy2Y = enemyPositionsY[randomPosition];
                    score++;
                }

                if(map[bottomBulletRow][leftBulletCol] != 1 && map[bottomBulletRow][rightBulletCol] != 1)
                    bulletY += bulletSpeed;
                else{
                    keyHandler.bulletActive = false;
                }
            }else if(keyHandler.bulletDirectionLeft){
                playerBulletRectangle.x -= bulletSpeed;

                leftBulletCol = (leftBulletX - bulletSpeed) / tileSize;

                if(playerBulletRectangle.intersects(enemyRectangle)){
                    int randomPosition = random.nextInt(7);
                    enemy1X = enemyPositionsX[randomPosition];
                    enemy1Y = enemyPositionsY[randomPosition];
                    score++;
                }
                if(playerBulletRectangle.intersects(enemy2Rectangle)){
                    int randomPosition = random.nextInt(7);
                    enemy2X = enemyPositionsX[randomPosition];
                    enemy2Y = enemyPositionsY[randomPosition];
                    score++;
                }

                if(map[topBulletRow][leftBulletCol] != 1 && map[bottomBulletRow][leftBulletCol] != 1)
                    bulletX -= bulletSpeed;
                else{
                    keyHandler.bulletActive = false;
                }
            } else if(keyHandler.bulletDirectionRight){
                playerBulletRectangle.x += bulletSpeed;

                rightBulletCol = (rightBulletX + bulletSpeed) / tileSize;

                if(playerBulletRectangle.intersects(enemyRectangle)){
                    int randomPosition = random.nextInt(7);
                    enemy1X = enemyPositionsX[randomPosition];
                    enemy1Y = enemyPositionsY[randomPosition];
                    score++;
                }
                if(playerBulletRectangle.intersects(enemy2Rectangle)){
                    int randomPosition = random.nextInt(7);
                    enemy2X = enemyPositionsX[randomPosition];
                    enemy2Y = enemyPositionsY[randomPosition];
                    score++;
                }

                if(map[topBulletRow][rightBulletCol] != 1 && map[bottomBulletRow][rightBulletCol] != 1)
                    bulletX += bulletSpeed;
                else{
                    keyHandler.bulletActive = false;
                }
            } else{
                playerBulletRectangle.x += bulletSpeed;

                rightBulletCol = (rightBulletX + bulletSpeed) / tileSize;

                if(playerBulletRectangle.intersects(enemyRectangle)){
                    int randomPosition = random.nextInt(7);
                    enemy1X = enemyPositionsX[randomPosition];
                    enemy1Y = enemyPositionsY[randomPosition];
                    score++;
                }
                if(playerBulletRectangle.intersects(enemy2Rectangle)){
                    int randomPosition = random.nextInt(7);
                    enemy2X = enemyPositionsX[randomPosition];
                    enemy2Y = enemyPositionsY[randomPosition];
                    score++;
                }

                if(map[topBulletRow][rightBulletCol] != 1 && map[bottomBulletRow][rightBulletCol] != 1)
                    bulletX += bulletSpeed;
                else{
                    keyHandler.bulletActive = false;
                }
            }
        }
    }

    private void checkCollisionBetweenPlayerAndEnemy() {
        if(keyHandler.upPressed) {
            playerRectangle.y -= playerSpeed;
            if(playerRectangle.intersects(enemyRectangle)){
                gameOver = true;
            }
            if(playerRectangle.intersects(enemy2Rectangle)){
                gameOver = true;
            }
        }else if(keyHandler.downPressed){
            playerRectangle.y += playerSpeed;
            if(playerRectangle.intersects(enemyRectangle)){
                gameOver = true;
            }
            if(playerRectangle.intersects(enemy2Rectangle)){
                gameOver = true;
            }
        }else if(keyHandler.leftPressed){
            playerRectangle.x -= playerSpeed;
            if(playerRectangle.intersects(enemyRectangle)){
                gameOver = true;
            }
            if(playerRectangle.intersects(enemy2Rectangle)){
                gameOver = true;
            }
        }else if(keyHandler.rightPressed){
            playerRectangle.x += playerSpeed;
            if(playerRectangle.intersects(enemyRectangle)){
                gameOver = true;

            }
            if(playerRectangle.intersects(enemy2Rectangle)){
                gameOver = true;
            }
        }
    }

    private void moveEnemies() {
        enemyMoveCooldown++;
        if(enemyMoveCooldown == 15) {
            moveEnemyOne();
            moveEnemyTwo();
            enemyMoveCooldown = 0;
        }
    }

    private void moveEnemyTwo() {
        int leftEnemy2X = enemy2X;
        int rightEnemy2X = enemy2X + tileSize; // x pos + width
        int topEnemy2Y = enemy2Y;
        int bottomEnemy2Y = enemy2Y + tileSize; //y pos + height

        int leftEnemy2Col = leftEnemy2X / tileSize;
        int rightEnemy2Col = rightEnemy2X / tileSize;
        int topEnemy2Row = topEnemy2Y / tileSize;
        int bottomEnemy2Row = bottomEnemy2Y / tileSize;

        if(!enemy2MustRotate){
            topEnemy2Row = (topEnemy2Y - enemySpeed) / tileSize;

            if (map[topEnemy2Row][leftEnemy2Col] == 0 && map[topEnemy2Row][rightEnemy2Col] == 0)
                enemy2Y -= enemySpeed;
            else{
                enemy2MustRotate = true;
            }
        }else{
            bottomEnemy2Row = (bottomEnemy2Y + enemySpeed) / tileSize;

            if (map[bottomEnemy2Row][leftEnemy2Col] == 0 && map[bottomEnemy2Row][rightEnemy2Col] == 0)
                enemy2Y += enemySpeed;
            else{
                enemy2MustRotate = false;
            }
        }
    }

    private void moveEnemyOne() {
        int leftEnemy1X = enemy1X;
        int rightEnemy1X = enemy1X + tileSize; // x pos + width
        int topEnemy1Y = enemy1Y;
        int bottomEnemy1Y = enemy1Y + tileSize; //y pos + height

        int leftEnemy1Col = leftEnemy1X / tileSize;
        int rightEnemy1Col = rightEnemy1X / tileSize;
        int topEnemy1Row = topEnemy1Y / tileSize;
        int bottomEnemy1Row = bottomEnemy1Y / tileSize;


        if(!enemy1MustRotate){
            leftEnemy1Col = (leftEnemy1X - enemySpeed) / tileSize;

            if (map[topEnemy1Row][leftEnemy1Col] == 0 && map[bottomEnemy1Row][leftEnemy1Col] == 0)
                enemy1X -= enemySpeed;
            else{
                enemy1MustRotate = true;
            }
        }else{
            rightEnemy1Col = (rightEnemy1X + enemySpeed) / tileSize;

            if (map[topEnemy1Row][rightEnemy1Col] == 0 && map[bottomEnemy1Row][rightEnemy1Col] == 0)
                enemy1X += enemySpeed;
            else{
                enemy1MustRotate = false;
            }
        }
    }

    private void movePlayer(int leftPlayerX, int rightPlayerX, int topPlayerY, int bottomPlayerY, int leftPlayerCol, int rightPlayerCol, int topPlayerRow, int bottomPlayerRow) {
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
    }

    public void paint(Graphics g){
        super.paint(g);

        showScoreLabel(g);

        if(gameOver){
            showGameOverLabel(g);
            showPlayAgainButton();
            showQuitButton();
        }else {
            g.drawImage(berzerkPlayerImage, playerX, playerY, tileSize, tileSize, null);

            drawGameMap(g);

            if (keyHandler.bulletActive)
                g.drawImage(bulletImage, bulletX, bulletY, tileSize, tileSize, null);

            if(enemyBulletActive)
                g.drawImage(bulletImage, enemyBulletX, enemyBulletY, tileSize, tileSize, null);

            g.drawImage(enemyImage, enemy1X, enemy1Y, tileSize, tileSize, null);
            g.drawImage(enemyImage, enemy2X, enemy2Y, tileSize, tileSize, null);
        }

        g.dispose();
    }

    private void showScoreLabel(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("MS Mincho", Font.PLAIN, 20));
        g.drawString("Score: "+ score, 50, screenHeight-50);
    }

    private void showQuitButton() {
        quitGameButton.setText("Quit");
        quitGameButton.setSize(100, 50);
        quitGameButton.setLocation(screenWidth/3 + 100, screenHeight/3 + 120);
        quitGameButton.setBackground(Color.white);
        quitGameButton.setVisible(true);
    }

    private void showPlayAgainButton() {
        playAgainButton.setText("Play again");
        playAgainButton.setSize(200, 50);
        playAgainButton.setLocation(screenWidth/3 + 50, screenHeight/3 + 50);
        playAgainButton.setBackground(Color.white);
        playAgainButton.setVisible(true);
    }

    private void showGameOverLabel(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("MS Mincho", Font.PLAIN, 50));
        g.drawString("GAME OVER", screenWidth/3, screenHeight/3);
    }

    private void drawGameMap(Graphics g) {
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
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(e.getActionCommand(), "Play again")){
            setDefaultGameRules();
        } else if(Objects.equals(e.getActionCommand(), "Quit")){
            System.exit(0);
        }
    }

    private void setDefaultGameRules() {
        playerX = 150;
        playerY = 150;
        playAgainButton.setVisible(false);
        quitGameButton.setVisible(false);
        keyHandler.bulletActive = false;
        keyHandler.bulletDirectionUp = false;
        keyHandler.bulletDirectionLeft = false;
        keyHandler.bulletDirectionDown = false;
        keyHandler.bulletDirectionRight = false;
        enemyBulletActive = false;
        enemyBulletDirectionRight = false;
        enemyBulletDirectionLeft = false;
        enemyBulletDirectionDown = false;
        enemyBulletDirectionUp = false;
        enemy1X = 3 * tileSize;
        enemy1Y = 18 * tileSize;
        enemy2X = 15 * tileSize;
        enemy2Y = 3 * tileSize;
        score = 0;
        gameOver = false;
    }
}
