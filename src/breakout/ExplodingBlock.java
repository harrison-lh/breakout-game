package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ExplodingBlock extends Block{

    private static final String EXPLODING_BLOCK_IMAGE = "brick10.gif";
    private ImageView block;
    private boolean isExploded = false;

    public ExplodingBlock(int health, double x, double y) {

        super(health, x+1, y+1);

        block = new ImageView(assignImage(health));
        node = block;

        block.setX(x+1);
        block.setY(y+1);

        block.setFitWidth(Block.DEFAULT_SIZE-2);
        block.setFitHeight(Block.DEFAULT_SIZE/2-2);

    }

    @Override
    public Image assignImage(int health) {
        return new Image(this.getClass().getClassLoader().getResourceAsStream(EXPLODING_BLOCK_IMAGE));
    }

    @Override
    public void removeFromScene(GameWorld gameWorld) {
        block.setX(block.getX() - block.getFitWidth()/2);
        block.setY(block.getY() - block.getFitHeight()/2);
        block.setFitWidth(block.getFitWidth() * 2);
        block.setFitHeight(block.getFitHeight() * 2);

        isExploded = true;
    }

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

    public boolean isExploded() {
        return isExploded;
    }
}
