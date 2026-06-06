package com.rishi.dsa.priorityqueue;

import java.util.*;

public class IndexedMinHeap {
    private final List<Integer> list;
    private final Map<Integer , Set<Integer>> lookUpMap;

    public IndexedMinHeap() {
        list = new ArrayList<Integer>();
        lookUpMap = new HashMap<Integer, Set<Integer>>();
    }

    public int getSize(){
        return list.size();
    }

    public void insert(int value) {
        list.add(value);
        lookUpMap.computeIfAbsent(value, k -> new HashSet<>()).add(list.size()-1);
        bubbleUp(list, list.size()-1);
    }

    private void bubbleUp(List<Integer> list, int index) {
        if(index <= 0){
            return;
        }
        int parentIndex = (index-1)/2;
        if(list.get(index) < list.get(parentIndex)){
            swap(list, index,parentIndex);
            bubbleUp(list, parentIndex);
        }
    }

    private void swap(List<Integer> list, int index1, int index2) {
        Set<Integer> set1 = lookUpMap.get(list.get(index1));
        set1.remove(index1);
        set1.add(index2);
        lookUpMap.put(list.get(index1), set1);
        Set<Integer> set2 = lookUpMap.get(list.get(index2));
        set2.remove(index2);
        set2.add(index1);
        lookUpMap.put(list.get(index2), set2);
        int temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

    public int peek(){
        if(list.isEmpty()){
            throw new NoSuchElementException();
        }
        return list.getFirst();
    }

    public int pop(){
        if(list.isEmpty()){
            throw new NoSuchElementException();
        }
        int lastIndex = list.size()-1;
        int value = list.getFirst();
        list.set(0, list.getLast());
        list.removeLast();
        if(list.isEmpty()){
            lookUpMap.remove(value);
            return value;
        }
        Set<Integer> set = lookUpMap.get(value);
        set.remove(0);
        if(set.isEmpty()){
            lookUpMap.remove(value);
        }
        Set<Integer> set2 = lookUpMap.get(list.getFirst());
        set2.remove(lastIndex);
        set2.add(0);
        lookUpMap.put(list.getFirst(), set2);
        bubbleDown(list,0);
        validateHeap();
        return value;
    }

    private void bubbleDown(List<Integer> list, int index) {
        if(index >= list.size()){
            return;
        }
        int minIndex = index;
        int leftChildIndex = index*2+1;
        int rightChildIndex = index*2+2;
        if(leftChildIndex >= list.size() && rightChildIndex >= list.size()){
            return;
        }
        if(leftChildIndex < list.size()){
            minIndex = list.get(index)<=list.get(leftChildIndex)?index:leftChildIndex;
        }
        if(rightChildIndex < list.size()){
            minIndex = list.get(minIndex)<=list.get(rightChildIndex)?minIndex:rightChildIndex;
        }
        if(minIndex == index){
            return;
        }
        swap(list, index, minIndex);
        bubbleDown(list, minIndex);
    }

    public void remove(int value) {
        if(list.isEmpty()){
            throw new RuntimeException("List is empty");
        }
        if(!lookUpMap.containsKey(value)){
            throw new NoSuchElementException();
        }
        Set<Integer> set = lookUpMap.get(value);
        if(set.isEmpty()){
            throw new RuntimeException("Set of indexes is empty");
        }
        int lastIndex = list.size()-1;
        int lastValue = list.getLast();
        int index = set.iterator().next();
        list.set(index, list.getLast());
        list.removeLast();
        if(list.isEmpty()){
            lookUpMap.remove(value);
            return;
        }
       set.remove(index);
        if(set.isEmpty()){
            lookUpMap.remove(value);
        }
        if(index == lastIndex){
            return;
        }
        Set<Integer> set2 = lookUpMap.get(lastValue);
        set2.remove(lastIndex);
        set2.add(index);
        lookUpMap.put(lastValue, set2);
        bubbleDown(list,index);
        bubbleUp(list,index);
        validateHeap();
        return;
    }

    private void validateHeap(){
        if(list.isEmpty()){
            return;
        }
        for(int i=0;i<list.size();i++){
            int value = list.get(i);
            if(!lookUpMap.containsKey(value) || !lookUpMap.get(value).contains(i)){
                throw new IllegalStateException("Index does not contain value"+value+" at index "+i);
            }
            int leftChildIndex = i*2+1;
            int rightChildIndex = i*2+2;
            if(leftChildIndex < list.size() && value > list.get(leftChildIndex)){
                throw new IllegalStateException("Value at index"+i+"violates heap property");
            }
            if(rightChildIndex < list.size() && value > list.get(rightChildIndex)){
                throw new IllegalStateException("Value at index"+i+"violates heap property");
            }
        }

        for(Map.Entry<Integer,Set<Integer>> entry : lookUpMap.entrySet()){
            int key = entry.getKey();

            for(Integer index : entry.getValue()){
                if(index <0 || index >=list.size()){
                    throw new IndexOutOfBoundsException("index in the lookUpMap is out of bounds of thr actual list"+index);
                }
                if(!list.get(index).equals(key)){
                    throw new IllegalStateException("Value at index"+index+"does not contain key"+key);
                }
            }
        }
    }

}
