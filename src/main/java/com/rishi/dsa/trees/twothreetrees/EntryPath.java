package com.rishi.dsa.trees.twothreetrees;

public class EntryPath {
    private Node node;
    private int childIndex;

    public EntryPath(Node node, int childIndex) {
        this.node = node;
        this.childIndex = childIndex;
    }
    public Node getNode() {
        return node;
    }
    public int getChildIndex() {
        return childIndex;
    }
}
