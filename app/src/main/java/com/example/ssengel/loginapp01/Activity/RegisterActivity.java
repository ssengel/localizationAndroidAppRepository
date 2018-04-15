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

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = RegisterActivity.class.getName();

    private Button btnRegister;
    private EditText txtName;
    private EditText txtLastName;
    private EditText txtAge;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtUserName;
    private TextView txtStatus;

    private RequestQueue requestQueue;

    private void initVars(){
        btnRegister = (Button) findViewById(R.id.btnRegister);
        txtName = (EditText) findViewById(R.id.txtName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtAge = (EditText) findViewById(R.id.txtAge);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtStatus = (TextView) findViewById(R.id.txtStatus);

        requestQueue = Volley.newRequestQueue(this);
    }

    private void initListeners(){


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "onClick: " );

                User user = new User();
                user.setName(txtName.getText().toString());
                user.setLastName(txtLastName.getText().toString());
                user.setAge(Integer.parseInt(txtAge.getText().toString()));
                user.setEmail(txtEmail.getText().toString());
                user.setUserName(txtUserName.getText().toString());
                user.setPassword(txtPassword.getText().toString());
                try {
                    registerUser(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void registerUser(User user) throws JSONException {

        Log.e(TAG, "onClick:2 " );

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("name",user.getName());
        jsonParam.put("lastName",user.getLastName());
        jsonParam.put("age",user.getAge());
        jsonParam.put("email",user.getEmail());
        jsonParam.put("userName",user.getUserName());
        jsonParam.put("password",user.getPassword());


        JsonObjectRequest req = new JsonObjectRequest(ServerURL.REGISTER, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Yazi RENGI YESIL OLMALI

                try {
                    txtStatus.setText("GIRIS BASARALI.. Email`inize dogrulama kodu gonderilmistir");
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                };
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(req);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initVars();
        initListeners();
    }
}
