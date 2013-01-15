package com.ib.client.examples;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderComboLeg;

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

            puts("\nDefine options Combo (GOOGLE butterfly)");
            Contract combo = createButterfy("GOOG", "201301", "CALL", 500.0, 510.0, 520.0);

            puts("\nPlace order for an Option Combo");

            Order order = createOrder("Original", "BUY", 10, "LMT", 0.01, "DAY", null, true);

            for (int i = 0; i < 3; i++) {
                OrderComboLeg legPrice = new OrderComboLeg(); // 0.0
                order.m_orderComboLegs.add(legPrice);
            }

            place(nextOrderId, combo, order);

            puts("\nModify placed order for an Option Combo");
            order.m_totalQuantity = 15;
            order.m_lmtPrice = 0.06;
            order.m_tif = "GTC";

            client.placeOrder(nextOrderId, combo, order);
            puts("Modified order:", nextOrderId);
            snooze(2000);

            puts("\nSee? Order is modified. Cancelling it now");

            client.cancelOrder(nextOrderId);

            snooze(2000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectFromTWS();
        }
    }

}
