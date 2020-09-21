package com.example.androidyelpassesment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    VolleyClass vc;
    AutoCompleteTextView atv;
    YelpAdapter yelpAdapter;
    public static final int TRIGGER=100;
    public static final long DELAY=300;
    Handler handler;
    List<Address> addresses;
    SharedPreferences sp;
    double lat=0,longt=0;

    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        while(true)
        {
            if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                { getLocation();
                break;
                }
            }
            }

        yelpAdapter=new YelpAdapter(getApplicationContext(),android.R.layout.simple_dropdown_item_1line);

        atv=(AutoCompleteTextView)findViewById(R.id.autoTextView);

        atv.setAdapter(yelpAdapter);



        atv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                handler.removeMessages(TRIGGER);
                handler.sendEmptyMessageDelayed(TRIGGER,DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        vc=new VolleyClass(getApplicationContext(),yelpAdapter);

        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==TRIGGER)
                {
                    if(!TextUtils.isEmpty(atv.getText()))
                    {
                        try {
                            Log.d("klkklkkl",""+lat+longt);
                            vc.getLocation(atv.getText().toString(),lat+"", longt+"");
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                return true;
            }
        });
    }
    void getLocation()
    {
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        lat=location.getLatitude();
                        longt=location.getLongitude();

                    }
                }
            });
        }
        else
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }

}
