
package edu.eci.arsw.threads;
/**
 *
 * @author Sierra - Suarez
 */
public class CountThreadsMain {
    
    public static void main(String a[]){

        CountThread countThread1 = new CountThread(0,99);
        CountThread countThread2 = new CountThread(99,199);
        CountThread countThread3 = new CountThread(200,299);

        System.out.println("Inicio con start()");
        countThread1.start();
        countThread2.start();
        countThread3.start();
        
        /**System.out.println("Inicio con run()");
        countThread1.run();
        countThread2.run();
        countThread3.run();*/
    }
}