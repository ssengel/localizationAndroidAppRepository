package com.example.ssengel.loginapp01.Helper;


import android.bluetooth.le.ScanResult;

import java.util.List;

/**
 * Created by thekme on 14.03.2018.
 */

public interface BLEScanner {
    void scanLeDevice(String option);
    int getPosition(String adress);
    void addBeacon(ScanResult scanResult);
    void sendBeaconFrame(int miliSecond);

}
