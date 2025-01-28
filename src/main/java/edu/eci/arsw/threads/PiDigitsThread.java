package edu.eci.arsw.threads;

import edu.eci.arsw.math.PiDigits;
import java.util.concurrent.*;

/**
 *
 * @author Sierra - Suarez
 */
public class PiDigitsThread {

    public static byte[] calculateInParallel(int start, int count, int numThreads) {
        if (start < 0 || count < 0 || numThreads <= 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int chunkSize = count / numThreads;
        int remainder = count % numThreads;

        byte[] result = new byte[count];
        int currentStart = start;

        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            final int threadStart = currentStart;
            final int threadCount = (i < remainder) ? chunkSize + 1 : chunkSize;

            executor.submit(() -> {
                try {
                    byte[] partialResult = PiDigits.getDigits(threadStart, threadCount, 1);
                    System.arraycopy(partialResult, 0, result, threadStart - start, partialResult.length);
                } finally {
                    latch.countDown();
                }
            });

            currentStart += threadCount;
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread interrupted", e);
        } finally {
            executor.shutdown();
        }

        return result;
    }
}
