package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/** A repository for Capers 
 * @author bandiaoz
 * The structure of a Capers Repository is as follows:
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(CWD, ".capers"); // TODO Hint: look at the `join` function in Utils

    /**
     * Does require filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        if (!CAPERS_FOLDER.exists()) {
            CAPERS_FOLDER.mkdir();
        }
        File dogFolder = Utils.join(CAPERS_FOLDER, "dogs");
        if (!dogFolder.exists()) {
            dogFolder.mkdir();
        }
        File story = Utils.join(CAPERS_FOLDER, "story");
        try {
            story.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File f = Utils.join(CAPERS_FOLDER, "story");
        String old = Utils.readContentsAsString(f);
        if (old.equals("")) {
            Utils.writeContents(f, text);
        } else {
            Utils.writeContents(f, old + "\n" + text);
        }

        System.out.println(Utils.readContentsAsString(f));
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog dog = new Dog(name, breed, age);
        dog.saveDog();
        System.out.println(dog);
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // TODO
        Dog dog = Dog.fromFile(name);
        System.out.println(dog);
        dog.haveBirthday();
        dog.saveDog();
    }
}
