 /*
  * PSET 2
  * by William Burke (wburke@g.harvard.edu)
  * 9/28/2020
  *
  * This class tests the code for part1 of the pset
  *
  */

import java.util.*;

public class DNode {
    private char ch;
    private DNode next;
    private DNode prev;
    
    public String toString(){
        if (this.prev == null) {
            return "{ch:" + this.ch + ", next:" + this.next + ", prev:" + this.prev + "}";
        } else {
            return "{ch:" + this.ch + ", next:" + this.next + ", prev:" + this.prev.ch + "}";
        }
    }
    
    public static void main(String[] args) {
        
        //make all of the empty nodes
        DNode c = new DNode();
        DNode a = new DNode();
        DNode t = new DNode();
        DNode h = new DNode();
        System.out.println("c = "  + c);
        
        //make "cat"
        c.ch = 'c';
        c.next = a;
        a.ch = 'a';
        a.next = t;
        a.prev = c;
        t.ch = 't';
        t.prev = a;
        System.out.println("cat = "  + c);
        
        //insert h to make "chat"
        h.ch = 'h';
        h.next = a;
        h.prev = c;
        c.next = h;
        a.prev = h;
        System.out.println("chat = "  + c);

    }
}