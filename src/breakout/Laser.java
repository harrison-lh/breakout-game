package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Laser extends Sprite {

    private static final double LASER_SPEED = 10;

    private Rectangle laser;

    public Laser(double x, double y) {
        laser = new Rectangle(2,30, Paint.valueOf("orange"));
        laser.setX(x);
        laser.setY(y);
        node = laser;
    }
    @Override
    public void update() {
        laser.setY(laser.getY() - LASER_SPEED);
    }
    public Rectangle getLaser() {
        return laser;
    }

    @Override
    public boolean collide(Sprite other) {
        if (other instanceof Block) {
            return collide((Block)other);
        }
        else return false;
    }
    public boolean collide(Block block) {
        return laser.intersects(block.getBlock().getBoundsInParent());
    }
}
