package com.ib.client.examples;

import com.ib.client.Contract;
import com.ib.client.Order;

/**
 * Testing modifications for Combo orders
 */
public class TestFundamental extends TestBase {

    public TestFundamental() {
    }

    public static void main(String args[]) {
        new TestFundamental().start();
    }

    // This places orders and listens to orders status
    public void run() {
        try {
            // Make connection, wait for nextOrderId
            connectToTWS();

            puts("\nRequest Fundamental Data");

            Contract ibm = new Contract();
            ibm.m_symbol = "IBM";
            ibm.m_secType = "STK";
            ibm.m_exchange = "NYSE";
            ibm.m_currency = "USD";

            client.reqFundamentalData(10, ibm, "Estimates");

            snooze(2000);

            puts("\nLeaving now");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectFromTWS();
        }
    }

}
