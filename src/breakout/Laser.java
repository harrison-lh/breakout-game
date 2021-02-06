package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * The laser, another implementation of the Sprite class, shoots upward and is intended to collide with blocks
 * and destroy them.
 *
 * @author harrisonh
 */
public class Laser extends Sprite {

    private static final double LASER_SPEED = 10;

    private Rectangle laser;

    /**
     * Creates new Laser object at the given x and y coordinates.
     * @param x initial X location
     * @param y initial Y location
     */
    public Laser(double x, double y) {
        laser = new Rectangle(2,30, Paint.valueOf("orange"));
        laser.setX(x);
        laser.setY(y);
        node = laser;
    }

    /**
     * Moves the laser upward for every frame.
     */
    @Override
    public void update() {
        laser.setY(laser.getY() - LASER_SPEED);
    }

    /**
     * Getter method that returns the Rectangle object that the laser is represented by.
     * @return Rectangle object
     */
    public Rectangle getLaser() {
        return laser;
    }

    /**
     * Detects if the laser has collided with a sprite, but only checks for blocks.
     * @param other other Sprite with which to check collision
     * @return
     */
    @Override
    public boolean collide(Sprite other) {
        if (other instanceof Block) {
            return collide((Block)other);
        }
        else return false;
    }

    /**
     * Determines if the laser has collided with a block.
     * @param block block to check collision
     * @return boolean, true if the laser has collided with the block
     */
    public boolean collide(Block block) {
        return laser.intersects(block.getBlock().getBoundsInParent());
    }
}
