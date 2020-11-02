/*
 * Problem8.java
 */

import java.io.*;
import java.util.*;
   
public class Problem8 {
    // Add your definition of isPal here.
    public static boolean isPal(String str)  throws IllegalArgumentException {
        //base cases
        if (str == null) {
            throw new IllegalArgumentException("string may not be null");
        } else if (str.length() < 2) {
            return true;
        }
        
        //create 2 stacks and a variable to store the length of the cleaned string
        LLStack<Character> l1 = new LLStack<Character>();
        LLStack<Character> l2 = new LLStack<Character>();
        int length = 0;
        
        for (int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                l1.push(c.toUpperCase(c)); //put the letters and only the letters on the stack
                length++;
            }
        }
        
        //put half of the letters on the other stack
        for (int i = 0; i < length/2; i++) {
            l2.push(l1.pop());
        }
        
        //if the length was odd, discard the middle letter
        if (length % 2 == 1) {
            l1.pop();
        }
        
        //compare each pair of letters
        for (int i = 0; i < length/2; i++) {
            Character c1 = l1.pop();
            Character c2 = l2.pop();
            if (c1 != c2) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        System.out.println("--- Testing method isPal ---");
        System.out.println();

        System.out.println("(0) Testing on \"A man, a plan, a canal, Panama!\"");
        try {
            boolean results = isPal("A man, a plan, a canal, Panama!");
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();    // include a blank line between tests
        
        /*
         * We encourage you to add more unit tests below that test a variety 
         * of different cases, although doing so is not required.
         */
        
        System.out.println(isPal(""));
        System.out.println(isPal("A"));
        System.out.println(isPal("z"));
        System.out.println(isPal("z!@#$sf"));
        System.out.println(isPal("z!  z!!"));
        System.out.println(isPal(" $5z!  z!Z!"));
    }
}