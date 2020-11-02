/*
 * Part1.java
 *
 * Computer Science E-22, Harvard University
 * 
 * modified by:
 *   name:
 *   email:
 */

import java.io.*;
import java.util.*;

public class Part1 {
    
    public static LLList intersect1(LLList list1, LLList list2) {
        LLList inters = new LLList();

        for (int i = 0; i < list1.length(); i++) {
            Object item1 = list1.getItem(i);
            for (int j = 0; j < list2.length(); j++) {
                Object item2 = list2.getItem(j);
                if (item2.equals(item1)) {
                    inters.addItem(item2, inters.length());
                    break;   // move onto the next item from list1
                }
            }
        }

        return inters;
    }
    
    public static LLList intersect(LLList list1, LLList list2) {
        LLList inters = new LLList();
        ListIterator i1 = list1.iterator();

        while(i1.hasNext()) {
            Object item1 = i1.next();
            ListIterator i2 = list2.iterator();
            while(i2.hasNext()) {
                Object item2 = i2.next();
                if (item2.equals(item1)) {
                    inters.addItem(item2, 0);
                    break;   // move onto the next item from list1
                }
            }
        }

        return inters;
    }
    
    public static boolean findItemInStack(Object item, Stack S) {
        //null check
        if (item == null || S == null) {
            return false;
        }
        
        //make the queue
        LLQueue Q = new LLQueue();
        boolean returnvalue = false;
        
        //put the stack on the Queue and figure out if the item is in there
        while (!S.isEmpty()) {
            Object o = S.pop();
            if (item.equals(o)) {
                returnvalue = true;
            }
            Q.insert(o); 
        }
        
        //put the queue on the stack in backward order
        while (!Q.isEmpty()) {
            Object o = Q.remove();
            S.push(o); 
        }
        
        //put the stack on the Queue
        while (!S.isEmpty()) {
            Object o = S.pop();
            Q.insert(o);
        }
        
        //put the queue on the stack in the correct order
        while (!Q.isEmpty()) {
            Object o = Q.remove();
            S.push(o);
        }
        
        return returnvalue;
    }
    
    public static void main(String[] args) throws IOException {
        
        LLList listy = new LLList();
        listy.addItem(1, 0);
        listy.addItem(2, 0);
        listy.addItem(1, 0);
        listy.addItem(2, 0);
        listy.addItem(1, 0);
        listy.addItem(2, 0);
        listy.addItem(1, 0);
        listy.addItem(2, 0);
        System.out.println(listy.toString());
        
        LLList list2 = new LLList();
        list2.addItem(3, 0);
        list2.addItem(2, 0);
        list2.addItem(1, 0);
        list2.addItem(1, 0);
        list2.addItem(0, 0);
        list2.addItem(-1, 0);
        System.out.println(list2.toString());
        
        LLList list3 = new LLList();
        
        LLList int1 = intersect1(listy,list2);
        System.out.println(int1.toString());
        
        LLList int2 = intersect1(listy,list3);
        System.out.println(int2.toString());
        
        LLList int3 = intersect1(list2,listy);
        System.out.println(int3.toString());
        
        LLList int4 = intersect(listy,list2);
        System.out.println(int4.toString());
        
        LLList int5 = intersect(listy,list3);
        System.out.println(int5.toString());
        
        LLList int6 = intersect(list2,listy);
        System.out.println(int6.toString());
        
        LLStack S = new LLStack();
        S.push(0);
        S.push(0);
        S.push(0);
        S.push(0);
        S.push(0);
        S.push(3);
        S.push(1);
        S.push(1);
        S.push(1);
        S.push(1);
        System.out.println(S);
        System.out.println(findItemInStack(3,S));
        System.out.println(findItemInStack(3,null));
        System.out.println(findItemInStack(4,S));
        System.out.println(S);
    }
}