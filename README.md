# Bug-Fight-

### How to Run
1. Enter the src directory
2. In terminal, run the command `javac Driver.java` to compile the program
3. run the command `java Driver` to execute the program
4. You will be prompted to enter a species filename, enter the filepath to one of the text files in the species subfolder
&nbsp;&nbsp;&nbsp;&nbsp;ex: `species/Medusa.txt`
5. You will be prompted to enter a color, enter a standard color like blue or red to label that creature.
6. You will re-prompted steps 4 and 5 to added more creatures to the game if wanted.
7. Once you're ready for the game to start, simply press enter (no text) to the prompts in 4 and 5

## Decription
This program makes use of the key features of object-oriented programming. All aspects of the program are classified as objects, such as the creatures, position, instructions, and the world itself. These objects are controlled and dictated by the Driver class to execute the game and make use of the different relationships between classes. For example, the creauture class represents the "bug" that will be fighting, it the class also contains a Position attribute which indicates the bug's location in the grid at that point in time. The grid itself (world) is an object in which only one instance can at a time in order to 1. only run one game at a time and 2. to not overwrite the current world with a new one somehwere along the implementation process. I used to the singleton design pattern to achieve this; the contructor is set to private so that a static variable within the class is assigned to the one and only instance. The instructions and opcodes in the text files (in the species subfolder) are parsed to be treated at object as well. For example, the Hop.txt file defines the instructions that the Hop creature must follow during the game. The `start:` instruction is, by default, the first to be executed; for Hop, the opcodes (actions) following the instruction are `hop` and `go start`. The `hop` opcodes tells the Creature class to move one slot forward where the creature is facing. The `go start` opcode tells the Creature class to repeat the `start` instruction.

## Creature Sample Image Upon Program Execution

## Creature Sample Instruction
