package com.example.ssengel.loginapp01.Model;


import com.example.ssengel.loginapp01.Helper.RssiFilter;

public interface Beacon {
    String getMacAddress();
    void setBeaconName(String name);
    String getBeaconName();
    void setMacAddress(String macAddress);
    double getRssi();
    void setRssi(double rssi);
    int getTxPower();
    void setTxPower(int txPower);
    RssiFilter getRssiFilter();
    void setRssiFilter(RssiFilter rssiFilter);
    void applyFilter();
}
