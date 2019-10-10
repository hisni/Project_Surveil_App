package com.aslam.co321_project.Distributor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aslam.co321_project.AboutUs;
import com.aslam.co321_project.Authentication.logIn;
import com.aslam.co321_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//distributor
public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView dpView;
    TextView tvUserName;
    TextView tvMail;

    static String uid;
    static DatabaseReference databaseReference;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_distributor);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvUserName = navigationView.getHeaderView(0).findViewById(R.id.distributorName);
        dpView = navigationView.getHeaderView(0).findViewById(R.id.dpDistributor);
        tvMail = navigationView.getHeaderView(0).findViewById(R.id.distributorMail);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        getParams();

        try {
            setUserDetails();
        } catch (Exception e){
            Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setUserDetails() {

        try{
            setUserImage();
        } catch (Exception e){
            setDefaultUserImage();
        }

        try{
            setUserName();
        } catch (Exception e){
            tvUserName.setText("");
        }

        try{
            setMail();
        } catch (Exception e){
            tvMail.setText("");
        }
    }

    private void setDefaultUserImage() {
    }

    private void setUserImage() {
    }

    private void setMail() {
        tvMail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    private void setUserName() {
        databaseReference.child("userInfo").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String usrNAme = dataSnapshot.child("name").getValue().toString();
                tvUserName.setText(usrNAme);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the MainActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);

            builder.setMessage("Are you sure?")
                    .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logOut();
                        }
                    })
                    .setNegativeButton("Cancel", null);

            AlertDialog alert = builder.create();
            alert.show();
            return true;
        } else if (id == R.id.action_aboutUs) {
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //handle the click event for the navigation drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setTitle("Survail Pharma");
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment, homeFragment).commit();
        } else if (id == R.id.nav_assignWork) {
            setTitle("Assign Work");
            AssignWork assignWork = new AssignWork();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment, assignWork).commit();
        } else if (id == R.id.nav_manageDistributes) {
            setTitle("Manage Distributes");
            ManageDistributes manageDistributes = new ManageDistributes();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment, manageDistributes).commit();
        } else if (id == R.id.nav_myProfile) {
            setTitle("My Profile");
            MyProfile myProfile = new MyProfile();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment, myProfile).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //this function will handle the logout process
    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Home.this, logIn.class);
        finish();
        finishAffinity();
        startActivity(intent);
    }


    //get parameters from previous activity
    private void getParams() {
        uid = getIntent().getStringExtra("uid");
    }

}
