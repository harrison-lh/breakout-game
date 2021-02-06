package breakout;

import java.util.*;

/**
 * The Sprite Manager manages the valid sprites in conjunction with the Scene.
 * It allows for Sprites to be iterated for updating, checked in the case of
 * collisions, and removed outside of the iteration loop.
 *
 * @author harrisonh
 */

public class SpriteManager {

    private final static List<Sprite> GAME_ACTORS = new ArrayList<>();

    private final static List<Sprite> CHECK_COLLISION_LIST = new ArrayList<>();

    private final static Set CLEAN_UP_SPRITES = new HashSet<>();

    /**
     * Getter method that returns the current list of all valid sprites.
     * @return List of sprites
     */
    public List<Sprite> getAllSprites() {
        return GAME_ACTORS;
    }

    /**
     * Adds sprites to the sprite manager.
     * @param sprites singular or collection of sprites to be added
     */
    public void addSprites(Sprite... sprites) {
        GAME_ACTORS.addAll(Arrays.asList(sprites));
    }

    /**
     * Removes sprites from the sprite manager.
     * @param sprites singular or collection of sprites to be removed
     */
    public void removeSprites(Sprite... sprites) {
        GAME_ACTORS.removeAll(Arrays.asList(sprites));
    }

    /**
     * Getter method that gives a set of sprites queued to be removed.
     * @return Set of sprites to be removed in the future
     */
    public Set getSpritesToBeRemoved() {
        return CLEAN_UP_SPRITES;
    }

    /**
     * Queues sprites to be removed at the clean up sprites call.
     * @param sprites sprites to be removed in the future
     */
    public void addSpritesToBeRemoved(Sprite... sprites) {
        if (sprites.length > 1) {
            CLEAN_UP_SPRITES.addAll(Arrays.asList((Sprite[]) sprites));
        } else {
            CLEAN_UP_SPRITES.add(sprites[0]);
        }
    }

    /**
     * Returns list of Sprites in order to check collisions.
     * @return list of sprites
     */
    public List<Sprite> getCollisionsToCheck() {
        return CHECK_COLLISION_LIST;
    }

    /**
     * Clears and refills the check collisions list.
     */
    public void resetCollisionsToCheck() {
        CHECK_COLLISION_LIST.clear();
        CHECK_COLLISION_LIST.addAll(GAME_ACTORS);
    }

    /**
     * Removes all sprites queued to be removed.
     */
    public void cleanupSprites() {
        GAME_ACTORS.removeAll(CLEAN_UP_SPRITES);
        CLEAN_UP_SPRITES.clear();
    }
}
