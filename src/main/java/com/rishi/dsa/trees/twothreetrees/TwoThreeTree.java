package com.rishi.dsa.trees.twothreetrees;

import java.util.*;

public class TwoThreeTree {
    private Node root;

    public void printDebugTree(){
        if(root == null){
            System.out.println("Tree is empty");
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int level =0;
        while(!queue.isEmpty()){
            int levelSize = queue.size();
            System.out.println("Level " + level);

            for(int i = 0; i < levelSize; i++){
                Node node = queue.remove();
                System.out.print(
                        node.getKeys() +
                                "(children=" + node.childCount() + ") "
                );
                for (Node child : node.getChildren()) {
                    queue.add(child);
                }
            }
            System.out.println();
            level++;
        }
    }
    public void insert(int key) {
        if(root == null) {
            root = new Node();
            root.addKey(key);
            return;
        }
        Deque<EntryPath> internalNodePath = new ArrayDeque<>();
        Node currentNode = root;
        while(!currentNode.isLeaf()) {
           int pathIndex = currentNode.getChildIndex(key);
           internalNodePath.push(new EntryPath(currentNode, pathIndex));
           currentNode = currentNode.getChildren().get(pathIndex);
        }
        if(!currentNode.isLeaf()){
            throw  new RuntimeException("the algorithm didnt reach till the leaf");
        }
        currentNode.addKey(key);
        if(currentNode.isOverflow()){
            SplitResult splitResult = splitNode(currentNode);
            splitOnInsert(splitResult, internalNodePath);
        }
    }

    private void splitOnInsert(SplitResult splitResult, Deque<EntryPath> internalNodePath) {
        if(splitResult == null) {
            throw new IllegalArgumentException("splitResult is null");
        }
        assertValid(splitResult.getLeft(), "SplitNode left is invalid Node");
        assertValid(splitResult.getRight(), "SplitNode right is invalid Node");
        if(internalNodePath.isEmpty()) {
            Node parent = new Node();
            parent.addKey(splitResult.getPromotedKey());
            parent.addChild(0,splitResult.getRight());
            parent.addChild(0, splitResult.getLeft());
            assertValid(parent , "Root node after split is invalid Node");
            if(!parent.isTwoNode()){
                throw new RuntimeException("the root node should be a 2 Node");
            }
            root = parent;
            return;
        }
        EntryPath entryPath = internalNodePath.pop();
        Node currentNode = entryPath.getNode();
        int childIndex = entryPath.getChildIndex();

        assertValid(currentNode, "Parent invalid before absorbing split");

        if (childIndex < 0 || childIndex >= currentNode.childCount()) {
            throw new IllegalStateException(
                    "Invalid child index " + childIndex +
                            " for parent childCount=" + currentNode.childCount() +
                            " | parent keys=" + currentNode.getKeys()
            );
        }

        currentNode.absorbSplitAt(entryPath.getChildIndex(), splitResult);

        assertValid(currentNode, "Parent node after absorbing split is invalid Node");
        if(currentNode.isOverflow()){
            SplitResult splitParent = splitNode(currentNode);
            splitOnInsert(splitParent, internalNodePath);
        }
    }
    private SplitResult  splitNode(Node node){
        if(!node.isOverflow()){
            throw new RuntimeException("Splitting non overflow node");
        }
        if(!node.isLeaf() && node.childCount() !=4){
            throw new RuntimeException("Splitting parent Node without 4 child nodes");
        }
        if(node.isLeaf()){
            int promotedKey = node.removeKey(1);
            Node left = new Node();
            left.addKey(node.getKeys().getFirst());
            Node right = new Node();
            right.addKey(node.getKeys().getLast());
            return new SplitResult(promotedKey, left, right);
        }
        int promotedKey = node.removeKey(1);
        Node childLeft1 = node.getChildren().get(0);
        Node childLeft2 = node.getChildren().get(1);
        Node childRight1 = node.getChildren().get(2);
        Node childRight2 = node.getChildren().get(3);
        Node parentLeft = new Node();
        Node parentRight = new Node();
        parentLeft.addKey(node.getKeys().getFirst());
        parentRight.addKey(node.getKeys().getLast());
        parentLeft.addChild(0, childLeft2);
        parentLeft.addChild(0, childLeft1);
        parentRight.addChild(0, childRight2);
        parentRight.addChild(0, childRight1);
        return new SplitResult(promotedKey, parentLeft, parentRight);
    }

    private void assertValid(Node node, String message) {
        if (node == null) {
            throw new IllegalStateException(message + ": node is null");
        }

        if (!node.isStructurallyValid()) {
            throw new IllegalStateException(
                    message +
                            " | keys=" + node.getKeys() +
                            " | childCount=" + node.childCount()
            );
        }
    }


}
