package com.ib.client.examples;

import com.ib.client.EWrapper;
import com.ib.client.*;

/**
 * Base class providing default implementation of all EWrapper methods.
 * <p/>
 * $Id$
 */
public abstract class ExampleBase extends Thread implements EWrapper {

    protected EClientSocket client = new EClientSocket(this);
    protected final static String TWS_HOST = "localhost";
    protected final static int TWS_PORT = 4001; //7496;
    protected final static int TWS_CLIENT_ID = 1;
    protected final static int MAX_WAIT_COUNT = 15; // 15 secs
    protected final static int WAIT_TIME = 1000; // 1 sec

    protected void connectToTWS() {
        client.eConnect(TWS_HOST, TWS_PORT, TWS_CLIENT_ID);
    }

    protected void disconnectFromTWS() {
        if (client.isConnected()) {
            client.eDisconnect();
        }
    }

    protected Order createOrder(String action, int quantity, String orderType) {
        return createOrder(action, quantity, orderType, null, null, null, true);
    }

    protected Order createOrder(String action, int quantity, String orderType,
                                Double limitPrice, String tif, Integer parentId, boolean transmit) {
        Order order = new Order();

        order.m_action = action;
        order.m_totalQuantity = quantity;
        order.m_orderType = orderType;
        if (limitPrice != null) order.m_lmtPrice = limitPrice;
        if (parentId != null) order.m_parentId = parentId;
        if (tif != null) order.m_tif = tif;
        if (!transmit) order.m_transmit = transmit;
        return order;
    }

    protected Contract createContract(String symbol, String securityType,
                                      String exchange, String currency) {
        return createContract(symbol, securityType, exchange, currency,
                null, null, 0.0);
    }

    protected Contract createContract(String symbol, String securityType,
                                      String exchange, String currency,
                                      String expiry, String right, double strike) {
        Contract contract = new Contract();

        contract.m_symbol = symbol;
        contract.m_secType = securityType;
        contract.m_exchange = exchange;
        contract.m_currency = currency;

        if (expiry != null) {
            contract.m_expiry = expiry;
        }

        if (strike != 0.0) {
            contract.m_strike = strike;
        }

        if (right != null) {
            contract.m_right = right;
        }

        return contract;
    }

    public void error(String str) {
        System.out.println(" [API.msg1] " + str);
    }

    public void error(int one, int two, String str) {
        System.out.println(" [API.msg2] " + str + " {" + one + ", " + two + "}");
    }

    public void error(Exception e) {
        System.out.println(" [API.msg3] " + e.getMessage());
    }

    public void connectionClosed() {
        System.out.println(" [API.connectionClosed] Closed connection with TWS");
    }

    public void nextValidId(int orderId) {
    }

    public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
    }

    public void tickSize(int tickerId, int field, int size) {
    }

    public void tickOptionComputation(int tickerId, int field, double impliedVol,
                                      double delta, double modelPrice, double pvDividend) {
    }

    public void tickOptionComputation(int tickerId, int field, double impliedVol,
                                      double delta, double optPrice, double pvDividend,
                                      double gamma, double vega, double theta, double undPrice) {
    }

    public void tickGeneric(int tickerId, int field, double generic) {
    }

    public void tickString(int tickerId, int field, String value) {
    }

    public void tickEFP(int tickerId, int field, double basisPoints, String formattedBasisPoints, double totalDividends, int holdDays, String futureExpiry, double dividendImpact, double dividendsToExpiry) {
    }

    public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId) {
    }

    public void orderStatus(int orderId, String status, int filled, int remaining,
                            double avgFillPrice, int permId, int parentId, double lastFillPrice,
                            int clientId, String whyHeld) {
    }

    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
    }

    public void openOrderEnd() {
    }

    public void updateAccountValue(String key, String value, String currency, String accountName) {
    }

    public void updatePortfolio(Contract contract, int position, double marketPrice, double marketValue, double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
    }

    public void updateAccountTime(String timeStamp) {
    }

    public void accountDownloadEnd(String accountName) {
    }

    public void contractDetails(int reqId, ContractDetails contractDetails) {
    }

    public void bondContractDetails(int reqId, ContractDetails contractDetails) {
    }

    public void contractDetailsEnd(int reqId) {
    }

    public void execDetails(int orderId, Contract contract, Execution execution) {
    }

    public void execDetailsEnd(int orderId) {
    }

    public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {
    }

    public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {
    }

    public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
    }

    public void managedAccounts(String accountsList) {
    }

    public void receiveFA(int faDataType, String xml) {
    }

    public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {
    }

    public void scannerParameters(String xml) {
    }

    public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark, String projection, String legsStr) {
    }

    public void scannerDataEnd(int reqId) {
    }

    public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count) {
    }

    public void currentTime(long time) {
    }

    public void fundamentalData(int reqId, String data) {
    }

    public void deltaNeutralValidation(int reqId, UnderComp underComp) {
    }

    public void tickSnapshotEnd(int reqId) {
    }

    public void marketDataType(int reqId, int marketDataType) {
    }

//    public void commissionReport(CommissionReport commissionReport) {
//    }
}
