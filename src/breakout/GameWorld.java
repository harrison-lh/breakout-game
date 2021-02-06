package breakout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This application demonstrates a JavaFX Game Loop.
 *
 * @author harrisonh
 */

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

    /**
     * Creates game loop and performs actions that take place within each frame.
     */
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

    /**
     * Performs auxiliary actions to reset the scene as needed by the game mechanics.
     */
    protected abstract void resetScene();

    /**
     * Initialize the game world by update the JavaFX Stage.
     * @param primaryStage
     */
    public abstract void initialize(final Stage primaryStage);

    /**
     * Starts the game loop.
     */
    public void beginGameLoop() {
        getGameLoop().play();
    }

    /**
     * Calls on all the sprites to be updated, used by game loop once per frame.
     */
    protected void updateSprites() {
        for (Sprite sprite:spriteManager.getAllSprites()){
            handleUpdate(sprite);
        }
    }

    /**
     * Method to update each Sprite, depending on the type of Sprite.
     * Implementation to be determined by the subclass.
     * @param sprite Sprite to be updated
     */
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

    /**
     * Calls on sprite manager to remove sprites queued to be removed.
     */
    protected void cleanupSprites() {
        spriteManager.cleanupSprites();
    }

    /**
     * Returns the frames per second of the game.
     * @return fps of the game
     */
    protected int getFramesPerSecond() {
        return framesPerSecond;
    }

    /**
     * Returns the title of the window.
     * @return Title of the window
     */
    public String getWindowTitle() {
        return windowTitle;
    }

    /**
     * Getter method for returning the game loop
     * @return game loop
     */
    protected static Timeline getGameLoop() {
        return gameLoop;
    }

    /**
     * Setter method for the game loop
     * @param gameLoop game loop to be set
     */
    protected static void setGameLoop(Timeline gameLoop) {
        GameWorld.gameLoop = gameLoop;
    }

    /**
     * Getter method for the sprite manager
     * @return sprite manager object for the game world
     */
    protected SpriteManager getSpriteManager() {
        return spriteManager;
    }

    /**
     * Getter method for the game surface/scene
     * @return Scene object
     */
    public Scene getGameSurface() {
        return gameSurface;
    }

    /**
     * Setter method for the game surface/scene
     * @param gameSurface Scene object to set
     */
    protected void setGameSurface(Scene gameSurface) {
        this.gameSurface = gameSurface;
    }

    /**
     * Returns Group object containing all of the nodes in the current scene.
     * @return Group with all current nodes
     */
    public Group getSceneNodes() {
        return sceneNodes;
    }

    /**
     * Overwrites all the nodes in the current scene with the input Group
     * @param sceneNodes Group of nodes to overwrite the current nodes
     */
    protected void setSceneNodes(Group sceneNodes) {
        this.sceneNodes = sceneNodes;
    }
}
