/* 
 * Bag.java
 * 
 * Computer Science E-22, Harvard University
 *
 * Modified by: <your name>, <your e-mail address>
 */

/**
 * An interface for the Bag ADT.
 */
public interface Bag {
    /** 
     * adds the specified item to the Bag.  Returns true on success
     * and false if there is no more room in the Bag.
     */
    boolean add(Object item);
    
    /** 
     * removes one occurrence of the specified item (if any) from the
     * Bag.  Returns true on success and false if the specified item
     * (i.e., an object equal to item) is not in the Bag.
     */
    boolean remove(Object item);
    
    /**
     * returns true if the specified item is in the Bag, and false
     * otherwise.
     */
    boolean contains(Object item);
    
    /**
     * returns true if the calling object contain all of the items in
     * otherBag, and false otherwise.  Also returns false if otherBag 
     * is null or empty. 
     */
    boolean containsAll(Bag otherBag);
    
    /**
     * returns the number of items in the Bag.
     */
    int numItems();
    
    /**
     * grab - returns a reference to a randomly chosen in the Bag.
     */
    Object grab();
    
    /**
     * toArray - return an array containing the current contents of the bag
     */
    Object[] toArray();
    
    //This method returns the maximum number of items that the called ArrayBag is able to hold.
    //This value does not depend on the number of items that are currently in the ArrayBag — it
    //is the same as the maximum size specified when the ArrayBag was created.
    int capacity();
    
    //This method returns true if the called ArrayBag is full, and false otherwise.
    boolean isFull();
        
    //This method increases the maximum capacity of the called ArrayBag by the specified amount.
    //For example, if b has a maximum capacity of 10, then b.increaseCapacity(5) gives b a maximum
    //capacity of 15. As part of the implementation, it creates a new array with room to support
    //the new maximum capacity, copies any existing items into that array, and replaces the original
    //array with the new one by storing its reference in the called object.
    //Special cases:
        //If the parameter is 0, the method just returns without making any changes to the called object.
        //If the parameter is negative, the method throws an IllegalArgumentException. See the second
        //ArrayBag constructor for an example of throwing an exception.
    void increaseCapacity(int amount);
        
    //This method attempts to remove from the called ArrayBag all occurrences of the items found in 
    //the parameter other. If the called object contains multiple copies of an item from otherBag, all of
    //the copies are removed. The method returns true if one or more items are removed and false otherwise.
    //Special cases:  
        //If the parameter is null, the method throws an IllegalArgumentException.
        //If the parameter is an empty Bag, the method returns false.
        //Note that the parameter is of type Bag. As a result, the method uses method calls to access the 
        //internals of that bag. See our implementation of the containsAll() method for an example of this.
    boolean removeItems(Bag other);
    
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
    Bag unionWith(Bag other);
} 
