package breakout;


import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

  GameWorld gameWorld = new BreakoutGame(60, "Breakout Game");

  public static void main(String[] args) {
    launch(args);
  }

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