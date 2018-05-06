package com.example.ssengel.loginapp01.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ssengel.loginapp01.Auth.AuthController;
import com.example.ssengel.loginapp01.Common.UserInfo;
import com.example.ssengel.loginapp01.Constant.ServerURL;
import com.example.ssengel.loginapp01.R;
import com.example.ssengel.loginapp01.Model.User;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


public class LoginActivity extends AppCompatActivity {

    private BluetoothManager btManager;

    private Button btnLogin;
    private EditText txtUserName;
    private EditText txtPassword;
    private TextView btnRegister;
    private ProgressBar spinner;
    private Toolbar toolbar;

    private final static String TAG  = LoginActivity.class.getName();
    private RequestQueue requestQueue;


    private void initVar(){
        //btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnRegister = (TextView) findViewById(R.id.btnRegister);
        requestQueue = Volley.newRequestQueue(this);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        toolbar = findViewById(R.id.my_toolbar);

    };

    private void initListeners(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                try {
                    if(TextUtils.isEmpty(userName)){
                        Toast.makeText(getApplicationContext(), "Kullanici adi bos gecilemez !",Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(password)){
                        Toast.makeText(getApplicationContext(), "Sifre bos gecilemez !",Toast.LENGTH_SHORT).show();
                    }else{
                        checkAccount(userName, password);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setSupportActionBar(toolbar);

        initVar();
        initListeners();

        //checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtUserName.setText("");
        txtPassword.setText("");
    }


    // Kullanici Giris Kontrolu
    private void checkAccount(String userName, String password) throws IOException, JSONException {
        spinner.setVisibility(View.VISIBLE);

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userName",userName);
        jsonParam.put("password",password);

        JsonObjectRequest req = new JsonObjectRequest(ServerURL.LOGIN, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if (!response.getBoolean("auth")) {
                        spinner.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), response.getString("message"),Toast.LENGTH_SHORT).show();
                    } else {

                        AuthController.TOKEN = response.getString("token");
                        JSONObject user = response.getJSONObject("user");
                        UserInfo.USER.setId(user.getString("_id"));
                        UserInfo.USER.setName(user.getString("name"));
                        UserInfo.USER.setLastName(user.getString("lastName"));
                        UserInfo.USER.setUserName(user.getString("userName"));
                        UserInfo.USER.setEmail(user.getString("email"));
                        UserInfo.USER.setStoreId("5addc203ab75782870a7cc14");
                        UserInfo.USER.setCompanyId("5addbc06aa4d52265447025e");


                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                        spinner.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    spinner.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spinner.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Sunucu Erisim Saglanamadi !",Toast.LENGTH_SHORT).show();
                Log.e(TAG, error.getMessage());
            }
        });
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                String bearer = "Bearer ".concat(token);
//                Map<String, String> headersSys = super.getHeaders();
//                Map<String, String> headers = new HashMap<String, String>();
//                headersSys.remove("Authorization");
//                headers.put("Authorization", bearer);
//                headers.putAll(headersSys);
//                return headers;
//            }
//        };

        requestQueue.add(req);

    }
    // Izinler
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissions() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported",
                    Toast.LENGTH_SHORT).show();
            finish();
        }

        if (btManager.getAdapter() != null && !btManager.getAdapter().isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 1);
        }


        //konum kontrolu
        if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "Ble konum verilerini almak icin konum izni gerekli..", Toast.LENGTH_SHORT).show();
            }else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }else{
            Toast.makeText(this, "Konum izni zaten verildi.", Toast.LENGTH_SHORT).show();
        }

    }
}
