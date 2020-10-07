 /*
  * PSET 2
  * by William Burke (wburke@g.harvard.edu)
  * 9/28/2020
  *
  * This class contains the code for problem 2.8 of the pset
  *
  */

import java.util.*; //import the java.util package to gain access to the Arrays class.

/**
 * all of the code
 */
public class Problem8  {
    
    /**
     * findIntersect() that takes two arrays of integers as parameters
     * and uses an approach based on merging to find and return the
     * intersection of the two arrays.
     * 
     * More specifically, this method begins by creating a new array
     * for the intersection, giving it the length of the smaller of the
     * two arrays. Next, is uses one of the more efficient sorting
     * algorithms from our Sort class to sort both of the arrays.
     * Finally, it finds the intersection of the two arrays by employing
     * an approach that is similar to the one used to merge two sorted
     * subarrays (i.e., the approach taken by the merge method in
     * Sort.java). The method doesuld not actually merge the two arrays,
     * but it takes a similar approach—using indices to “walk down” the
     * two arrays, and making use of the fact that the arrays are sorted.
     * As the elements of the intersection are found, it puts them in the
     * array that it created at the start of the method. At the end of
     * the method, it return a reference to the array containing the
     * intersection.
     * 
     * The intersection that is created does not have any duplicates,
     * and the algorithm is as efficient as possible. In particular, it
     * performs at most one complete pass through each of the arrays. 
     * 
     */ 
    public static int[] findIntersect(int[] arr1, int[] arr2){
        //check the input
        if (arr1 == null || arr2 == null || arr1.length == 0 || arr2.length == 0){
            return new int[0]; //intersection is empty array
        }
        
        //the newarray that will be returned
        int[] newarr = new int[Math.min(arr1.length,arr2.length)];
        
        //sort the input using mergesort
        Sort.mergeSort(arr1);
        Sort.mergeSort(arr2);
        
        int i = 0;  // index for arr1
        int j = 0;  // index for arr2
        int k = 0;  // index for newarr
        
        while (k < newarr.length && i < arr1.length && j < arr2.length) {
            //add an element to the newarr
            if (arr1[i]==arr2[j]){
                newarr[k]=arr1[i];
                i++;
                j++;
                k++;
            }
            
            //increment i and j
            while (i < arr1.length && arr1[i]<arr2[j]) {
                i++;
            }
            while (i < arr1.length && j < arr2.length && arr2[j]<arr1[i]) {
                j++;
            }
        }

        //return a reference to the new array
        return newarr;
    }
    
    /**
     * test code
     */    
    public static void main(String[] arr) { 
        
        //The test code for the findIntersect method to the main method. 
        //It uses the Arrays.toString() method to convert an array to a string;
        int[] a1 = {10, 5, 7, 5, 9, 4};
        int[] a2 = {7, 5, 15, 7, 7, 9, 10};
        int[] result1 = Problem8.findIntersect(a1, a2);
        Sort.printArray(result1); //{ 5, 7, 9, 10, 0, 0 }
            
        //second recommended test
        int[] a3 = {0, 2, -4, 6, 10, 8};
        int[] a4 = {12, 0, -4, 8};
        int[] result2 = Problem8.findIntersect(a3, a4);
        Sort.printArray(result2); //{-4, 0, 8, 0}
        
        //another  test
        int[] a5 = {2, 3, 4, 5};
        int[] a6 = {1, 3, 5};
        int[] result3 = Problem8.findIntersect(a5, a6);
        Sort.printArray(result3); //{3, 5, 0}
    }

}