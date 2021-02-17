package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The block is an implementation of the sprite class. Properties of blocks are that they are stationary,
 * have a certain amount of health when hit by balls, and some contain power-ups.
 *
 * @author harrisonh
 */
public class Block extends Sprite{

    public int health;
    public final static double DEFAULT_SIZE = 75;
    private final static String BRICK_IMAGE = "brick3.gif";
    private final static String BRICK_IMAGE2 = "brick2.gif";
    private final static String BRICK_IMAGE3 = "brick5.gif";
    public double xPos, yPos;
    public double size;
    private ImageView block;
    private PowerUp powerup;

    /**
     * Initializes a block containing a power-up.
     * @param health amount of hits the block takes before breaking
     * @param x X location of the block
     * @param y Y location of the block
     * @param powerupType the type of power-up the block contains
     */
    public Block(int health, double x, double y, int powerupType) {
        this(health, x, y);
        powerup = new PowerUp(x + size/2 - 5, y + block.getFitHeight()*1/3, powerupType);
    }

    /**
     * Initializes a block without a power-up.
     * @param health amount of hits the block takes before breaking
     * @param x X location of the block
     * @param y Y location of the block
     */
    public Block(int health, double x, double y) {
        this.health = health;
        Image image = assignImage(health);

        block = new ImageView(image);
        node = block;

        xPos = x;
        yPos = y;
        block.setX(x);
        block.setY(y);

        setSize(DEFAULT_SIZE);
    }

    /**
     * Assigns an image to the block based on its health.
     * @param health health of the block
     * @return Image object corresponding to its initial health
     */
    public Image assignImage(int health) {
        Image image;
        if (health ==1){
            image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE));
        }
        else if (health ==2) {
            image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE2));
        }
        else {
            image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE3));
        }
        return image;
    }

    /**
     * Changes the size of the block, maintaining both its dimensions.
     * @param size
     */
    public void setSize(double size) {
        this.size = size;
        block.setFitWidth(size);
        block.setFitHeight(size/2);
    }

    @Override
    public void update() {
        if (health<=0) {
            isDead = true;
            if (powerup != null) {
                powerup.canMove = true;
            }
        }
    }

    public ImageView getBlock() {
        return block;
    }

    public PowerUp getPowerup() { return powerup; }

    public void removeFromScene(final GameWorld gameWorld) {
        update();
        isDead = true;
        gameWorld.getSceneNodes().getChildren().remove(node);
    }
}
