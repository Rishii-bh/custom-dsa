package com.rishi.dsa;

import com.rishi.dsa.priorityqueue.IndexedMinHeap;
import com.rishi.dsa.trees.twothreetrees.TwoThreeTree;

import java.util.*;

public class Main {
    public static void main(String[] args) {
       // twoThreeTree();
        priorityQueue();


    }

    private static void twoThreeTree() {
        TwoThreeTree twoThreeTree = new TwoThreeTree();
        List<Integer> values = new ArrayList<>();

        for(int i=1; i<=10; i++) {
            values.add(i);
        }
        Collections.shuffle(values);
        System.out.println(values);

        for(int value: values) {
            twoThreeTree.insert(value);
        }
        twoThreeTree.printDebugTree();


    }

    private static void priorityQueue() {
        IndexedMinHeap indexedMinHeap = new IndexedMinHeap();
        List<Integer> values = new ArrayList<>();
        for(int i=1; i<=5; i++) {
            values.add(i);
        }
        for(int i=1; i<=3; i++) {
            values.add(i);
        }
        Collections.shuffle(values);
        System.out.println(values);

        for(int value: values) {
            indexedMinHeap.insert(value);
        }
        indexedMinHeap.remove(2);

        int n = indexedMinHeap.getSize();
        System.out.println(n);

        for(int i=0; i<n; i++) {
            System.out.println(indexedMinHeap.pop());
        }
    }
}