import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents the species of the creature, containing a method to parse text files
 * and some additional helper methods.
 * @author Diego Torres-Ramos
 */

/**
 * This class represents a type of creature in a bug fight simulation. All
 * creatures of a given species execute the instructions of that species's
 *  program, which specify how the creatures behave in the world. Each
 * Bug Fight program consists of a set of ordered instructions, each with an
 * instruction address (starting from address 0, like an index). At any given
 * time, each creature is at some instruction address, and continues executing
 * from that point the next time the creature acts. All creatures of a given
 * species are represented by a particular color.
 */
public class Species {

    /**
     * An exception indicating that the given Species program file was malformed.
     */
    public static class BadSpeciesException extends RuntimeException {

        /**
         * Construct a new exception indicating that the species program was
         * malformed.
         *
         * @param msg A message describing the problem.
         */
        public BadSpeciesException(String msg) {
            super(msg); // pass msg to parent constructor
        }

    }

    private String name;
    private final Color colorOfSpecies;
    private ArrayList<Instruction> program;

    /**
     * Create a new species using the given Darwin program and the specified
     * color. May throw a BadSpeciesException if the given file does not exist or
     * does not contain a well-formed Darwin program.
     *
     * @param filename The filename of a Darwin program.
     * @param color The color to use for this species.
     */
    public Species(String filename, Color color) {

        this.colorOfSpecies = color;
        this.program = parser(filename);

    }

    /**
     * Adds the instructions onto the program. Parses through the file and each
     * line will have its own address in the program.
     * @param filename The filename that will be parsed through
     * @return The program (ArrayList) holding the instructions of the file
     */
    public ArrayList<Instruction> parser(String filename){

        int counter = 0;

        // Making a new arraylist to store instructions
        ArrayList<Instruction> storage = new ArrayList<>();

        Scanner fileInput;

        try {
            // try to read the file
            fileInput = new Scanner(new File(filename));
        }
        catch (Exception e){
            System.out.println("This file does not exist");
            return null;
        }

        while (fileInput.hasNext()){

            // get the next line in the file
            String line = fileInput.nextLine();

            // assign the first line of the file to the name of the species
            if (counter == 0){
                this.name = line;
                // increment the counter so that the name isn't re-assigned
                counter++;
                continue;
            }

            // ignore the lines that are blank or are comments
            if (line.startsWith("#") || line.equals("")){
                continue;
            }

            // split the line into an array
            String[] splitLine = line.split(" ");

            // make an opcode out of the first element of the split line
            Opcode speciesOpcode = Opcode.fromString(splitLine[0]);

            // check to see if the line a label
            if (splitLine[0].charAt(splitLine[0].length() - 1) == ':'){
                speciesOpcode = Opcode.LABEL;
                // remove the colon from the end of the label
                splitLine[0] = splitLine[0].substring(0,splitLine[0].length() - 1);
                Instruction ints = new Instruction(speciesOpcode, splitLine[0]);
                // add label to the Arraylist
                storage.add(ints);
            }


            else if(splitLine.length == 1){

                // if the splitline array only has one element, make an instruction out of it and add to arraylist
                Instruction currentInstruction = new Instruction(speciesOpcode);
                storage.add(currentInstruction);

            }
            else if (splitLine.length == 2){

                // if two elements, make an instruction out of the opcaode and label and add it to the arraylist
                Instruction currentInstruction = new Instruction(speciesOpcode, splitLine[1]);
                storage.add(currentInstruction);
            }

        }

        return storage;

    }

    /**
     * Get the name of the species.
     *
     * @return The species name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the color of the species.
     *
     * @return The species color.
     */
    public Color getColor() {
        return colorOfSpecies;
    }

    /**
     * Get the number of instructions in the species program.
     *
     * @return The number of species program instructions.
     */
    public int programSize() {
        return program.size();
    }

    /**
     * Get a particular instruction from the species program.
     *
     * @param address The address of the desired instruction.
     * @return The specified instruction.
     */
    public Instruction programStep(int address) {
        return program.get(address);
    }

    /**
     * Get the address of the instruction within the species program corresponding
     * to the given label. Assumes that a label instruction with the given name
     * exists within the species program.
     *
     * @param label The name of the label to lookup.
     * @return The instruction address of the given label.
     */
    public int getLabelAddress(String label) {
        for (int i = 0; i < program.size(); i++) {
            Instruction x = program.get(i);
            if (x.getOpcode() == Opcode.LABEL && x.getLabel().equals(label)){
                return i;
            }
        }
        return 0;
    }

    /**
     * Construct a string representation of the species program in some reasonable
     * format. Useful for debugging.
     *
     * @return A string representing the species program.
     */
    public String programToString() {
        String textualRepresentation = "";
        textualRepresentation += "[";
        for (int i = 0; i < program.size(); i++) {

            if (i == program.size()- 1){
                textualRepresentation += program.get(i).getLabel();
                break;
            }

            textualRepresentation += program.get(i).getLabel() + ", ";

        }
        textualRepresentation += "]";
        return textualRepresentation;
    }

    public ArrayList<Instruction> getProgram() {
        return program;
    }

    /**
     * Tests the functionality of the Species class.
     */
    public static void main(String[] s) {

        Species someTestCase = new Species("species/Food.txt", Color.BLACK);

        System.out.println(someTestCase.program);
    }

}
