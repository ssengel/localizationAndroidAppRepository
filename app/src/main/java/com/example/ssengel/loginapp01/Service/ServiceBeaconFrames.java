package com.example.ssengel.loginapp01.Service;

import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.test.mock.MockApplication;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ssengel.loginapp01.Activity.LoginActivity;
import com.example.ssengel.loginapp01.Common.StoreInfo;
import com.example.ssengel.loginapp01.Common.UserInfo;
import com.example.ssengel.loginapp01.Constant.ServerURL;
import com.example.ssengel.loginapp01.Model.BeaconImpl;
import com.example.ssengel.loginapp01.Model.Notification;
import com.example.ssengel.loginapp01.Model.NotificationHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ssengel on 28.04.2018.
 */

public class ServiceBeaconFrames {

    private RequestQueue mRequestQueue;
    private NotificationHelper notiHelper;

    public ServiceBeaconFrames(Context context){
        this.mRequestQueue = Volley.newRequestQueue(context);
        notiHelper = new NotificationHelper(context);
    }

    public void postBeaconFrame(ArrayList<BeaconImpl> lstScanResults) throws JSONException {
        JSONArray beacons = new JSONArray();
        int length = lstScanResults.size();
        if (length >= 5) length = 5;

        for (int i = 0; i < length; i++) {
            JSONObject beacon = new JSONObject();
            beacon.put("macAddress", lstScanResults.get(i).getMacAddress());
            beacon.put("name", lstScanResults.get(i).getBeaconName());
            beacon.put("rssi", (int) lstScanResults.get(i).getRssi());
            beacons.put(beacon);
        }
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userId", UserInfo.USER.getId());
        jsonParam.put("storeId", UserInfo.USER.getStoreId());// magaza id si dinamik olarak elde edilmeli
        jsonParam.put("companId", UserInfo.USER.getCompanyId());// register da yapilan secim sonucu elde edilmeli
        jsonParam.put("beacons", beacons);

        JsonObjectRequest req = new JsonObjectRequest(ServerURL.BEACONFRAMES, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("status")) {
                        Log.e("JSON", response.toString());
                        checkNotification(response.getString("konum"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("JSON", error.toString());
            }
        });

        mRequestQueue.add(req);
    }

    private void checkNotification(String konum){
        String title = "";
        String body = "";
        for(String k : StoreInfo.MAP_NOTIFICATIONS.keySet()){
            if(k.equals(konum)){
                if(!StoreInfo.MAP_NOTIFICATIONS.get(konum).get(0).getVisited()){
                    for (Notification notification : StoreInfo.MAP_NOTIFICATIONS.get(konum)) {
                        notification.setVisited(true);
                        body += notification.getDescription() + "\n";
                        title += notification.getTitle() + "\n";
                    }
                    title = StoreInfo.MAP_NOTIFICATIONS.get(konum).get(0).getLocation() +" "+ title;
                    displayNotification(title, body);
                    break;
                }
            }
        }
    }

    private void displayNotification(String title, String body){
        NotificationCompat.Builder builder = notiHelper.getNotificationBuilder(title, body);
        notiHelper.getManager().notify(new Random().nextInt(), builder.build());
    }

}
