package com.example.ssengel.loginapp01.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ssengel.loginapp01.R;

public class DiscountDetailActivity extends AppCompatActivity {

    private TextView txtDiscountId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_detail);
        initViews();

        //txtDiscountId.setText(getIntent().getStringExtra("discountId"));
    }

    private void initViews(){
        //txtDiscountId = findViewById(R.id.txtDiscountId);
    }
}
