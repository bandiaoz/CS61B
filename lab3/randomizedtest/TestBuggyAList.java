package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();
        for (int i = 0; i < 3; i++) {
            int randVal = StdRandom.uniform(100);
            correct.addLast(randVal);
            buggy.addLast(randVal);
        }
        assertEquals(correct.size(), buggy.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(correct.removeLast(), buggy.removeLast());
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                buggy.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                assertEquals(correct.size(), buggy.size());
                System.out.println("size: " + correct.size());
            } else if (operationNumber == 2) {
                // getLast
                if (correct.size() == 0) {
                    continue;
                }
                int correctLast = correct.getLast();
                int buggyLast = buggy.getLast();
                assertEquals(correctLast, buggyLast);
                System.out.println("getLast: " + buggyLast);
            } else if (operationNumber == 3) {
                // removeLast
                if (correct.size() == 0) {
                    continue;
                }
                int correctLast = correct.removeLast();
                int buggyLast = buggy.removeLast();
                assertEquals(correctLast, buggyLast);
                System.out.println("removeLast: " + buggyLast);
            }
        }
    }
}
