/* 
 * ArrayBag.java
 * 
 * Computer Science E-22
 *
 * Modified by: William Burke, wburke@g.harvard.edu
 */

import java.util.*;

/**
 * An implementation of the Bag ADT using an array.
 */
public class ArrayBag implements Bag {
    /** 
     * The array used to store the items in the bag.
     */
    private Object[] items;
    
    /** 
     * The number of items in the bag.
     */
    private int numItems;
    
    public static final int DEFAULT_MAX_SIZE = 50;
    
    /**
     * Constructor with no parameters - creates a new, empty ArrayBag with 
     * the default maximum size.
     */
    public ArrayBag() {
        this.items = new Object[DEFAULT_MAX_SIZE];
        this.numItems = 0;
    }
    
    /** 
     * A constructor that creates a new, empty ArrayBag with the specified
     * maximum size.
     */
    public ArrayBag(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must be > 0");
        }
        this.items = new Object[maxSize];
        this.numItems = 0;
    }
    
    /**
     * numItems - accessor method that returns the number of items 
     * in this ArrayBag.
     */
    public int numItems() {
        return this.numItems;
    }
    
    /** 
     * add - adds the specified item to this ArrayBag. Returns true if there 
     * is room to add it, and false otherwise.
     * Throws an IllegalArgumentException if the item is null.
     */
    public boolean add(Object item) {
        if (item == null) {
            throw new IllegalArgumentException("item must be non-null");
        } else if (this.numItems == this.items.length) {
            return false;    // no more room!
        } else {
            this.items[this.numItems] = item;
            this.numItems++;
            return true;
        }
    }
    
    /** 
     * remove - removes one occurrence of the specified item (if any)
     * from this ArrayBag.  Returns true on success and false if the
     * specified item (i.e., an object equal to item) is not in this ArrayBag.
     */
    public boolean remove(Object item) {
        for (int i = 0; i < this.numItems; i++) {
            if (this.items[i].equals(item)) {
                // Shift the remaining items left by one.
                for (int j = i; j < this.numItems - 1; j++) {
                    this.items[j] = this.items[j + 1];
                }
                this.items[this.numItems - 1] = null;
                
                this.numItems--;
                return true;
            }
        }
        
        return false;  // item not found
    }
    
    /**
     * contains - returns true if the specified item is in the Bag, and
     * false otherwise.
     */
    public boolean contains(Object item) {
        for (int i = 0; i < this.numItems; i++) {
            if (this.items[i].equals(item)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * containsAll - does this ArrayBag contain all of the items in
     * otherBag?  Returns false if otherBag is null or empty. 
     */
    public boolean containsAll(Bag otherBag) {
        if (otherBag == null || otherBag.numItems() == 0) {
            return false;
        }
        
        Object[] otherItems = otherBag.toArray();
        for (int i = 0; i < otherItems.length; i++) {
            if (! this.contains(otherItems[i])) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * grab - returns a reference to a randomly chosen item in this ArrayBag.
     */
    public Object grab() {
        if (this.numItems == 0) {
            throw new IllegalStateException("the bag is empty");
        }
        
        int whichOne = (int)(Math.random() * this.numItems);
        return this.items[whichOne];
    }
    
    /**
     * toArray - return an array containing the current contents of the bag
     */
    public Object[] toArray() {
        Object[] copy = new Object[this.numItems];
        
        for (int i = 0; i < this.numItems; i++) {
            copy[i] = this.items[i];
        }
        
        return copy;
    }
    
    /**
     * toString - converts this ArrayBag into a string that can be printed.
     * Overrides the version of this method inherited from the Object class.
     */
    public String toString() {
        String str = "{";
        
        for (int i = 0; i < this.numItems; i++) {
            str = str + this.items[i];
            if (i != this.numItems - 1) {
                str += ", ";
            }
        }
        
        str = str + "}";
        return str;
    }
    
    //This method returns the maximum number of items that the called ArrayBag is able to hold.
    //This value does not depend on the number of items that are currently in the ArrayBag — it
    //is the same as the maximum size specified when the ArrayBag was created.
    public int capacity(){
        return this.items.length;
    }
    
    //This method returns true if the called ArrayBag is full, and false otherwise.
    public boolean isFull() {
        if (this.numItems == this.items.length) {
            return true;    // no more room!
        } else {
            return false;
        }
    }
        
    //This method increases the maximum capacity of the called ArrayBag by the specified amount.
    //For example, if b has a maximum capacity of 10, then b.increaseCapacity(5) gives b a maximum
    //capacity of 15. As part of the implementation, it creates a new array with room to support
    //the new maximum capacity, copies any existing items into that array, and replaces the original
    //array with the new one by storing its reference in the called object.
    //Special cases:
        //If the parameter is 0, the method just returns without making any changes to the called object.
        //If the parameter is negative, the method throws an IllegalArgumentException. See the second
        //ArrayBag constructor for an example of throwing an exception.
    public void increaseCapacity(int amount){
        if (amount == 0) {
            return;
        } else if (amount < 0) {
            throw new IllegalArgumentException("amount must be non-negative");
        } else {
            Object[] copy = new Object[this.items.length + amount];
            
            for (int i = 0; i < this.numItems; i++) {
                copy[i] = this.items[i];
            }
            
            this.items = copy;
        }
    }
        
        
    //This method attempts to remove from the called ArrayBag all occurrences of the items found in 
    //the parameter other. If the called object contains multiple copies of an item from otherBag, all of
    //the copies are removed. The method returns true if one or more items are removed and false otherwise.
    //Special cases:  
        //If the parameter is null, the method throws an IllegalArgumentException.
        //If the parameter is an empty Bag, the method returns false.
        //Note that the parameter is of type Bag. As a result, the method uses method calls to access the 
        //internals of that bag. See our implementation of the containsAll() method for an example of this.
    public boolean removeItems(Bag other){
        if (other == null) {
            throw new IllegalArgumentException("other bag must be non-null");
        } else if (other.numItems() == 0) {
            return false;
        } else {
            for (int i = 0; i < this.numItems; i++) {
                if (other.contains(this.items[i])){
                    this.remove(this.items[i]);
                    i--;
                }
            }
            return true;
        }
    }
    
    //This method creates and returns an ArrayBag containing one occurrence of any item that is found in
    //either the called object or the parameter other. The resulting bag does not include any duplicates.
    //For example, if b1 represents the bag {2, 2, 3, 5, 7, 7, 7, 8} and b2 represents the bag {2, 3, 4,5, 5, 6, 7},
    //then b1.unionWith(b2) returns an ArrayBag representing the bag {2, 3, 4, 5, 6, 7, 8}. The new ArrayBag
    //has a maximum size that is the sum of the two bag’s maximum sizes.
    //Special cases:
            //If one of the bags is empty, the method returnw an ArrayBag containing one occurrence of each
            //item in the non-empty bag.
            //If both of the bags are empty, the method returns an empty ArrayBag.
            //If the parameter is null, the method throws an IllegalArgumentException.
            //Here again, the parameter is of type Bag. As a result, the method uses method calls to access
            //the internals of that bag. See our implementation of the containsAll() method for an example of
            //this. The return type is also Bag, but polymorphism allows you to just return the ArrayBag that
            //was created, because ArrayBag implements Bag.
    public Bag unionWith(Bag other){
        if (other == null) {
            throw new IllegalArgumentException("other bag must be non-null");
        } else {
            ArrayBag newbag = new ArrayBag(other.capacity() + this.capacity());
            for (int i = 0; i < this.numItems; i++) {
                if (newbag.numItems() == 0 || !newbag.contains(this.items[i])) {
                    newbag.add(this.items[i]);
                }
            }
            Object[] otheritems = other.toArray();
            for (int i = 0; i < other.numItems(); i++) {
                if (newbag.numItems() == 0 || !newbag.contains(otheritems[i])) {
                    newbag.add(otheritems[i]);
                }
            }
            return newbag;
        }
    }
    
    /* Test the ArrayBag implementation. */
    public static void main(String[] args) {
        // Create a Scanner object for user input.
        Scanner scan = new Scanner(System.in);
        
        // Create an ArrayBag named bag1.
        System.out.print("size of bag 1: ");
        int size = scan.nextInt();
        ArrayBag bag1 = new ArrayBag(size);
        scan.nextLine();    // consume the rest of the line
        
        // Read in strings, add them to bag1, and print out bag1.
        String itemStr;        
        for (int i = 0; i < size; i++) {
            System.out.print("item " + i + ": ");
            itemStr = scan.nextLine();
            bag1.add(itemStr);
        }
        System.out.println("bag 1 = " + bag1);
        System.out.println();
        
        // Select a random item and print it.
        Object item = bag1.grab();
        System.out.println("grabbed " + item);
        System.out.println();
        
        // Iterate over the objects in bag1, printing them one per
        // line.
        Object[] items = bag1.toArray();
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i]);
        }
        System.out.println();
        
        // Get an item to remove from bag1, remove it, and reprint the bag.
        System.out.print("item to remove: ");
        itemStr = scan.nextLine();
        if (bag1.contains(itemStr)) {
            bag1.remove(itemStr);
        }
        System.out.println("bag 1 = " + bag1);
        System.out.println();
        
        //test the capacity method
        System.out.println("bag 1 capacity = " + bag1.capacity());
        System.out.println();
        
        //test the isFull method
        System.out.println("bag 1 isFull = " + bag1.isFull());
        System.out.println();
        
        //test the increaseCapacity method
        System.out.println("bag 1 increaseCapacity(5)");
        bag1.increaseCapacity(5);
        System.out.println("bag 1 increaseCapacity(0)");
        bag1.increaseCapacity(0);
        try {
            bag1.increaseCapacity(-1);
        } catch(IllegalArgumentException e) {
            System.out.println("bag 1 increaseCapacity(-1)");
        }
        System.out.println("bag 1 capacity = " + bag1.capacity());
        System.out.println("bag 1 isFull = " + bag1.isFull());
        System.out.println();
        
        //test the removeItems method
        ArrayBag bag2 = new ArrayBag();
        ArrayBag bag3 = new ArrayBag();
        bag2.add("a");
        bag2.add("a");
        bag2.add("b");
        bag2.add("c");
        bag1.add("c");
        bag1.add("c");
        System.out.println("bag 1 = " + bag1);
        System.out.println("bag 2 = " + bag2);
        System.out.println("bag1.numItems() = " + bag1.numItems());
        System.out.println("bag1.removeItems(bag2)");
        bag1.removeItems(bag2);
        System.out.println("bag 1 = " + bag1);
        System.out.println("bag 2 = " + bag2);
        try {
            bag1.removeItems(null);
        } catch(IllegalArgumentException e) {
            System.out.println("bag 1 .removeItems(null)");
        }
        System.out.println("bag 1 .removeItems(bag3) = " + bag1.removeItems(bag3));
        System.out.println("bag1.numItems() = " + bag1.numItems());
        
        //test the unionWith method
        try {
            bag1.unionWith(null);
        } catch(IllegalArgumentException e) {
            System.out.println("bag1.unionWith(null) = exception");
        }
        bag1.unionWith(bag3);
        System.out.println("bag1.unionWith(bag3) = " + bag1.unionWith(bag3));
        System.out.println("bag1.unionWith(bag2) = " + bag1.unionWith(bag2));
        System.out.println("bag2.unionWith(bag3) = " + bag2.unionWith(bag3));
        System.out.println("bag3.unionWith(bag3) = " + bag3.unionWith(bag3));
    }
}
