package fpt.com.problem4.component;

import java.util.LinkedList;

/**
 * Created by senycorp on 04.01.16.
 */
public class WaitingQueue extends LinkedList<Customer> {

    /**
     * Singleton-Holder
     */
    private static WaitingQueue instance = null;

    /**
     * Private constructor
     */
    public WaitingQueue() {

    }

    /**
     * Singleton-Getter
     *
     * @return
     */
    public static WaitingQueue getInstance() {
        // Check for existing instance
        if (instance == null) {
            instance = new WaitingQueue();
        }

        return instance;
    }

    /**
     * Factory
     *
     * @return
     */
    public static WaitingQueue factory() {
        return new WaitingQueue();
    }
}
