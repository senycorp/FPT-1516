package fpt.com.problem4;

import fpt.com.problem4.threads.Acquisition;
import fpt.com.problem4.threads.Cashpoint;

/**
 * Main-Thread-Class for Problem 4
 *
 * @author Selcuk Kekec
 */
public class Main {

    /**
     * Main-Method
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread t1 = new Thread ( new Acquisition () ) ;
        t1.start();

        try{
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Ende...") ;
    }

    public static void pooledThreads() {
        Thread Acquisition = fpt.com.problem4.threads.Acquisition.factory();
    }
}
