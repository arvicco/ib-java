package com.ib.client.examples;

import com.ib.client.Contract;
import com.ib.client.Order;

/**
 * Testing modifications for Combo orders
 */
public class TestContractData extends TestBase {

    public TestContractData() {
    }

    public static void main(String args[]) {
        new TestContractData().start();
    }

    // This places orders and listens to orders status
    public void run() {
        try {
            // Make connection, wait for nextOrderId
            connectToTWS();

            while (nextOrderId == -1) snooze(1000);

            puts("\nFind Contract Definitions for GOOG option");

            Contract con1 = new Contract();
            con1.m_symbol = "GOOG";
            con1.m_secType = "OPT";
            con1.m_expiry = "201301";
            con1.m_strike = 500.0;
            con1.m_right = "C";
            con1.m_multiplier = "100";
            con1.m_exchange = "SMART";
            con1.m_currency = "USD";

            client.reqContractDetails(110, con1);

            puts("Request sent");
            snooze(4000);

            puts("\nFind Contract Definitions for APPLE stock");

            Contract aapl = new Contract();
            aapl.m_symbol = "AAPL";
            aapl.m_secType = "STK";

            client.reqContractDetails(111, aapl);

            puts("Request sent");
            snooze(4000);

            puts("\nSee? No reply...");

            snooze(4000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectFromTWS();
        }
    }

}
