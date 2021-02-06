package breakout;

import javafx.scene.shape.Circle;

/**
 * The ball is an implementation of a sprite that can collide with blocks, reflecting its motion.
 * It is in constant motion.
 *
 * @author harrisonh
 */
public class Ball extends Sprite{

    public double radius;
    private Circle ball;
    private static final double TOLERANCE = 5;

    /**
     * Initializes ball.
     */
    public Ball() {
        ball = new Circle();
        radius = 10;
        ball.setRadius(radius);

        node = ball;
    }

    /**
     * Moves the ball according to its x and y velocity.
     */
    @Override
    public void update() {
        node.setTranslateX(node.getTranslateX() + xVelocity);
        node.setTranslateY(node.getTranslateY() + yVelocity);
    }

    /**
     * Increases the size of the ball up to a certain limit.
     */
    public void incrementSize() {
        radius *= 1.01;
        if (radius > 15) radius = 15;
        ball.setRadius(radius);
    }

    /**
     * Getter method that returns the node representing the ball.
     * @return Node
     */
    public Circle getAsCircle() {
        return (Circle) node;
    }

    /**
     * Sets the velocity of the ball in the x direction
     * @param v double for ball's horizontal movement
     */
    public void setXVelocity(double v) {
        xVelocity = v;
    }

    /**
     * Sets the velocity of the ball in the y direction
     * @param v double for ball's vertical movement
     */
    public void setYVelocity(double v) {
        yVelocity = v;
    }

    /**
     * Checks for collision between the paddle and block. The ball does not
     * collide with any other sprite.
     * @param other other Sprite with which to check collision
     * @return
     */
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

    /**
     * Determines if the ball should bounce vertically off the paddle
     * @param paddle Paddle that the ball collided with
     * @return boolean, returns true if the ball should bounce up
     */
    public boolean collidesUp(Paddle paddle) {
        return (node.getTranslateY() < paddle.yPos + paddle.getSize()/10);
    }

    /**
     * Determines if the ball has collided with the left side of the paddle and
     * should reflect its motion to the left.
     * @param paddle Paddle that the ball collided with
     * @return boolean, returns true if the ball should bounce left
     */
    public boolean collidesLeftSide(Paddle paddle) {
        double xLeftPaddleFocus = paddle.xPos + paddle.size/12;
        return (node.getTranslateX() < xLeftPaddleFocus);
    }

    /**
     * Determines if the ball has collided with the right side of the paddle and
     * should reflect its motion to the right.
     * @param paddle Paddle that the ball collided with
     * @return boolean, returns true if the ball should bounce right
     */
    public boolean collidesRightSide(Paddle paddle) {
        double xRightPaddleFocus = paddle.xPos + paddle.size*11/12;
        return (node.getTranslateX() > xRightPaddleFocus);
    }

    /**
     * Determines if the ball has collided with a block.
     * @param block block to check if paddle has collided
     * @return boolean, returns true if ball has collided with the block
     */
    public boolean collide(Block block) {
        return (block.getBlock().intersects(ball.getBoundsInParent()));
    }

    /**
     * Determines if the ball should bounce in the x direction having collided with a block.
     * @param block block that ball has collided with
     * @return boolean, true if the block should reflect its movement in the x direction
     */
    public boolean collidesX(Block block) {
        return (node.getTranslateX()+radius <= block.xPos + TOLERANCE ||
                node.getTranslateX()-radius >= block.xPos+block.getBlock().getFitWidth() - TOLERANCE);
    }
    /**
     * Determines if the ball should bounce in the y direction having collided with a block.
     * @param block block that ball has collided with
     * @return boolean, true if the block should reflect its movement in the y direction
     */
    public boolean collidesY(Block block) {
        return (node.getTranslateY()+radius <= block.yPos + TOLERANCE ||
                node.getTranslateY()-radius >= block.yPos+block.getBlock().getFitHeight() - TOLERANCE);
    }

    /**
     * Determines if the block should bounce in the x direction, to be used when the ball hits
     * close to the corner of the block.
     * @param block block that ball has collided with at its corner
     * @return boolean, true if the block should reflect its movement in the x direction
     */
    public boolean collidesCornerX(Block block) {
        return (Math.min(Math.abs(node.getTranslateX()-block.xPos),
                        Math.abs(node.getTranslateX()-block.xPos-block.getBlock().getFitWidth())) >=
                Math.min(Math.abs(node.getTranslateY()-block.yPos),
                        Math.abs(node.getTranslateY()-block.yPos-block.getBlock().getFitHeight())));
    }
}
