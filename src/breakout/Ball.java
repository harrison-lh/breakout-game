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
    private static final double TOLERANCE = 5;

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
//        radius *= 1.0001;
//        ball.setRadius(radius);
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
        return (node.getTranslateY() < paddle.yPos + paddle.getSize()/10);
    }
    public boolean collidesLeftSide(Paddle paddle) {
        double xLeftPaddleFocus = paddle.xPos + paddle.size/12;
        return (node.getTranslateX() < xLeftPaddleFocus);
    }
    public boolean collidesRightSide(Paddle paddle) {
        double xRightPaddleFocus = paddle.xPos + paddle.size*11/12;
        return (node.getTranslateX() > xRightPaddleFocus);
    }

    public boolean collide(Block block) {
        return (block.getBlock().intersects(ball.getBoundsInParent()));
    }
    public boolean collidesX(Block block) {
        return (node.getTranslateX()+radius <= block.xPos + TOLERANCE ||
                node.getTranslateX()-radius >= block.xPos+block.getBlock().getFitWidth() - TOLERANCE);
    }
    public boolean collidesY(Block block) {
        return (node.getTranslateY()+radius <= block.yPos + TOLERANCE ||
                node.getTranslateY()-radius >= block.yPos+block.getBlock().getFitHeight() - TOLERANCE);
    }
    public boolean collidesCornerX(Block block) {
        return (Math.min(Math.abs(node.getTranslateX()-block.xPos),
                        Math.abs(node.getTranslateX()-block.xPos-block.getBlock().getFitWidth())) >=
                Math.min(Math.abs(node.getTranslateY()-block.yPos),
                        Math.abs(node.getTranslateY()-block.yPos-block.getBlock().getFitHeight())));
    }
}
