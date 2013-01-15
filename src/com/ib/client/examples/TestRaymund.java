package com.ib.client.examples;


import com.ib.client.ComboLeg;
import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.TagValue;

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

            puts("\nCalling onTwoLeggedCombo");

            onTwoLeggedCombo();

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

    void onThreeLeggedCombo(String tif, double price) {

        Order order = new Order();
        Contract contract = new Contract();


        Vector m_smartComboRoutingParams = new Vector(); 
        m_smartComboRoutingParams.add(new TagValue("NonGuaranteed", "1")); 
        order.m_smartComboRoutingParams = m_smartComboRoutingParams; 

        Vector comboLegs = new Vector();
        comboLegs.add(new ComboLeg(81032967, 1, "BUY", "SMART", 0)); 
        comboLegs.add(new ComboLeg(81032968, 2, "SELL", "SMART", 0)); 
        comboLegs.add(new ComboLeg(81032973, 1, "BUY", "SMART", 0)); 

        contract.m_symbol = "USD";
        contract.m_secType = "BAG";
        contract.m_exchange = "SMART";
        contract.m_currency = "USD";
        contract.m_comboLegs = comboLegs;

        order.m_tif = tif;
        order.m_action = "BUY";
        order.m_totalQuantity = 1;
        order.m_orderType = "LMT";
        order.m_lmtPrice = price;

        client.placeOrder(nextOrderId, contract, order);

    }

    void onTwoLeggedCombo(){ 
        
      Order order = new Order(); 
      Contract contract = new Contract(); 
    
      Vector m_smartComboRoutingParams = new Vector(); 
      m_smartComboRoutingParams.add(new TagValue("NonGuaranteed", "1")); 
      order.m_smartComboRoutingParams = m_smartComboRoutingParams; 
      
      Vector comboLegs = new Vector(); 
      comboLegs.add(new ComboLeg(272093 /* MSFT */,1,"BUY","SMART",0)); 
      comboLegs.add(new ComboLeg(87335484 /* C */,2,"SELL","SMART",0)); 
      
      order.m_action = "SELL"; 
      order.m_totalQuantity = 30; 
      order.m_orderType = "LMT"; 
      order.m_lmtPrice = 1; 
      
      contract.m_symbol = "USD"; 
      contract.m_secType = "BAG"; 
      contract.m_exchange = "SMART"; 
      contract.m_currency = "USD";   
      contract.m_comboLegs = comboLegs; 
      
      client.placeOrder(nextOrderId, contract, order); 
   } 

}
