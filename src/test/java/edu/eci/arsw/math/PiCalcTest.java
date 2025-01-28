package edu.eci.arsw.math;

import edu.eci.arsw.threads.PiDigitsThread;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase de pruebas para verificar el cálculo de dígitos de PI usando PiDigits.
 */
public class PiCalcTest {

    // Definir 'expected' como un campo estático accesible en todas las pruebas.
    private static final byte[] expected = new byte[]{
        0x2, 0x4, 0x3, 0xF, 0x6, 0xA, 0x8, 0x8,
        0x8, 0x5, 0xA, 0x3, 0x0, 0x8, 0xD, 0x3,
        0x1, 0x3, 0x1, 0x9, 0x8, 0xA, 0x2, 0xE,
        0x0, 0x3, 0x7, 0x0, 0x7, 0x3, 0x4, 0x4,
        0xA, 0x4, 0x0, 0x9, 0x3, 0x8, 0x2, 0x2,
        0x2, 0x9, 0x9, 0xF, 0x3, 0x1, 0xD, 0x0,
        0x0, 0x8, 0x2, 0xE, 0xF, 0xA, 0x9, 0x8,
        0xE, 0xC, 0x4, 0xE, 0x6, 0xC, 0x8, 0x9,
        0x4, 0x5, 0x2, 0x8, 0x2, 0x1, 0xE, 0x6,
        0x3, 0x8, 0xD, 0x0, 0x1, 0x3, 0x7, 0x7
    };

    public PiCalcTest() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test para generar dígitos de Pi y comparar con los valores esperados.
     */
    @Test
    public void piGenTest() throws Exception {
        // Test para diferentes rangos de inicio y cantidad de dígitos
        for (int start = 0; start < expected.length; start++) {
            for (int count = 0; count < expected.length - start; count++) {
                byte[] digits = PiDigits.getDigits(start, count, 200);
                assertEquals("Error en el cálculo para start=" + start + " y count=" + count, count, digits.length);

                for (int i = 0; i < digits.length; i++) {
                    assertEquals("Dígito erróneo en la posición " + (start + i), expected[start + i], digits[i]);
                }
            }
        }
    }

    /**
     * Test para calcular 100,000 de dígitos de PI usando 1 hilo.
     */
    @Test
    public void testWithOneThread() throws Exception {
        int start = 0;
        int count = 100000; // Número total de dígitos a calcular
        int numThreads = 1; // 1 hilo

        System.out.println("Calculando " + count + " dígitos de PI en " + numThreads + " hilo...");

        try {
            byte[] result = PiDigitsThread.calculateInParallel(start, count, numThreads);

            // Verificamos que los dígitos calculados no son nulos y tienen la longitud esperada
            assertNotNull("El resultado no debe ser nulo", result);
            assertEquals("La cantidad de dígitos calculados debe ser igual a la cantidad solicitada", count, result.length);

        } catch (Exception e) {
            fail("La ejecución del test falló: " + e.getMessage());
        }
    }

    /**
     * Test para calcular 100,000 de dígitos de PI usando 2 hilos.
     */
    @Test
    public void testWithTwoThreads() throws Exception {
        int start = 0;
        int count = 100000; // Número total de dígitos a calcular
        int numThreads = 2; // 2 hilos

        System.out.println("Calculando " + count + " dígitos de PI en " + numThreads + " hilos...");

        try {
            byte[] result = PiDigitsThread.calculateInParallel(start, count, numThreads);

            // Verificamos que los dígitos calculados no son nulos y tienen la longitud esperada
            assertNotNull("El resultado no debe ser nulo", result);
            assertEquals("La cantidad de dígitos calculados debe ser igual a la cantidad solicitada", count, result.length);

        } catch (Exception e) {
            fail("La ejecución del test falló: " + e.getMessage());
        }
    }

    /**
     * Test para calcular 100,000 de dígitos de PI usando 3 hilos.
     */
    @Test
    public void testWithThreeThreads() throws Exception {
        int start = 0;
        int count = 100000; // Número total de dígitos a calcular
        int numThreads = 3; // 3 hilos

        System.out.println("Calculando " + count + " dígitos de PI en " + numThreads + " hilos...");

        try {
            byte[] result = PiDigitsThread.calculateInParallel(start, count, numThreads);

            // Verificamos que los dígitos calculados no son nulos y tienen la longitud esperada
            assertNotNull("El resultado no debe ser nulo", result);
            assertEquals("La cantidad de dígitos calculados debe ser igual a la cantidad solicitada", count, result.length);

        } catch (Exception e) {
            fail("La ejecución del test falló: " + e.getMessage());
        }
    }

    // Método auxiliar para convertir los bytes a formato hexadecimal
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexChars.length; i = i + 2) {
            sb.append(hexChars[i + 1]);
        }
        return sb.toString();
    }
}
