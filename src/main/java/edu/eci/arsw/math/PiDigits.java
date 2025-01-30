package edu.eci.arsw.math;
///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
import edu.eci.arsw.threads.PiDigitsThread;
import java.util.concurrent.CountDownLatch;

/**
 * Calcula los dígitos hexadecimales de Pi en paralelo utilizando varios hilos.
 */
public class PiDigits {

    private static final int DigitsPerSum = 8;
    private static final double Epsilon = 1e-17;

    /**
     * Retorna un rango de dígitos hexadecimales de pi.
     * @param start La ubicación inicial del rango.
     * @param count La cantidad de dígitos a retornar.
     * @param n El número de hilos a usar para la paralelización.
     * @return Un arreglo con los dígitos hexadecimales.
     */
    public static byte[] getDigits(int start, int count, int n) {
        if (start < 0 || count < 0 || n <= 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        byte[] digits = new byte[count];
        int chunkSize = count / n; 
        int remainder = count % n; 

        CountDownLatch latch = new CountDownLatch(n); 

        int currentStart = start;
        PiDigitsThread[] threads = new PiDigitsThread[n]; 

        for (int i = 0; i < n; i++) {
            final int threadStart = currentStart;
            final int threadCount = (i < remainder) ? chunkSize + 1 : chunkSize;

            threads[i] = new PiDigitsThread(threadStart, threadCount, digits, start, latch);
            threads[i].start(); 

            currentStart += threadCount;
        }

        for (PiDigitsThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Thread interrupted", e);
            }
        }

        return digits;
    }

    /**
     * Calcula un rango de dígitos de Pi secuencialmente.
     * @param start El dígito inicial.
     * @param count La cantidad de dígitos a calcular.
     * @return Un arreglo de dígitos.
     */
    public static byte[] calculateRange(int start, int count) {
        byte[] digits = new byte[count];
        double sum = 0;

        for (int i = 0; i < count; i++) {
            if (i % DigitsPerSum == 0) {
                sum = 4 * sum(1, start)
                        - 2 * sum(4, start)
                        - sum(5, start)
                        - sum(6, start);

                start += DigitsPerSum;
            }

            sum = 16 * (sum - Math.floor(sum));
            digits[i] = (byte) sum;
        }

        return digits;
    }

    /**
     * Retorna la suma de 16^(n - k)/(8 * k + m) de 0 a k.
     * @param m La base del módulo.
     * @param n La base del exponente.
     * @return La suma calculada.
     */
    private static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < Epsilon) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    /**
     * Retorna 16^p mod m.
     * @param p El exponente.
     * @param m El módulo.
     * @return El resultado de 16^p mod m.
     */
    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }
}
