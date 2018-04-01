package chapter4;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class SuperMarketSimulation {

    private final static String EXIT_COMMAND = "q";
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();
    private int numberOfCpus = Runtime.getRuntime().availableProcessors();
    private Map<Queue, CashierTask> cashierProcessor = new LinkedHashMap<Queue, CashierTask>();
    private ExecutorService customerProcessor = Executors.newFixedThreadPool(numberOfCpus);
    private CountDownLatch latch = new CountDownLatch(1);
    private int customersQuantity = numberOfCpus * 100;
    private AtomicBoolean shouldContinue = new AtomicBoolean(true);

    public static void main(String[] args) {
        new SuperMarketSimulation().testDrive();
    }

    public void testDrive() {
        createCashierProcessor();
        prefillQueues();

        for (Runnable task : cashierProcessor.values()) {
            customerProcessor.submit(task);
        }

        System.out.printf("enter '%s' to quit or new customer will be added to some queue", EXIT_COMMAND);
        while (!EXIT_COMMAND.equals(scanner.next())) {
            if (latch.getCount() > 0) {
                latch.countDown();
            }

            addCustomerToSomeQueue();
        }

        System.out.println("shutting down");
        shouldContinue.set(false);
        customerProcessor.shutdown();
    }

    private void createCashierProcessor() {
        Queue newQueue = null;
        for (int i = 0; i < numberOfCpus; i++) {
            newQueue = new Queue(customersQuantity / numberOfCpus);
            cashierProcessor.put(newQueue, new CashierTask(newQueue, latch, i, shouldContinue));
        }
    }

    private void prefillQueues() {
        for (int i = 0; i < customersQuantity; i++) {
            addCustomerToSomeQueue();
        }
    }

    private void addCustomerToSomeQueue() {
        Queue shortest = cashierProcessor.keySet().toArray(new Queue[]{})[0];
        for (Queue queue : cashierProcessor.keySet()) {
            if (queue.size() < shortest.size()) {
                shortest = queue;
            }
        }

        long newCustomer  = (Math.abs(random.nextLong()) % 10) + 1;
        System.out.printf("inserting customer with %d goods to queue #%d\n", newCustomer, cashierProcessor.get(shortest).getQueueNumber());
        synchronized (shortest) {
            shortest.insert(newCustomer);
        }
    }

    static class CashierTask implements Runnable {
        private Queue queue;
        private CountDownLatch latch;
        private int queueNumber;
        private AtomicBoolean exitFlag;

        public CashierTask(Queue queue, CountDownLatch latch, int queueNumber, AtomicBoolean exitFlag) {
            this.queue = queue;
            this.latch = latch;
            this.queueNumber = queueNumber;
            this.exitFlag = exitFlag;
        }

        public int getQueueNumber() {
            return queueNumber;
        }

        public void run() {
            while (exitFlag.get()) {
                if (queue.isEmpty()) {
                    try {
                        sleep(250);

                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }

                System.out.printf("queue #%d, size %d, next customer has %d goods\n", queueNumber, queue.size(), queue.peekFront());
                long groceries = 0;
                synchronized (queue) {
                    groceries = queue.remove();
                }
                try {
                    sleep(groceries * 250);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
