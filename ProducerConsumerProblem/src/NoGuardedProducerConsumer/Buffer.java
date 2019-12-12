package NoGuardedProducerConsumer;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {

    private Queue<Integer> list;
    private int size;
    private static Buffer instance;

    private Buffer(int size) {
        this.list = new LinkedList<Integer>();
        this.size = size;
    }

    public static Buffer getInstance(int size) {
        if(instance == null) {
            instance = new Buffer(size);
        }
        return instance;
    }

    public void consume() throws InterruptedException {
        while (true) {
            while (list.size() == 0) {
                wait();
            }

            int item = list.poll();
            System.out.println("Consume " + item);

            if(item == size - 1) {
                notify();
            }
            Thread.sleep(1000);
        }
    }

    public void produce() throws InterruptedException {
        while(true) {
            int item = 0;

            while(list.size() >= size) {
                wait();
            }

            list.add(item);
            System.out.println("Producer produced " + item);

            item++;
            if(item >= 0) {
                notify();
            }
            Thread.sleep(1000);
        }
    }
}
