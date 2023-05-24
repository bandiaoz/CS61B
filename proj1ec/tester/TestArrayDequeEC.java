package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
//    private static final int testTime = 10000;
//    @Test
//    public void testAddFirst() {
//        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> sol1 = new ArrayDequeSolution<>();
//        for (int i = 0; i < testTime; i++) {
//            int rand = StdRandom.uniform(100);
//            sad1.addFirst(rand);
//            sol1.addFirst(rand);
//        }
//        for (int i = 0; i < testTime; i++) {
//            assertEquals(sol1.get(i), sad1.get(i));
//        }
//    }
//
//    @Test
//    public void testAddLast() {
//        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> sol1 = new ArrayDequeSolution<>();
//        for (int i = 0; i < testTime; i++) {
//            int rand = StdRandom.uniform(100);
//            sad1.addLast(rand);
//            sol1.addLast(rand);
//        }
//        for (int i = 0; i < testTime; i++) {
//            assertEquals(sol1.get(i), sad1.get(i));
//        }
//    }
//
//    @Test
//    public void testRemoveFirst() {
//        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> sol1 = new ArrayDequeSolution<>();
//        for (int i = 0; i < testTime; i++) {
//            int rand = StdRandom.uniform(100);
//            sad1.addFirst(rand);
//            sol1.addFirst(rand);
//        }
//        for (int i = 0; i < testTime; i++) {
//            assertEquals(sol1.removeFirst(), sad1.removeFirst());
//        }
//    }

    @Test
    public void testRemoveLast() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sol1 = new ArrayDequeSolution<>();
        String message = "";
        for (int i = 0; i < 10; i++) {
            int rand = StdRandom.uniform(100);
            sad1.addLast(rand);
            sol1.addLast(rand);
            message += "addLast(" + rand + ")\n";
        }
        for (int i = 0; i < 10; i++) {
            int student = sad1.removeLast();
            int correct = sol1.removeLast();
            message += "removeLast()\n";
            assertEquals(message, student, correct);
        }
    }
}
