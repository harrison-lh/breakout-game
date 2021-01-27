package breakout;

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

    public BreakoutGame(int fps, String title) {
        super(fps, title);
    }

    @Override
    public void initialize(Stage primaryStage) {

        primaryStage.setTitle(getWindowTitle());

        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), 720, 720));
        primaryStage.setScene(getGameSurface());

        addBall();

        final Timeline gameloop = getGameLoop();


    }
    private void addBall() {
        Scene gameSurface = getGameSurface();
        Ball ball = new Ball();
        Circle circle = ball.getAsCircle();

        circle.setTranslateX(360);
        circle.setTranslateY(200);
        circle.setVisible(true);
        //ball.setId(b.toString());

        ball.setXVelocity(2);
        ball.setYVelocity(2);

        getSpriteManager().addSprites(ball);
        getSceneNodes().getChildren().add(0, ball.node);


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
    }
}
