package breakout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Ball extends Sprite{

    public double size;

    public Ball() {
        Circle ball = new Circle();
        size = 10;
        ball.setRadius(size);

        node = ball;
    }


    @Override
    public void update() {
        node.setTranslateX(node.getTranslateX() + xVelocity);
        node.setTranslateY(node.getTranslateY() + yVelocity);
    }

    public Circle getAsCircle() {
        return (Circle) node;
    }

    public void setXVelocity(double v) {
        xVelocity = v;
    }
    public void setYVelocity(double v) {
        yVelocity = v;
    }

    @Override
    public boolean collide(Sprite other) {
        if (other instanceof Paddle) {
            return collide((Paddle)other);
        }
        return false;
    }

    private boolean collide(Paddle paddle) {
        double xCenter = node.getTranslateX()+size/2;
        double yCenter = node.getTranslateY()+size/2;

        if (paddle.xPos <= xCenter &&
            xCenter <= paddle.xPos + paddle.size &&
            paddle.yPos <= yCenter &&
            yCenter <= paddle.yPos + paddle.size) {
            return true;
        }
        return false;
    }
}
