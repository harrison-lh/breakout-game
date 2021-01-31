package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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


    @Override
    public void update() {
        if (canMove) {
            powerup.setY(powerup.getY() + FALL_SPEED);
        }
    }

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
