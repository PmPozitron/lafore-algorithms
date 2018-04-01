package chapter5.sorted;

public class PriorityQueue {

    private SortedList list;

    public PriorityQueue() {
        this.list = new SortedList();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void insert(long key) {
        list.insert(key);
    }

    public long remove() {
        if (!list.isEmpty()) {
            return list.remove().dData;
        }

        throw new IllegalStateException("очередь пуста");
    }

    public void display() {
        list.displayList();
    }
}

class PriorityQueueTestDrive {
    public static void main(String[] args) {
        new PriorityQueueTestDrive().testDrive();
    }

    private void testDrive() {
        PriorityQueue queue = new PriorityQueue();
        queue.insert(10L);
        queue.insert(15L);
        queue.insert(30L);
        queue.insert(12L);
        queue.insert(50L);
        queue.insert(45L);

        queue.display();

        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());

        queue.display();
    }
}
