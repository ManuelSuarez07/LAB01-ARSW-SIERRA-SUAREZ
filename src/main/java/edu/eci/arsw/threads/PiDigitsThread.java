
package edu.eci.arsw.threads;

import edu.eci.arsw.math.PiDigits;
import java.util.concurrent.CountDownLatch;
/**
 * @author Sierra - Suarez
 */
public class PiDigitsThread extends Thread {
    
    private final int start;
    private final int count;
    private final byte[] digits;
    private final int baseStart;
    private final CountDownLatch latch;

    public PiDigitsThread(int start, int count, byte[] digits, int baseStart, CountDownLatch latch) {
        this.start = start;
        this.count = count;
        this.digits = digits;
        this.baseStart = baseStart;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            byte[] partialDigits = PiDigits.calculateRange(start, count);           
            synchronized (digits) {
                System.arraycopy(partialDigits, 0, digits, start - baseStart, partialDigits.length);
            }
        } finally {
            latch.countDown(); 
        }
    }
}
