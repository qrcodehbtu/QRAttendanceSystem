package com.example.qrattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentHomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private  Sharedpref pref;
    private  ImageView studentqr;
    private Bitmap qrbitmap=null;
    private  TextView userNameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        studentqr = (ImageView) findViewById(R.id.qr_code);
        pref = new Sharedpref(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("HOME");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
       userNameTextView = headerView.findViewById(R.id.profilename);
        CircleImageView profileImageView = headerView.findViewById(R.id.profile_image);
        HashMap<String, String> user = pref.getuserdetails();
        String name = user.get(Sharedpref.KEY_NAME);
        String year = user.get(Sharedpref.KEY_year);
        String roll =user.get(Sharedpref.KEY_rollno);

        showqrcodeandname(name,year,roll);

    }

    private void showqrcodeandname(String name, String year, String roll) {
        QRCodeGen qrgen = new QRCodeGen(200,200);
        qrbitmap = qrgen.generateQRCode(roll);
        studentqr.setImageBitmap(qrbitmap);
        qrbitmap = null;

        if(year.equals("1"))
            year+="st Year";
        if(year.equals("2"))
            year+="nd Year";
        if(year.equals("3"))
            year+="rd Year";
        if(year.equals("4"))
            year+="Final Year";
        userNameTextView.setText(name+" || " + year);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_qr_id) {

        } else if (id == R.id.nav_attendance) {

        }
        else if(id==R.id.nav_profile)
        {

        }
        else if (id == R.id.nav_setting) {

        }
        else if (id == R.id.nav_logout) {
            pref.logoutUser();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}