 package com.ib.client.examples;


import com.ib.client.ComboLeg;
import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.TagValue;

import java.util.Vector;

public class TestPlacement extends TestBase {

    public TestPlacement() {
    }

    public static void main(String args[]) {
        new TestPlacement().start();
    }

    // This places orders and listens to orders status
    public void run() {
        try {
            // Make connection, wait for nextOrderId
            connectToTWS();

            while (nextOrderId == -1) snooze(1000);

            puts("\nCalling onWFC");

            onWFC();

            snooze(2000);

            // nextOrderId += 1;

            // puts("\nCalling onThreeLeggedCombo to place original order");

            // onThreeLeggedCombo("DAY", 0.10);
            // snooze(2000);

            // puts("\nOrders is placed. Cancelling them now");

            // client.cancelOrder(nextOrderId);
            // client.cancelOrder(nextOrderId-1);

            snooze(4000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectFromTWS();
        }
    }

    void onWFC(){ 
        
      Order order = new Order(); 
      Contract contract = new Contract(); 
    
      // Vector m_smartComboRoutingParams = new Vector(); 
      // m_smartComboRoutingParams.add(new TagValue("NonGuaranteed", "1")); 
      // order.m_smartComboRoutingParams = m_smartComboRoutingParams; 
      
      order.m_action = "BUY"; 
      order.m_totalQuantity = 100; 
      order.m_orderType = "LMT"; 
      order.m_lmtPrice = 9.13; 
      
      contract.m_symbol = "WFC"; 
      contract.m_secType = "STK"; 
      contract.m_exchange = "NYSE"; 
      contract.m_currency = "USD";   
      
      client.placeOrder(nextOrderId, contract, order); 
   } 

}
