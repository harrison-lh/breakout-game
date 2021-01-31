package breakout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class GameWorld {

    private Scene gameSurface;
    private Group sceneNodes;
    private static Timeline gameLoop;

    private final int framesPerSecond;

    private final String windowTitle;

    private final SpriteManager spriteManager = new SpriteManager();

    /**
     * Constructor that is called by the derived class. This will
     * set the frames per second, title, and setup the game loop.
     * @param fps - Frames per second.
     * @param title - Title of the application window.
     */
    public GameWorld(final int fps, final String title) {
        framesPerSecond = fps;
        windowTitle = title;
        // create and set timeline for the game loop
        buildAndSetGameLoop();
    }

    protected final void buildAndSetGameLoop() {

        final Duration oneFrameAmt = Duration.millis(1000/getFramesPerSecond());
        final KeyFrame keyFrame = new KeyFrame(oneFrameAmt,
                (EventHandler) event -> {
            
                    resetScene();

                    // update actors
                    updateSprites();

                    // check for collision
                    checkCollisions();

                    // removed dead things
                    cleanupSprites();

                }); // oneFrame

        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        setGameLoop(timeline);

    }

    protected abstract void resetScene();

    /**
     * Initialize the game world by update the JavaFX Stage.
     * @param primaryStage
     */
    public abstract void initialize(final Stage primaryStage);

    public void beginGameLoop() {
        getGameLoop().play();
    }

    protected void updateSprites() {
        for (Sprite sprite:spriteManager.getAllSprites()){
            handleUpdate(sprite);
        }
    }

    protected void handleUpdate(Sprite sprite) {
    }

    /**
     * Checks each game sprite in the game world to determine a collision
     * occurred. The method will loop through each sprite and
     * passing it to the handleCollision()
     * method. The derived class should override handleCollision() method.
     *
     */
    protected void checkCollisions() {
        // check other sprite's collisions
        spriteManager.resetCollisionsToCheck();
        // check each sprite against other sprite objects.
        for (Sprite spriteA:spriteManager.getCollisionsToCheck()){
            for (Sprite spriteB:spriteManager.getAllSprites()){
                if (handleCollision(spriteA, spriteB)) {
                    // The break helps optimize the collisions
                    //  The break statement means one object only hits another
                    // object as opposed to one hitting many objects.
                    // To be more accurate comment out the break statement.
                    break;
                }
            }
        }
    }
    /**
     * When two objects collide this method can handle the passed in sprite
     * objects. By default it returns false, meaning the objects do not
     * collide.
     * @param spriteA - called from checkCollision() method to be compared.
     * @param spriteB - called from checkCollision() method to be compared.
     * @return boolean True if the objects collided, otherwise false.
     */
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        return false;
    }

    protected void cleanupSprites() {
        spriteManager.cleanupSprites();
    }

    protected int getFramesPerSecond() {
        return framesPerSecond;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    protected static Timeline getGameLoop() {
        return gameLoop;
    }

    protected static void setGameLoop(Timeline gameLoop) {
        GameWorld.gameLoop = gameLoop;
    }

    protected SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public Scene getGameSurface() {
        return gameSurface;
    }

    protected void setGameSurface(Scene gameSurface) {
        this.gameSurface = gameSurface;
    }

    public Group getSceneNodes() {
        return sceneNodes;
    }

    protected void setSceneNodes(Group sceneNodes) {
        this.sceneNodes = sceneNodes;
    }
}
