package com.ib.client.examples;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderState;

/**
 * Testing attached (GTC) orders
 */
public class TestAttached extends TestBase {

    public TestAttached() {
    }

    public static void main(String args[]) {
        new TestAttached().start();
    }

    // This places orders and listens to orders status
    public void run() {
        try {
            // Make connection, wait for nextOrderId
            connectToTWS();
            while (nextOrderId == -1) snooze(1000);

            Contract contract = createContract("IBM", "STK", "SMART", "USD");

            puts("\nPlace order with attached target for a regular IBM stock");
            Order par = createOrder("Parent", "BUY", 100, "LMT", 180.00, "GTC", null, false);
            place(contract, par);
            place(contract, createOrder("Attached", "SELL", 100, "LMT", 220.00, "GTC", nextOrderId, true));
            puts(par.m_orderId);
            puts("\nSee? Two sets of callbacks received: for parent BUY order and target SELL. Everything works fine so far...");

            puts("\nDefining options Combo (let's try GOOGLE butterfly)");
            Contract combo = createButterfy("GOOG", "201301", "CALL", 500.0, 510.0, 520.0);

            puts("\nNow, let's place order for an Option Combo with attached target");
            place(combo, createOrder("Parent", "BUY", 10, "LMT", 0.10, "GTC", null, false));
            place(combo, createOrder("Attached", "SELL", 10, "LMT", 1.00, "GTC", nextOrderId, true));

            puts("\nSee? Instead of confirmation, target SELL order is rejected, just sits Inactive!");

            snooze(4000);
            puts("\nSo, what shall we do now?");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectFromTWS();
        }
    }

}
