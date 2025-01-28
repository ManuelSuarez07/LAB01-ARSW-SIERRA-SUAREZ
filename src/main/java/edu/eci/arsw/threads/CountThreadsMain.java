package edu.eci.arsw.threads;

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
        int start = 0;
        int count = 50; // Número total de dígitos a calcular
        int numThreads = 4; // Número de hilos

        System.out.println("Calculando " + count + " dígitos de PI en " + numThreads + " hilos...");

        try {
            // Realizar el cálculo en paralelo
            byte[] result = PiDigitsThread.calculateInParallel(start, count, numThreads);

            // Mostrar los resultados (convertidos a caracteres hexadecimales para mejor lectura)
            System.out.println("Dígitos calculados:");
            for (byte b : result) {
                System.out.print(Integer.toHexString(b & 0xFF).toUpperCase());
            }
            System.out.println(); // Salto de línea final

        } catch (IllegalArgumentException e) {
            System.err.println("Error en los parámetros: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Error en la ejecución: " + e.getMessage());
        }
    }
}
