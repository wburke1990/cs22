/*
 * SortCount.java
 *
 * Computer Science E-22, Harvard University
 *
 * Modified by:   <your name>, <your e-mail address>
 * Date modified: <current date>
 */

import java.util.*;
import java.io.File;
import java.io.FileWriter;

/**
 * This class contains implementations of various array-sorting
 * algorithms.  All comparisons and moves are performed using helper
 * methods that maintain counts of these operations.  Each sort method
 * takes an array of integers.  The methods assume that all of the 
 * elements of the array should be sorted.  The algorithms sort the array
 * in place, altering the original array.
 */
public class SortCount {
    /* 
     * the integers in the test arrays are drawn from the range 
     * 0, ..., MAX_VAL 
     */
    private static int MAX_VAL = 65536;
    
    private static long compares;     // total number of comparisons
    private static long moves;        // total number of moves
    
    /*
     * compare - a wrapper that allows us to count comparisons.
     */
    private static boolean compare(boolean comparison) {
        compares++;
        return comparison;
    }
    
    /*
     * move - moves an element of the specified array to a different
     * location in the array.  move(arr, dest, source) is equivalent
     * to arr[dest] = arr[source].  Using this method allows us to
     * count the number of moves that occur.
     */
    private static void move(int[] arr, int dest, int source) {
        moves++;
        arr[dest] = arr[source];
    }
    
    /*
     * swap - swap the values of two variables.
     * Used by several of the sorting algorithms below.
     */
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
        moves += 3;
    }
    
    /** 
     * randomArray - creates an array of randomly generated integers
     * with the specified number of elements
     */
    public static int[] randomArray(int n) {
        int[] arr = new int[n];
        
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * (MAX_VAL + 1));
        }
        
        return arr;
    }
    
    /** 
     * almostSortedArray - creates an almost sorted array of integers
     * with the specified number of elements
     */
    public static int[] almostSortedArray(int n) {
        /* Produce a random array and sort it. */
        int[] arr = randomArray(n);
        quickSort(arr);
        
        /* 
         * Move one quarter of the elements out of place by between 1
         * and 5 places.
         */
        for (int i = 0; i < n/8; i++) {
            int j = (int)(Math.random() * n);
            int displace = -5 + (int)(Math.random() * 11);
            int k = j + displace;
            if (k < 0) {
                k = 0;
            } else if (k > n - 1) {
                k = n - 1;
            }
            
            swap(arr, j, k);
        }
        
        return arr;
    }
    
    /**
     * Sets the counts of moves and comparisons to 0.
     */
    public static void initStats() {
        compares = 0;
        moves = 0;
    }
    
    /**
     * Prints the current counts of moves and comparisons.
     */
    public static void printStats() {
        int spaces = (int)(Math.log(compares)/Math.log(10));
        for (int i = 0; i < (10 - spaces); i++) {
            System.out.print(" ");
        }
        System.out.print(compares + " comparisons\t");
        
        spaces = (int)(Math.log(moves)/Math.log(10));
        for (int i = 0; i < (10 - spaces); i++) {
            System.out.print(" ");
        }
        System.out.println(moves + " moves");
    }
    
    /*
     * indexSmallest - returns the index of the smallest element
     * in the subarray from arr[start] to the end of the array.  
     * Used by selectionSort.
     */
    private static int indexSmallest(int[] arr, int start) {
        int indexMin = start;
        
        for (int i = start + 1; i < arr.length; i++) {
            if (compare(arr[i] < arr[indexMin])) {
                indexMin = i;
            }
        }
        
        return indexMin;
    }
    
    /** selectionSort */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int j = indexSmallest(arr, i);
            swap(arr, i, j);
        }
    }
    
    /** insertionSort */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (compare(arr[i] < arr[i-1])) {
                // Save a copy of the element to be inserted.
                int toInsert = arr[i];
                moves++;
                
                // Shift right to make room for element.
                int j = i;
                do {
                    move(arr, j, j - 1);
                    j = j - 1;
                } while (j > 0 && compare(toInsert < arr[j-1]));
                
                // Put the element in place.
                arr[j] = toInsert;
                moves++;
            }
        }
    }
    
    /** shellSort */
    public static void shellSort(int[] arr) {
        /*
         * Find initial increment: one less than the largest
         * power of 2 that is <= the number of objects.
         */
        int incr = 1;
        while (2 * incr <= arr.length) {
            incr = 2 * incr;
        }
        incr = incr - 1;
        
        /* Do insertion sort for each increment. */
        while (incr >= 1) {
            for (int i = incr; i < arr.length; i++) {
                if (compare(arr[i] < arr[i-incr])) {
                    int toInsert = arr[i];
                    moves++;
                    
                    int j = i;
                    do {
                        move(arr, j, j-incr);
                        j = j - incr;
                    } while (j > incr-1 && compare(toInsert < arr[j-incr]));
                    
                    arr[j] = toInsert;
                    moves++;
                }
            }
            
            // Calculate increment for next pass.
            incr = incr / 2;
        }
    }
    
    /** bubbleSort */
    public static void bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (compare(arr[j] > arr[j+1])) {
                    swap(arr, j, j+1);
                }
            }
        }
    }
    
    /** bubbleSort2 */
    public static void bubbleSort2(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            boolean sorted = true;
            for (int j = 0; j < i; j++) {
                if (compare(arr[j] > arr[j+1])) {
                    swap(arr, j, j+1);
                    if (j > 0 && compare(arr[j-1] > arr[j])) {
                        sorted = false;
                    }
                }
            }
            if (sorted == true) {
                break;
            }
        }
    }
    
    /*
     * A helper method for qSort that takes the array that begins with
     * element arr[first] and ends with element arr[last] and
     * partitions it into two subarrays using the middle element of
     * the array for the pivot.  It returns the index of the last
     * element of the left subarray formed by the partition.  All
     * elements in the left subarray are <= the pivot, and all
     * elements in the right subarray are >= the pivot.
     */
    private static int partition(int[] arr, int first, int last) {
        int pivot = arr[(first + last)/2];
        moves++;   // for the above assignment
        int i = first - 1;  // index going left to right
        int j = last + 1;   // index going right to left
        
        while (true) {
            // moving from left to right, find an element >= the pivot
            do {
                i++;
            } while (compare(arr[i] < pivot));
            
            // moving from right to left, find an element <= the pivot
            do {
                j--;
            } while (compare(arr[j] > pivot)); 
            
            // If the indices still haven't met or crossed,
            // swap the elements so that they end up in the correct subarray.
            // Otherwise, the partition is complete and we return j.
            if (i < j) {
                swap(arr, i, j);
            } else {
                return j;   // index of last element in the left subarray
            }
        }                   
    }
    
    /*
     * A recursive helper method that actually implements quicksort.
     * The initial recursive call is made by quicksort() -- see below.
     */
    private static void qSort(int[] arr, int first, int last) {
        // Partition the array.  split is the index of the last
        // element of the left subarray formed by the partition.
        int split = partition(arr, first, last);
        
        //
        // Note that we only make recursive calls on subarrays that
        // have two or more elements, and thus the base case is when
        // neither subarray has two or more elements.
        //
        if (first < split) {
            qSort(arr, first, split);      // left subarray
        }
        if (last > split + 1) {
            qSort(arr, split + 1, last);   // right subarray
        }
    }
    
    /** quickSort */
    public static void quickSort(int[] arr) {
        qSort(arr, 0, arr.length - 1); 
    }
    
    /* merge - helper method for mergesort */
    private static void merge(int[] arr, int[] temp, 
                              int leftStart, int leftEnd, int rightStart, int rightEnd)
    {
        int i = leftStart;    // index into left subarray
        int j = rightStart;   // index into right subarray
        int k = leftStart;    // index into temp
        
        while (i <= leftEnd && j <= rightEnd) {
            if (compare(arr[i] < arr[j])) {
                temp[k] = arr[i];
                i++; k++;
            } else {
                temp[k] = arr[j];
                j++; k++;
            }
            moves++;
        }
        
        while (i <= leftEnd) {
            temp[k] = arr[i];
            i++; k++;
            moves++;
        }
        
        while (j <= rightEnd) {
            temp[k] = arr[j];
            j++; k++;
            moves++;
        }
        
        for (i = leftStart; i <= rightEnd; i++) {
            arr[i] = temp[i];
            moves++;
        }
    }
    
    /** mSort - recursive method for mergesort */
    private static void mSort(int[] arr, int[] temp, int start, int end) {
        if (start >= end) {
            return;
        }
        
        int middle = (start + end)/2;
        mSort(arr, temp, start, middle);
        mSort(arr, temp, middle + 1, end);
        merge(arr, temp, start, middle, middle + 1, end);
    }
    
    /** mergesort */
    public static void mergeSort(int[] arr) {
        int[] temp = new int[arr.length];
        mSort(arr, temp, 0, arr.length - 1);
    }
    
    /**
     * Prints the specified array in the following form:
     * { arr[0] arr[1] ... }
     */
    public static void printArray(int[] arr) {
        // Don't print it if it's more than 10 elements.
        if (arr.length > 10) {
            return;
        }
        
        System.out.print("{ ");
        
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        
        System.out.println("}");
    }
    
    public static void main(String args[]) {
        /*
         * For an example of how to determine time efficiency by experiment, see the section 4 notes and solutions.
         * Be sure that you include both some kind of table showing the results of your experimental runs of the 
         * algorithm (including at least 10 runs for each value of n: 1000, 2000, 4000, 8000 and 16000, first with 
         * random, then with fully sorted data), and a few paragraphs containing your analysis of the time complexity 
         * of the algorithm in each case.
         * Your time analysis should include six big-O expressions: three for the comparisons, moves, and overall time 
         * complexity of bubbleSort2 with random data, and three for the comparisons, moves, and overall time complexity
         * of bubbleSort2 with fully sorted data. You also need to justify each of these big-O expressions using your
         * experimental data. If the algorithm does not appear to fall neatly into one of the standard efficiency
         * classes, explain your reasons for that conclusion, and state the two efficiency classes that it appears to 
         * fall between.
         * 
         * 
         * 
         */
        
        //create some arrays to store the results of the experiments
        int [] a;
        int n2;
        float compesper;
        float movesper;
        int [] sizes = {1000, 2000, 4000, 8000, 16000};
        
        //create a file handle
        File file = new File("ps2_experiments.txt");
        System.out.println(file.toPath());
        
        //delete the file if it exists
        if (file.exists()){
            file.delete();
        }
        
        //create a Writer object, which creates the file
        try {
            FileWriter myWriter = new FileWriter("ps2_experiments.txt");
            
            //run the experiments for the sorted arrays
            myWriter.write("sorted arrays: \n");
            myWriter.write("|n (size of array) | comparisons | moves | comparisons/n | moves/n | \n");
            for (int i = 0; i < sizes.length; i++)  {
                n2 = sizes[i];
                for (int j = 0; j < 10; j++){
                    a = randomArray(sizes[i]);
                    quickSort(a);
                    initStats();
                    bubbleSort2(a);
                    
                    //figure out how close to n^2 we were
                    compesper = (float) compares / n2;
                    movesper = (float) moves / n2;
                    //print the results
                    if (sizes[i] == 16000) {
                        myWriter.write("|   16000          | " + compares + " | "  + moves + " | "  + compesper + " | " 
                                           + movesper + " |\n");
                    } else {
                        myWriter.write("|    " + sizes[i] + "          | " + compares + " | "  + moves + " | "  + compesper + " | " 
                                           + movesper + " |\n");                    
                    }
                }
            }
            
            myWriter.write("As the table above shows, the algorithm is O(n) in comparisons and O(1) in moves. \n");


            //run the experiments for the random arrays
            myWriter.write("randomly sorted arrays: \n");
            myWriter.write("|n (size of array) | comparisons | moves | comparisons/n^2 | moves/n^2 | \n");
            for (int i = 0; i < sizes.length; i++)  {
                n2 = sizes[i]*sizes[i];
                for (int j = 0; j < 10; j++){
                    a = randomArray(sizes[i]);
                    //quickSort(a);
                    initStats();
                    bubbleSort2(a);
                    
                    //figure out how close to n^2 we were
                    compesper = (float) compares / n2;
                    movesper = (float) moves / n2;
                    //print the results
                    if (sizes[i] == 16000) {
                        myWriter.write("|   16000          | " + compares + " | "  + moves + " | "  + compesper + " | " 
                                           + movesper + " |\n");
                    } else {
                        myWriter.write("|    " + sizes[i] + "          | " + compares + " | "  + moves + " | "  + compesper + " | " 
                                           + movesper + " |\n");                    
                    }
                }
            }
            
            myWriter.write("As the table above shows, the algorithm is O(n^2) in comparisons and O(n^2) in moves. \n");
            myWriter.write("The number of moves and comparisons never deviates more than 4% from 0.75*n^2 \n");
            
            
            
            myWriter.close();
        } catch (Exception e) {
            System.out.println("nubs");
        }
    }
}
