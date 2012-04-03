package com.ib.client.examples;

import com.ib.client.Contract;
import com.ib.client.Order;

/**
 * Testing modifications for Combo orders
 */
public class TestModify extends TestBase {

    public TestModify() {
    }

    public static void main(String args[]) {
        new TestModify().start();
    }

    // This places order with bracket and listens to orders status
    public void run() {
        try {
            // Make connection, wait for nextOrderId
            connectToTWS();
            while (nextOrderId == -1) snooze(1000);

            // Place order with attached target for a regular Stock contract
            System.out.println("\nPlacing order for a regular IBM stock");

            Contract contract = createContract("IBM", "STK", "SMART", "USD");
            Order order = createOrder("BUY", 10, "LMT", 125.00, "GTC", null, true);

//            client.placeOrder(nextOrderId++, contract, order);
            snooze(2000);
            System.out.println("\nSee? Two sets of callbacks received: parent and stop. Everything works fine so far...");


            //  Define options Combo (let's try GOOGLE butterfly)
            System.out.println("\nDefining options Combo (let's try GOOGLE butterfly)");
            Contract combo = createButterfy("GOOG", "201301", "CALL", 500.0, 510.0, 520.0);
            System.out.println(combo.m_comboLegs.size());

            //  Place bracket order for an Options Combo
            System.out.println("\nNow, placing order for an Option Combo with attached target");
//            placeOrderWithAttach(combo, 10, 0.10, 1.0);

            // Now let's just wait for all Order confirmations/errors from TWS
            snooze(4000);
            System.out.println("\nSee? Instead of confirmation, target Order is rejected, sits inactive!");

            snooze(4000);
            System.out.println("\nSo, what shall we do now?");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectFromTWS();
        }
    }

}
