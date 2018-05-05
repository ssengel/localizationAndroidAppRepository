package com.example.ssengel.loginapp01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DiscountDetailActivity extends AppCompatActivity {

    private TextView txtDiscountId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_detail);
        initViews();

        Intent intent = getIntent();
        System.out.println(intent.getStringExtra("discountId"));
        txtDiscountId.setText(intent.getStringExtra("discountId"));
    }

    private void initViews(){
        txtDiscountId = findViewById(R.id.txtDiscountId);
    }
}
