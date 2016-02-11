package fpt.com.problem4.threads;

import fpt.com.problem4.component.Balance;
import fpt.com.problem4.component.Customer;

import java.util.Random;

/**
 * Acquisition-Thread
 *
 * @author Selcuk Kekec
 */
public class Acquisition
        implements Runnable {

    /**
     * Array of cashpoints
     */
    private Cashpoint[] cashPoints;

    /**
     * Counter of current active cashpoint
     */
    private int cashPointCounter = 0;

    /**
     * Indicator setted when all cashpoints are running
     */
    private boolean allCashPointsRunning = false;

    /**
     * Constructor
     */
    public Acquisition() {
        this.cashPoints =
                new Cashpoint[]{Cashpoint.factory(0),
                                Cashpoint.factory(1),
                                Cashpoint.factory(2),
                                Cashpoint.factory(3),
                                Cashpoint.factory(4),
                                Cashpoint.factory(5)};
    }

    @Override
    public void run() {
        // Open first cashpoint
        this.cashPoints[cashPointCounter].getAsThread().start();

        while (true) {
            System.out.println("[BALANCE]: " +Balance.getInstance());

            // Check for maximum amount of customers in queue
            try {
                // Generate random number between 0 - 2
                int random = new Random().nextInt(2000 - 0 + 1) + 0;
                System.out.println("[ACQUISITION]: Starting acquistion of new customer...");
                System.out.println("[ACQUISITION]: Sleep for " + random + " ms...");

                // Sleep thread
                Thread.sleep(random);

                // Add a new customer
                try {
                    // Are all cashpoints running?
                    if (!this.allCashPointsRunning) {
                        System.out.println("[ACQUISITION]: Current cashpoint [" + cashPointCounter + "]");

                        // Check amount of customers
                        if (this.cashPoints[this.cashPointCounter].getCustomerCount() < 6) {
                            System.out.println("[ACQUISITION]: Adding acquired customer to cashpoint [" +
                                                       this.cashPoints[cashPointCounter]
                                                               .getCustomerCount() + " customers]...");
                            this.cashPoints[cashPointCounter].addCustomer(new Customer());
                        } else {
                            // We need to open a new cashpoint...
                            System.out.println("[ACQUISITION]: Cashpoint [" + this.cashPointCounter + "] is full...");
                            System.out.println(
                                    "[ACQUISITION]: Opening Cashpoint [" + (this.cashPointCounter + 1) + "]...");

                            this.cashPointCounter++;
                            this.cashPoints[cashPointCounter].getAsThread().start();

                            System.out.println("[ACQUISITION]: Adding acquired customer to cashpoint [" +
                                                       this.cashPoints[cashPointCounter]
                                                               .getCustomerCount() + " customers]...");
                            this.cashPoints[cashPointCounter].addCustomer(new Customer());

                            // Set indicator when all cashpoints are running
                            if (cashPointCounter == (this.cashPoints.length - 1)) {
                                System.out.println("[ACQUISITION]: All cashpoints are opened and running...");
                                this.allCashPointsRunning = true;
                            }
                        }
                    } else {
                        // Find cashpoint with lowest amount of customers
                        int low = 99999;

                        System.out.println("[ACQUISITION]: Determine cashpoint with lowest customer amount...");
                        for (int i = 0; i < this.cashPoints.length; i++) {
                            // Compare amount of customers
                            if (this.cashPoints[i].getCustomerCount() < low) {
                                low = this.cashPoints[i].getCustomerCount();
                                cashPointCounter = i;
                            }
                        }

                        System.out.println("[ACQUISITION]: Cashpoint[" + this.cashPointCounter + "] has "
                                                   + low + " customers");

                        if ((low + 1) == 8) {
                            return;
                        } else {
                            this.cashPoints[cashPointCounter].addCustomer(new Customer());
                        }
                    }
                } catch (Exception e) {

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get Runnable as thread
     *
     * @return
     */
    public static Thread factory() {
        return new Thread(new Acquisition());
    }
}
