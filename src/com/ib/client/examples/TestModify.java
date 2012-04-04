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

    // This places orders and listens to orders status
    public void run() {
        try {
            // Make connection, wait for nextOrderId
            connectToTWS();
            while (nextOrderId == -1) snooze(1000);

            Contract contract = createContract("IBM", "STK", "SMART", "USD");

            puts("\nPlace order for a regular IBM stock");
            Order order = createOrder("Original", "BUY", 100, "LMT", 180.00, "GTC", null, true);
            place(contract, order);

            puts("\nModify placed order for a regular IBM stock");
            order.m_lmtPrice = 200.0;
            client.placeOrder(nextOrderId, contract, order);
            puts("Modified order:", nextOrderId);
            snooze(2000);

            puts("\nSee? Order modified successfully. Everything works fine so far...");

            puts("\nDefining options Combo (let's try GOOGLE butterfly)");
            Contract combo = createButterfy("GOOG", "201301", "CALL", 500.0, 510.0, 520.0);

            puts("\nNow, let's place order for an Option Combo");

            order = createOrder("Original",  "BUY", 10, "LMT", 0.10, "GTC", null, true);
            place(combo, order);

            puts("\nModify placed order for an Option Combo");
            order.m_lmtPrice = 0.20;
            client.placeOrder(nextOrderId, contract, order);
            puts("Modified order:", nextOrderId);
            snooze(2000);

            puts("\nSee? Order modification is rejected for no good reason!");

            snooze(4000);
            puts("\nSo, what shall we do now?");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectFromTWS();
        }
    }

}
