package com.future.it.taxi.client;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.future.it.taxi.client.listener.LogoutListener;
import com.future.it.taxi.client.utils.ToastUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LogoutListener {

    @BindView(R.id.ivMenu)
    ImageView ivMenu;
    @BindView(R.id.ivPrebook)
    ImageView ivPrebook;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(15.0f);
        mMap.setMaxZoomPreference(15.0f);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(23.777176, 90.399452);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Dhaka"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @OnClick({R.id.ivMenu, R.id.ivPrebook})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMenu:
                MenusActivity.start(this, this);
                break;
            case R.id.ivPrebook:
                ToastUtils.shortToast("Prebook Coming soon...");
                break;
        }
    }

    @Override
    public void success() {
        finish();
    }
}
