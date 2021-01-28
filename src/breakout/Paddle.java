package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle extends Sprite{
    public static final String PADDLE_IMAGE = "paddle.gif";

    public double xPos;
    public double yPos;
    public double size;
    ImageView paddle;

    public Paddle() {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        paddle = new ImageView(image);

        node = paddle;
    }

    public void setXPos(double x) {
        xPos = x;
        paddle.setX(x);
    }
    public double getXPos() {
        return xPos;
    }
    public void setYPos(double y) {
        yPos = y;
        paddle.setY(y);
    }
    public double getYPos() {
        return yPos;
    }

    public void setSize(double s) {
        size = s;
        paddle.setFitWidth(s);
        paddle.setFitHeight(s/5);
    }
    public double getSize() {
        return size;
    }

    @Override
    public void update() {

    }
}
