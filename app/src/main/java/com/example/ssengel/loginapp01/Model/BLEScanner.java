package com.example.ssengel.loginapp01.Model;


import android.bluetooth.le.ScanResult;

import java.util.List;

/**
 * Created by thekme on 14.03.2018.
 */

public interface BLEScanner {
    void scanLeDevice(String option);
    int getPosition(String adress);
    void addBeacon(ScanResult scanResult);
    void updatelistRssi(String url,String userId);
    List <BeaconImpl> getListScanResult();

}
