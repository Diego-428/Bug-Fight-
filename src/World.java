import java.util.Random;

/**
 * This class represents the two-dimensional world during a Darwin simulation.
 * Each position of the world may be populated either by nothing or by a single
 * creature.
 */
public class World {

    // random position generation
    private final Random rand = new Random();

    // contents of the world
    private final Creature[][] board;

    /**
     * Create a new world consisting of width columns and height rows. Initially,
     * the world contains no creatures.
     * 
     * @param width
     *          The width of the world.
     * @param height
     *          The height of the world.
     */
    public World(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be >0");
        }
        board = new Creature[height][width];
    }

    /**
     * Get the height of the world.
     * 
     * @return The world height.
     */
    public int height() {
        return board.length;
    }

    /**
     * Get the width of the world.
     * 
     * @return The world width.
     */
    public int width() {
        return board[0].length;
    }

    /**
     * Check whether the given position is within the bounds of the world (i.e.,
     * its x and y coordinates specify a valid world position).
     * 
     * @param pos
     *          The position to check.
     * @return Whether the given position is within the world bounds.
     */
    public boolean inBounds(Position pos) {
        return 0 <= pos.getX() && pos.getX() < width() && 0 <= pos.getY()
                && pos.getY() < height();
    }

    /**
     * Get a random position within the bounds of the world. All valid world
     * positions (occupied or not) are returned with equal probability.
     * 
     * @return A random position within the world.
     */
    public Position randomPosition() {
        return new Position(rand.nextInt(width()), rand.nextInt(height()));
    }

    /**
     * Update the given world position to contain the given creature (which may be
     * null, in which case the world position is cleared).
     * 
     * @param pos
     *          The position to update.
     * @param creature
     *          The creature to place at the given position, or null.
     */
    public void set(Position pos, Creature creature) {
        if (!inBounds(pos)) {
            throw new IllegalArgumentException("bad position: " + pos);
        }
        board[pos.getY()][pos.getX()] = creature;
    }

    /**
     * Get the creature at the given position of the board, or null if no creature
     * occupies that position.
     * 
     * @param pos
     *          The position to get.
     * @return The creature at the specified position, or null.
     */
    public Creature get(Position pos) {
        if (!inBounds(pos)) {
            throw new IllegalArgumentException("bad position: " + pos);
        }
        return board[pos.getY()][pos.getX()];
    }

}
