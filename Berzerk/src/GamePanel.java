import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, ActionListener {
    private final int originalTileSize = 16;
    private final int scale = 2;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenColumn = 28;
    private final int maxScreenRow = 22;
    private final int screenWidth = tileSize * maxScreenColumn;
    private final int screenHeight = tileSize * maxScreenRow;
    private final int fpsCount = 60;
    private final int oneSecondInNanoTime = 1000000000;
    private Thread gameThread;

    private KeyHandler keyHandler = new KeyHandler();;
    private Map gameMap;
    private Collision collision;
    ArrayList<Enemy> enemies;
    Player player;
    ArrayList<Bullet> bullets;


    private Random random;
    private int[] enemyPositionsX;
    private int[] enemyPositionsY;

    private JButton playAgainButton;
    private JButton quitGameButton;
    private boolean gameOver;
    private int score;

    public GamePanel(){
        setWindowParameters();
        setButtonParameters();
        setDefaultGameValues();
    }

    private void setDefaultGameValues() {
        gameMap = new Map(this);
        collision = new Collision(this);
        enemies = new ArrayList<>();
        player = new Player(this, keyHandler);
        bullets = new ArrayList<>();
        random = new Random();
        enemyPositionsX = new int[7];
        enemyPositionsY = new int[7];
        score = 0;
        playAgainButton.setVisible(false);
        quitGameButton.setVisible(false);
        setEnemyPositionsArrayValues();
        addNewEnemies();
        gameOver = false;
    }

    private void addNewEnemies() {
        for(int i = 0; i < 3; i++){
            enemies.add(generateEnemy());
        }
    }

    public void addNewEnemy(){
        enemies.add(generateEnemy());
    }

    private Enemy generateEnemy(){
        int randomPosition = random.nextInt(7);
        Enemy enemy = new Enemy(this);
        enemy.setEnemy(enemyPositionsX[randomPosition], enemyPositionsY[randomPosition]);
        return enemy;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getMaxScreenColumn() {
        return maxScreenColumn;
    }

    public Map getGameMap() {
        return gameMap;
    }

    public Player getPlayer() {
        return player;
    }

    public Collision getCollision() {
        return collision;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
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

    private void setWindowParameters() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
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
        enemyPositionsY[4] = 3 * tileSize;

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
        player.update();

        for(int i = 0; i < bullets.size(); i++){
            if(bullets.get(i) != null){
                if(bullets.get(i).isActive()){
                    bullets.get(i).update();
                }else{
                    bullets.remove(i);
                }
            }
        }

        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i) != null){
                enemies.get(i).update();
            }
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
            player.paint(g);
            gameMap.paint(g);

            for (Bullet bullet : bullets) {
                if (bullet != null) {
                    bullet.paint(g);
                }
            }

            for (Enemy enemy : enemies) {
                if (enemy != null) {
                    enemy.paint(g);
                }
            }
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

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(e.getActionCommand(), "Play again")){
            setDefaultGameValues();
        } else if(Objects.equals(e.getActionCommand(), "Quit")){
            System.exit(0);
        }
    }
}
