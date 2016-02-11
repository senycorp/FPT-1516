package fpt.com.problem4.threads;

import fpt.com.problem4.component.Balance;
import fpt.com.problem4.component.Customer;
import fpt.com.problem4.component.WaitingQueue;

import java.util.Random;

/**
 * Cashpoint-Thread
 *
 * @author Selcuk Kekec
 */
public class Cashpoint implements Runnable, Comparable {

    /**
     * Customer queue of cashpoint
     */
    private WaitingQueue queue;

    /**
     * Cash
     */
    private int sum = 0;

    /**
     * Thread-Holder
     */
    private Thread thread;

    private int id;

    public Cashpoint(int id) {
        this.queue = WaitingQueue.factory();
        this.thread = new Thread(this);
        this.id = id;

        Balance.getInstance().registerCashpoint(this);
    }

    public int getId() {
        return this.id;
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
                    System.out.println("[CASHPOINT " + this.id + "]: Sleep for " + random + " ms...");

                    // Sleep thread
                    Thread.sleep(random);

                    // Delete a customer from
                    this.queue.pop();

                    // Increase cashpoint sum
                    this.sum += new Random().nextInt(50 - 1 + 1) + 1;
                    Balance.getInstance().sortCashpoints();

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
    public static Cashpoint factory(int id) {
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

    public double getSum() {
        return this.sum;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o)
            return 0;

        Cashpoint c = (Cashpoint) o;
        if (this.getSum() > c.getSum())
            return -1;
        else if (this.getSum() < c.getSum())
            return 1;
        else
            return 0;
    }

    public String toString() {
        return "Cashpoint["+this.getId()+"] = "+this.getSum();
    }
}
