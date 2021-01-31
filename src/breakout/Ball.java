package breakout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Ball extends Sprite{

    public double radius;
    private Circle ball;
    private static final double TOLERANCE = 2;

    public Ball() {
        ball = new Circle();
        radius = 10;
        ball.setRadius(radius);

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
        else if (other instanceof Block) {
            return collide((Block)other);
        }
        return false;
    }

    private boolean collide(Paddle paddle) {
        return (paddle.getPaddle().intersects(ball.getBoundsInParent()));
    }
    public boolean collidesUp(Paddle paddle) {
        double yCenter = node.getTranslateY()+radius;
        return (yCenter < paddle.yPos + paddle.getSize()/10);
    }
    public boolean collidesLeftSide(Paddle paddle) {
        double xCenter = node.getTranslateX()+radius;
        double xLeftPaddleFocus = paddle.xPos + paddle.size/12;
        return (xCenter<xLeftPaddleFocus);
    }
    public boolean collidesRightSide(Paddle paddle) {
        double xCenter = node.getTranslateX()+radius;
        double xRightPaddleFocus = paddle.xPos + paddle.size*11/12;
        return (xCenter>xRightPaddleFocus);
    }

    public boolean collide(Block block) {
        return (block.getBlock().intersects(ball.getBoundsInParent()));
    }
    public boolean collidesX(Block block) {
        return (node.getTranslateX()+2*radius <= block.xPos + TOLERANCE ||
                node.getTranslateX() >= block.xPos+block.getBlock().getFitWidth() - TOLERANCE);
    }
}
