package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block extends Sprite{

    public int health;
    private final static String BRICK_IMAGE = "brick3.gif";
    public double xPos, yPos;
    public double size;
    ImageView block;

    public Block(int health, double x, double y) {
        this.health = health;
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE));
        block = new ImageView(image);
        node = block;

        xPos = x;
        yPos = y;
        block.setX(x);
        block.setY(y);

        setSize(70);
    }
    public void setSize(double size) {
        this.size = size;
        block.setFitWidth(size);
        block.setFitHeight(size/2);
    }

    @Override
    public void update() {
        if (health==0) {
            isDead = true;
        }
    }
}
