package com.example.ssengel.loginapp01.Model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ssengel.loginapp01.Activity.LoginActivity;
import com.example.ssengel.loginapp01.Constant.ServerURL;
import com.example.ssengel.loginapp01.Service.ServiceBeaconFrames;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class BLEScannerImpl implements BLEScanner {
    private ServiceBeaconFrames serviceBeaconFrames;
    private ScanCallback scanCallback;
    private ArrayList<BeaconImpl> lstScanResults;
    private ArrayList<BeaconImpl> tempLstScanResults;
    private ScanSettings lowScanSettings;
    private ScanSettings fastScanSettings;
    private BluetoothAdapter btAdapter;
    private BluetoothLeScanner btScanner;
    private List<ScanFilter> scanFilters;
    private String beaconNames[]={"DBMBLE0","DBMBLE1","DBMBLE2","DBMBLE3","DBMBLE4","DBMBLE5","DBMBLE6","DBMBLE7","DBMBLE8"};
    final Comparator com;

    public BLEScannerImpl(BluetoothManager btManager, Context context){

        serviceBeaconFrames = new ServiceBeaconFrames(context);
        lstScanResults = new ArrayList<>();
        tempLstScanResults = new ArrayList<>();
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();
        lowScanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_POWER).build();
        fastScanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();
        scanFilters = new ArrayList<>();

        scanCallback = new ScanCallback(){
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                String name=result.getDevice().getName();
                for(String beaconName:beaconNames ) {
                    if (beaconName.equals(name)) {
                        addBeacon(result);
                    }
                }
            }
        };

        com=new Comparator() {         //List leri Short etmek için gereki obje
            @Override
            public int compare(Object o1, Object o2) {
                if(((BeaconImpl)o1).getRssi()>((BeaconImpl)o2).getRssi())
                    return -1;
                else
                    return 1;
            }
        };


    }

    @Override
    public void scanLeDevice(String option) {
        if (option.equals("LOW")){
            btScanner.startScan(scanFilters,lowScanSettings,scanCallback);

        }else if (option.equals("FAST")){
            btScanner.startScan(scanFilters,fastScanSettings,scanCallback);

        }else {//stop
            btScanner.stopScan(scanCallback);

        }
    }

    @Override
    public int getPosition(String adress) {
        int position = -1;
        for (int i = 0; i < lstScanResults.size(); i++){

            if (lstScanResults.get(i).getMacAddress().equals(adress)){
                position = i;
                break;
            }
        }
        return position;
    }

    public int getPositionInTemp(String adress) {
        int position = -1;

        for (int i = 0; i < tempLstScanResults.size(); i++){
            if (tempLstScanResults.get(i).getMacAddress().equals(adress)){
                position=i;
                break;
            }
        }
        return position;
    }

    @Override
    public void addBeacon(ScanResult scanResult) {

        int existingPosition = getPosition(scanResult.getDevice().getAddress());

        if (existingPosition > -1){

            lstScanResults.get(existingPosition).setRssi(scanResult.getRssi());
        }
        else {

            existingPosition = getPositionInTemp(scanResult.getDevice().getAddress());
            if (existingPosition > -1){
                tempLstScanResults.get(existingPosition).setRssi(scanResult.getRssi());
                lstScanResults.add(tempLstScanResults.get(existingPosition));
            }
            else{

                BeaconImpl mBeaconImpl = new BeaconImpl(
                        scanResult.getDevice().getName(),
                        scanResult.getDevice().getAddress(),
                        scanResult.getRssi(),
                        -60,
                        new KalmanFilter()
                );
                lstScanResults.add(0,mBeaconImpl);
            }
        }
    }


    @Override
    public void updatelistRssi() {
        new Thread (new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1500);
//                        if(lstScanResults.size() > 0){
                            Collections.sort(lstScanResults,com);
                            serviceBeaconFrames.postBeaconFrame(lstScanResults);
                            tempLstScanResults = lstScanResults;
                            lstScanResults = new ArrayList<>();
//                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
