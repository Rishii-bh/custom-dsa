package com.rishi.dsa;

import com.rishi.dsa.trees.twothreetrees.TwoThreeTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
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
}