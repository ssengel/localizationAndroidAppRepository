package com.example.ssengel.loginapp01.Activity;


import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ssengel.loginapp01.Adapter.DiscountAdapter;
import com.example.ssengel.loginapp01.Common.StoreInfo;
import com.example.ssengel.loginapp01.Constant.ServerURL;
import com.example.ssengel.loginapp01.Model.BLEScannerImpl;
import com.example.ssengel.loginapp01.Model.GeneralDiscount;
import com.example.ssengel.loginapp01.Model.NotificationHelper;
import com.example.ssengel.loginapp01.R;
import com.example.ssengel.loginapp01.Service.ServiceNotifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView recyclerView;
    private DiscountAdapter discountAdapter;
    private List<GeneralDiscount> discountList;
    private BluetoothManager btManager;
    private BLEScannerImpl mBLEScannerImpl;
    private ServiceNotifications serviceNotifications;

    private void init(){

        serviceNotifications = new ServiceNotifications(this);
        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBLEScannerImpl = new BLEScannerImpl(btManager, this);
        //notiHelper = new NotificationHelper(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGeneralDiscount);
        discountList = new ArrayList<>();
        discountAdapter = new DiscountAdapter(this,discountList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(discountAdapter);

        for (int i= 1 ;i < 5; i++){
            GeneralDiscount discount = new GeneralDiscount();
            discount.setTag("Boyner Magazalarinda Indirim");
            discount.setPrice( 50);
            discount.setDiscountRate((i * 6));
            discount.toString();
            discountList.add(discount);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();

        // Magazaya ait bildirimleri getir
        StoreInfo.MAP_NOTIFICATIONS = serviceNotifications.getNotifications();

        Log.e("Notification Service",serviceNotifications.getNotifications().toString());
        Log.e("StoreInfo",StoreInfo.MAP_NOTIFICATIONS.toString());

        //kullanicinin ilgilerinin getir.

        //Taramayi baslat
        mBLEScannerImpl.scanLeDevice("FAST");
        mBLEScannerImpl.updatelistRssi();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.discounts) {

        } else if (id == R.id.myOrders) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
