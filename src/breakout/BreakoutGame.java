package breakout;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import static javafx.animation.Animation.Status.RUNNING;
import static javafx.animation.Animation.Status.STOPPED;

public class BreakoutGame extends GameWorld{

    private Block[] blocks;
    private Paddle paddle;
    private static final int PADDLE_SPEED = 30;
    private static final double WINDOW_WIDTH = 600;
    public static final double WINDOW_HEIGHT = 600;
    private static final double BALL_ACCELERATION = 1.02;
    private static final double MAX_BALL_SPEED_X = 6;
    private static final double MIN_BALL_SPEED_Y = 2;

    private KeyCode lastDirectionInput;
    private static final double TIME_TO_RESET_DIRECTION = 10;
    private double timeSinceDirection = TIME_TO_RESET_DIRECTION;
    private static final double DIRECTION_ROTATION = Math.toRadians(7.5);

    public BreakoutGame(int fps, String title) {
        super(fps, title);
    }


    @Override
    public void initialize(Stage primaryStage) {

        primaryStage.setTitle(getWindowTitle());

        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setScene(getGameSurface());

        addPaddle();
        addBall();
        constructLevelOne();

        final Timeline gameloop = getGameLoop();

        primaryStage.getScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));

    }
    private void addPaddle() {
        Scene gameSurface = getGameSurface();

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

    private void constructLevelOne() {
        blocks = new Block[8*5];
        for (int i=0; i<5; i++) {
            for (int j = 0; j < 8; j++) {
                if (i==3 && j==4) {
                    PowerUp powerup = new PowerUp(75*j,75+37.5*i);
                    blocks[8*3 + 4] = new Block(1,75*j,75+37.5*i, powerup);
                    getSpriteManager().addSprites(blocks[28].getPowerup());
                    getSceneNodes().getChildren().add(0, blocks[28].getPowerup().node);
                }
                else blocks[8 * i + j] = new Block(1, 75 * j, 75 + 37.5 * i);
                if (i == 0) blocks[8 * i + j].health = 2;
                getSpriteManager().addSprites(blocks[8 * i + j]);
                getSceneNodes().getChildren().add(0, blocks[8 * i + j].node);
            }
        }
    }

    @Override
    protected void resetScene() {
        boolean needsBall = true;
        for (Sprite sprite : getSpriteManager().getAllSprites()) {
            if (sprite instanceof Ball) {
                needsBall = false;
                break;
            }
        }
        if (needsBall) addBall();
    }

    @Override
    protected void handleUpdate(Sprite sprite) {
        if (sprite instanceof Ball) {
            if (timeSinceDirection < TIME_TO_RESET_DIRECTION) timeSinceDirection++;
            else lastDirectionInput = null;

            Ball ball = (Ball) sprite;

            ball.update();

            if (ball.node.getTranslateX() + ball.radius >= WINDOW_WIDTH) {
                ball.xVelocity = -1.0 * Math.abs(ball.xVelocity);
            }
            else if (ball.node.getTranslateX() - ball.radius <= 0) {
                ball.xVelocity = Math.abs(ball.xVelocity);
            }
            if (ball.node.getTranslateY() - ball.radius < 0) {
                ball.yVelocity = Math.abs(ball.yVelocity);
            }
            // Testing purposes only
            else if (ball.node.getTranslateY() + ball.radius >= WINDOW_HEIGHT + 200) {
                ball.yVelocity = -1.0 * Math.abs(ball.yVelocity);
            }
            if (ball.node.getTranslateY() + ball.radius >= WINDOW_HEIGHT + 100) {
                getSpriteManager().addSpritesToBeRemoved(sprite);
                // need to add ball
            }
        }
        if (sprite instanceof PowerUp) {
            PowerUp powerup = (PowerUp)sprite;
            powerup.update();
            if (powerup.node.getTranslateY() > WINDOW_HEIGHT) {
                getSpriteManager().addSpritesToBeRemoved(powerup);
            }
        }
        if (sprite instanceof Block) {
            ((Block)sprite).update();
        }


    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
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
        else if (code == KeyCode.LEFT) {
            lastDirectionInput = code;
            timeSinceDirection = 0;
            if (paddle.xPos <= 0) {
                paddle.setXPos(getGameSurface().getWidth()-paddle.size);
            }
            else {
                paddle.setXPos(Math.max(0, paddle.getXPos() - PADDLE_SPEED));
            }
        }
    }

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
        if (lastDirectionInput != null) {
            if (lastDirectionInput == KeyCode.LEFT) {
                double newX = ball.xVelocity*Math.cos(-1 * DIRECTION_ROTATION) -
                        ball.yVelocity*Math.sin(-1 * DIRECTION_ROTATION);
                double newY = ball.xVelocity*Math.sin(-1 * DIRECTION_ROTATION) +
                        ball.yVelocity*Math.cos(-1 * DIRECTION_ROTATION);
                if (newX > MAX_BALL_SPEED_X || newX < -1 * MAX_BALL_SPEED_X)
                    newX = MAX_BALL_SPEED_X * newX / Math.abs(newX);
                ball.setXVelocity(newX);
                ball.setYVelocity(Math.min(newY,-1 * MIN_BALL_SPEED_Y));
            }
            else if (lastDirectionInput == KeyCode.RIGHT) {
                double newX = ball.xVelocity*Math.cos(DIRECTION_ROTATION) -
                        ball.yVelocity*Math.sin(DIRECTION_ROTATION);
                double newY = ball.xVelocity*Math.sin(DIRECTION_ROTATION) +
                        ball.yVelocity*Math.cos(DIRECTION_ROTATION);
                if (newX > MAX_BALL_SPEED_X || newX < -1 * MAX_BALL_SPEED_X)
                    newX = MAX_BALL_SPEED_X * newX / Math.abs(newX);
                ball.setXVelocity(newX);
                ball.setYVelocity(Math.min(newY,-1 * MIN_BALL_SPEED_Y));
            }
            lastDirectionInput = null;
        }
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
        }
    }
    private void powerUpCollidesPaddle(PowerUp powerup, Paddle paddle) {
        getSpriteManager().addSpritesToBeRemoved(powerup);
        getSceneNodes().getChildren().remove(powerup.node);
    }
}
