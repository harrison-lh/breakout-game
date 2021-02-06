package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import java.util.Random;

/**
 * The Breakout Game, made using JavaFX, pits a player armed with a paddle, a ball, and occasional
 * lasers to break blocks and advance through the three levels. Players can move the paddle with
 * arrow keys to keep the ball afloat for as long as possible.
 *
 * @author harrisonh
 */
public class BreakoutGame extends GameWorld{

    private Block[] blocks;
    private Paddle paddle;
    private int remainingLives = 5;
    private Text livesText, levelText, scoreText;
    private int levelNum = 1;
    private boolean gameOver = false;
    private int score = 0;

    private static final int PADDLE_SPEED = 30;
    private static final double WINDOW_WIDTH = 600;
    public static final double WINDOW_HEIGHT = 600;
    private static final double BALL_ACCELERATION = 1.02;
    private static final double MAX_BALL_SPEED_X = 6;
    private static final double MIN_BALL_SPEED_Y = 2;

    private KeyCode lastDirectionInput;
    private static final double TIME_TO_RESET_DIRECTION = 10;
    private double timeSinceDirection = TIME_TO_RESET_DIRECTION;
    private static final double DIRECTION_ROTATION = Math.toRadians(15);

    private boolean sizePowerupActive = false;
    private static final int SIZE_POWERUP_TIME = 1000;
    private int sizePowerupTime = SIZE_POWERUP_TIME;
    private boolean toAddBall = false;

    /**
     * Creates a new Breakout Game.
     * @param fps frames per second
     * @param title the title of the window
     */
    public BreakoutGame(int fps, String title) {
        super(fps, title);
    }

    /**
     * Initializes the window and creates the opening layout.
     * @param primaryStage Stage object
     */
    @Override
    public void initialize(Stage primaryStage) {

        primaryStage.setTitle(getWindowTitle());

        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setScene(getGameSurface());

        addText();
        addPaddle();
        addBall();
        constructLevel(levelNum);

        primaryStage.getScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));

    }

    private void addText() {
        livesText = new Text(550, 20, "Lives: "+remainingLives);
        livesText.setFont(new Font("Verdana", 10));
        levelText = new Text(10, 20, "Level: "+levelNum);
        levelText.setFont(new Font("Verdana", 10));
        scoreText = new Text(290, 20, "Score: "+score);
        scoreText.setFont(new Font("Verdana", 10));

        getSceneNodes().getChildren().add(0, livesText);
        getSceneNodes().getChildren().add(0, levelText);
        getSceneNodes().getChildren().add(0, scoreText);
    }

    private void addPaddle() {

        paddle = new Paddle();
        paddle.setXPos(WINDOW_WIDTH/2);
        paddle.setYPos(WINDOW_HEIGHT*5/6);
        paddle.setSize(100);

        getSpriteManager().addSprites(paddle);
        getSceneNodes().getChildren().add(0, paddle.node);


    }

    private void addBall() {
        Ball ball = new Ball();
        Circle circle = ball.getAsCircle();

        circle.setTranslateX(WINDOW_WIDTH*1/3);
        circle.setTranslateY(WINDOW_HEIGHT*6/12);

        ball.setXVelocity(2.5);
        ball.setYVelocity(2.5);

        getSpriteManager().addSprites(ball);
        getSceneNodes().getChildren().add(0, ball.node);
    }

    private void constructLevel(int levelNum) {
        int rows, cols=8;
        if (levelNum == 1) {
            rows = 5;
            blocks = new Block[rows * cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (i == 2 && j == 3) {
                        blocks[cols * i + j] = new Block(2, 75 * j, 75 + 37.5 * i, 3);
                        getSpriteManager().addSprites(blocks[cols*i + j].getPowerup());
                        getSceneNodes().getChildren().add(0, blocks[cols*i + j].getPowerup().node);
                    }
                    else if (i==2 && j==4) {
                        blocks[cols * i + j] = new ExplodingBlock(2, 75 * j, 75 + 37.5 * i);
                    }
                    else if ((i>=1 && i<=3) && (j>=2 && j<=5)) {
                        blocks[cols * i + j] = new Block(2, 75 * j, 75 + 37.5 * i);
                    }
                    else blocks[cols * i + j] = new Block(1, 75 * j, 75 + 37.5 * i);
                    getSpriteManager().addSprites(blocks[cols * i + j]);
                    getSceneNodes().getChildren().add(0, blocks[cols * i + j].node);
                }
            }
        }
        if (levelNum==2) {
            rows = 6;
            blocks = new Block[rows * cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (i == 2 && j == 3) {
                        blocks[cols * i + j] = new Block(2, 75 * j, 37.5 + 37.5 * i, 1);
                        getSpriteManager().addSprites(blocks[cols*i + j].getPowerup());
                        getSceneNodes().getChildren().add(0, blocks[cols*i + j].getPowerup().node);
                    }
                    else if ((i>=1 && i<=3) && (j>=2 && j<=5)) {
                        blocks[cols * i + j] = new Block(3, 75 * j, 37.5 + 37.5 * i);
                    }
                    else blocks[cols * i + j] = new Block(1, 75 * j, 37.5 + 37.5 * i);
                    getSpriteManager().addSprites(blocks[cols * i + j]);
                    getSceneNodes().getChildren().add(0, blocks[cols * i + j].node);
                }
            }
        }
        if (levelNum==3) {
            Random generator = new Random();
            rows = 7;
            blocks = new Block[rows*cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    double rand = generator.nextDouble();
                    if (rand<.1) {
                        blocks[cols * i + j] = new Block(2, 75 * j, 37.5 + 37.5 * i,
                                generator.nextInt(3)+1);
                        getSpriteManager().addSprites(blocks[cols*i + j].getPowerup());
                        getSceneNodes().getChildren().add(0, blocks[cols*i + j].getPowerup().node);
                    }
                    else if (rand<.4) {
                        blocks[cols * i + j] = new Block(3, 75 * j, 37.5 + 37.5 * i);
                    }
                    else blocks[cols * i + j] = new Block(1, 75 * j, 37.5 + 37.5 * i);
                    getSpriteManager().addSprites(blocks[cols * i + j]);
                    getSceneNodes().getChildren().add(0, blocks[cols * i + j].node);
                }
            }

        }
    }

    /**
     * Resolves issue with the game if certain elements are absent. Adds ball if absent, moves to the
     * next level if all bricks are broken, ends game if the third level is beaten or if the player
     * has no lives remaining.
     */
    @Override
    protected void resetScene() {
        if (gameOver) return;
        removeText();
        addText();

        if (toAddBall) {
            addBall();
            toAddBall = false;
        }

        if (needsBall()) {
            remainingLives--;
            if (remainingLives > 0) {
                addBall();
            }
            else {
                removeText();
                addText();
                gameOverText();
            }
        }
        if (needsBlocks()) {
            clearScene();
            if (levelNum >= 3) {
                youWinText();
                return;
            }
            if (levelNum < 3) {
                levelNum++;
                constructLevel(levelNum);
            }
            addBall();
        }

    }

    private Boolean needsBall() {
        for (Sprite sprite : getSpriteManager().getAllSprites()) {
            if (sprite instanceof Ball) {
                return false;
            }
        }
        return true;
    }
    private Boolean needsBlocks() {
        for (Sprite sprite : getSpriteManager().getAllSprites()) {
            if (sprite instanceof Block) {
                return false;
            }
        }
        return true;
    }

    private void gameOverText() {
        Text gameOverText = new Text(WINDOW_WIDTH/4,WINDOW_HEIGHT/2, "Game Over");
        gameOverText.setFont(new Font("Verdana", 50));
        getSceneNodes().getChildren().add(gameOverText);
        gameOver = true;
    }

    private void removeText() {
        getSceneNodes().getChildren().remove(livesText);
        getSceneNodes().getChildren().remove(levelText);
        getSceneNodes().getChildren().remove(scoreText);
    }

    private void youWinText() {
        Text gameOverText = new Text(WINDOW_WIDTH/4,WINDOW_HEIGHT/2-75, "You Win!");
        gameOverText.setFont(new Font("Verdana", 50));
        getSceneNodes().getChildren().add(gameOverText);
        gameOver = true;
    }

    private void clearScene() {
        for (Sprite sprite : getSpriteManager().getAllSprites()) {
            if (sprite instanceof Laser || sprite instanceof PowerUp ||
                sprite instanceof Ball || sprite instanceof Block) {
                getSpriteManager().addSpritesToBeRemoved(sprite);
                getSceneNodes().getChildren().remove(sprite.node);
            }
        }
    }
    private void clearBalls() {
        for (Sprite sprite : getSpriteManager().getAllSprites()) {
            if (sprite instanceof Ball) {
                getSpriteManager().addSpritesToBeRemoved(sprite);
                getSceneNodes().getChildren().remove(sprite.node);
            }
        }
    }

    /**
     * Updates and/or moves sprites as desired for every frame.
     * Balls reflect off of the window. Power-ups fall, and lasers rise.
     * @param sprite Sprite to be updated
     */
    @Override
    protected void handleUpdate(Sprite sprite) {
        if (sprite instanceof Ball) {
            if (timeSinceDirection < TIME_TO_RESET_DIRECTION) timeSinceDirection++;
            else lastDirectionInput = null;

            if (sizePowerupTime < SIZE_POWERUP_TIME) sizePowerupTime++;
            else if (sizePowerupActive) {
                sizePowerupActive = false;
                paddle.setSize(paddle.getSize() / 1.5);
            }

            Ball ball = (Ball) sprite;

            ball.update();
            if (levelNum >= 2) {
                ball.incrementSize();
            }

            handleBallMovement(ball);
        }
        if (sprite instanceof PowerUp) {
            PowerUp powerup = (PowerUp)sprite;
            powerup.update();
            if (powerup.node.getTranslateY() > WINDOW_HEIGHT) {
                getSpriteManager().addSpritesToBeRemoved(powerup);
            }
        }
        if (sprite instanceof Block) {
            sprite.update();
        }
        if (sprite instanceof Laser) {
            sprite.update();
            if (((Laser)sprite).getLaser().getY() + ((Laser)sprite).getLaser().getHeight() < 0) {
                getSpriteManager().addSpritesToBeRemoved(sprite);
            }
        }
        if (sprite instanceof ExplodingBlock) {
            if (((ExplodingBlock) sprite).isExploded()) {
                getSpriteManager().addSpritesToBeRemoved(sprite);
                getSceneNodes().getChildren().remove(sprite.node);
            }
        }


    }

    private void handleBallMovement(Ball ball) {
        if (ball.node.getTranslateX() + ball.radius >= WINDOW_WIDTH) {
            ball.xVelocity = -1.0 * Math.abs(ball.xVelocity);
        }
        else if (ball.node.getTranslateX() - ball.radius <= 0) {
            ball.xVelocity = Math.abs(ball.xVelocity);
        }
        if (ball.node.getTranslateY() - ball.radius < 0) {
            ball.yVelocity = Math.abs(ball.yVelocity);
        }

        if (ball.node.getTranslateY() + ball.radius >= WINDOW_HEIGHT + 100) {
            getSpriteManager().addSpritesToBeRemoved(ball);
        }
    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            movePaddleRight(code);
        }
        else if (code == KeyCode.LEFT) {
            movePaddleLeft(code);
        }
        else if (code == KeyCode.L && remainingLives>0 && remainingLives<99) {
            remainingLives++;
        }
        else if (code == KeyCode.B && !gameOver) {
            addBall();
        }
        else if (code == KeyCode.S && !gameOver) {
            shootLaser();
        }
        else if (code == KeyCode.DIGIT1) {
            changeToLevel(1);
        }
        else if (code == KeyCode.DIGIT2) {
            changeToLevel(2);
        }
        else if (code == KeyCode.DIGIT3) {
            changeToLevel(3);
        }
        else if (code == KeyCode.R) {
            paddle.setXPos(WINDOW_WIDTH/2);
            clearBalls();
            addBall();
        }
        else if (code == KeyCode.I) {
            increasePaddleSize();
        }
    }

    private void changeToLevel(int i) {
        levelNum = i;
        clearScene();
        constructLevel(i);
        remainingLives++;
    }

    private void movePaddleLeft(KeyCode code) {
        lastDirectionInput = code;
        timeSinceDirection = 0;
        if (paddle.xPos <= 0) {
            paddle.setXPos(getGameSurface().getWidth()-paddle.size);
        }
        else {
            paddle.setXPos(Math.max(0, paddle.getXPos() - PADDLE_SPEED));
        }
    }

    private void movePaddleRight(KeyCode code) {
        lastDirectionInput = code;
        timeSinceDirection = 0;
        if (paddle.xPos >= getGameSurface().getWidth()-paddle.size) {
            paddle.setXPos(0);
        }
        else {
            paddle.setXPos(Math.min(
                getGameSurface().getWidth()-paddle.size,
                paddle.getXPos() + PADDLE_SPEED));
        }
    }

    /**
     * Takes action for collision between any two sprites.
     * @param spriteA - called from checkCollision() method to be compared.
     * @param spriteB - called from checkCollision() method to be compared.
     * @return
     */
    @Override
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        if (spriteA.collide(spriteB)) {
            if (spriteA instanceof Paddle && spriteB instanceof Ball) {
                ballCollidesPaddle((Ball)spriteB, (Paddle)spriteA);
            }
            else if (spriteB instanceof Paddle && spriteA instanceof Ball) {
                ballCollidesPaddle((Ball)spriteA, (Paddle)spriteB);
            }
            else if (spriteA instanceof Ball && spriteB instanceof Block) {
                ballCollidesBlock((Ball)spriteA, (Block)spriteB);
            }
            else if (spriteB instanceof Ball && spriteA instanceof Block) {
                ballCollidesBlock((Ball)spriteB, (Block)spriteA);
            }
            else if (spriteA instanceof Paddle && spriteB instanceof PowerUp) {
                powerUpCollidesPaddle((PowerUp)spriteB, (Paddle)spriteA);
            }
            else if (spriteB instanceof Paddle && spriteA instanceof PowerUp) {
                powerUpCollidesPaddle((PowerUp)spriteA, (Paddle)spriteB);
            }
            else if (spriteA instanceof Block && spriteB instanceof Laser) {
                laserCollidesBlock((Laser)spriteB, (Block)spriteA);
            }
            else if (spriteB instanceof Block && spriteA instanceof Laser) {
                laserCollidesBlock((Laser)spriteA, (Block)spriteB);
            }
            else if (spriteA instanceof ExplodingBlock && spriteB instanceof Block) {
                explodingBlockExplosion((ExplodingBlock) spriteA,(Block) spriteB);
            }
            else if (spriteB instanceof ExplodingBlock && spriteA instanceof Block) {
                explodingBlockExplosion((ExplodingBlock) spriteB,(Block) spriteA);
            }
            return true;
        }
        return false;
    }
    private void ballCollidesPaddle(Ball ball, Paddle paddle) {
        if (ball.collidesUp(paddle)) {
            ball.setYVelocity(-1.0 * BALL_ACCELERATION * Math.abs(ball.yVelocity));
        }
        if (ball.collidesLeftSide(paddle)) {
            ball.setXVelocity(-1.0 * BALL_ACCELERATION * Math.abs(ball.xVelocity));
        }
        else if (ball.collidesRightSide(paddle)) {
            ball.setXVelocity(BALL_ACCELERATION * Math.abs(ball.xVelocity));
        }
        if (lastDirectionInput != null && ball.collidesUp(paddle)) {
            if (lastDirectionInput == KeyCode.LEFT) {
                rotateBallMovement(ball, -1 * DIRECTION_ROTATION);
            }
            else if (lastDirectionInput == KeyCode.RIGHT) {
                rotateBallMovement(ball, DIRECTION_ROTATION);
            }
            lastDirectionInput = null;
        }
    }

    private void rotateBallMovement(Ball ball, double v) {
        double newX = ball.xVelocity * Math.cos(v) -
                ball.yVelocity * Math.sin(v);
        double newY = ball.xVelocity * Math.sin(v) +
                ball.yVelocity * Math.cos(v);
        if (newX > MAX_BALL_SPEED_X || newX < -1 * MAX_BALL_SPEED_X)
            newX = MAX_BALL_SPEED_X * newX / Math.abs(newX);
        ball.setXVelocity(newX);
        ball.setYVelocity(Math.min(newY, -1 * MIN_BALL_SPEED_Y));
    }

    private void ballCollidesBlock(Ball ball, Block block) {
        if (ball.collidesX(block) &&
            (!ball.collidesY(block) || ball.collidesCornerX(block))) {
            ball.setXVelocity(-1.0 * ball.xVelocity);
        }
        else {
            ball.setYVelocity(-1.0 * ball.yVelocity);
        }
        block.health--;
        if (block.health <= 0) {
            block.removeFromScene(this);
            getSpriteManager().addSpritesToBeRemoved(block);
            score++;
        }
    }
    private void powerUpCollidesPaddle(PowerUp powerup, Paddle paddle) {
        getSpriteManager().addSpritesToBeRemoved(powerup);
        getSceneNodes().getChildren().remove(powerup.node);
        if (powerup.type==1) {
            increasePaddleSize();
        }
        if (powerup.type==2) {
            toAddBall = true;
        }
        if (powerup.type==3) {
            shootLaser();
        }
    }

    private void increasePaddleSize() {
        if (!sizePowerupActive) {
            paddle.setSize(paddle.getSize() * 1.5);
            sizePowerupActive = true;
            sizePowerupTime = 0;
        }
    }

    private void shootLaser() {
        Laser laser = new Laser(paddle.xPos+paddle.getSize()/2,paddle.yPos);
        getSceneNodes().getChildren().add(laser.node);
        getSpriteManager().addSprites(laser);
    }

    private void laserCollidesBlock(Laser laser, Block block) {
        block.health = 0;
        block.removeFromScene(this);
        score++;
        if (!(block instanceof ExplodingBlock)) {
            getSpriteManager().addSpritesToBeRemoved(block);
        }
    }

    private void explodingBlockExplosion(ExplodingBlock eblock, Block block) {
        block.removeFromScene(this);
        getSceneNodes().getChildren().remove(block.node);
        getSpriteManager().removeSprites(block);
    }
}
