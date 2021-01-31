package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerUp extends Sprite {

    private static final double FALL_SPEED = 2;
    private static final String POWERUP_IMAGE = "sizepower.gif";
    private ImageView powerup;
    public boolean canMove = false;

    public PowerUp(double x, double y) {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(POWERUP_IMAGE));
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
