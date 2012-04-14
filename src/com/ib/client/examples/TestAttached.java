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

            puts("\nDefine options Combo (GOOGLE butterfly)");
            Contract combo = createButterfy("GOOG", "201301", "CALL", 500.0, 510.0, 520.0);

            puts("\nPlace order for an Option Combo with attached target");

            place(nextOrderId, combo,
                    createOrder("Parent", "BUY", 10, "LMT", 0.10, "GTC", null, false));

            place(nextOrderId + 1, combo,
                    createOrder("Attached", "SELL", 10, "LMT", 1.00, "GTC", nextOrderId, true));

            snooze(2000);
            puts("\nSee? Instead of confirmation, target SELL order is rejected, just sits Inactive!");

            puts("\nCancelling it now");

            client.cancelOrder(nextOrderId);
            snooze(2000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectFromTWS();
        }
    }

}
