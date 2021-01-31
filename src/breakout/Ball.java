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
    private Circle ball;
    private static final double TOLERANCE = 5;

    public Ball() {
        ball = new Circle();
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
        else if (other instanceof Block) {
            return collide((Block)other);
        }
        return false;
    }

    private boolean collide(Paddle paddle) {
        return (paddle.getPaddle().intersects(ball.getBoundsInParent()));
    }
    public boolean collidesUp(Paddle paddle) {
        double yCenter = node.getTranslateY()+size/2;
        return (yCenter < paddle.yPos + paddle.getSize()/10);
    }
    public boolean collidesLeftSide(Paddle paddle) {
        double xCenter = node.getTranslateX()+size/2;
        double xLeftPaddleFocus = paddle.xPos + paddle.size/12;
        return (xCenter<xLeftPaddleFocus);
    }
    public boolean collidesRightSide(Paddle paddle) {
        double xCenter = node.getTranslateX()+size/2;
        double xRightPaddleFocus = paddle.xPos + paddle.size*11/12;
        return (xCenter>xRightPaddleFocus);
    }

    public boolean collide(Block block) {
        return (block.getBlock().intersects(ball.getBoundsInParent()));
    }
    public boolean collidesX(Block block) {
        return (node.getTranslateX()+size <= block.xPos + TOLERANCE ||
                node.getTranslateX() >= block.xPos+block.getBlock().getFitWidth() - TOLERANCE);
    }
    public boolean collidesY(Block block) {
        return (node.getTranslateY()+size <= block.yPos + TOLERANCE ||
                node.getTranslateY() >= block.yPos+block.getBlock().getFitHeight() - TOLERANCE);
    }
}
