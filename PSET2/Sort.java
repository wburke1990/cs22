/*
 * Sort.java
 *
 * Computer Science E-22, Harvard University
 *
 * ======IMPORTANT: Your solution to Problem 9 should go 
 * in SortCount.java, rather than in this file.========
 * 
 * You should not need to modify this file.
 *
 * To call one of these methods from another class, precede its name
 * by the name of the class.  For example:
 *
 *     Sort.bubbleSort(arr);
 */

/**
 * Sort - a class containing implementations of various array-sorting
 * algorithms.  Each method takes an array of ints.  The methods
 * assume that the array is full.  They sort the array in place,
 * altering the original array.
 */
public class Sort {
    public static final int NUM_ELEMENTS = 20;
    
    /*
     * swap - swap the values of arr[a] and arr[b].
     * Used by several of the sorting algorithms below.
     */
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
    
    /*
     * indexSmallest - returns the index of the smallest element
     * in the subarray from arr[start] to the end of the array.  
     * Used by selectionSort.
     */
    private static int indexSmallest(int[] arr, int start) {
        int indexMin = start;
        
        for (int i = start + 1; i < arr.length; i++) {
            if (arr[i] < arr[indexMin]) {
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
            if (arr[i] < arr[i-1]) {
                // Save a copy of the element to be inserted.
                int toInsert = arr[i];
                
                // Shift right to make room for element.
                int j = i;
                do {
                    arr[j] = arr[j - 1];
                    j = j - 1;
                } while (j > 0 && toInsert < arr[j-1]);
                
                // Put the element in place.
                arr[j] = toInsert;
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
                if (arr[i] < arr[i-incr]) {
                    int toInsert = arr[i];
                    
                    int j = i;
                    do {
                        arr[j] = arr[j - incr];
                        j = j - incr;
                    } while (j > incr-1 &&
                             toInsert < arr[j-incr]);
                    
                    arr[j] = toInsert;
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
                if (arr[j] > arr[j+1]) {
                    swap(arr, j, j+1);
                }
            }
        }
    }
    
    /* partition - helper method for qSort */
    public static int partition(int[] arr, int first, int last) {
        int pivot = arr[(first + last)/2];
        int i = first - 1;  // index going left to right
        int j = last + 1;   // index going right to left
        
        while (true) {
            // moving from left to right, find an element >= the pivot
            do {
                i++;
            } while (arr[i] < pivot);
            
            // moving from right to left, find an element <= the pivot
            do {
                j--;
            } while (arr[j] > pivot); 
            
            // If the indices still haven't met or crossed,
            // swap the elements so that they end up in the correct subarray.
            // Otherwise, the partition is complete and we return j.
            if (i < j) {
                swap(arr, i, j);
            } else {
                return j;     // index of last element in the left subarray
            }
        }
    }
    
    /* qSort - recursive method that does the work for quickSort */
    private static void qSort(int[] arr, int first, int last) {
        int split = partition(arr, first, last);
        
        if (first < split) {
            qSort(arr, first, split);      // left subarray
        }
        if (last > split + 1) {
            qSort(arr, split + 1, last);   // right subarray
        }
    }
    
    /** quicksort */
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
            if (arr[i] < arr[j]) {
                temp[k] = arr[i];
                i++; k++;
            } else {
                temp[k] = arr[j];
                j++; k++;
            }
        }
        
        while (i <= leftEnd) {
            temp[k] = arr[i];
            i++; k++;
        }
        while (j <= rightEnd) {
            temp[k] = arr[j];
            j++; k++;
        }
        
        for (i = leftStart; i <= rightEnd; i++) {
            arr[i] = temp[i];
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
     * printArray - prints the specified array in the following form:
     * { arr[0] arr[1] ... }
     */
    public static void printArray(int[] arr) {
        System.out.print("{ ");
        
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        
        System.out.println("}");
    }
    
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
        mergeSort(arr); //mergesort

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
    
    public static void main(String[] arr) { 
        int[] orig = new int[NUM_ELEMENTS];
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            orig[i] = (int)(50 * Math.random());
        }
        printArray(orig);
        
        int[] copy = new int[NUM_ELEMENTS];
        
        /* selection sort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        selectionSort(copy);
        System.out.print("selection sort:\t");
        printArray(copy);
        
        /* insertion sort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        insertionSort(copy);
        System.out.print("insertion sort:\t");
        printArray(copy);
        
        /* Shell sort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        shellSort(copy);
        System.out.print("Shell sort:\t");
        printArray(copy);
        
        /* bubble sort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        bubbleSort(copy);
        System.out.print("bubble sort:\t");
        printArray(copy);
        
        /* quicksort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        quickSort(copy);
        System.out.print("quicksort:\t");
        printArray(copy);
        
        /* mergesort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        mergeSort(copy);
        System.out.print("mergesort:\t");
        printArray(copy);
        
        /* pairSums */
        int[] testarray = {10, 4, 7, 7, 8, 5, 15};
        //pairSums(12,testarray);
        
        /* pairSumsImproved */
        pairSumsImproved(20,testarray);
        pairSumsImproved(12,testarray);
    }
}
