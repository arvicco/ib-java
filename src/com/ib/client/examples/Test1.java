package com.ib.client.examples;

import com.ib.client.Contract;

/**
 * Simple test which will pull price/size ticks for a given symbol.
 */
public class Test1 extends TestBase {
    private String symbol = null;
    private int requestId = 0;

    public Test1(String symbol) {
        this.symbol = symbol;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            puts(" Usage: java Test1 <symbol>");
            System.exit(1);
        } else {
            new Test1(args[0]).start();
        }
    }

    public void run() {
        try {
            // Make connection
            connectToTWS();

            puts("Sample TWS API Client code to print market data");
            puts("Getting quotes for: " + symbol);
            puts("Press CTRL-C to quit");

            // Create a contract, with defaults...
            Contract contract = createContract(symbol, "STK", "SMART", "USD");

            // Request market data stream
            client.reqMktData(requestId++, contract, "", true);

            int waitCount = 0;
            while (waitCount < MAX_WAIT_COUNT) {
                sleep(WAIT_TIME); // Pause for 1 second
                waitCount++;
            }

        } catch (Throwable t) {
            puts("Test1.run: Problem occurred while processing: " + t.getMessage());
        } finally {
            disconnectFromTWS();
        }
    }

    public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
        switch (field) {
            case 1:  //bid
                puts("Bid Price = " + String.valueOf(price));
                break;
            case 2:  //ask
                puts("Ask Price = " + String.valueOf(price));
                break;
            case 4:  //last
                puts("Last Price = " + String.valueOf(price));
                break;
            case 6:  //high
                puts("High Price = " + String.valueOf(price));
                break;
            case 7:  //low
                puts("Low Price = " + String.valueOf(price));
                break;
            case 9:  //close
                puts("Close Price = " + String.valueOf(price));
                break;
        }
    }

    public void tickSize(int tickerId, int field, int size) {
        switch (field) {
            case 0:   //bid
                puts("Bid Size = " + String.valueOf(size));
                break;
            case 3:   //ask
                puts("Ask Size = " + String.valueOf(size));
                break;
            case 5:   //last
                puts("Last Size = " + String.valueOf(size));
                break;
            case 8:   //volume
                puts("Volume = " + String.valueOf(size));
                break;
        }
    }


}
