package com.example.ssengel.loginapp01.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ssengel.loginapp01.Activity.LoginActivity;
import com.example.ssengel.loginapp01.Common.StoreInfo;
import com.example.ssengel.loginapp01.Common.UserInfo;
import com.example.ssengel.loginapp01.Constant.ServerURL;
import com.example.ssengel.loginapp01.Model.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ssengel on 29.04.2018.
 */

public class ServiceNotifications {

    private RequestQueue mRequestQueue;
    private Map<String, ArrayList<Notification>> mapNotifications;


    public ServiceNotifications(Context context ){
        mRequestQueue = Volley.newRequestQueue(context);
        mapNotifications = new HashMap<>();
    }

    public Map<String, ArrayList<Notification>> getNotifications(){
        getNotificationsFromServer();
        Log.e("Cevap2",mapNotifications.toString());
        return mapNotifications;
    }

    private void getNotificationsFromServer(){

        String url = ServerURL.NOTIFICATIONS+"?storeId="+ UserInfo.USER.getStoreId();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Cevap1",mapNotifications.toString());
                try {
                    if(response.getBoolean("status")) {
                        JSONArray jsonArray = response.getJSONArray("notifications");

                        for(int i=0; i< jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Notification notification = new Notification();
                            notification.setLocation(obj.getString("location"));
                            notification.setDescription(obj.getString("declaration"));
                            notification.setTitle(obj.getString("name"));
                            notification.setId(obj.getString("_id"));
                            notification.setVisited(false);

                            if(mapNotifications.containsKey(notification.getLocation())){
                                mapNotifications.get(obj.getString("location")).add(notification);
                            }else{
                                mapNotifications.put(notification.getLocation(), new ArrayList<Notification>());
                                mapNotifications.get(notification.getLocation()).add(notification);
                            }
                        }
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


}
