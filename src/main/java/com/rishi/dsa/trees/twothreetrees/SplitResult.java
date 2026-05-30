package com.rishi.dsa.trees.twothreetrees;

public class SplitResult {
    int promotedKey;
    Node left;
    Node right;

    public SplitResult(int promotedKey, Node left, Node right) {
        this.promotedKey = promotedKey;
        this.left = left;
        this.right = right;
    }

    public int getPromotedKey() {
        return promotedKey;
    }
    public Node getLeft() {
        return left;
    }
    public Node getRight() {
        return right;
    }
}
