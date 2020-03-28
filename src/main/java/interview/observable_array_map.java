package interview;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class observable_array_map {
    Collection<_listener> listeners;
    Map<byte[], byte[]> valueMap = new HashMap<byte[], byte[]>();

    protected observable_array_map() {
        listeners = createListenersContainer();
    }

    protected Collection<_listener> createListenersContainer() {
        return new ArrayList<_listener>();
    }

    public synchronized void addListener(_listener x) {
        listeners.add(x);
    }

    public synchronized void removeListener(_listener x) {
        listeners.remove(x);
    }

    public synchronized void put(byte[] key, byte[] value) {
        byte[] oldValue = valueMap.put(key, value);
        if (!hasChanges(oldValue, value)) {
            for (_listener x : listeners) {
                x.onChange(key, value);
            }
        }
    }

    protected abstract boolean hasChanges(byte[] oldValue, byte[] newValue);
}

class TestDrive extends observable_array_map {

    public static void main(String[] args) throws InterruptedException {
        TestDrive drive = new TestDrive();
        StringBuffer stringBuffer = new StringBuffer();
        int numberOfCpus = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numberOfCpus + 1);
        CountDownLatch latch = new CountDownLatch(numberOfCpus);
        Runnable[] adders = new Runnable[numberOfCpus*10];
        Arrays.fill(adders, (Runnable) () -> {
            latch.countDown();
            drive.addListener((oldValue, newValue) ->
                    stringBuffer.append("old ").append(oldValue.hashCode())
                            .append(" new ").append(Arrays.toString(newValue))
                            .append(" thread ").append(Thread.currentThread().getName())
                            .append("\n"));
        });

        Runnable[]removers = new Runnable[numberOfCpus*10];
        Arrays.fill(removers, (Runnable) () -> {
            drive.awaitLatch(latch);
            stringBuffer.append("removing from ")
                    .append((Thread.currentThread().getName()))
                    .append("\n");
            drive.removeListener(drive.listeners.stream().findAny().get());
        });

        Runnable[] putters = new Runnable[numberOfCpus*10];
        AtomicInteger counter = new AtomicInteger();
        Arrays.fill(putters, (Runnable) () -> {
            drive.awaitLatch(latch);
            if (counter.intValue() < numberOfCpus*5) {
                drive.putNew(stringBuffer);
                counter.incrementAndGet();

            } else {
                drive.updateAny(stringBuffer);
            }


        });

        Arrays.stream(adders).forEach((adder -> executor.submit(adder)));
        Arrays.stream(removers).forEach((remover -> executor.submit(remover)));
        Arrays.stream(putters).forEach((putter -> executor.submit(putter)));


        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(stringBuffer.toString());
    }

    @Override
    protected boolean hasChanges(byte[] oldValue, byte[] newValue) {
        return false;
    }

    public void putNew(StringBuffer stringBuffer) {
        stringBuffer.append("putting new from ")
                .append(Thread.currentThread().getName())
                .append("\n");
        byte value = (byte)(10* Math.random());
        put(new byte[]{value}, new byte[]{value++});
    }

    public void updateAny(StringBuffer stringBuffer) {
        stringBuffer.append("updating existing from ")
                .append(Thread.currentThread().getName())
                .append("\n");
        Map.Entry<byte[], byte[]> any = valueMap.entrySet().stream().findAny().get();
        put(any.getKey(), new byte[]{any.getValue()[0]++});
    }

    private void awaitLatch(CountDownLatch latch) {
        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}