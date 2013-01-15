package com.ib.client.examples;


import com.ib.client.ComboLeg;
import com.ib.client.Contract;
import com.ib.client.Order;

import java.util.Vector;

public class TestRayAttach extends TestBase {

    public TestRayAttach() {
    }

    public static void main(String args[]) {
        new TestRayAttach().start();
    }

    // This places orders and listens to orders status
    public void run() {
        try {
            // Make connection, wait for nextOrderId
            connectToTWS();

            while (nextOrderId == -1) snooze(1000);

            puts("\nCalling onThreeLeggedCombo to place original order");

            onThreeLeggedCombo(nextOrderId, "GTC", 0.10, null, false );

            snooze(2000);

            puts("\nCalling onThreeLeggedCombo to add attach order");

            onThreeLeggedCombo(nextOrderId +1, "GTC", 0.50, nextOrderId, true);

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

    void onThreeLeggedCombo(int id, String tif, double price, Integer parent, boolean transmit) {

        Vector allAllLegs = new Vector();
        ComboLeg leg1 = new ComboLeg();
        ComboLeg leg2 = new ComboLeg();
        ComboLeg leg3 = new ComboLeg();

        leg1.m_conId = 81032967;
        leg1.m_ratio = 1;
        leg1.m_action = "BUY";
        leg1.m_exchange = "SMART";
        leg1.m_openClose = 0;
        leg1.m_designatedLocation = "";

        leg2.m_conId = 81032968;
        leg2.m_ratio = 2;
        leg2.m_action = "SELL";
        leg2.m_exchange = "SMART";
        leg2.m_openClose = 0;
        leg2.m_designatedLocation = "";

        leg3.m_conId = 81032973;
        leg3.m_ratio = 1;
        leg3.m_action = "BUY";
        leg3.m_exchange = "SMART";
        leg3.m_openClose = 0;
        leg3.m_designatedLocation = "";

        allAllLegs.add(leg1);
        allAllLegs.add(leg2);
        allAllLegs.add(leg3);

        Contract contract = new Contract();
        contract.m_symbol = "GOOG";
        contract.m_secType = "BAG";
        contract.m_exchange = "SMART";
        contract.m_currency = "USD";
        contract.m_comboLegs = allAllLegs;

        Order order = new Order();
        order.m_tif = tif;
        order.m_action = "BUY";
        order.m_totalQuantity = 1;
        order.m_orderType = "LMT";
        order.m_lmtPrice = price;
        order.m_transmit = transmit;
        if (parent != null ) order.m_parentId = parent;

        client.placeOrder(id, contract, order);

    }
}
