/*
 * ps5.java
 *
 * Computer Science E-22
 *
 * Modifications and additions by:
 *     name: William Burke
 *     username: wburke1990
 */

import java.util.*;

public class ps5 {
    public static boolean isHeap(int[] arr) {
        if (arr == null) {
            return false;
        }

        return isHeapTree(arr, 0);
    }

    private static boolean isHeapTree(int[] arr, int i) {
        //since this is a private method, i always starts out at 0
        //check the length of the array
        int arrlength = arr.length; 
        
        //check if we are in a leaf node
        if (2*i + 1 > arrlength) {
            return true; //leaf node or empty array
        } else if (2*i + 2 > arrlength && arr[i] > arr[2*i + 1]) {
            return isHeapTree(arr, 2*i + 1); //one child
        } else if (2*i + 1 < arrlength && 2*i + 2 <= arrlength && arr[i] > arr[2*i + 1] && arr[i] > arr[2*i + 2]) {
            return (isHeapTree(arr, 2*i + 1) && isHeapTree(arr, 2*i + 2)); //2 children
        } else {
            return false; //not a heap
        }
        
    }

    public static void main(String[] args) {
        int[] arr = {48, 25, 16, 10, 18, 36};
        
        System.out.println(isHeapTree(arr, 1));
        System.out.println(isHeapTree(arr, 2));
        System.out.println(isHeapTree(arr, 0));
    }

}


