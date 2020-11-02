/*
 * IntNode.java
 *
 * Computer Science E-22, Harvard University
 * 
 * modified by:
 *   name:
 *   email:
 */

import java.io.*;
import java.util.*;

/**
 * A class for representing integers using a linked list.
 * Each integer of the list is stored in a separate node.  
 *
 * This class represents one node of the linked list.  The list as a
 * whole is represented by storing a reference to the first node in
 * the linked list. The methods in this class are static methods that
 * take a reference to an integer linked-list as a parameter. This
 * approach allows us to use recursion to write many of the methods,
 * and it also allows the methods to handle empty lists, which are 
 * represented using a value of null.
 */
public class IntNode {
    private int val;
    private IntNode next;
    
    /**
     * Constructor
     */
    public IntNode(int c, IntNode n) {
        this.val = c;
        this.next = n;
    }
    
    /**
     * Public method that prints all odd integers in the list.
     * Prints nothing if the list is empty.
     */
    public static void printOddsRecursive(IntNode first) {
        if (first != null) {
            if (first.val % 2 != 0) {
                System.out.println(first.val);
            }
            printOddsRecursive(first.next);
        }
    
    }

    
    /**
     * Public method that prints all odd integers in the list.
     * Prints nothing if the list is empty.
     */
    public static void printOddsIterative(IntNode first) {
        if (first != null){
            IntNode currentNode = first;
        
            do {
                if (currentNode.val % 2 != 0) {
                    System.out.println(currentNode.val);
                }
                currentNode = currentNode.next;
            } while (currentNode.next != null);
        }
    }


    
    public static void main(String[] args) throws IOException {
        //Scanner in = new Scanner(System.in);
        
        IntNode firstNode = new IntNode(5, null);
        IntNode prevNode = firstNode;
        IntNode nextNode;

        for (int i = -3; i < 10; i++) {
            nextNode = new IntNode(i, null);
            prevNode.next = nextNode;
            prevNode = nextNode;
        }
        
        printOddsRecursive(firstNode);
        printOddsRecursive(null);
        printOddsRecursive(prevNode.next);
        printOddsIterative(firstNode);
        printOddsIterative(null);
        printOddsIterative(prevNode.next);
    }
}