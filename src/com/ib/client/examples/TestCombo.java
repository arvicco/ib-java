package com.ib.client.examples;

import com.ib.client.*;

import java.util.Vector;

/**
 * Replicates Combo example from API reference
 */
public class TestCombo extends ExampleBase {
    private int requestId = 0;
    private int leg1_conId = -1;
    private int leg2_conId = -1;
    private int leg3_conId = -1;
    private double lastPrice = 0.0;

    public TestCombo() {
    }

    public static void main(String[] args) {
        new TestCombo().start();
    }

    public void run() {
        try {
            boolean isSuccess = false;
            int waitCount = 0;

            // Make connection
            connectToTWS();

            //First leg
            Contract con1 = new Contract();
            con1.m_symbol = "GOOG";
            con1.m_secType = "OPT";
            con1.m_expiry = "201301";
            con1.m_strike = 500.0;
            con1.m_right = "C";
            con1.m_multiplier = "100";
            con1.m_exchange = "SMART";
            con1.m_currency = "USD";
            client.reqContractDetails(1, con1);


            //Second leg
            Contract con2 = new Contract();
            con2.m_symbol = "GOOG";
            con2.m_secType = "OPT";
            con2.m_expiry = "201301";
            con2.m_strike = 510.0;
            con2.m_right = "C";
            con2.m_multiplier = "100";
            con2.m_exchange = "SMART";
            con2.m_currency = "USD";
            client.reqContractDetails(2, con2);

            //Third leg
            Contract con3 = new Contract();
            con3.m_symbol = "GOOG";
            con3.m_secType = "OPT";
            con3.m_expiry = "201301";
            con3.m_strike = 520.0;
            con3.m_right = "C";
            con3.m_multiplier = "100";
            con3.m_exchange = "SMART";
            con3.m_currency = "USD";
            client.reqContractDetails(2, con3);

            // Wait for conIds
            while (leg1_conId == -1 || leg2_conId == -1 || leg3_conId == -1) {
                sleep(WAIT_TIME);
            }

            //    Once the program has acquired the conId value for
            //    each leg, include it in ComboLeg object:
            ComboLeg leg1 = new ComboLeg(); // for the first leg
            ComboLeg leg2 = new ComboLeg(); // for the second leg
            ComboLeg leg3 = new ComboLeg(); // for the second leg

            Vector addAllLegs = new Vector();

            leg1.m_conId = leg1_conId;
            leg1.m_ratio = 1;
            leg1.m_action = "SELL";
            leg1.m_exchange = "SMART";
            leg1.m_openClose = 0;
            leg1.m_shortSaleSlot = 0;
            leg1.m_designatedLocation = "";

            leg2.m_conId = leg2_conId;
            leg2.m_ratio = 1;
            leg2.m_action = "BUY";
            leg2.m_exchange = "SMART";
            leg2.m_openClose = 0;
            leg2.m_shortSaleSlot = 0;
            leg2.m_designatedLocation = "";

            leg3.m_conId = leg2_conId;
            leg3.m_ratio = 1;
            leg3.m_action = "SELL";
            leg3.m_exchange = "SMART";
            leg3.m_openClose = 0;
            leg3.m_shortSaleSlot = 0;
            leg3.m_designatedLocation = "";

            addAllLegs.add(leg1);
            addAllLegs.add(leg2);
            addAllLegs.add(leg3);

            //    Invoke the placeOrder()method with the appropriate contract and order objects

            Contract contract = new Contract();

            Order order = new Order();
            contract.m_symbol = "GOOG";     // For combo order use "USD" as the symbol value all the time
            contract.m_secType = "BAG";   // BAG is the security type for COMBO order
            contract.m_exchange = "SMART";
            contract.m_currency = "USD";
            contract.m_comboLegs = addAllLegs; //including combo order in contract object
            order.m_action = "BUY";
            order.m_tif = "DAY";
            order.m_totalQuantity = 10;
            order.m_lmtPrice = 0.01;
            order.m_orderRef = "Original";
            order.m_orderType = "LMT";

            client.placeOrder(22, contract, order);

            // Now let's just wait for all Order confirmations/errors from TWS
            while (true) {
                System.out.println(".");
                sleep(1000);
            }

        } catch (Throwable t) {
            System.out.println("Example1.run() :: Problem occurred during processing: " + t.getMessage());
        } finally {
            disconnectFromTWS();
        }
    }


    // All  conId numbers are delivered by the ContractDetail()
    public void contractDetails(int reqId, ContractDetails contractDetails) {
        Contract contract = contractDetails.m_summary;
        /*
        reqId = 1 is corresponding to the first request or first leg
        reqId = 2 is corresponding to the second request or second leg*/

        if (reqId == 1) {
            leg1_conId = contract.m_conId;
            System.out.println(leg1_conId);
        } // to obtain conId for first leg

        if (reqId == 2) {
            leg2_conId = contract.m_conId;
            System.out.println(leg2_conId);
        } // to obtain conId for second leg

        if (reqId == 3) {
            leg3_conId = contract.m_conId;
            System.out.println(leg3_conId);
        } // to obtain conId for second leg
    }

    public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
        System.out.println(" [API.orderStatus] " + EWrapperMsgGenerator.orderStatus(orderId, status, filled, remaining, avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld));
    }

    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
        System.out.println(" [API.openOrder] " + EWrapperMsgGenerator.openOrder(orderId, contract, order, orderState));
    }


}
