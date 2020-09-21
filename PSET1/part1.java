 /*
  * PSET 1
  * by William Burke (wburke@g.harvard.edu)
  * 9/7/2020
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
        int[] a = {5, 4, 3, 2, 1};
        int[] b = {5, 4, 3, 2, 1};
        int[] c = a;
        
        System.out.println("b.length = " + b.length);

        for (int i = 0; i < b.length; i++) {
            c[i] = b[i];
            System.out.println("i = " + i);
        }

        b[3] += b.length;
        a[3]--;
        
        System.out.println("a = " + Arrays.toString(a));
        System.out.println("b = " + Arrays.toString(b));
        System.out.println("c = " + Arrays.toString(c));

        System.out.println(a[3] + " " + b[3] + " " + c[3]);
        
        int[] values = {0, 2, 4, 6, 8, 10};
        System.out.println("values = " + Arrays.toString(values));
        shiftRight(values);
        System.out.println("values = " + Arrays.toString(values));
        try {
            shiftRight(null);
        }
        catch(IllegalArgumentException e) {
            System.out.println("IllegalArgumentException");
        }
        int[] values0 = {4};
        System.out.println("values = " + Arrays.toString(values0));
        shiftRight(values0);
        System.out.println("values = " + Arrays.toString(values0));
        int[] values1 = {};
        System.out.println("values = " + Arrays.toString(values1));
        shiftRight(values1);
        System.out.println("values = " + Arrays.toString(values1));
        
        int[] list1 = {1, 3, 6};
        int[] list2 = {1, 3, 5, 8, 12, 1, 3, 17, 1, 3, 6, 9, 1, 3, 6};
        System.out.println("index = " + indexOf(list1, list2));
        System.out.println("index = " + indexOf(list2, list1));
        System.out.println("index = " + indexOf(a, c));
        
        System.out.println("mystery(5,6) = " + mystery(5,6));
        //System.out.println("mystery(5,5) = " + mystery(5,5));
        //mystery(-1,-1);
        
        Integer[] list3 = {1, 3, 5, 8, 12, 1, 3, 17, 1, 3, 6, 9, 1, 3, 6};
        System.out.println("search(12,list3) = " + search(12,list3));
        System.out.println("search(121,list3) = " + search(121,list3));
        System.out.println("search(12,list3.0) = " + search(12,list3,0));
        System.out.println("search(121,list3,0) = " + search(121,list3,0));
        String[] list4 = {"my","dog","is"," a","naughty","puppy"};
        System.out.println("search(12,list4) = " + search(12,list4));
        System.out.println("search(12,list4) = " + search("puppy",list4));
        System.out.println("search(12,list4) = " + search("pupp",list4));
        System.out.println("search(12,list4,0) = " + search(12,list4,0));
        System.out.println("search(12,list4,0) = " + search("puppy",list4,0));
        System.out.println("search(12,list4,0) = " + search("pupp",list4,0));
        System.out.println("search(12,list4,0) = " + search("pupp",list4,-1));
        System.out.println("search(12,list4,0) = " + search("puppy",list4,5));
        System.out.println("search(12,list4,0) = " + search("puppy",list4,6));
        
    }

    //This method takes a reference to an array of integers and shifts all of the
    //array elements one position to the right, with the original last element wrapping
    //around to become the new first element. It throws an IllegalArgumentException if 
    //the input is null and does nothing if the length of the array is 0 or 1
    public static void shiftRight(int[] arr){
        //throw an exception if the input is null
        if (arr == null) throw new IllegalArgumentException("arr is null");
        
        //check the length and return if there is no required action
        if (arr.length < 2) return;
        
        //save the value of the last element
        int temp = arr[arr.length-1];
        for (int i = arr.length-1; i > 0; i--) { //loop backwards
            arr[i] = arr[i-1];//shift right
        }
        arr[0]=temp;//replace the first element
    }
  
    //This method takes two arrays of integers and that returns the index of the first
    //occurrence of the first list in the second list, or -1 if the first list does not
    //appear in the second list.
    public static int indexOf(int[] arr1, int[] arr2){
        //throw  exceptionw if the input is null
        if (arr1 == null) throw new IllegalArgumentException("arr is null");
        if (arr2 == null) throw new IllegalArgumentException("arr is null");
        
        int rval = -1; 
        for (int i = 0; i < arr2.length-arr1.length+1; i++){//the max length is arr2.length-arr1.length
            if (arr2[i]==arr1[0]){
                rval = i; //set the return value and cross your fingers
                for (int j = 1; j < arr1.length; j++){
                    if(arr2[i+j]!=arr1[j]){
                        rval = -1; //bad news, update the return value
                        break; //break out of the loop if they don't match
                    }
                }
                if (rval == i) return rval;//if you made it out without updating rval, you did it!
            }
        }
        return -1;
    }
    
    //mystery method
    public static int mystery(int a, int b) {
        System.out.println("a = " + a + ", b = " + b);
        if (a * b == 0) {
            return a;
        } else {
            int myst_rest = mystery(a - 1, b - 2);
            return b + myst_rest;
        }
    }
    
    //the following method uses iteration (a for loop) to search for an object in
    //an array of objects. The method returns true if the item is found in the array,
    //and false if it is not.
    public static boolean search(Object item, Object[] arr) {
        if (item == null) throw new IllegalArgumentException("item is null");
        if (arr == null) throw new IllegalArgumentException("arr is null");
        for (int i = 0; i< arr.length; i++) {
            if (item.equals(arr[i])) { //use the built-in equals method
                return true;
            }
        }

    return false;
    }
    
    //the following method uses recursion (not a for loop) to search for an object in
    //an array of objects. The method returns true if the item is found in the array,
    //and false if it is not. It takes an integer parameter that tells it where to start
    public static boolean search(Object item, Object[] arr, int start) {
        if (item == null) throw new IllegalArgumentException("item is null");
        if (arr == null) throw new IllegalArgumentException("arr is null");
        
        if (start > arr.length - 1 || start < 0) return false; //base case
        
        if (item.equals(arr[start])) { //use the built-in equals method
            return true;
        } else {
            return search(item, arr, start + 1);
        }
    }
}


    
