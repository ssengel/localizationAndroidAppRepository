package com.example.ssengel.loginapp01.Model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ssengel.loginapp01.Activity.LoginActivity;
import com.example.ssengel.loginapp01.Constant.ServerURL;

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



public class BLEScannerImpl implements BLEScanner {

    private ScanCallback scanCallback;
    private ArrayList<BeaconImpl> lstScanResults;
    private ScanSettings lowScanSettings;
    private ScanSettings fastScanSettings;
    private BluetoothAdapter btAdapter;
    private BluetoothLeScanner btScanner;
    private List<ScanFilter> scanFilters;
    private String beaconNames[]={"DBMBLE0","DBMBLE1","DBMBLE2","DBMBLE3","DBMBLE4","DBMBLE5","DBMBLE6","DBMBLE7","DBMBLE8"};
    final Comparator com;
    private RequestQueue mRequestQueue;
    public BLEScannerImpl(BluetoothManager btManager, Context context){
        mRequestQueue= Volley.newRequestQueue(context);

        lstScanResults = new ArrayList<>();
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

    @Override
    public void addBeacon(ScanResult scanResult) {
        int existingPosition = getPosition(scanResult.getDevice().getAddress());

        if (existingPosition > -1){
            lstScanResults.get(existingPosition).setRssi(scanResult.getRssi());
        }
        else {
            BeaconImpl mBeaconImpl = new BeaconImpl(
                    scanResult.getDevice().getName(),
                    scanResult.getDevice().getAddress(),
                    scanResult.getRssi(),
                    -60,
                    new KalmanFilter()
            );
            lstScanResults.add(mBeaconImpl);
        }
    }

    @Override
    public void updatelistRssi(final String  url,final String userId) {
        new Thread (new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);                             //Threadi 1 saniye bekler
                        Collections.sort(lstScanResults,com);            //Rssi büyükten küçüğe sıralar ..
                        if(lstScanResults.size() > 0)
                            try {
                                BluetoothPosting(url,userId);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        lstScanResults.clear();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public List<BeaconImpl> getListScanResult() {
        return lstScanResults;
    }

    // Beacon post işlemi
    void BluetoothPosting(String ServerUrl,String userId) throws JSONException, IOException {
//        String url ="http://192.168.0.35:3000/beaconFrames";

        URL url =new URL(ServerUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        conn.setRequestProperty("Accept","application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);

        JSONArray beacons  = new JSONArray();
        int length=lstScanResults.size();
        if(length>= 5){
            length=5;
        }
        for(int i=0; i<length ;i++){
            JSONObject beacon= new JSONObject();
            beacon.put("macAddress", lstScanResults.get(i).getMacAddress() );
            beacon.put("name", lstScanResults.get(i).getBeaconName());
            beacon.put("rssi", (int)lstScanResults.get(i).getRssi());
            beacons.put(beacon) ;
        }
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userId", userId);
        jsonParam.put("beacons", beacons );


//
//        JsonObjectRequest req = new JsonObjectRequest(url, jsonParam, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.i("JSON", response.toString());
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("JSON", error.toString());
//            }
//        });
//
//        mRequestQueue.add(req);

        Log.i("JSON", jsonParam.toString());
        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
        //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
        os.writeBytes(jsonParam.toString());

        os.flush();
        os.close();

        Log.i("STATUS", String.valueOf(conn.getResponseCode()));


        conn.disconnect();
    }

}
