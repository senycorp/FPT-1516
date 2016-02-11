package fpt.com.problem4.component;

import fpt.com.problem4.threads.Cashpoint;

import java.util.Arrays;

/**
 * Balance
 */
public class Balance {
    private static Balance   instance   = null;
    private        Cashpoint[] cashpoints = new Cashpoint[6];
    private        Cashpoint[] sortedCashpoints = new Cashpoint[6];

    private Balance() {

    }

    public static Balance getInstance() {
        if (instance == null)
            instance = new Balance();

        return instance;
    }

    public void registerCashpoint(Cashpoint c) {
        this.cashpoints[c.getId()] = c;
    }

    public void sortCashpoints() {
        System.arraycopy( this.cashpoints, 0, this.sortedCashpoints, 0, this.sortedCashpoints.length );

        Arrays.sort(this.sortedCashpoints);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.sortedCashpoints);
    }
}
