package edu.eci.arsw.threads;

import edu.eci.arsw.math.PiDigits;
import java.util.ArrayList;
import java.util.List;

public class PiDigitsThread extends Thread {
    private final int start;
    private final int count;
    private byte[] result;

    /**
     * Constructor para inicializar el rango de cálculo.
     *
     * @param start El inicio del rango.
     * @param count La cantidad de dígitos a calcular.
     */
    public PiDigitsThread(int start, int count) {
        this.start = start;
        this.count = count;
    }

    @Override
    public void run() {
        // Calcular los dígitos utilizando directamente la clase PiDigits
        result = PiDigits.getDigits(start, count, 1);
    }

    /**
     * Devuelve los resultados calculados por el hilo.
     *
     * @return Un arreglo de bytes con los dígitos calculados.
     */
    public byte[] getResult() {
        return result;
    }

    /**
     * Calcula los dígitos de π en paralelo usando múltiples hilos.
     *
     * @param start      El inicio del rango.
     * @param count      El número total de dígitos a calcular.
     * @param numThreads El número de hilos a utilizar.
     * @return Un arreglo con los dígitos calculados.
     */
    public static byte[] calculateInParallel(int start, int count, int numThreads) {
        if (start < 0 || count < 0 || numThreads <= 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        // Crear una lista de hilos
        List<PiDigitsThread> threads = new ArrayList<>();
        int chunkSize = count / numThreads; // Tamaño base de cada división
        int remainder = count % numThreads; // Distribuir dígitos adicionales

        int currentStart = start; // Índice inicial para el cálculo
        for (int i = 0; i < numThreads; i++) {
            int currentCount = (i < remainder) ? chunkSize + 1 : chunkSize; // Distribuir de manera balanceada
            PiDigitsThread thread = new PiDigitsThread(currentStart, currentCount);
            threads.add(thread);
            thread.start();
            currentStart += currentCount; // Ajustar el inicio para el siguiente hilo
        }

        // Esperar a que todos los hilos terminen
        for (PiDigitsThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Thread interrupted", e);
            }
        }

        // Combinar los resultados de todos los hilos
        byte[] result = new byte[count];
        int offset = 0;
        for (PiDigitsThread thread : threads) {
            byte[] partialResult = thread.getResult();
            System.arraycopy(partialResult, 0, result, offset, partialResult.length);
            offset += partialResult.length;
        }

        return result;
    }
}

