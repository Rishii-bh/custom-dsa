package PriorityQueueTests;

import com.rishi.dsa.priorityqueue.IndexedMinHeap;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class IndexedMinHeapTests {

    @Test
    void testMinHeapIndexesAfterRemovalShouldRespectInvariant(){
        IndexedMinHeap minHeap = new IndexedMinHeap();
        minHeap.insert(1);
        minHeap.insert(50);
        minHeap.insert(2);
        minHeap.insert(60);
        minHeap.insert(70);
        minHeap.insert(3);

        minHeap.remove(70);

        assertEquals(1, minHeap.pop());
        assertEquals(2, minHeap.pop());
        assertEquals(3, minHeap.pop());
        assertEquals(50, minHeap.pop());
        assertEquals(60, minHeap.pop());
    }

    @Test
    void minHeapInsertionRemovalPreservesHeap(){
        IndexedMinHeap minHeap = new IndexedMinHeap();
        minHeap.insert(1);
        minHeap.insert(3);
        minHeap.insert(2);
        minHeap.remove(2);

        assertThrows(NoSuchElementException.class, () -> minHeap.remove(2));
        minHeap.insert(6);
        assertEquals(1, minHeap.pop());
        assertEquals(3, minHeap.pop());
        assertEquals(6, minHeap.pop());

    }

}
