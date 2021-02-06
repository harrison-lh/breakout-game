package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The power-up, normally created as a part of the block, falls when the block is destroyed.
 * When it collides with the paddle, it gives the player some sort of power-up.
 *
 * @author harrisonh
 */
public class PowerUp extends Sprite {

    private static final double FALL_SPEED = 2;

    private static final int SIZE_POWERUP = 1;
    private static final int POINTS_POWERUP = 2;
    private static final int LASER_POWERUP = 3;
    private static final String POWERUP_SIZE_IMAGE = "sizepower.gif";
    private static final String POWERUP_POINTS_IMAGE = "pointspower.gif";
    private static final String POWERUP_LASER_IMAGE = "laserpower.gif";
    private ImageView powerup;
    public boolean canMove = false;
    public int type;

    /**
     * Creates new PowerUp object, with specifications of where to place it
     * and what type it is, in other words what power-up it gives to the
     * player after it is obtained.
     * @param x starting x position
     * @param y starting y position
     * @param type type of power-up (size = 1, ball = 2, laser = 3)
     */
    public PowerUp(double x, double y, int type) {
        Image image;
        this.type = type;
        if (type==SIZE_POWERUP) {
            image = new Image(this.getClass().getClassLoader().getResourceAsStream(POWERUP_SIZE_IMAGE));
        }
        else if (type==POINTS_POWERUP) {
            image = new Image(this.getClass().getClassLoader().getResourceAsStream(POWERUP_POINTS_IMAGE));
        }
        else {
            image = new Image(this.getClass().getClassLoader().getResourceAsStream(POWERUP_LASER_IMAGE));
        }
        powerup = new ImageView(image);
        node = powerup;

        powerup.setX(x);
        powerup.setY(y);
    }

    /**
     * Changes the position of the power-up if its corresponding block has broken.
     */
    @Override
    public void update() {
        if (canMove) {
            powerup.setY(powerup.getY() + FALL_SPEED);
        }
    }

    /**
     * Determines if power-up has collided with the paddle.
     * @param other other Sprite with which to check collision
     * @return boolean, true if power-up collides paddle
     */
    @Override
    public boolean collide(Sprite other) {
        if (other instanceof Paddle) {
            return collide((Paddle)other);
        }
        else return false;
    }
    private boolean collide(Paddle paddle) {
        return powerup.intersects(paddle.getPaddle().getBoundsInParent());
    }
}
