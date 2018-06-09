package com.example.ssengel.loginapp01.Model;

import com.example.ssengel.loginapp01.Helper.RssiFilter;

public class BeaconImpl implements Beacon {
    private String macAddress;
    private String beaconName;

    private double rssi;
    private int txPower;
    RssiFilter rssiFilter;
    private boolean isFilterApplied = false;


    public BeaconImpl(String macAddress, int txPower) {
        this.macAddress = macAddress;
        this.txPower = txPower;
    }

    public BeaconImpl(String macAddress, double rssi, int txPower) {
        this.macAddress = macAddress;
        this.rssi = rssi;
        this.txPower = txPower;
    }

    public BeaconImpl(String macAddress, double rssi, int txPower, RssiFilter rssiFilter) {
        this(macAddress,rssi, txPower);
        this.rssiFilter = rssiFilter;
    }


    public BeaconImpl(String beaconName, String macAddress, double rssi, int txPower, RssiFilter rssiFilter) {
        this(macAddress,rssi, txPower);
        this.rssiFilter = rssiFilter;
        this.beaconName=beaconName;
    }

    @Override
    public String getMacAddress() {
        return macAddress;
    }

    @Override
    public void setBeaconName(String beaconName) {
        this.beaconName=beaconName;
    }

    @Override
    public String getBeaconName() {
        return beaconName;
    }

    @Override
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public double getRssi() {
        return rssi;
    }

    @Override
    public void setRssi(double rssi) {
        isFilterApplied = false;
        this.rssi = rssi;
        applyFilter();
    }

    @Override
    public int getTxPower() {
        return txPower;
    }

    @Override
    public void setTxPower(int txPower) {
        this.txPower = txPower;
    }

    @Override
    public RssiFilter getRssiFilter() {
        return rssiFilter;
    }

    @Override
    public void setRssiFilter(RssiFilter rssiFilter) {
        this.rssiFilter = rssiFilter;
    }


    @Override
    public void applyFilter() {
        if (rssiFilter == null) {
            throw new IllegalStateException("RSSI filter must be set before applyFilter operation call!");
        }
        rssi = rssiFilter.applyFilter(rssi);
        isFilterApplied = true;
    }


}
