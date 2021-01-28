package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle extends Sprite{
    public static final String PADDLE_IMAGE = "paddle.gif";

    public int xPos;
    public int yPos;
    ImageView paddle;

    public Paddle() {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        paddle = new ImageView(image);

        node = paddle;
    }

    public void setXPos(int x) {
        xPos = x;
        paddle.setX(x);
    }
    public int getXPos() {
        return xPos;
    }
    public void setYPos(int y) {
        yPos = y;
        paddle.setY(y);
    }
    public int getYPos() {
        return yPos;
    }

    @Override
    public void update() {

    }
}
