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

    private Paddle paddle;
    private static final int PADDLE_SPEED = 30;
    private static final double WINDOW_WIDTH = 600;
    private static final double WINDOW_HEIGHT = 600;
    private static final double BALL_ACCELERATION = 1.02;

    public BreakoutGame(int fps, String title) {
        super(fps, title);
    }

    @Override
    public void initialize(Stage primaryStage) {

        primaryStage.setTitle(getWindowTitle());

        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setScene(getGameSurface());

        addBallAndPaddle();

        final Timeline gameloop = getGameLoop();

        primaryStage.getScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));

    }
    private void addBallAndPaddle() {
        Scene gameSurface = getGameSurface();
        Ball ball = new Ball();
        Circle circle = ball.getAsCircle();

        circle.setTranslateX(WINDOW_WIDTH/2);
        circle.setTranslateY(WINDOW_HEIGHT/12);

        ball.setXVelocity(3);
        ball.setYVelocity(4);

        getSpriteManager().addSprites(ball);
        getSceneNodes().getChildren().add(0, ball.node);

        paddle = new Paddle();
        paddle.setXPos(WINDOW_WIDTH/2);
        paddle.setYPos(WINDOW_HEIGHT*5/6);
        paddle.setSize(100);

        getSpriteManager().addSprites(paddle);
        getSceneNodes().getChildren().add(0, paddle.node);

        Block[] blocks = new Block[8*5];
        for (int i=0; i<5; i++) {
            for (int j=0; j<8; j++) {
                blocks[8*i+j] = new Block(1, 75*j, 75+37.5*i);
                if (i==0) blocks[8*i+j].health = 2;
                getSpriteManager().addSprites(blocks[8*i+j]);
                getSceneNodes().getChildren().add(0,blocks[8*i+j].node);
            }
        }


    }

    @Override
    protected void handleUpdate(Sprite sprite) {
        if (sprite instanceof Ball) {
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
//            else if (ball.node.getTranslateY() + ball.radius >= WINDOW_HEIGHT) {
//                ball.yVelocity = -1.0 * Math.abs(ball.yVelocity);
//            }

        }
        if (sprite.isDead) {
            getSpriteManager().addSpritesToBeRemoved(sprite);
        }
        cleanupSprites();
    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            if (paddle.xPos >= getGameSurface().getWidth()-paddle.size) {
                paddle.setXPos(0);
            }
            else {
                paddle.setXPos(Math.min(
                    getGameSurface().getWidth()-paddle.size,
                    paddle.getXPos() + PADDLE_SPEED));}
        }
        else if (code == KeyCode.LEFT) {
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
}
