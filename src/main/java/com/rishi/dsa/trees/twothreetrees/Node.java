package com.rishi.dsa.trees.twothreetrees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node {
    private List<Node> children;
    private List<Integer> keys;

    public Node() {
        keys = new ArrayList<>();
        children = new ArrayList<>();
    }

    public void addKey(int key) {
      this.keys.add(key);
      sortKey();
    }
    private void sortKey() {
        Collections.sort(this.keys);
    }
    public int removeKey(int index) {
        return  this.keys.remove(index);
    }

    public void addChild(int index, Node child) {
        this.children.add(index, child);
    }

    public List<Node> getChildren() {
        return children;
    }
    public List<Integer> getKeys() {
        return keys;
    }
    public int childCount() {
        return children.size();
    }
    public int keyCount() {
        return keys.size();
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public boolean isTwoNode() {
        return keys.size() == 1;
    }

    public boolean isThreeNode() {
        return keys.size() == 2;
    }

    public boolean isOverflow() {
        return keys.size() == 3;
    }

    public boolean isStructurallyValid() {
        int keyCount = keyCount();
        int childCount = childCount();
        if(keyCount <1 || keyCount > 3) return false;

        if (isLeaf()) {
            return true;
        }

        return childCount == keyCount + 1;
    }

    public void absorbSplitAt(int index , SplitResult splitResult) {

        if (isLeaf()) {
            throw new IllegalStateException("Leaf cannot absorb child split");
        }

        if (index < 0 || index >= children.size()) {
            throw new IndexOutOfBoundsException("Invalid child index: " + index);
        }
        children.remove(index);
        children.add(index, splitResult.getRight());
        children.add(index, splitResult.getLeft());
        keys.add(index, splitResult.getPromotedKey());
        if (!isStructurallyValid()) {
            throw new IllegalStateException(
                    "Node corrupted after absorbSplitAt | keys=" + keys +
                            " | childCount=" + children.size()
            );
        }
    }

    public int getChildIndex(int key){
            if (isLeaf()) {
                throw new IllegalStateException("Leaf nodes do not have child indices");
            }

            if (!isTwoNode() && !isThreeNode()) {
                throw new IllegalStateException("Cannot descend through invalid/overflow node");
            }

            if (keys.contains(key)) {
                throw new IllegalArgumentException("Duplicate keys not allowed");
            }

            if (key < keys.getFirst()) {
                return 0;
            }

            if (isTwoNode()) {
                return 1;
            }

            if (key < keys.get(1)) {
                return 1;
            }

            return 2;
        }

}
