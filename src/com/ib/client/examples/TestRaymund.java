package com.ib.client.examples;


import com.ib.client.ComboLeg;
import com.ib.client.Contract;
import com.ib.client.Order;

import java.util.Vector;

public class TestRaymund extends TestBase {

    public TestRaymund() {
    }

    public static void main(String args[]) {
        new TestRaymund().start();
    }

    // This places orders and listens to orders status
    public void run() {
        try {
            // Make connection, wait for nextOrderId
            connectToTWS();

            while (nextOrderId == -1) snooze(1000);

            puts("\nCalling onThreeLeggedCombo to place original order");

            onThreeLeggedCombo("DAY", 0.10);

            snooze(2000);

            puts("\nCalling onThreeLeggedCombo to modify original order");

            onThreeLeggedCombo("GTC", 0.10);

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

    void onThreeLeggedCombo(String tif, double price) {

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
//        order.m_account = "DU74649";
        order.m_action = "BUY";
        order.m_totalQuantity = 1;
        order.m_orderType = "LMT";
        order.m_lmtPrice = price;

        client.placeOrder(nextOrderId, contract, order);

    }
}
