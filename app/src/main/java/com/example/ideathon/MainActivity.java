package com.example.ideathon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements AdsAdapter.ItemClickHandler {

    static FirebaseDatabase firebaseDatabase = null;
    private ArrayList<Place> placeArrayList;
    private AdsAdapter vodafoneAdsAdapter, partnerAdsAdapter, locationAdsAdapter;
    private DatabaseReference vodafoneDatabaseReference, partnerDatabaseReference, locationDatabaseReference;
    private HashSet<String> stringHashSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();

        retrieveNearbyPlaces();

        Log.v("asd", "asd");

        setUpRecyclerView();

        setUpDatabase();

    }

    private void initVariables() {
        placeArrayList = new ArrayList<>();
        stringHashSet = new HashSet<>();
    }

    private void retrieveNearbyPlaces() {
        Log.v("MainActivity", "step1");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Grant Location Permissions", Toast.LENGTH_LONG).show();
            return;
        }

        PlaceDetectionClient placeDetectionClient = Places.getPlaceDetectionClient(this);

        Task<PlaceLikelihoodBufferResponse> placeTask = placeDetectionClient.getCurrentPlace(null);

        placeTask.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                PlaceLikelihoodBufferResponse responses = task.getResult();
                for (PlaceLikelihood response : responses) {
                    placeArrayList.add(response.getPlace().freeze());
                }
                Log.v("Places", "Size:" + placeArrayList.size());
                responses.release();
                responses.release();

                for (Place p : placeArrayList) {
                    stringHashSet.add(p.getName().toString().trim().toLowerCase());
                }
                setUpDatabaseReferences();
            }
        });

    }

    void setUpRecyclerView() {
        RecyclerView vodafoneAdsRecyclerView = findViewById(R.id.main_recycler_view_vodafone_ads);
        vodafoneAdsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        vodafoneAdsRecyclerView.setHasFixedSize(false);

        vodafoneAdsAdapter = new AdsAdapter(new ArrayList<Ads>());
        vodafoneAdsRecyclerView.setAdapter(vodafoneAdsAdapter);
        vodafoneAdsAdapter.setItemClickHandler(this);

        RecyclerView partnerAdsRecyclerView = findViewById(R.id.main_recycler_view_partner_ads);
        partnerAdsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        partnerAdsRecyclerView.setHasFixedSize(false);

        partnerAdsAdapter = new AdsAdapter(new ArrayList<Ads>());
        partnerAdsRecyclerView.setAdapter(partnerAdsAdapter);
        partnerAdsAdapter.setItemClickHandler(this);

        RecyclerView locationAdsRecyclerView = findViewById(R.id.main_recycler_view_location_id);
        locationAdsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        locationAdsRecyclerView.setHasFixedSize(false);

        locationAdsAdapter = new AdsAdapter(new ArrayList<Ads>());
        locationAdsRecyclerView.setAdapter(locationAdsAdapter);
        locationAdsAdapter.setItemClickHandler(this);
    }

    void setUpDatabase() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            firebaseDatabase.setPersistenceCacheSizeBytes(5* 1024 * 1024);
        }
    }

    void setUpDatabaseReferences() {
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
                    Log.v("Location", msg.getCompany());
                    if (stringHashSet.contains(msg.getCompany().trim().toLowerCase())) {
                        locationAdsAdapter.add(msg);
                        locationAdsAdapter.notifyDataSetChanged();
                    }
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

    @Override
    public void onAdClick(Ads ad) {
        Intent intent = new Intent(MainActivity.this, WebActivity.class);
        intent.putExtra(getString(R.string.url), ad.getOfferUrl());
        startActivity(intent);
    }
}
