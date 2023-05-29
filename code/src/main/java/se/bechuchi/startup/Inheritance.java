package se.bechuchi.startup;

import java.util.Stack;

public class Inheritance extends Stack<Integer> {
    public void printStackSize() {
        System.out.println("The size of this stack it: " + this.size());
    }
}
