/*
 * LinkedTree.java
 *
 * Computer Science E-22
 *
 * Modifications and additions by:
 *     name: William Burke
 *     username: wburke1990
 */

import java.util.*;

/*
 * LinkedTree - a class that represents a binary tree containing data
 * items with integer keys.  If the nodes are inserted using the
 * insert method, the result will be a binary search tree.
 */
public class LinkedTree {
    // An inner class for the nodes in the tree
    private class Node {
        private int key;         // the key field
        private LLList data;     // list of data values for this key
        private Node left;       // reference to the left child/subtree
        private Node right;      // reference to the right child/subtree
        private Node parent;     // reference to the parent. NOT YET MAINTAINED!
        
        private Node(int key, Object data){
            this.key = key;
            this.data = new LLList();
            this.data.addItem(data, 0);
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
    
    // the root of the tree as a whole
    private Node root;
    
    public LinkedTree() {
        root = null;
    }
    
    /*
     * LinkedTree constructor method
     * from PS 4, Problem 9
     * Invokes the recursive addInOrder method to do the work.
     * This constructor creates a LinkedTree containing the specified keys and data items;
     * each element of the keys array, keys[i], is paired with the corresponding element of the data-items array, dataItems[i].
     * The resulting tree is a balanced binary search tree. The method assumes that there are no duplicates – i.e., no repeated keys.
     * 
     * For example, if we run the following test code:
     * 
     * int[] keys = {10, 8, 4, 2, 15, 12, 7};
     * String[] dataItems = {"d10", "d8", "d4", "d2", "d15", "d12", "d7"};
     * LinkedTree tree = new LinkedTree(keys, dataItems);
     * tree.levelOrderPrint();
     * System.out.println();
     * 
     * we should see:
     * 
     * 8
     * 4 12
     * 2 7 10 15
     */
    public LinkedTree(int[] keys, Object[] dataItems) {
        if (keys == null || dataItems == null || keys.length != dataItems.length || keys.length == 0) {
            root = null;
        } else {
            SortHelper.quickSort(keys,dataItems);
            root = null;
            addInOrder(keys,dataItems,0,keys.length-1);
        }
    }
    
    /*
     * Private helper class that creates a balanced binary search tree from a sorted array of keys and an array of data.
     * 
     */
    private void addInOrder(int[] keys, Object[] dataItems,int minIndex, int maxIndex) {
        if (minIndex == maxIndex) {
            //base case, add an item
            insert(keys[minIndex], dataItems[minIndex]);
        } else if (minIndex < maxIndex) {
            //recursive case, add an item then add items to both subtrees
            insert(keys[(minIndex+maxIndex)/2], dataItems[(minIndex+maxIndex)/2]);
            addInOrder(keys,dataItems,minIndex,(minIndex+maxIndex)/2-1);// left subtree
            addInOrder(keys,dataItems,(minIndex+maxIndex)/2+1,maxIndex);// right subtree
        }
    }
    
    /*
     * Prints the keys of the tree in the order given by a preorder traversal.
     * Invokes the recursive preorderPrintTree method to do the work.
     */
    public void preorderPrint() {
        if (root != null) {
            preorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs a preorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void preorderPrintTree(Node root) {
        System.out.print(root.key + " ");
        if (root.left != null) {
            preorderPrintTree(root.left);
        }
        if (root.right != null) {
            preorderPrintTree(root.right);
        }
    }
    
    /*
     * Prints the keys of the tree in the order given by a postorder traversal.
     * Invokes the recursive postorderPrintTree method to do the work.
     */
    public void postorderPrint() {
        if (root != null) {
            postorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs a postorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void postorderPrintTree(Node root) {
        if (root.left != null) {
            postorderPrintTree(root.left);
        }
        if (root.right != null) {
            postorderPrintTree(root.right);
        }
        System.out.print(root.key + " ");
    }
    
    /*
     * Prints the keys of the tree in the order given by an inorder traversal.
     * Invokes the recursive inorderPrintTree method to do the work.
     */
    public void inorderPrint() {
        if (root != null) {
            inorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs an inorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void inorderPrintTree(Node root) {
        if (root.left != null) {
            inorderPrintTree(root.left);
        }
        System.out.print(root.key + " ");
        if (root.right != null) {
            inorderPrintTree(root.right);
        }
    }
    
    /* 
     * Inner class for temporarily associating a node's depth
     * with the node, so that levelOrderPrint can print the levels
     * of the tree on separate lines.
     */
    private class NodePlusDepth {
        private Node node;
        private int depth;
        
        private NodePlusDepth(Node node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
    
    /*
     * Prints the keys of the tree in the order given by a
     * level-order traversal.
     */
    public void levelOrderPrint() {
        LLQueue<NodePlusDepth> q = new LLQueue<NodePlusDepth>();
        
        // Insert the root into the queue if the root is not null.
        if (root != null) {
            q.insert(new NodePlusDepth(root, 0));
        }
        
        // We continue until the queue is empty.  At each step,
        // we remove an element from the queue, print its value,
        // and insert its children (if any) into the queue.
        // We also keep track of the current level, and add a newline
        // whenever we advance to a new level.
        int level = 0;
        while (!q.isEmpty()) {
            NodePlusDepth item = q.remove();
            
            if (item.depth > level) {
                System.out.println();
                level++;
            }
            System.out.print(item.node.key + " ");
            
            if (item.node.left != null) {
                q.insert(new NodePlusDepth(item.node.left, item.depth + 1));
            }
            if (item.node.right != null) {
                q.insert(new NodePlusDepth(item.node.right, item.depth + 1));
            }
        }
        System.out.println();
    }
    
    /*
     * Searches for the specified key in the tree.
     * If it finds it, it returns the list of data items associated with the key.
     * Invokes the recursive searchTree method to perform the actual search.
     */
    public LLList search(int key) {
        Node n = searchTree(root, key);
        if (n == null) {
            return null;
        } else {
            return n.data;
        }
    }
    
    /*
     * Recursively searches for the specified key in the tree/subtree
     * whose root is specified. Note that the parameter is *not*
     * necessarily the root of the entire tree.
     */
    private static Node searchTree(Node root, int key) {
        if (root == null) {
            return null;
        } else if (key == root.key) {
            return root;
        } else if (key < root.key) {
            return searchTree(root.left, key);
        } else {
            return searchTree(root.right, key);
        }
    }
    
    /*
     * Inserts the specified (key, data) pair in the tree so that the
     * tree remains a binary search tree.
     */
    public void insert(int key, Object data) {
        // Find the parent of the new node.
        Node parent = null;
        Node trav = root;
        while (trav != null) {
            if (trav.key == key) {
                trav.data.addItem(data, 0);
                return;
            }
            parent = trav;
            if (key < trav.key) {
                trav = trav.left;
            } else {
                trav = trav.right;
            }
        }
        
        // Insert the new node.
        Node newNode = new Node(key, data);
        if (parent == null) {    // the tree was empty
            root = newNode;
        } else if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        newNode.parent = parent; //set the parent of the new node
    }
    
    /*
     * FOR TESTING: Processes the integer keys in the specified array from 
     * left to right, adding a node for each of them to the tree. 
     * The data associated with each key is a string based on the key.
     */
    public void insertKeys(int[] keys) {
        for (int i = 0; i < keys.length; i++) {
            insert(keys[i], "data for key " + keys[i]);
        }
    }
    
    /*
     * Deletes the node containing the (key, data) pair with the
     * specified key from the tree and return the associated data item.
     */
    public LLList delete(int key) {
        // Find the node to be deleted and its parent.
        Node parent = null;
        Node trav = root;
        while (trav != null && trav.key != key) {
            parent = trav;
            if (key < trav.key) {
                trav = trav.left;
            } else {
                trav = trav.right;
            }
        }
        
        // Delete the node (if any) and return the removed data item.
        if (trav == null) {   // no such key    
            return null;
        } else {
            LLList removedData = trav.data;
            deleteNode(trav, parent);
            return removedData;
        }
    }
    
    /*
     * Deletes the node specified by the parameter toDelete.  parent
     * specifies the parent of the node to be deleted. 
     */
    private void deleteNode(Node toDelete, Node parent) {
        if (toDelete.left != null && toDelete.right != null) {
            // Case 3: toDelete has two children.
            // Find a replacement for the item we're deleting -- as well as 
            // the replacement's parent.
            // We use the smallest item in toDelete's right subtree as
            // the replacement.
            Node replaceParent = toDelete;
            Node replace = toDelete.right;
            while (replace.left != null) {
                replaceParent = replace;
                replace = replace.left;
            }
            
            // Replace toDelete's key and data with those of the 
            // replacement item.
            toDelete.key = replace.key;
            toDelete.data = replace.data;
            
            // Recursively delete the replacement item's old node.
            // It has at most one child, so we don't have to
            // worry about infinite recursion.
            deleteNode(replace, replaceParent);
        } else {
            // Cases 1 and 2: toDelete has 0 or 1 child
            Node toDeleteChild;
            if (toDelete.left != null) {
                toDeleteChild = toDelete.left;
            } else {
                toDeleteChild = toDelete.right;  // null if it has no children
            }
            
            if (toDelete == root) {
                root = toDeleteChild;
            } else if (toDelete.key < parent.key) {
                parent.left = toDeleteChild;
            } else {
                parent.right = toDeleteChild;
            }
            
            //keep the parent references up to date
            if (parent.right != null) {
                parent.right.parent = parent;
            }
            if (parent.left != null) {
                parent.left.parent = parent;
            }
        }
    }
    
    /* Returns a preorder iterator for this tree. */
    public LinkedTreeIterator preorderIterator() {
        return new PreorderIterator();
    }
    
    /* 
     * inner class for a preorder iterator 
     * IMPORTANT: You will not be able to actually use objects from this class
     * to perform a preorder iteration until you have modified the LinkedTree
     * methods so that they maintain the parent fields in the Nodes.
     */
    private class PreorderIterator implements LinkedTreeIterator {
        private Node nextNode;
        
        private PreorderIterator() {
            // The traversal starts with the root node.
            nextNode = root;
        }
        
        public boolean hasNext() {
            return (nextNode != null);
        }
        
        public int next() {
            if (nextNode == null) {
                throw new NoSuchElementException();
            }
            
            // Store a copy of the key to be returned.
            int key = nextNode.key;
            
            // Advance nextNode.
            if (nextNode.left != null) {
                nextNode = nextNode.left;
            } else if (nextNode.right != null) {
                nextNode = nextNode.right;
            } else {
                // We've just visited a leaf node.
                // Go back up the tree until we find a node
                // with a right child that we haven't seen yet.
                Node parent = nextNode.parent;
                Node child = nextNode;
                while (parent != null &&
                       (parent.right == child || parent.right == null)) {
                    child = parent;
                    parent = parent.parent;
                }
                
                if (parent == null) {
                    nextNode = null;  // the traversal is complete
                } else {
                    nextNode = parent.right;
                }
            }
            
            return key;
        }
    }
    
    /* Returns a preorder iterator for this tree. */
    public LinkedTreeIterator inorderIterator() {
        return new InorderIterator();
    }
    
    /* 
     * inner class for a preorder iterator 
     * IMPORTANT: You will not be able to actually use objects from this class
     * to perform a preorder iteration until you have modified the LinkedTree
     * methods so that they maintain the parent fields in the Nodes.
     */
    private class InorderIterator implements LinkedTreeIterator {
        private Node nextNode;
        
        private InorderIterator() {
            // The traversal starts with the leftmost node.
            if (root == null) {
                nextNode = root;
            } else {
                nextNode = root;
                while (nextNode.left != null) {
                    nextNode = nextNode.left;
                }
            }
        }
        
        public boolean hasNext() {
            return (nextNode != null);
        }
        
        public int next() {
            if (nextNode == null) {
                throw new NoSuchElementException();
            }
            
            // Store a copy of the key to be returned.
            int key = nextNode.key;
            
            // Advance nextNode.
            if (nextNode.right != null) {
                // Case 1: Right child exists. Find the smallest element in the right child.
                nextNode = nextNode.right;
                while (nextNode.left != null) {
                    nextNode = nextNode.left;
                }
            } else if (nextNode.parent == null) {
                // Case 2: No parent. Set nextNode to null.
                nextNode = null;
            } else {
                // We need to go back up the tree. There are 2 cases to consider here:
                if (nextNode.parent.left == nextNode) {
                    // Case 3: We are coming from the left. The parent is next.
                    nextNode = nextNode.parent;
                } else {
                    // Case 4: We are coming from the right. 
                    // Go back up the tree until we come up on a node
                    // from the left, which means that we haven't seen that node yet.
                    Node parent = nextNode.parent;
                    Node child = nextNode;
                    while (parent != null && parent.right == child) {
                        child = parent;
                        parent = parent.parent;
                    }
                
                    if (parent == null) {
                        nextNode = null;  // the traversal is complete
                    } else {
                        nextNode = parent;
                    }
                }
            }
            
            return key;
        }
    }
    
    /*
     * "wrapper method" for the recursive depthInTree() method
     * from PS 4, Problem 2
     */
    public int depth(int key) {
        if (root == null) {    // root is the root of the entire tree
            return -1;
        }
        
        return depthInTree(key, root);
    }
    
    /*
     * original version of the recursive depthInTree() method  
     * from PS 4, Problem 2. You will write a more efficient version
     * of this method.
     */
    private static int depthInTree(int key, Node root) {
        if (key == root.key) {
            return 0;     // found
        } else {
            if (key < root.key && root.left != null) {
                int depthInLeft = depthInTree(key, root.left);
                if (depthInLeft != -1) {
                    return depthInLeft + 1;
                }
            }
        
            if (key > root.key && root.right != null) { //I realize that this extra check is not strictly necessary, but it makes the code more readable
                int depthInRight = depthInTree(key, root.right);
                if (depthInRight != -1) {
                    return depthInRight + 1;
                }
            }
        
            return -1;    // not found in either subtree
        }
    }
    
    
    /*
     * The recursive depthIter() method
     * from PS 4, Problem 7
     */
    public int depthIter(int key) {
        if (root == null) {    // root is the root of the entire tree
            return -1;
        }
        
        int d = 0;
        
        Node parent = null;
        Node trav = root;
        while (trav != null) {
            if (trav.key == key) {
                return d;
            }
            parent = trav;
            if (key < trav.key) {
                trav = trav.left;
            } else {
                trav = trav.right;
            }
            d++;
        }
        
        return -1;
    }
    
    
    /*
     * "wrapper method" for the recursive sumEvensInTree() method
     * from PS 4, Problem 7
     */
    public int sumEvens() {
        if (root == null) {    // root is the root of the entire tree
            return 0;
        }
        
        return sumEvensInTree(root);
    }
    
    /*
     * The recursive sumEvensInTree() method  
     * from PS 4, Problem 7.
     * Returns the sum of the even keys.
     */
    private static int sumEvensInTree(Node root) {
        int s = 0;
        
        if ((root.key % 2) == 0) {
            s = s + root.key;     // base case: found even number
        } 
        
        if (root.left != null) {
                s = s + sumEvensInTree(root.left); // sum from the left
        }
        
        if (root.right != null) {
            s = s + sumEvensInTree(root.right); // sum from the right
        }
    
        return s;    // sum of all the evens
    }
    
    
    /*
     * Iterative deleteMax() method
     * from PS 4, Problem 7
     * Deletes the node with the largest key from the tree and returns the key
     */
    public int deleteMax() {
        if (root == null) {    // root is the root of the entire tree
            return -1;
        } else if (root.right == null) {
            int k = root.key;
            root = root.left;
            root.parent = null; //maintain the parent reference
            return k;
        }
        
        //find the largest element
        Node parent = null;
        Node trav = root;
        while (trav.right != null) {
            parent = trav;
            trav = trav.right;
        }
        
        int k = trav.key; //save the key to return it later
        
        parent.right = trav.left; //update the reference in the parent
        if (parent.right != null) {
            parent.right.parent = parent; //keep the parent references up to date
        }
        
        return k;
    }
    
    public static void main(String[] args) {
        System.out.println("--- Testing depth()/depthInTree() from Problem 2 ---");
        System.out.println();
        System.out.println("(0) Testing on tree from Problem 7.1, depth of 13");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            
            int results = tree.depth(13);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(2);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 2);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();    // include a blank line between tests
        
        /*
         * Add at least two unit tests for each method from Problem 7.
         * Test a variety of different cases. 
         * Follow the same format that we have used above.
         * 
         * IMPORTANT: Any tests for your inorder iterator from Problem 8
         * should go BEFORE your tests of the deleteMax method.
         */
        System.out.println("--- Testing depthIter() for Problem 7 ---");
        System.out.println();
        System.out.println("(0) Testing on empty tree, depth of 13");
        try {
            LinkedTree tree = new LinkedTree();
            
            int results = tree.depthIter(13);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(-1);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == -1);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(1) Testing on tree from Problem 7.1, depth of 13");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            
            int results = tree.depthIter(13);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(2);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 2);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(2) Testing on tree from Problem 7.1, depth of 37");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            
            int results = tree.depthIter(37);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(0);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 0);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(3) Testing on tree from Problem 7.1, depth of 47");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            
            int results = tree.depthIter(47);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(3);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 3);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(4) Testing on tree from Problem 7.1, depth of 50");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            
            int results = tree.depthIter(50);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(-1);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == -1);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        
        System.out.println("--- Testing sumEvens()/sumEvensInTree() for Problem 7 ---");
        System.out.println();
        System.out.println("(5) Testing on empty tree");
        try {
            LinkedTree tree = new LinkedTree();
            
            int results = tree.sumEvens();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(0);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 0);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(6) Testing on tree from earlier in Problem 7.1");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            
            int results = tree.sumEvens();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(224);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 224);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(7) Testing on the suggested tree from  Problem 7.2");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {4, 1, 3, 6, 5, 2};
            tree.insertKeys(keys);
            
            int results = tree.sumEvens();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(12);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 12);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(8) Testing on a tree of odds");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {5, 1, 3, 7, 5, 19};
            tree.insertKeys(keys);
            
            int results = tree.sumEvens();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(0);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 0);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        
        System.out.println("--- Testing deleteMax() for Problem 7 ---");
        System.out.println();
        System.out.println("(9) Testing on empty tree");
        try {
            LinkedTree tree = new LinkedTree();
            
            int results = tree.deleteMax();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(-1);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == -1);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(10) Testing 1st deletion on tree from earlier in Problem 7.1");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            
            int results = tree.deleteMax();
            System.out.println("actual results:");
            System.out.println(results);
            tree.levelOrderPrint();
            System.out.println();
            System.out.println("expected results:");
            System.out.println(70);
            System.out.println("37\n 26 42\n 13 35 56\n 30 47");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 70);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(11) Testing 2nd deletion on tree from earlier in Problem 7.1");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            
            int temp = tree.deleteMax();
            int results = tree.deleteMax();
            System.out.println("actual results:");
            System.out.println(results);
            tree.levelOrderPrint();
            System.out.println();
            System.out.println("expected results:");
            System.out.println(56);
            System.out.println("37\n 26 42\n 13 35 47\n 30");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 56);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(12) Testing deletion on tree with max at root");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {71, 37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            tree.levelOrderPrint();
            
            int results = tree.deleteMax();
            System.out.println("actual results:");
            System.out.println(results);
            tree.levelOrderPrint();
            System.out.println();
            System.out.println("expected results:");
            System.out.println(71);
            System.out.println("37\n 26 42\n 13 35 56\n 30 47 70");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 71);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        
        
        
        
        System.out.println("--- Testing deleteMax() for Problem 8 ---");
        System.out.println();
        System.out.println("(13) Testing preorderIterator on empty tree");
        try {
            LinkedTree tree = new LinkedTree();
            LinkedTreeIterator iter = tree.preorderIterator();
            
            boolean results = iter.hasNext();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(false);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == false);
            try {
                iter.next();
                System.out.println("INCORRECTLY DID NOT THROW AN EXCEPTION for iter.next(); ");
            } catch (NoSuchElementException e) {
                System.out.println("CORRECTLY THREW AN EXCEPTION: " + e);
            }
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(14) Testing preorderIterator with sumEvens() tree from earlier in Problem 7.1");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            LinkedTreeIterator iter = tree.preorderIterator();
            int s = 0;
            while (iter.hasNext()) {
                int key = iter.next();

                if ((key%2) == 0) {
                    s = s + key;
                }
            }
            
            int results = tree.sumEvens();
            System.out.println("actual results:");
            System.out.println(s);
            System.out.println("results from sumEvens:");
            System.out.println(results);
            System.out.println();
            System.out.println("expected results:");
            System.out.println(224);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(s == 224);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(15) Testing preorderIterator by traversing tree from earlier in Problem 7.");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            LinkedTreeIterator iter = tree.preorderIterator();
            String results = "";
            while (iter.hasNext()) {
                int key = iter.next();
                results = results + key + ",";
            }
            
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println();
            System.out.println("expected results:");
            System.out.println("37,26,13,35,30,42,56,47,70,");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results.equals("37,26,13,35,30,42,56,47,70,"));
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(16) Testing inorderIterator on empty tree");
        try {
            LinkedTree tree = new LinkedTree();
            LinkedTreeIterator iter = tree.inorderIterator();
            
            boolean results = iter.hasNext();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(false);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == false);
            try {
                iter.next();
                System.out.println("INCORRECTLY DID NOT THROW AN EXCEPTION for iter.next(); ");
            } catch (NoSuchElementException e) {
                System.out.println("CORRECTLY THREW AN EXCEPTION: " + e);
            }
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(17) Testing inorderIterator with sumEvens() tree from earlier in Problem 7.1");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            LinkedTreeIterator iter = tree.inorderIterator();
            int s = 0;
            while (iter.hasNext()) {
                int key = iter.next();

                if ((key%2) == 0) {
                    s = s + key;
                }
            }
            
            int results = tree.sumEvens();
            System.out.println("actual results:");
            System.out.println(s);
            System.out.println("results from sumEvens:");
            System.out.println(results);
            System.out.println();
            System.out.println("expected results:");
            System.out.println(224);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(s == 224);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        System.out.println("(18) Testing inorderIterator by traversing tree from earlier in Problem 7.");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            LinkedTreeIterator iter = tree.inorderIterator();
            String results = "";
            while (iter.hasNext()) {
                int key = iter.next();
                results = results + key + ",";
            }
            
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println();
            System.out.println("expected results:");
            System.out.println("13,26,30,35,37,42,47,56,70,");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results.equals("13,26,30,35,37,42,47,56,70,"));
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();    // include a blank line between tests
        
        
        System.out.println("--- Testing LinkedTree(int[] keys, Object[] dataItems) for Problem 9 ---");
        System.out.println();
        
        //test code for the constructor class
        int[] keys0 = {10, 8, 4, 2, 15, 12, 7}; 
        String[] dataItems0 = {"d10", "d8", "d4", "d2", "d15", "d12", "d7"}; 
        LinkedTree tree0 = new LinkedTree(keys0, dataItems0);
        tree0.levelOrderPrint();
        System.out.println();
        
        //test code for the constructor class
        int[] keys1 = {10, 8, 4, 2, 15, 12}; 
        String[] dataItems1 = {"d10", "d8", "d4", "d2", "d15", "d12"}; 
        LinkedTree tree1 = new LinkedTree(keys1, dataItems1);
        tree1.levelOrderPrint();
        System.out.println();
        
        //test code for the constructor class
        int[] keys2 = {10, 8, 4, 2, 15}; 
        String[] dataItems2 = {"d10", "d8", "d4", "d2", "d15"}; 
        LinkedTree tree2 = new LinkedTree(keys2, dataItems2);
        tree2.levelOrderPrint();
        System.out.println();
        
        //test code for the constructor class
        int[] keys3 = {10, 8, 4, 2}; 
        String[] dataItems3 = {"d10", "d8", "d4", "d2"}; 
        LinkedTree tree3 = new LinkedTree(keys3, dataItems3);
        tree3.levelOrderPrint();
        System.out.println();
        
        //test code for the constructor class
        int[] keys4 = {10, 8, 4, 2}; 
        String[] dataItems4 = {"d10", "d8", "d4"}; 
        LinkedTree tree4 = new LinkedTree(keys4, dataItems4);
        tree4.levelOrderPrint();
        System.out.println();
        
        //test code for the constructor class
        int[] keys5 = {10}; 
        String[] dataItems5 = {"d10"}; 
        LinkedTree tree5 = new LinkedTree(keys5, dataItems5);
        tree5.levelOrderPrint();
        System.out.println();
        
        //test code for the constructor class
        int[] keys6 = {}; 
        String[] dataItems6 = {}; 
        LinkedTree tree6 = new LinkedTree(keys6, dataItems6);
        tree6.levelOrderPrint();
        System.out.println();
    }
}
