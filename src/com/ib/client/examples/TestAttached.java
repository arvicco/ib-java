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

    // This places order with bracket and listens to orders status
    public void run() {
        try {
            // Make connection, wait for nextOrderId
            connectToTWS();
            while (nextOrderId == -1) snooze(1000);

            // Place order with attached target for a regular Stock contract
            System.out.println("\nOrder with attached target for a regular IBM stock");

            Contract contract = createContract("IBM", "STK", "SMART", "USD");

            Order parent = createOrder("BUY", 100, "LMT", 180.00, "GTC", null, false);
            client.placeOrder(++nextOrderId, contract, parent);
            System.out.println("Placed order: " + nextOrderId);

            Order target = createOrder("SELL", 100, "LMT", 220.00, "GTC", nextOrderId, true);
            client.placeOrder(++nextOrderId, contract, target);
            System.out.println("Placed attached: " + nextOrderId);

            snooze(2000);
            System.out.println("\nSee? Two sets of callbacks received: for parent BUY order and target SELL. Everything works fine so far...");

            //  Define options Combo (let's try GOOGLE butterfly)
            System.out.println("\nDefining options Combo (let's try GOOGLE butterfly)");
            Contract combo = createButterfy("GOOG", "201301", "CALL", 500.0, 510.0, 520.0);

            //  Place bracket order for an Options Combo
            System.out.println("\nNow, order for an Option Combo with attached target");

            parent = createOrder("BUY", 10, "LMT", 0.10, "GTC", null, false);
            client.placeOrder(++nextOrderId, combo, parent);
            System.out.println("Placed order: " + nextOrderId);

            target = createOrder("SELL", 10, "LMT", 1.00, "GTC", nextOrderId, true);
            client.placeOrder(++nextOrderId, combo, target);
            System.out.println("Placed attached: " + nextOrderId);

            // Now let's just wait for all Order confirmations/errors from TWS
            snooze(4000);
            System.out.println("\nSee? Instead of confirmation, target SELL order is rejected, just sits Inactive!");

            snooze(4000);
            System.out.println("\nSo, what shall we do now?");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectFromTWS();
        }
    }
}
