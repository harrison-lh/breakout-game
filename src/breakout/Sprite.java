package breakout;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.scene.Node;

/**
 * The Sprite class is an abstract class that displays an image or node on the JavaFX Scene.
 *
 * @author harrisonh
 */
public abstract class Sprite {

    public List animations = new ArrayList<>();

    public Node node;

    public double xVelocity = 0;
    public double yVelocity = 0;

    public boolean isDead = false;

    /**
     * Method to update characteristics of each Sprite object for every frame.
     * To be implemented for every subclass.
     */
    public abstract void update();

    /**
     * Determines if a Sprite object collides with another Sprite object. Defaults
     * to false if not overwritten by the subclass.
     * @param other other Sprite with which to check collision
     * @return boolean, true if the sprites collide
     */
    public boolean collide(Sprite other) {
        return false;
    }

}
