package com.ib.client.examples;

import com.ib.client.*;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Base class for my tests
 */
public abstract class TestBase extends ExampleBase {
    protected int nextOrderId = -1;
    protected int requestId;

    public Hashtable<Double, Contract> options = new Hashtable<Double, Contract>();

    // Sleep safely (swallows interrupts)
    protected void snooze(int millis) {
        try {
            sleep(millis);
        } catch (Exception e) {
        }
    }

    // Creates BAG Contract representing a butterfly option Combo
    protected Contract createButterfy(String symbol, String expiry, String right,
                                      double strike1, double strike2, double strike3) {
        // Create appropriate Legs for a butterfly
        Vector legs = new Vector();
        legs.add(createLeg("BUY", 1, symbol, expiry, right, strike1));
        legs.add(createLeg("SELL", 2, symbol, expiry, right, strike2));
        legs.add(createLeg("BUY", 1, symbol, expiry, right, strike3));

        // Now, construct option combo (BAG) Contract
        Contract butterfly = new Contract();
        butterfly.m_symbol = symbol;
        butterfly.m_secType = "BAG"; // BAG is the security type for COMBO order
        butterfly.m_exchange = "SMART";
        butterfly.m_currency = "USD";
        butterfly.m_comboLegs = legs; //including combo legs in Contract object

        return butterfly;
    }

    // Create ComboLeg that can be added to Combo contract
    protected ComboLeg createLeg(String action, int ratio, String symbol,
                                 String expiry, String right, double strike) {

        // Request Contract details (arrives via callback)
        Contract option = createContract(symbol, "OPT", "SMART", "USD", expiry, right, strike);
        client.reqContractDetails(requestId, option);

        // Wait for Contract details to arrive into options Hash
        while (options.get(strike) == null) snooze(200);

        // Return ComboLeg with a found conId
        int conId = options.get(strike).m_conId;
        return new ComboLeg(conId, ratio, action, null, 0);
    }

    public void contractDetails(int reqId, ContractDetails contractDetails) {
        //        System.out.println(" [API.contractDetails] for " + EWrapperMsgGenerator.contractDetails(reqId, contractDetails));

        // Puts received contract into options Hash, keyed by strike
        Contract contract = contractDetails.m_summary;
        if (contract != null && "OPT".equals(contract.m_secType)) {
            System.out.println(" [API.contractDetails] for " + contract.m_symbol + "  " + contract.m_expiry + "  " + contract.m_right + "  " + contract.m_strike);
            options.put(contract.m_strike, contract);
        }
    }

    public void nextValidId(int orderId) {
        System.out.println(" [API.nextValidId] " + EWrapperMsgGenerator.nextValidId(orderId));
        nextOrderId = orderId;
    }

    public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
        System.out.println(" [API.orderStatus] " + EWrapperMsgGenerator.orderStatus(orderId, status, filled, remaining, avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld));
    }

    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
        System.out.println(" [API.openOrder] " + EWrapperMsgGenerator.openOrder(orderId, contract, order, orderState));
    }

}
