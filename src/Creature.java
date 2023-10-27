import java.util.Random;

/**
 * This class represents one creature in a Darwin simulation. Each creature is
 * of a particular species and has a position and direction within the
 * simulation world. In addition, each creature must remember its current
 * position within its species program, which tells it which instruction to
 * execute next. Lastly, creatures are responsible for drawing themselves in the
 * graphical world map and updating the map appropriately as actions are taken.
 * @author Diego Torres-Ramos
 */

public class Creature {

    Species species;
    World world;
    Position pos;
    Direction dir;
    private int counter = 0;

    /**
     * Create a creature of the given species, within the given world, with the
     * indicated starting position and direction.
     */
    public Creature(Species species, World world, Position pos, Direction dir) {
        this.species = species;
        this.world = world;
        this.pos = pos;
        this.dir = dir;
        WorldMap.drawCreature(this);
    }

    /**
     * Get the current species of the creature.
     *
     * @return The current creature species.
     */
    public Species species() {
        return species;
    }

    /**
     * Get the current direction of the creature.
     *
     * @return The current creature direction.
     */
    public Direction direction() {
        return dir;
    }

    /**
     * Get the current position of the creature.
     *
     * @return The current creature position.
     */
    public Position position() {
        return pos;
    }

    /**
     * Repeatedly execute instructions from the creature's program until one of
     * the 'terminating' instructions (hop, left, right, or infect) is executed.
     */
    public void execute() {

        boolean actionOver = false;

        while (!actionOver) {

            Instruction instruction = species.programStep(counter);
            String instructLabel = species.programStep(counter).getLabel();
            counter++;
            Position inFront = pos.getAdjacent(dir);

            switch (instruction.getOpcode()){

                case HOP:

                    // if the position in front of the current creature is in bounds and
                    // that position is unoccupied, the creature can hop

                    if (world.inBounds(inFront) && world.get(inFront) == null){

                        world.set(pos, null);
                        world.set(inFront, this);

                        Position oldPos = pos;
                        pos = inFront;

                        WorldMap.drawMovedCreature(this, oldPos);
                    }
                    actionOver = true;
                    break;

                case LEFT:
                    dir = dir.left();
                    WorldMap.drawCreature(this);
                    actionOver = true;
                    break;

                case RIGHT:
                    dir = dir.right();
                    WorldMap.drawCreature(this);
                    actionOver = true;
                    break;

                case INFECT:

                    // if the space in front is in bounds and has a creature in it,
                    // infection can happen

                    if (world.inBounds(inFront) && world.get(inFront) != null){

                        Creature frontCreature = world.get(inFront);

                        // makes sure that the creature to infect is not the same species

                        if (frontCreature.species != species){
                            frontCreature.species = this.species;
                        }

                        if (instruction.getLabel() == null){
                            frontCreature.counter = 0;
                        }else{
                            frontCreature.counter = species.getLabelAddress(instructLabel);
                        }
                        WorldMap.drawCreature(frontCreature);
                    }
                    actionOver = true;
                    break;

                case IFEMPTY:
                    if (world.inBounds(inFront) && world.get(inFront) == null){
                        // continues execution of the program from the address indicated by label
                        counter = species.getLabelAddress(instructLabel);

                    }

                    break;

                case IFWALL:
                    if (!world.inBounds(inFront)){
                        dir = dir.left();
                        counter = species.getLabelAddress(instructLabel);
                    }
                    break;

                case IFSAME:
                    if (world.get(inFront) == this){
                        dir = dir.right();
                        counter = species.getLabelAddress(instructLabel);
                    }
                    break;

                case IFENEMY:
                    if (world.inBounds(inFront)){
                        Creature frontCreature2 = world.get(inFront);

                        if (frontCreature2 != null && this.species != frontCreature2.species){
                            counter = species.getLabelAddress(instructLabel);
                        }
                    }

                    break;

                case IFRANDOM:
                    Random rand = new Random();

                    // gets a random integer, either 0 or 1
                    int helperCount = rand.nextInt(2);

                    if (helperCount == 0){
                        counter = species.getLabelAddress(instructLabel);
                    }

                    break;

                case GO:
                    counter = species.getLabelAddress(instruction.getLabel());
                    break;

                case LABEL:
                    break;

                default:
                    break;
            }

        }
    }

}