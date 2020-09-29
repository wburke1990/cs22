 /*
  * PSET 2
  * by William Burke (wburke@g.harvard.edu)
  * 9/28/2020
  *
  * This class contains the code for problem 2.7 of the pset
  *
  */

import java.util.*;
//import Sort.Sort;

/**
 * all of the code
 */
public class Problem7  {
    
    
    /**
     * pairSums - find all pairs of values in the array (if any) that sum to a given integer k
     * and then prints them in the following form:
     * 4 + 8 = 12
     * 7 + 5 = 12
     * 7 + 5 = 12
     * Note that we get two 7 + 5 sums because 7 appears twice in the array.
     * The order in which the sums (and the terms within each sum) is the order in which they appear in the array.
     * This method does not have a return value
     * 
     * COMMENTS: This algorithm is O(n²). The first time through the outer lopp, the inner loop exectutes n-1 times. 
     * The second time through the outer loop, the inner loop exectures n-1 times, so the overall number of inner loop
     * exectutions is n(n-1)/2. O(n(n-1)/2) = O(n²)
     */
    public static void pairSums(int k, int[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] + arr[i] == k) {
                    System.out.println(arr[j] + " + " + arr[i] + " = " + k);
                }
            }
        }
    }
    
    /**
     * pairSumsImproved - find all pairs of values in the array (if any) that sum to a given integer k
     * and then prints them in the following form:
     * 4 + 8 = 12
     * 7 + 5 = 12
     * 7 + 5 = 12
     * Note that we get two 7 + 5 sums because 7 and 5 appear twice each in the array.
     * The order in which the sums (and the terms within each sum) is determined by the first, smallest term.
     * This method does not have a return value
     * 
     * COMMENTS: This algorithm is O(nlogn). Sorting the original array using mergesort is O(nlogn).
     * The while loop is O(n). i counts up from -1 and j counts down from n-1. At each run of the while loop, i 
     * increases by at least 1 and j decreases by at least 1. The while loop stops when j<i. So there will be n/2 
     * iterations in the worst case.
     * For the entire algorithm, we have O(nlogn + n) = O(nlogn)
     */
    public static void pairSumsImproved(int k, int[] arr){
        Sort.mergeSort(arr); //mergesort

        int i = -1;  // index going left to right
        int j = arr.length-1;   // index going right to left
        
        while (true) {
            // moving from left to right, find an element >= the pivot
            do {
                i++;
            } while (arr[i] < k-arr[j]);
            
            // If the indices still haven't met or crossed,
            // print the elements.
            if (arr[j] + arr[i] == k) {
                System.out.println(arr[j] + " + " + arr[i] + " = " + k);
            }
            
            // moving from right to left, find an element <= the pivot
            do {
                j--;
            } while (arr[j] > k-arr[i]); 
            
            // If the indices still haven't met or crossed,
            // print the elements.
            if (arr[j] + arr[i] == k) {
                System.out.println(arr[j] + " + " + arr[i] + " = " + k);
            }
            
            // If the scan is complete, we return j.
            if (j < i) {
                break;
            }
        }
    }
    
    /**
     * test code
     */    
    public static void main(String[] arr) { 
        
        /* pairSums */
        int[] testarray = {10, 4, 7, 7, 8, 5, 15};
        pairSums(12,testarray);
        
        /* pairSumsImproved */
        pairSumsImproved(20,testarray);
        pairSumsImproved(12,testarray);
    }
}