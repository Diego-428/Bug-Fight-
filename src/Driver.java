import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This class runs a bug fight simulation.
 * @author Diego Torres-Ramos
 */
public class Driver {

    /**
     * Prompt for species filenames and colors, generate the starting world and
     * creatures, then run the bug fight simulation.
     */

    public static int NUM_CREATURES = 10;

    public static void main(String[] args) {

        // create the world (15 x 15 grid)
        WorldMap.initialize(15, 15);
        World worldForCreatures = new World(15, 15);
        // species file
        String file = fileInput();
        // color of the species
        String color = colorInput();
        // color object of the species
        Color colorObj = colorFromString(color);
        // Arraylist where we'll add the creatures that are created and drawn into the WorldMap
        ArrayList<Creature> creatureList = new ArrayList<Creature>();
        // this while loop iterates as long as the user doesn't provide a blank response when prompted.
        while (!file.equals("")){
            // create scpecies given the file (to parse through) and color object
            Species newSpecies = new Species(file, colorObj);
            // use for loop to create creatures and add to arraylist
            for (int i = 0; i < NUM_CREATURES; i++) {
                Position startingPosition = worldForCreatures.randomPosition();
                Direction startingDirection = Direction.random();
                // create creature
                Creature newCreature = new Creature(newSpecies, worldForCreatures, startingPosition, startingDirection);
                // set the creatures on the world
                worldForCreatures.set(startingPosition, newCreature);
                // draw the creatures on the worldmap
                WorldMap.drawCreature(newCreature);
                // add the creature to the arraylist
                creatureList.add(newCreature);

            }
            // prompt user again
            file = fileInput();
            color = colorInput();
            colorObj = colorFromString(color);
        }
        // infinitely loop through the simulation
        while (true){
            // for loop inside while loop to loop through all creatures
            Collections.shuffle(creatureList);
            for (Creature newCreature : creatureList){
                //execute each creatures' instructions
                newCreature.execute();
                WorldMap.pause(100);
            }

        }
    }

    /**
     * fileInput Method
     * This method gets input from the user, prompting for the file that will be parsed.
     * @return species filename
     */
    public static String fileInput(){
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter the species filename: ");
        String response = userInput.nextLine();
        return response;
    }

    /**
     * colorInput Method
     * This method gets input from the user, prompting for the color of the species
     * @return species color
     */
    public static String colorInput(){
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter color of species: ");
        String response = userInput.nextLine();
        return response;
    }

    /**
     * converts the given color (string) and returns the color object associated with that string.
     * @param colorName color of the species (string)
     * @return Color of the species (Color object)
     */
    private static Color colorFromString(String colorName){
        try {
            Field field = Color.class.getField(colorName);
            return (Color) field.get(null);
        }
        catch (Exception e){
            return null; // no such color
        }
    }

}
