/* 
 * MagicSquare.java
 * 
 * Computer Science E-22
 * 
 * Modified by: <your name>, <your e-mail address>
 */

import java.util.*;

public class MagicSquare {
    // the current contents of the cells of the puzzle values[r][c]
    // gives the value in the cell at row r, column c
    private int[][] values;
    
    // the order (i.e., the dimension) of the puzzle
    private int order;
    
    // stores the desired sum of each row and column
    private int magicSum;
    
    // stores the current sum of each row
    private int[] rowSum;
    
    // stores the current sum of each column
    private int[] columnSum;
    
    // stores a boolean value of the available numbers
    private int[] numbers;
    
    // stores the min available number and its index
    private int min;
    
    // stores the max available number
    private int max;
    
    /*
     * Creates a MagicSquare object for a puzzle with the specified
     * dimension/order.
     */
    public MagicSquare(int order) {
        this.values = new int[order][order];
        this.order = order;
        
        // store the sum
        this.magicSum = (order*order*order + order)/2;
            
        // initialize the row and column sums
        this.rowSum = new int[order];
        this.columnSum = new int[order];
        
        //initialize the available numbers
        this.numbers = new int[order*order];
        this.min = 1;
        this.max = order*order;
    }
    
    /*
     * This method should call the separate recursive-backtracking method
     * that you will write, passing it the appropriate initial parameter(s).
     * It should return true if a solution is found, and false otherwise.
     */
    public boolean solve() {
        // Replace the line below with your implementation of this method.
        // REMEMBER: The recursive-backtracking code should NOT go here.
        // See the comments above.
        return solveLayer(0);
    }
    
    /*
     * This attempts to solve a row and a column of the grid
     * It returns true if it succeeds, and false otherwise.
     * Layer 0 corresponds to the first row and column
     * If the condition "layer == this.order" is reached,
     * we have solved the entire grid, so we return
     * After solveLayer(n) places a number on the diagonal, it calls solveRow to fill out the row, which calls solveColumn 
     * to fill out the column. solveColumn calls solveLayer(n), and so on until we reach the base case. We get a little
     * tricky to save steps as described in the 2 extra cases that I added to each of solveRow and solveColumn
     */
    private boolean solveLayer(int layer) {        
        if (layer == this.order){
            return true;
        } else {
            for (int k = this.min; k <= this.max; k++){
                if (this.addNumber(k, layer, layer)){
                    if (solveRow(layer,layer+1)) {
                        return true;
                    }
                    removeNumber(layer, layer);
                }
            }
        }
        return false;
    }
    
    /*
     * This attempts to solve row i of the the grid starting at position i,j and holding all indices <= j fixed.
     * It returns true if it succeeds, and false otherwise.
     * i,j == 0,0 corresponds to the first row and column
     * If the condition "j == this.order" is reached, we have solved the entire row, so we start the column
     */
    private boolean solveRow(int i, int j) {
        if (j == this.order){
            //you reached the end of the row; go do the next row
            return solveColumn(i+1,i);
        } else if (j == this.order - 1){
            //if you come in at this point, you need to place the difference between the row sum and the magicSum
            if (this.magicSum - this.rowSum[i] <= this.max
                    && this.magicSum - this.rowSum[i] >= this.min
                    && this.magicSum - this.rowSum[i] - 1 > 0
                    && this.addNumber(this.magicSum - this.rowSum[i], i, j)) {
                if (solveColumn(i+1,i)){
                    return true;
                }
                this.removeNumber(i, j);
            }
        } else if (j == this.order - 2){
            //if you come in at this point, you need to find a pair of numbers that are both available and sum to the 
            //difference between the row sum and the magicSum
            for (int num = this.min; num <= this.max; num++){
                if (this.numbers[num-1] == 0 
                        && this.magicSum - this.rowSum[i] - num <= this.max 
                        && this.magicSum - this.rowSum[i] - num >= this.min
                        && this.magicSum - this.rowSum[i] - num != num
                        && this.magicSum - this.rowSum[i] - num - 1 > 0
                        && this.numbers[this.magicSum - this.rowSum[i] - num - 1] == 0
                   ) {
                    if (this.addNumber(num, i, j)){
                        if (this.addNumber(this.magicSum - this.rowSum[i], i, j+1)){
                            if (solveColumn(i+1,i)){
                                return true;
                            }
                            this.removeNumber(i, j+1);
                        }
                        this.removeNumber(i, j);
                    }
                }
            }
        } else {
            //vanilla recusive case
            for (int k = this.min; k <= this.max; k++){
                if (this.addNumber(k, i, j)){
                    if (solveRow(i,j+1)){
                        return true;
                    }
                    this.removeNumber(i, j);
                }
            }
        }
        return false;
    }
    
    /*
     * This attempts to solve column j of the the grid starting at position i,j and holding all indices <= i fixed.
     * It returns true if it succeeds, and false otherwise.
     * i,j == 0,0 corresponds to the first row and column
     * If the condition "i == this.order - 1" is reached, we have solved the entire row, so we start on the next layer
     */
    private boolean solveColumn(int i, int j) {
        if (i == this.order){
            //you reached the end of the column; go do the next layer
            return solveLayer(j+1);
        } else if (i == this.order - 1){
            //if you come in at this point, you need to place the difference between the column sum and the magicSum
            if (this.magicSum - this.columnSum[j] <= this.max
                    && this.magicSum - this.columnSum[j] >= this.min
                    && this.magicSum - this.columnSum[j] - 1 > 0
                    && this.addNumber(this.magicSum - this.columnSum[j], i, j)){
                if (solveLayer(j+1)){
                    return true;
                }
                this.removeNumber(i, j);
            }
        } else if (i == this.order - 2){
            //if you come in at this point, you need to find a pair of numbers that are both available and sum to the 
            //difference between the column sum and the magicSum
            for (int num = this.min; num <= this.max; num++){
                if (this.numbers[num-1] == 0 
                        && this.magicSum - this.columnSum[j] - num <= this.max 
                        && this.magicSum - this.columnSum[j] - num >= this.min
                        && this.magicSum - this.columnSum[j] - num != num
                        && this.magicSum - this.columnSum[j] - num - 1 > 0
                        && this.numbers[this.magicSum - this.columnSum[j] - num - 1] == 0
                   ) {
                    if (this.addNumber(num, i, j)){
                        if (this.addNumber(this.magicSum - this.columnSum[j], i+1, j)){
                            if (solveLayer(j+1)){
                                return true;
                            }
                            this.removeNumber(i+1, j);
                        }
                        this.removeNumber(i, j);
                    }
                }
            }
        } else {
            //vanilla recusive case
            for (int num = this.min; num <= this.max; num++){
                if (this.addNumber(num, i, j)){
                    if (solveColumn(i+1,j)){
                        return true;
                    }
                    this.removeNumber(i, j);
                }
            }
        }
        return false;
    }
    
    /*
     * This attempts to add a number to a spot on the grid
     * It returns true if it succeeds, and false otherwise.
     */
    private boolean addNumber(int num, int i, int j) {
        if (this.numbers[num-1] == 0 && this.rowSum[i] + num <= this.magicSum 
                && this.columnSum[j] + num <= this.magicSum){
            this.values[i][j] = num;
            this.rowSum[i] = this.rowSum[i] + num;
            this.columnSum[j] = this.columnSum[j] + num;
            this.numbers[num-1] = 1;
            updateMinMax(num,"remove");
            return true;
        } else {
            return false;
        }
    }
    
    /*
     * This removes number from a spot on the grid and puts the number back in the array of number options
     */
    private void removeNumber(int i, int j) {
        int num = this.values[i][j];
        if(num > 0) {
            this.values[i][j] = 0;
            this.rowSum[i] = this.rowSum[i] - num;
            this.columnSum[j] = this.columnSum[j] - num;
            this.numbers[num-1] = 0;
            updateMinMax(num,"add");
        }
    }
    
    /*
     * This method finds the new min and max available unused number after an update
     */
    private void updateMinMax(int num, String flag) {
        return;
//        if (flag == "add") {
//            if (num < this.min) {
//                this.min = num;
//                return;
//            }
//            if (num > this.max) {
//                this.max = num;
//                return;
//            }
//        } else if (flag == "remove") {
//            if (num == this.min) {
//                for (int i = num-1; i<this.max; i++){
//                    if(numbers[i] == 0){
//                        this.min = i+1;
//                        break;
//                    }
//                }
//            }
//            if (num == this.max) {
//                for (int i = num-1; i>this.min-2; i--){
//                    if(numbers[i] == 0){
//                        this.max = i+1;
//                        break;
//                    }
//                }
//            }
//        } else {
//            throw new IllegalArgumentException("pick one: add or remove from minmax");
//        }
    }
    
    /*
     * Displays the current state of the puzzle.
     * You should not change this method.
     */
    public void display() {
        for (int r = 0; r < order; r++) {
            printRowSeparator();
            for (int c = 0; c < order; c++) {
                System.out.print("|");
                if (values[r][c] == 0) {
                    System.out.print("   ");
                } else {
                    if (values[r][c] < 10) {
                        System.out.print(" ");
                    }
                    System.out.print(" " + values[r][c] + " ");
                }
            }
            System.out.println("|");
        }
        printRowSeparator();
    }
    
    // A private helper method used by display()
    // to print a line separating two rows of the puzzle.
    private void printRowSeparator() {
        for (int i = 0; i < order; i++) {
            System.out.print("-----");
        }
        System.out.println("-");
    }
    
    public static void main(String[] args) {
        /*
         * You should NOT change any code in this method
         */
        
        Scanner console = new Scanner(System.in);
        System.out.print("What order Magic Square? ");
        int order = console.nextInt();
        
        MagicSquare puzzle = new MagicSquare(order);
        if (puzzle.solve()) {
            System.out.println("Here's the solution:");
            puzzle.display();
        } else {
            System.out.println("No solution found.");
            puzzle.display();
        }
    }
}