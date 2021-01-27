package breakout;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.scene.Node;

public abstract class Sprite {

    public List animations = new ArrayList<>();

    public Node node;

    public double xVelocity = 0;
    public double yVelocity = 0;

    public boolean isDead = false;

    public abstract void update();

    public boolean collide(Sprite other) {
        return false;
    }

}
