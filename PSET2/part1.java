 /*
  * PSET 2
  * by William Burke (wburke@g.harvard.edu)
  * 9/28/2020
  *
  * This class tests the code for part1 of the pset
  *
  */

import java.util.*;

/**
 * all of the code
 */
public class part1  {
    public static void main(String[] args) {
        int[] arry = {10, 6, 2, 5, 6, 6, 8, 10, 5};
        System.out.println("numDuplicatesA = " + numDuplicatesA(arry));
        
        
        generateSums(7);
        generateSums2(7);
        

    }
    
    public static int numDuplicatesA(int[] arr) {
        int numDups = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] == arr[i]) {
                    numDups++;
                    break;
                }
            }
        }
        return numDups;
    }
    
    public static void generateSums(int n) {
        for (int i = 1; i <= n; i++) {
            int sum = 0;
            for (int j = 1; j <= i; j++) {
                sum = sum + j; // how many times is this executed?
            }
            System.out.println(sum);
        }
    }
    
    public static void generateSums2(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum = sum + i;
            System.out.println(sum);
        }
    }
    

}




    
