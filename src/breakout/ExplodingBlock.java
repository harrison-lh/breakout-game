package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The exploding block is a different type of block that "explodes" upon its death,
 * thus destroying surrounding blocks.
 *
 * @author harrisonh
 */
public class ExplodingBlock extends Block{

    private static final String EXPLODING_BLOCK_IMAGE = "brick10.gif";
    private ImageView block;
    private boolean isExploded = false;

    /**
     * Creates new exploding block.
     * @param health amount of hits the block takes before breaking
     * @param x X location of the block
     * @param y Y location of the block
     */
    public ExplodingBlock(int health, double x, double y) {

        super(health, x+1, y+1);

        block = new ImageView(assignImage(health));
        node = block;

        block.setX(x+1);
        block.setY(y+1);

        block.setFitWidth(Block.DEFAULT_SIZE-2);
        block.setFitHeight(Block.DEFAULT_SIZE/2-2);

    }

    /**
     * Assigns image exclusive to the exploding block.
     * @param health health of the block
     * @return Image object
     */
    @Override
    public Image assignImage(int health) {
        return new Image(this.getClass().getClassLoader().getResourceAsStream(EXPLODING_BLOCK_IMAGE));
    }

    /**
     * On the explosion of the block, it doubles in size to create collisions with the
     * surrounding blocks, and the explosion is left to be removed in the subsequent frame.
     * @param gameWorld Breakout game from which to remove the block
     */
    @Override
    public void removeFromScene(GameWorld gameWorld) {
        block.setX(block.getX() - block.getFitWidth()/2);
        block.setY(block.getY() - block.getFitHeight()/2);
        block.setFitWidth(block.getFitWidth() * 2);
        block.setFitHeight(block.getFitHeight() * 2);

        isExploded = true;
    }

    /**
     * Checks for collisions between itself and other blocks, intending to succeed
     * after the explosion.
     * @param other other Sprite with which to check collision
     * @return boolean if collision happens with other blocks
     */
    @Override
    public boolean collide(Sprite other) {
        if (other instanceof Block) {
            return collide((Block)other);
        }
        return false;
    }
    private boolean collide(Block blockToExplode) {
        if (this.block.getX() == blockToExplode.getBlock().getX()) return false;
        return this.block.intersects(blockToExplode.getBlock().getBoundsInParent());
    }

    /**
     * Getter method for if the block has exploded
     * @return boolean if block has exploded
     */
    public boolean isExploded() {
        return isExploded;
    }
}
