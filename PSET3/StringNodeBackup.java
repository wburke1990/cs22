/*
 * StringNodeBackup.java
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
 * A class for representing a string using a linked list.
 * Each character of the string is stored in a separate node.  
 *
 * This class represents one node of the linked list.  The string as a
 * whole is represented by storing a reference to the first node in
 * the linked list. The methods in this class are static methods that
 * take a reference to a string linked-list as a parameter. This
 * approach allows us to use recursion to write many of the methods,
 * and it also allows the methods to handle empty strings, which are 
 * represented using a value of null.
 */
public class StringNodeBackup {
    private char ch;
    private StringNodeBackup next;

    /**
     * Constructor
     */
    public StringNodeBackup(char c, StringNodeBackup n) {
        this.ch = c;
        this.next = n;
    }

    /**
     * getNode - private helper method that returns a reference to
     * node i in the given linked-list string.  If the string is too
     * short or if the user passes in a negative i, the method returns null.
     */
    private static StringNodeBackup getNode(StringNodeBackup str, int i) {
        if (i < 0 || str == null) {    // base case 1: not found
            return null;
        } else if (i == 0) {           // base case 2: just found
            return str;
        } else {
            return getNode(str.next, i - 1);
        }
    }

    /*****************************************************
     * Public methods (in alphabetical order)
     *****************************************************/

    /**
     * charAt - returns the character at the specified index of the
     * specified linked-list string, where the first character has
     * index 0.  If the index i is < 0 or i > length - 1, the method
     * will end up throwing an IllegalArgumentException.
     */
    public static char charAt(StringNodeBackup str, int i) {
        if (str == null) {
            throw new IllegalArgumentException("the string is empty");
        } 
          
        StringNodeBackup node = getNode(str, i);

        if (node != null) {
            return node.ch;     
        } else {
            throw new IllegalArgumentException("invalid index: " + i);
        }
    }
    
    /**
     * compareAlpha - compares two linked-list strings to determine
     * which comes first alphabetically (i.e., according  to the ordering 
     * used for words in a dictionary). 
     * 
     * It returns:
     *    1 if str1 comes first alphabetically
     *    2 if str2 comes first alphabetically
     *    0 if str1 and str2 represent the same string
     * 
     * The empty string comes before any non-empty string, 
     * and the prefix of a string comes before the string
     * itself (e.g., "be" comes before "become").
     */
    public static int compareAlpha(StringNodeBackup str1, StringNodeBackup str2) {
        if (str1 == null && str2 == null) {
            return 0;
        } else if (str1 == null) {
            return 1;
        } else if (str2 == null) {
            return 2;
        } else if (str1.ch < str2.ch) {
            return 1;
        } else if (str2.ch < str1.ch) {
            return 2;
        } else {
            int compareRest = compareAlpha(str1.next, str2.next);
            return compareRest;
        }
    }

    /**
     * convert - converts a standard Java String object to a linked-list
     * string and returns a reference to the linked-list string
     */
    public static StringNodeBackup convert(String s) {
        if (s.length() == 0) {
            return null;
        }

        StringNodeBackup firstNode = new StringNodeBackup(s.charAt(0), null);
        StringNodeBackup prevNode = firstNode;
        StringNodeBackup nextNode;

        for (int i = 1; i < s.length(); i++) {
            nextNode = new StringNodeBackup(s.charAt(i), null);
            prevNode.next = nextNode;
            prevNode = nextNode;
        }

        return firstNode;
    }
    
    /**
     * copy - returns a copy of the given linked-list string
     */
    public static StringNodeBackup copy(StringNodeBackup str) {
        if (str == null) {
            return null;
        }

        // make a recursive call to copy the rest of the list
        StringNodeBackup copyRest = copy(str.next);
           
        // create and return a copy of the first node, 
        // with its next field pointing to the copy of the rest
        return new StringNodeBackup(str.ch, copyRest);
    }

    /**
     * deleteChar - deletes character i in the given linked-list string and
     * returns a reference to the resulting linked-list string
     */
    public static StringNodeBackup deleteChar(StringNodeBackup str, int i) {
        if (str == null) {
            throw new IllegalArgumentException("string is empty");
        } else if (i < 0) { 
            throw new IllegalArgumentException("invalid index: " + i);
        } else if (i == 0) { 
            str = str.next;
        } else {
            StringNodeBackup prevNode = getNode(str, i-1);
            if (prevNode != null && prevNode.next != null) {
                prevNode.next = prevNode.next.next;
            } else {
                throw new IllegalArgumentException("invalid index: " + i);
            }
        }

        return str;
    }
    
    /** 
     * insertBefore - inserts the specified new character (newChar) 
     * before the first occurrence of the specified character (afterChar)
     * in the linked-list string to which str refers.
     * If beforeChar is not in the string, the method adds the character
     * to the end of the string. Returns a reference to the first node
     * in the modified linked list, because the first node can change.
     */
    public static StringNodeBackup insertBefore(StringNodeBackup str, char newChar, 
                                         char beforeChar) 
    {
        StringNodeBackup newNode = new StringNodeBackup(newChar, null);
        
        // If the string is empty or beforeChar is in the current first node, 
        // return the new node, which is the new first node.
        if (str == null) {
            return newNode;
        } else if (str.ch == beforeChar) {
            newNode.next = str;
            return newNode;
        }
        
        StringNodeBackup trail = null;
        StringNodeBackup trav = str;
        while (trav != null) {
            if (trav.ch == beforeChar) {
                // Perform the insertion. Given the else-if check above,
                // we know that trail is non-null if we get here,
                // so we don't need to worry about a null-pointer exception.
                trail.next = newNode;
                newNode.next = trav; 
                
                // We're done. Return the first node as required.
                return str;
            }
            
            trail = trav;
            trav = trav.next;
        }
        
        // If we get here, we didn't find beforeChar,
        // so we insert the new node at the end of the list
        // by using trail, which is pointing to the current last node.
        trail.next = newNode;
        return str;
    }

    /**
     * insertChar - inserts the character ch before the character
     * currently in position i of the specified linked-list string.
     * Returns a reference to the resulting linked-list string.
     */
    public static StringNodeBackup insertChar(StringNodeBackup str, int i, char ch) {
        StringNodeBackup newNode, prevNode;

        if (i < 0) { 
            throw new IllegalArgumentException("invalid index: " + i);
        } else if (i == 0) {
            newNode = new StringNodeBackup(ch, str);
            str = newNode;
        } else {
            prevNode = getNode(str, i - 1);
            if (prevNode != null) {
                newNode = new StringNodeBackup(ch, prevNode.next);
                prevNode.next = newNode;
            } else {
                throw new IllegalArgumentException("invalid index: " + i);
            }
        }

        return str;
    }

    /**
     * insertSorted - inserts character ch in the correct position
     * in a sorted list of characters (i.e., a sorted linked-list string)
     * and returns a reference to the resulting list.
     */
    public static StringNodeBackup insertSorted(StringNodeBackup str, char ch) {
        StringNodeBackup newNode, trail, trav;

        // Find where the character belongs.
        trail = null;
        trav = str;
        while (trav != null && trav.ch < ch) {
            trail = trav;
            trav = trav.next;
        }

        // Create and insert the new node.
        newNode = new StringNodeBackup(ch, trav);
        if (trail == null) {
            // We never advanced the prev and trav references, so
            // newNode goes at the start of the list.
            str = newNode;
        } else { 
            trail.next = newNode;
        }
            
        return str;
    }

    /**
     * length - recursively determines the number of characters in the
     * linked-list string to which str refers
     */
    public static int length(StringNodeBackup str) {
        if (str == null) {
            return  0;
        } else {
            return 1 + length(str.next);
        }
    }

    /**
     * numOccur - find the number of occurrences of the character
     * ch in the linked list to which str refers
     */
    public static int numOccur(StringNodeBackup str, char ch) {
        if (str == null) {
            return 0;
        }
     
        int numInRest = numOccur(str.next, ch);
        if (str.ch == ch) {
            return 1 + numInRest;
        } else {
            return numInRest;
        }
    }

    /**
     * print - recursively writes the specified linked-list string to System.out
     */
    public static void print(StringNodeBackup str) {
        if (str == null) {
            return;
        } else {
            System.out.print(str.ch);
            print(str.next);
        }
    }

    /**
     * read - reads a string from an input stream and returns a
     * reference to a linked list containing the characters in the string
     */
    public static StringNodeBackup read(InputStream in) throws IOException { 
        char ch = (char)in.read();

        if (ch == '\n') {    // the string ends when we hit a newline character
            return null;         
        } else {
            StringNodeBackup restOfString = read(in);
            StringNodeBackup first = new StringNodeBackup(ch, restOfString);
            return first;
        }
    }
    
    /*
     * toString - creates and returns the Java string that
     * the current StringNodeBackup represents.  Note that this
     * method -- unlike the others -- is a non-static method.
     * Thus, it will not work for empty strings, since they
     * are represented by a value of null, and we can't use
     * null to invoke this method.
     */
    public String toString() {
        String str = "";
        
        StringNodeBackup trav = this;   // start trav on the current node    
        while (trav != null) {
            str = str + trav.ch;
            trav = trav.next;
        }
         
        return str;
    }
    
    /**
     * toUpperCase - converts all of the characters in the specified
     * linked-list string to upper case.  Modifies the list itself,
     * rather than creating a new list.
     */
    public static void toUpperCase(StringNodeBackup str) {        
        StringNodeBackup trav = str; 
        while (trav != null) {
            trav.ch = Character.toUpperCase(trav.ch); 
            trav = trav.next;
        }
    } 
              
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        
        // toUpperCase
        StringNodeBackup str = StringNodeBackup.convert("fine");
        System.out.print("Here's a string: "); 
        System.out.println(str);    // implicit toString call
        System.out.print("Here it is in upper-case letters: "); 
        StringNodeBackup.toUpperCase(str);
        System.out.println(str);
        
        // numOccur
        System.out.print("Enter a string: ");
        String s = in.nextLine();
        StringNodeBackup str1 = StringNodeBackup.convert(s);
        System.out.print("\nWhat character to count? ");
        char ch = in.nextLine().charAt(0);
        int count = StringNodeBackup.numOccur(str1, ch);
        System.out.println("There are " + count + " occurrences of " + ch);

        // copy and deleteChar
        int n = -1;
        while (n < 0) {
            System.out.print("\nWhat # character to delete (>= 0)? ");
            n = in.nextInt();
            in.nextLine();
        }
        StringNodeBackup copyStr1 = StringNodeBackup.copy(str1);
        try {
            str1 = StringNodeBackup.deleteChar(str1, n);
            StringNodeBackup.print(str1);
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }

        // The copy should be unchanged!
        str1 = copyStr1;
        System.out.print("\nReturning to the unchanged copy: ");
        System.out.println(copyStr1);
        
        // compareAlpha
        System.out.print("\nType another string: ");
        s = in.nextLine();
        StringNodeBackup str2 = StringNodeBackup.convert(s);
        System.out.print("\ncomparing " + str1 + " and " + str2 + " gives: ");
        System.out.println(StringNodeBackup.compareAlpha(str1, str2));   
        
        // insertBefore
        System.out.print("What character to insert? ");
        ch = in.nextLine().charAt(0);
        System.out.print("\nWhat character to insert before? ");
        char before = in.nextLine().charAt(0);
        str1 = StringNodeBackup.insertBefore(str1, ch, before);
        System.out.println(str1);
    }
}
