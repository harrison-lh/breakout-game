package breakout;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main driver class for the Breakout Game.
 *
 * @author harrisonh
 */
public class Main extends Application {

  GameWorld gameWorld = new BreakoutGame(60, "Breakout Game");

  /**
   * Main starts application
   * @param args command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Initializes and begins game engine
   * @param primaryStage Stage object to be used
   */
  @Override
  public void start(Stage primaryStage) {
    // setup title, scene, stats, controls, and actors.
    gameWorld.initialize(primaryStage);

    // kick off the game loop
    gameWorld.beginGameLoop();

    // display window
    primaryStage.show();
  }

}