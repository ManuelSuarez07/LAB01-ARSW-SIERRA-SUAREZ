package edu.eci.arsw.threads;

import edu.eci.arsw.math.PiDigits;

/**
 *
 * @author Sierra - Suarez
 */
public class CountThreadsMain {

    public static void main(String a[]) {
        
        /** PRIMER PUNTO */
        /**
         * CountThread countThread1 = new CountThread(0, 99); CountThread
         * countThread2 = new CountThread(99, 199); CountThread countThread3 =
         * new CountThread(200, 299);
         *
         * System.out.println("Inicio con start()"); countThread1.start();
         * countThread2.start(); countThread3.start();
         *
         * /**
         * System.out.println("Inicio con run()"); countThread1.run();
         * countThread2.run(); countThread3.run();
         */
        
        /** SEGUNDO PUNTO */
        /***/
        int start = 0;
        int count = 100000; // Número total de dígitos a calcular
        int numThreads = 4; // Número de hilos

        System.out.println("Calculando " + count + " dígitos de PI en " + numThreads + " hilos...");

        try {
            byte[] result = PiDigits.getDigits(start, count, numThreads);

            System.out.println("Dígitos calculados:");
            for (byte b : result) {
                System.out.print(Integer.toHexString(b & 0xFF).toUpperCase());
            }
            System.out.println();

        } catch (IllegalArgumentException e) {
            System.err.println("Error en los parámetros: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Error en la ejecución: " + e.getMessage());
        }
        
        /** TERCER PUNTO */
        /**
        int start = 0;
        int count = 100000; // Cien mil dígitos de PI
        int cores = Runtime.getRuntime().availableProcessors();
        int[] threadConfigs = {cores, cores * 2, 200, 500};

        for (int numThreads : threadConfigs) {
            long startTime = System.currentTimeMillis();

            try {
                byte[] result = PiDigits.getDigits(start, count, numThreads);
                
                // Imprimir algunos dígitos como ejemplo
                System.out.println("Primeros dígitos calculados con " + numThreads + " hilos:");
                for (int i = 0; i < 10; i++) {
                    System.out.print(Integer.toHexString(result[i] & 0xFF).toUpperCase());
                }
                System.out.println("...");

            } catch (Exception e) {
                System.err.println("Error al procesar con " + numThreads + " hilos.");
            }

            long endTime = System.currentTimeMillis();

            System.out.println("Proceso finalizado con " + numThreads + 
                               " hilo(s). El tiempo fue de " + 
                               (endTime - startTime) + " ms.");
        }*/
    }
}

