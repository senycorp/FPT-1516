package fpt.com.problem4.threads;

import fpt.com.problem4.component.Customer;
import fpt.com.problem4.component.WaitingQueue;

import java.util.Random;

/**
 * Cashpoint-Thread
 *
 * @author Selcuk Kekec
 */
public class Cashpoint implements Runnable {

    /**
     * Customer queue of cashpoint
     */
    private WaitingQueue queue;

    /**
     * Thread-Holder
     */
    private Thread thread;

    private String id;

    public Cashpoint(String id) {
        this.queue = WaitingQueue.factory();
        this.thread = new Thread(this);
        this.id = id;
    }

    @Override
    public void run() {
        try {
            // Preperation of new cashpoint costs 6 seconds - Let us sleep
            System.out.println("[CASHPOINT "+this.id+"]: Preperation started...");
            Thread.sleep(6000);
            System.out.println("[CASHPOINT "+this.id+"]: Preperation finished...");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            // Check for maximum amount of customers in queue
            if (this.queue.size() > 0
                    /*&& WaitingQueue.getInstance().size() < 8*/) {
                try {
                    // Generate random number between 0 - 2
                    int random = new Random().nextInt(10000 - 6000 + 1) + 6000;
                    System.out.println("[CASHPOINT "+this.id+"]: Sleep for " + random + " ms...");

                    // Sleep thread
                    Thread.sleep(random);

                    // Delete a customer from
                    this.queue.pop();

                    System.out.println("[CASHPOINT "+this.id+"]: " + this.queue.size() + " customers left...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return;
            }
        }
    }

    /**
     * Get Runnable as thread
     *
     * @return
     */
    public static Cashpoint factory(String id) {
        //return new Thread(new Cashpoint());
        return new Cashpoint(id);
    }

    public Thread getAsThread() {
       return this.thread;
    }

    public void addCustomer(Customer e) {
        this.queue.push(e);
    }

    public int getCustomerCount() {
        return this.queue.size();
    }
}
