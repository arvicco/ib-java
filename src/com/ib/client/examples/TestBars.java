package com.ib.client.examples;

import com.ib.client.Contract;
import com.ib.client.EClientSocket;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Simple test of RealTimeBars
 */
public class TestBars extends ExampleBase {
    public Hashtable<Integer, DataHolder> ht = new Hashtable<Integer, DataHolder>();
    private int nextSymbolID = -1;

    public TestBars() {
    }

    public static void main(String args[]) {
        new TestBars().start();
    }

    // This monitors all symbols (calling reqRealTimeBars every 300 millis).
    public void run() {
        try {

            // Make connection
            connectToTWS();

            // Start monitoring all symbols
            String symbols[] = getSymbols();
            System.out.println("Monitoring " + symbols.length + " symbols");
            for (String symbol : symbols) {
                Contract contract = createContract(symbol, "STK", "SMART", "USD");
                nextSymbolID++;
                DataHolder holder = new DataHolder(contract, nextSymbolID);

                System.out.println("Requesting: " + holder.getRequestID()+ "." + holder.getContract().m_symbol);

                client.reqRealTimeBars(holder.getRequestID(), holder.getContract(), 5, "TRADES", false);

                ht.put(holder.getRequestID(), holder);
            }

            // Now that all symbols are being monitored, let's just watch
            // to how many are actually receiving data.
            while (true) {
                sleep(1000);
                System.out.println(getSymbolsReceivingData().size() + " symbols receiving data");
            }
        } catch (Exception e) {
            System.out.println("Exception!");
            e.printStackTrace();
        } finally {
            disconnectFromTWS();
        }
    }

    public static String[] getSymbols() {
        String str[] = {
                "MSFT",
                "INTC",
                "ORCL",
                "WNR",
                "WMT",
                "WM",
                "WFR",
                "WFC",
                "WDC",
                "C",  // 10
                "AAPL",
                "VOD",
                "VLO",
                "VIV",
                "USB",
                "UPL",
                "UNP",
                "UNM",
                "UNH",
                "UMC",  // 20
        };

        return (str);
    }

    private ArrayList<String> getSymbolsReceivingData() {
        Enumeration<Integer> en = ht.keys();
        ArrayList<String> symbols = new ArrayList<String>();

        try {
            while (en.hasMoreElements()) {
                DataHolder holder = ht.get(en.nextElement());
                if (holder != null) {
                    if (holder.getLastBarReceived() != 0) {
                        if (holder.getLastBarReceived() + 1000 * 30 >= System.currentTimeMillis())
                            symbols.add(holder.getContract().m_symbol);
                    }
                }
            }
        } catch (Exception e) {
            return (symbols);
        }
        return (symbols);
    }

    private class DataHolder {
        private int requestID = 1;
        private Contract contract = null;
        private long lastBarReceived = 0;

        public DataHolder(Contract contract, int requestID) {
            this.requestID = requestID;
            this.contract = contract;
        }

        public void newBar() {
            lastBarReceived = System.currentTimeMillis();
        }

        public long getLastBarReceived() {
            return lastBarReceived;
        }

        public int getRequestID() {
            return requestID;
        }

        public Contract getContract() {
            return contract;
        }
    }
}
