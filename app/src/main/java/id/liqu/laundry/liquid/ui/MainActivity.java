package id.liqu.laundry.liquid.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import butterknife.OnClick;
import id.liqu.laundry.liquid.R;



public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    SupportMapFragment sMapFragment;
    //TextInputEditText date, date2, time, time2, location;
    FirebaseAuth mAuth;
    String as, ad;
    Uri url;
    private EditText  date, date_2, time, time_2, location, notes;
    private Button submit;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        sMapFragment = SupportMapFragment.newInstance();
        database = FirebaseDatabase.getInstance().getReference();
        FragmentManager fm = getFragmentManager();
        final android.support.v4.app.FragmentManager sfm = getSupportFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sMapFragment.getMapAsync(this);

        //tanggal
        date = (TextInputEditText) findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        date_2 = (TextInputEditText) findViewById(R.id.date_2);
        date_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog2(v);
            }
        });

        time = (TextInputEditText) findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime(v);
            }
        });

        time_2 = (TextInputEditText) findViewById(R.id.time_2);
        time_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime2(v);
            }
        });

        location = (TextInputEditText) findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sMapFragment.isAdded()){
                    sfm.beginTransaction().hide(sMapFragment).commit();
                }
                else startActivity(new Intent(MainActivity.this, MapsActivity.class));
                    if (sMapFragment.isAdded())
                    sfm.beginTransaction().add(R.id.map, sMapFragment).commit();
                else
                    sfm.beginTransaction().show(sMapFragment).commit();
            }
        });

        //getData
        date = (TextInputEditText) findViewById(R.id.date);
        date_2 = (TextInputEditText) findViewById(R.id.date_2);
        time = (TextInputEditText) findViewById(R.id.time);
        time_2 = (TextInputEditText) findViewById(R.id.time_2);
        location = (TextInputEditText) findViewById(R.id.location);
        notes = (TextInputEditText) findViewById(R.id.notes);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order o = new Order();
                o.setDateP(date.getText().toString());
                o.setTimeP(time.getText().toString());
                o.setLocation(location.getText().toString());
                o.setNotes(notes.getText().toString());
                o.setDateD(date_2.getText().toString());
                o.setTimeD(time_2.getText().toString());

                database.child("order").push().setValue(o).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.submit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                    }
                });

            }
        });


    }



    @Override
    public int layoutId() {
        return R.layout.activity_main;
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
        getMenuInflater().inflate(R.menu.main, menu);
        Log.d("pos: ","oncreate option menu");
        ImageView foto = (ImageView) findViewById(R.id.imageView);
        TextView nama = (TextView) findViewById(R.id.nav_nama);
        TextView email = (TextView) findViewById(R.id.nav_email);

        FirebaseUser user = mAuth.getCurrentUser();
        url = user.getPhotoUrl();
        as = user.getDisplayName();
        ad = user.getEmail();
        Log.d("isinya : ",url.toString());
        if(url != null) {
            Picasso.with(this).load(url).into(foto);
            nama.setText(as);
            email.setText(ad);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("pos: ","option item selected");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Log.d("pos: ","on navigation item selected");

        int id = item.getItemId();



        if (id == R.id.profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
        else if (id == R.id.laundry) {


        } else if (id == R.id.logout){
            Hawk.deleteAll();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    

    @OnClick(R.id.submit)
    void submit(){
        startActivity(new Intent(this, PricingActivity.class));
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showDatePickerDialog2(View v) {
        DialogFragment newFragment = new DatePickerFragment2();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTime(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timepiker");

    }

    public void showTime2(View v){
        DialogFragment newFragment = new TimePickerFragment2();
        newFragment.show(getSupportFragmentManager(), "timepiker");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
