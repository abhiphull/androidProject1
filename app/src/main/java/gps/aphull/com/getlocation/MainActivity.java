package gps.aphull.com.getlocation;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends Activity {

    TextView lati;
    TextView longi;

    DataBase dbase;
    SQLiteDatabase dbw,dbr;
    String[] projection = {
            "_id",
            "long",
            "lat",
    };
    List<Long> l = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Database instantiation
        dbase = new DataBase(getApplicationContext());
        dbw = dbase.getWritableDatabase();
        dbr = dbase.getReadableDatabase();
        lati = (TextView) findViewById(R.id.lati);
        longi = (TextView) findViewById(R.id.longi);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new myLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("skipping", "onCreate: ");
            return;
        } else {
            Log.d("now", "onCreate: ");

        }
        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);

    }
    class myLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location!=null) {
                Log.d("location", "onCreate: ");
                double plong = location.getLongitude();
                double plat = location.getLatitude();
                lati.setText(Double.toString(plat));
                longi.setText(Double.toString(plong));
                ContentValues values = new ContentValues();
                values.put("long", plong);
                values.put("lat", plat);
                try {
                    dbw.insert("coord", null, values);
                    Log.d("successfully", "added: coordinates");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Cursor c = dbr.query(
                            "coord",  // The table to query
                            projection,                               // The columns to return
                            null,                                // The columns for the WHERE clause
                            null,                            // The values for the WHERE clause
                            null,                                     // don't group the rows
                            null,                                     // don't filter by row groups
                            null// The sort order
                    );
                    if (c.moveToFirst()) {
                        do {
                            long itemId = c.getLong(
                                    c.getColumnIndexOrThrow("_id")
                            );
                            Log.d("readingdb", Long.toString(itemId));
                        } while (c.moveToNext());
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
