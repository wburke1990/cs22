/* 
 * StringRecursion.java
 * 
 * Computer Science E-22
 *
 * Modified by: William Burke, wburke@g.harvard.edu
 */

import java.util.*;

/**
 * An implementation of the Bag ADT using an array.
 */
public class StringRecursion {
    
    //This method should use recursion to print the individual characters in the string str, separated by commas. All 
    //characters in the string should be printed, not just the letters. For example, printLetters("Rabbit") should print
        //R, a, b, b, i, t
    //and printLetters("I like to recurse!") should print
        //I,  , l, i, k, e,  , t, o,  , r, e, c, u, r, s, e, !
    //Note that there is a single space between each comma and the subsequent character. The method should not do any 
    //printing if the empty string ("") or the value null is passed in as the parameter; it should simply return.
    public static void printLetters(String str) {
        if (str == null || str == ""){
            return;
        } else if (str.length() == 1) {
            System.out.print(str.charAt(0));
            return;
        } else {
            System.out.print(str.charAt(0) + ", ");
            printLetters(str.substring(1));
            return;
        }
    }
    
    //This method should use recursion to return a String that is formed by replacing all occurrences of the character 
    //oldChar in the string str with the character newChar. For example:
            //replace("base case", 'e', 'y') should return "basy casy"
            //replace("base case", 'r', 'y') should return "base case"
    //This method should not do any printing; it should simply return the resulting string.    
    //Special cases:
                //If the first parameter is null, the method should return null.
                //If the first parameter is the empty string (""), the method should return the empty string.
    public static String replace(String str, char oldChar, char newChar){
        if (str == null){
            return null;
        } else if (str == "") {
            return "";
        } else if (str.length() == 1) {
            if (str.charAt(0) == oldChar) {
                return newChar + "";
            } else {
                return str.charAt(0) + "";
            }
        } else {
            if (str.charAt(0) == oldChar) {
                return newChar + replace(str.substring(1), oldChar, newChar);
            } else {
                return str.charAt(0) + replace(str.substring(1), oldChar, newChar);
            }
        }
    }
               
    //This method should use recursion to find and return the index of the first occurrence of the character ch in the 
    //string str, or -1 if ch does not occur in str. For example:
                //indexOf('b', "Rabbit") should return 2
                //indexOf('P', "Rabbit") should return -1
    //The method should return -1 if the empty string ("") or the value null is passed in as the second parameter. The 
    //String class comes with a built-in indexOf() method; you may not use that method in your solution!
    public static int indexOf(char ch, String str){
        if (str == null || str == "") {
            return -1;
        } else if (str.length() == 1) {
            if (str.charAt(0) == ch) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (str.charAt(0) == ch) {
                return 0;
            } 
            int temp = indexOf(ch,str.substring(1));
            if ( temp == -1) {
                return -1;
            } else {
                return 1 + temp;
            }
        }
    }
    
    /* Test the StringRecursion implementation. */
    public static void main(String[] args) {
        printLetters("Rabbit");
        System.out.println();
        printLetters(null);
        System.out.println();
        printLetters("");
        System.out.println();
        printLetters("I like to recurse!");
        System.out.println();
        printLetters("1");
        System.out.println();
        System.out.println(replace("base case", 'e', 'y'));
        System.out.println(replace("", 'e', 'y'));
        System.out.println(replace(null, 'r', 'y'));
        System.out.println(replace("base case", 'r', 'y'));
        System.out.println("base case" == "base case" + "");
        System.out.println(indexOf('b', "Rabbit"));
        System.out.println(indexOf('P', ""));
        System.out.println(indexOf('P', null));
        System.out.println(indexOf('P', "Rabbit"));
    }
}