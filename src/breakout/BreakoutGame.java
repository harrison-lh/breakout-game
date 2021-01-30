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

    public Paddle paddle;
    private static final int PADDLE_SPEED = 30;
    private static final double WINDOW_WIDTH = 600;
    private static final double WINDOW_HEIGHT = 600;

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
        circle.setTranslateY(WINDOW_HEIGHT/4);

        ball.setXVelocity(3);
        ball.setYVelocity(3);

        getSpriteManager().addSprites(ball);
        getSceneNodes().getChildren().add(0, ball.node);

        paddle = new Paddle();
        paddle.setXPos(WINDOW_WIDTH/2);
        paddle.setYPos(WINDOW_HEIGHT*5/6);
        paddle.setSize(100);

        getSpriteManager().addSprites(paddle);
        getSceneNodes().getChildren().add(0, paddle.node);

        Block block = new Block(1, 200, 200);
        Block block2 = new Block(1, 270, 200);

        getSpriteManager().addSprites(block);
        getSceneNodes().getChildren().add(0, block.node);
        getSpriteManager().addSprites(block2);
        getSceneNodes().getChildren().add(0, block2.node);


    }

    @Override
    protected void handleUpdate(Sprite sprite) {
        if (sprite instanceof Ball) {
            Ball sphere = (Ball) sprite;

            sphere.update();

            if (sphere.node.getTranslateX() > (getGameSurface().getWidth()  -
                    sphere.node.getBoundsInParent().getWidth()/2.0) ||
                    sphere.node.getTranslateX() < sphere.node.getBoundsInParent().getWidth()/2.0 ) {
                sphere.xVelocity = sphere.xVelocity * -1;
            }
            if (sphere.node.getTranslateY() > getGameSurface().getHeight()-
                    sphere.node.getBoundsInParent().getHeight()/2.0 ||
                    sphere.node.getTranslateY() < sphere.node.getBoundsInParent().getWidth()/2.0) {
                sphere.yVelocity = sphere.yVelocity * -1;
            }
        }
        if (sprite.isDead) {
            getSpriteManager().addSpritesToBeRemoved(sprite);
        }
        cleanupSprites();
    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            paddle.setXPos(Math.min(
                    (int)(getGameSurface().getWidth()-paddle.size),
                    paddle.getXPos() + PADDLE_SPEED));
        }
        else if (code == KeyCode.LEFT) {
            paddle.setXPos(Math.max(0, paddle.getXPos() - PADDLE_SPEED));
        }
    }

    @Override
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        if (spriteA.collide(spriteB)) {
            if (spriteA instanceof Paddle && spriteB instanceof Ball) {
                ((Ball)spriteB).setYVelocity(-1 * Math.abs(spriteB.yVelocity));
                if (((Ball)spriteB).collideXToRight((Paddle)spriteA)) {
                    ((Ball)spriteB).setXVelocity(Math.abs(spriteB.xVelocity));
                }
                else {
                    ((Ball)spriteB).setXVelocity(-1 * Math.abs(spriteB.xVelocity));
                }
            }
            else if (spriteA instanceof Ball && spriteB instanceof Paddle) {
                ((Ball)spriteA).setYVelocity(-1 * Math.abs(spriteA.yVelocity));
                if (((Ball)spriteA).collideXToRight((Paddle)spriteB)) {
                    ((Ball)spriteA).setXVelocity(Math.abs(spriteA.xVelocity));
                }
                else {
                    ((Ball)spriteA).setXVelocity(-1 * Math.abs(spriteA.xVelocity));
                }
            }
            else if (spriteA instanceof Ball && spriteB instanceof Block) {
                ((Block)spriteB).health--;
                if (((Block)spriteB).health<=0) {
                    ((Block) spriteB).removeFromScene(this);
                    getSpriteManager().addSpritesToBeRemoved(spriteB);
                }
            }
            else if (spriteB instanceof Ball && spriteA instanceof Block) {
                ((Block)spriteA).health--;
            }
            return true;
        }
        return false;
    }
}
