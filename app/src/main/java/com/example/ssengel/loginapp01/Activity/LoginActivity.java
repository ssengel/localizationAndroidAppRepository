package com.example.ssengel.loginapp01.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ssengel.loginapp01.Constant.ServerURL;
import com.example.ssengel.loginapp01.R;
import com.example.ssengel.loginapp01.Model.User;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txtUserName;
    private EditText txtPassword;
    private TextView lnkRegister;

    private final static String TAG  = LoginActivity.class.getName();
    public final static User USER = new User();

    private RequestQueue requestQueue;


    private void initVar(){
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        lnkRegister = (TextView) findViewById(R.id.lnkRegister);
        requestQueue = Volley.newRequestQueue(this);
    };

    private void initListeners(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();

                try {
                    checkAccount(userName, password,"789789789");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        lnkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });
    }

    private void checkAccount(final String userName, String password, String deviceId) throws IOException, JSONException {

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userName",userName);
        jsonParam.put("password",password);

        JsonObjectRequest req = new JsonObjectRequest(ServerURL.LOGIN, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if(!response.getBoolean("status")){
                        Log.i(TAG, "Null geldi");
                    }else {

                        USER.setId(response.getString("_id"));
                        USER.setName(response.getString("name"));
                        USER.setLastName(response.getString("lastName"));
                        Log.i(TAG, "GIRIS BASARILI..");

                        //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                        LoginActivity.this.startActivity(intent);
                        
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        });

        requestQueue.add(req);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVar();
        initListeners();
    }
}
