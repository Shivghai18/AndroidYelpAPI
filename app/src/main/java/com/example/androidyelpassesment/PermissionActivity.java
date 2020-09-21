package com.example.androidyelpassesment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PermissionActivity extends AppCompatActivity {
    List<Address> addresses;

    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        ActivityCompat.requestPermissions(PermissionActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);

        if(ActivityCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(PermissionActivity.this, Locale.getDefault());

                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            try {
                                Log.d("msg1o", location.getLatitude() + "," + location.getLongitude() + "" + addresses.get(0).getCountryName());
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }
        else
        {

        }
    }

    public void getLoaction(View view) {
    }
}
