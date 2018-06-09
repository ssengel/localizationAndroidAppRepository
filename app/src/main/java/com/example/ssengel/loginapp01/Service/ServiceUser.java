package com.example.ssengel.loginapp01.Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ssengel.loginapp01.Activity.LoginActivity;
import com.example.ssengel.loginapp01.Activity.MainActivity;
import com.example.ssengel.loginapp01.Common.UserInfo;
import com.example.ssengel.loginapp01.Constant.ServerURL;
import com.example.ssengel.loginapp01.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ssengel on 07.05.2018.
 */

public class ServiceUser {

    private RequestQueue requestQueue;
    private User user;
    private Context mContext;
    private ProgressBar mSpinner;

    public ServiceUser(Context context, ProgressBar spinner){
        mContext = context;
        requestQueue = Volley.newRequestQueue(context);
        mSpinner = spinner;
    }

    private void checkFields(){

    }

    public void checkUserAccount(String userName, String password){
        mSpinner.setVisibility(View.VISIBLE);

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("userName",userName);
            jsonParam.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(ServerURL.LOGIN, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if (!response.getBoolean("auth")) {

                        mSpinner.setVisibility(View.GONE);
                        Toast.makeText(mContext, response.getString("message"),Toast.LENGTH_SHORT).show();
                    } else {

                        //AuthController.TOKEN = response.getString("token");
                        JSONObject user = response.getJSONObject("user");
                        UserInfo.USER.setId(user.getString("_id"));
                        UserInfo.USER.setName(user.getString("name"));
                        UserInfo.USER.setLastName(user.getString("lastName"));
                        UserInfo.USER.setUserName(user.getString("userName"));
                        UserInfo.USER.setEmail(user.getString("email"));
                        UserInfo.USER.setStoreId("5addc203ab75782870a7cc14");
                        UserInfo.USER.setCompanyId("5addbc06aa4d52265447025e");

                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                        mSpinner.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    mSpinner.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSpinner.setVisibility(View.GONE);
                Toast.makeText(mContext, "Sunucu Erisimi Saglanamadi !",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(req);

    }
}
