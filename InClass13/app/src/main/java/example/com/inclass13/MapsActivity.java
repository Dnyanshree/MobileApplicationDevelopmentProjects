package example.com.inclass13;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    LocationManager mLocationMgr;
    LocationListener mLocListener;
    // double latitude,longitude;
    private GoogleMap mMap;
    private String[] places = {"bakery", "book_store", "university", "gas_station", "cafe", "restaurant"};
  //  private int[] placesID = {7, 12, 94, 41, 15, 79};
    int selectedPlace = 0;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Place.TYPE_GAS_STATION

        //  mLocationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        AlertDialog.Builder placesAlert = new AlertDialog.Builder(this);
        placesAlert.setTitle(R.string.place_type_title)
                .setItems(places, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Demo", places[which]);
                        selectedPlace = which;
                        Log.d("Demo Selected", String.valueOf(selectedPlace));
                        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                                .getCurrentPlace(mGoogleApiClient, null);
                        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                            @Override
                            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                                    Log.i("Demo", String.format("Place '%s' has likelihood: %g",
                                            placeLikelihood.getPlace().getName(),
                                            placeLikelihood.getLikelihood()));
                                    if((placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_BAKERY) && selectedPlace ==0)||
                                            (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_BOOK_STORE) && selectedPlace ==1)||
                                            (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_UNIVERSITY) && selectedPlace ==2)
                                            ||(placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_GAS_STATION) && selectedPlace == 3) ||
                                            (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_CAFE) && selectedPlace ==4)||
                                            (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_RESTAURANT) && selectedPlace ==5))
                                        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                                .position(placeLikelihood.getPlace().getLatLng()).title(placeLikelihood.getPlace().getName()+""));
                                    else
                                        mMap.addMarker(new MarkerOptions().position(placeLikelihood.getPlace().getLatLng()).
                                                title(placeLikelihood.getPlace().getName()+"")); }
                                LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                                boundsBuilder.include(likelyPlaces.get(0).getPlace().getLatLng());
                                boundsBuilder.include(likelyPlaces.get(likelyPlaces.getCount() - 1).getPlace().getLatLng());
// pan to see all markers on map:
                                LatLngBounds bounds = boundsBuilder.build();
                                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
                                likelyPlaces.release();
                            }
                        });

                    }
                }).setCancelable(false);

        AlertDialog alert = placesAlert.create();
        alert.show();

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
      //  LatLng currentLoc = new LatLng(latitude,longitude);
    //   mMap.addMarker(new MarkerOptions().position(currentLoc).title("Marker Current Location"));
    // mMap.animateCamera(CameraUpdateFactory.newLatLng(currentLoc));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        mLocListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d("Demo", location.getLatitude() + " ," + location.getLongitude());
                    latitude=location.getLatitude();
                            longitude=location.getLongitude();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, mLocListener);

        }
        */
    }

