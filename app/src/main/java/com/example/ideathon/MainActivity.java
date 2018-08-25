package com.example.ideathon;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 432;
    private AdsAdapter vodafoneAdsAdapter, partnerAdsAdapter, locationAdsAdapter;
    private DatabaseReference vodafoneDatabaseReference, partnerDatabaseReference, locationDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView vodafoneAdsRecyclerView = findViewById(R.id.main_recycler_view_vodafone_ads);
        vodafoneAdsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        vodafoneAdsRecyclerView.setHasFixedSize(false);

        vodafoneAdsAdapter = new AdsAdapter(new ArrayList<Ads>());
        vodafoneAdsRecyclerView.setAdapter(vodafoneAdsAdapter);

        RecyclerView partnerAdsRecyclerView = findViewById(R.id.main_recycler_view_partner_ads);
        partnerAdsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        partnerAdsRecyclerView.setHasFixedSize(false);

        partnerAdsAdapter = new AdsAdapter(new ArrayList<Ads>());
        partnerAdsRecyclerView.setAdapter(partnerAdsAdapter);

        RecyclerView locationAdsRecyclerView = findViewById(R.id.main_recycler_view_location_id);
        locationAdsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        locationAdsRecyclerView.setHasFixedSize(false);

        locationAdsAdapter = new AdsAdapter(new ArrayList<Ads>());
        locationAdsRecyclerView.setAdapter(locationAdsAdapter);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        vodafoneDatabaseReference = firebaseDatabase.getReference().child("vodafoneAds");
        setUpVodafoneDatabase();

        partnerDatabaseReference = firebaseDatabase.getReference().child("partnerAds");
        setUpPartnerDatabase();

        locationDatabaseReference = firebaseDatabase.getReference().child("locationAds");
        setUpLocationDatabase();
    }

    void setUpVodafoneDatabase() {
        vodafoneDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Ads msg = dataSnapshot.getValue(Ads.class);
                if (msg != null) {
                    vodafoneAdsAdapter.add(msg);
                    vodafoneAdsAdapter.notifyDataSetChanged();
                } else {
                    Log.v("MainActivity", "msg is null");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void setUpPartnerDatabase() {
        partnerDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Ads msg = dataSnapshot.getValue(Ads.class);
                if (msg != null) {
                    partnerAdsAdapter.add(msg);
                    partnerAdsAdapter.notifyDataSetChanged();
                } else {
                    Log.v("MainActivity", "msg is null");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void setUpLocationDatabase() {
        locationDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Ads msg = dataSnapshot.getValue(Ads.class);
                if (msg != null) {
                    locationAdsAdapter.add(msg);
                    locationAdsAdapter.notifyDataSetChanged();
                } else {
                    Log.v("MainActivity", "msg is null");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    void requestLocationPermission(int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }


    void onExit(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(s)
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    onExit("Location Permission not granted");
                }
        }

    }
}
