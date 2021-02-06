package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The Paddle class is an extension of Sprite, controlling the player's paddle.
 * Though it contains information about its position and size,
 * its movement and collisions are determined by other classes.
 *
 * @author harrisonh
 */
public class Paddle extends Sprite{
    public static final String PADDLE_IMAGE = "paddle.gif";

    public double xPos;
    public double yPos;
    public double size;
    private ImageView paddle;

    /**
     * Initializes new Paddle.
     */
    public Paddle() {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        paddle = new ImageView(image);

        node = paddle;
    }

    /**
     * Sets x position of the paddle.
     * @param x x position corresponding to leftmost pixel
     */
    public void setXPos(double x) {
        xPos = x;
        paddle.setX(x);
    }

    /**
     * Getter method for x position of the paddle.
     * @return double, leftmost x position of the paddle
     */
    public double getXPos() {
        return xPos;
    }

    /**
     * Sets y position of the paddle.
     * @param y y position corresponding to highest pixel
     */
    public void setYPos(double y) {
        yPos = y;
        paddle.setY(y);
    }

    /**
     * Getter method for y position of the paddle.
     * @return double, highest y position of the paddle
     */
    public double getYPos() {
        return yPos;
    }

    /**
     * Scales the size of paddle, preserving the dimensions of the paddle.
     * The input corresponds to the width of the paddle in pixels.
     * @param s size of the paddle
     */
    public void setSize(double s) {
        size = s;
        paddle.setFitWidth(s);
        paddle.setFitHeight(s/5);
    }

    /**
     * Returns size of the paddle.
     * @return double, size of the paddle
     */
    public double getSize() {
        return size;
    }

    /**
     * Implementation of abstract method: unused as paddle's update relies
     * on other classes to manage.
     */
    @Override
    public void update() {

    }

    /**
     * Getter method for the paddle ImageView object.
     * @return ImageView object for the paddle
     */
    public ImageView getPaddle() {
        return paddle;
    }
}
