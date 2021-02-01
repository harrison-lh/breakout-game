package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block extends Sprite{

    public int health;
    private final static double DEFAULT_SIZE = 75;
    private final static String BRICK_IMAGE = "brick3.gif";
    private final static String BRICK_IMAGE2 = "brick2.gif";
    private final static String BRICK_IMAGE3 = "brick5.gif";
    public double xPos, yPos;
    public double size;
    private ImageView block;
    private PowerUp powerup;

    public Block(int health, double x, double y, int powerupType) {
        this(health, x, y);
        powerup = new PowerUp(x + size/2 - 5, y + block.getFitHeight()*1/3, powerupType);
    }

    public Block(int health, double x, double y) {
        this.health = health;
        Image image;
        if (health==1){
            image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE));
        }
        else if (health==2) {
            image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE2));
        }
        else {
            image = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK_IMAGE3));
        }

        block = new ImageView(image);
        node = block;

        xPos = x;
        yPos = y;
        block.setX(x);
        block.setY(y);

        setSize(DEFAULT_SIZE);
    }
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
